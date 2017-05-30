import java.util.ArrayList;

/**
 * a class to operate on the nearest neighbors to a query. This is to create features that are used by the parking classifier
 * @author Reid Spence
 * @date 4/9/2016
 * @version 1.0
 */
public class NearestNeighbors {
	public ArrayList<Location> neighbors;
	public Location query;
	
	/**
	 * constructor takes in a query location and a list of its nearest MFM points
	 * @param neighbors
	 * @param query
	 */
	public NearestNeighbors(ArrayList<Location> neighbors, Location query) {
		this.neighbors = neighbors;
		this.query = query;
	}
	
	/**
	 * the average distance of the neighbors from the query
	 * @return avgDist
	 */
	public double averageDistance() {
		if (this.size() < 1) return 0;
		double sum = 0;
		double total = 0;
		for (Location loc : neighbors) {
			sum += query.distanceH(loc);
			total++;
		}
		return sum/total;
	}
	
	/**
	 * the variance of the distance of the neighbors from the query
	 * @return varDist
	 */
    double varianceDistance()
    {
    	if (this.size() < 1) return 0;
    	double mean = this.averageDistance();
        double sum = 0;
        double total = 0;
        for(Location loc : neighbors) {
        	double b = query.distanceH(loc);
        	sum += (mean-b)*(mean-b);
        	total++;
        }
        return sum/total;
    }
	
    /**
	 * the average heading of the neighbors from the query
	 * @return avgheading
	 */
	public double averageHeading() {
		if (this.size() < 1) return 0;
		double sumsin = 0;
		double sumcos = 0;
		for (Location loc : neighbors) {
			double theta = query.bearing(loc);
			sumsin += Math.sin(theta*3.14/180) ;
			sumcos += Math.cos(theta*3.14/180) ;
		}
		return Math.atan(sumsin/sumcos);
	}
	
	/**
	 * the variance of the heading of the neighbors from the query
	 * @return varheading
	 */
    double varianceHeading()
    {
    	if (this.size() < 1) return 0;
    	double sin = 0;
        double cos = 0;
        for(Location loc : neighbors){
        	double theta = query.bearing(loc);
             sin += Math.sin(theta * (Math.PI/180.0));
             cos += Math.cos(theta * (Math.PI/180.0)); 
        }
        sin /= this.size();
        cos /= this.size();
        return Math.sqrt(-Math.log(sin*sin+cos*cos));
    }
    
    /**
     * @return the number of nearest neighbors
     */
    public int size() {
    	return neighbors.size();
    }
}
