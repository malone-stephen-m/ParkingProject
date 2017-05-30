import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * a class that utilizes confidence weighting on historical MFM data to estimate the validity of a given spot as a real parking spot
 * @author Reid Spence
 * @date 4/28/2016
 * @version 1.0
 */
public class MFMparkCheck {
	//MFM data storage
	ArrayList<Location> [][] tripEnds;
	int factor,lowlat,lowlon;
	//search Parameters
	double maxdistance, threshold, normalizeConstant;
	
	/**
	 * The constructor for this class takes in the absolute
	 * directory for the MFM points. The MFM file is just a list 
	 * of points with first col being lat and second col being long
	 * @param MFMfilename
	 * @throws FileNotFoundException
	 */
	public MFMparkCheck(String MFMfilename) throws FileNotFoundException{
		int decimal = 4;
		this.factor = (int) Math.pow(10,decimal);
		ArrayList<Double> latList = new ArrayList<Double>();
		ArrayList<Double> lonList = new ArrayList<Double>();
		//read the trip ends data file
		Scanner scan = new Scanner(new File(MFMfilename));
		while (scan.hasNext()) {
			String[] data = scan.nextLine().split(",");
			latList.add(Double.parseDouble(data[0]));
			lonList.add(Double.parseDouble(data[1]));
		}
		//calculated min and max lat long
		double minlat = Collections.min(latList);
		double maxlat = Collections.max(latList);
		double minlon = Collections.min(lonList);
		double maxlon = Collections.max(lonList);
		//create 2D array of location buckets
		this.lowlat = (int) Math.round(minlat*factor);
		this.lowlon = (int) Math.round(minlon*factor);
		int numrows = (int) (Math.round(maxlat*factor) -  lowlat);
		int numcols = (int) (Math.round(maxlon*factor) -  lowlon);
		tripEnds = (ArrayList<Location>[][]) new ArrayList [numrows+1][numcols+1] ;
		// add data to buckets
		int j = 0;
		for (double lat : latList) {
			double lon = lonList.get(j);
			int row = (int) (Math.round(lat*factor)) - lowlat;
			int col = (int) (Math.round(lon*factor)) - lowlon;
			if (tripEnds[row][col] == null) {
				tripEnds[row][col] = new ArrayList<Location>();
			}
			tripEnds[row][col].add(new Location(lat,lon));
			j++;
		}
		
		//set default parameters
		this.maxdistance = 10;
		this.threshold = maxdistance + 1;
		this.normalizeConstant = 3;
	}
	
	/**
	 * this is used to change the paramters in the confidence 
	 * weighting algorithm. maxdistance is the search radius for
	 * mfm points. threshold is the threshold of confidence value
	 * for which the algorithm returns true. NormalizingConstate is
	 * a factor of how much confidence is disconted do to a high
	 * number of nearby points 
	 * @param maxdistance
	 * @param threshold
	 * @param normalizeConstant
	 */
	public void setParameters(double maxdistance, double threshold, double normalizeConstant) {
		this.maxdistance = maxdistance;
		this.threshold = threshold;
		this.normalizeConstant = normalizeConstant;
	}
	
	/**
	 * Used to send a query to the confidence weighting algorithm
	 * to see if a given spot is valid parking
	 * @param lat
	 * @param lon
	 * @return if parking spot is valid then true
	 */
	public boolean isValidParking(double lat, double lon) {
		Location query = new Location(lat, lon);
		//find bucket index
		int row = (int) (Math.round(lat*factor)) - lowlat;
		int col = (int) (Math.round(lon*factor)) - lowlon;
		//search points
		double confidence = 0;
		double numPoints = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j ++) {
				if(tripEnds[row+i][col+j] == null) {continue;}
				for (Location loc : tripEnds[row+i][col+j]) {
					double distance = query.distanceH(loc);
					if (distance < maxdistance) {
						confidence += maxdistance - distance;
					}
				}
			numPoints++;
			}
		}
		threshold+= numPoints/normalizeConstant;
		//return true if confidence above threshold
		if (confidence > threshold) {
			return true;
		}
		return false;
	}
	
	/**
	 * returns the MFM trips ends within a certain radius of a location
	 * @param lat
	 * @param lon
	 * @param radius
	 * @return
	 */
	public ArrayList<Location> getNearestNeighbors(Location query, double radius) {
		ArrayList<Location> neighbors = new ArrayList<Location>();
		//find bucket index
		int row = (int) (Math.round(query.lat*factor)) - lowlat;
		int col = (int) (Math.round(query.lon*factor)) - lowlon;
		//search points
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j ++) {
				if(tripEnds[row+i][col+j] == null) {continue;}
				for (Location loc : tripEnds[row+i][col+j]) {
					double distance = query.distanceH(loc);
					if (distance < radius) {
						neighbors.add(loc);
					}
				}
			}
		}
		return neighbors;
	}
	
	
	
}
	
	
