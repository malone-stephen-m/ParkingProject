/**
 * This Class represents a decision tree style classifier for
 * sorting out valid and invalid parking spots
 * that was developed using Weka Machine Learning Software.
 * @author Reid Spence
 * @date 5/10/2016
 * @version 1.0
 *
 */
public class ParkingDTreeClassifier {
	private MFMparkCheck mfm;
	
	/**
	 * constructor takes in MFMparkCheck class
	 * @param mfm
	 */
	public ParkingDTreeClassifier(MFMparkCheck mfm) {
		this.mfm = mfm;
	}
	
	/**
	 * send lat lon of query point and classifier will estimate its validity
	 * @param lat
	 * @param lon
	 * @return true if valid parking spot, else false
	 */
	public boolean classify(double lat,double lon) {
		//Create atrributes based on MFM info
		Location query = new Location(lat,lon);
		NearestNeighbors nn20 = new NearestNeighbors(mfm.getNearestNeighbors(query, 20),query);
		NearestNeighbors nn7 = new NearestNeighbors(mfm.getNearestNeighbors(query, 7),query);
		int numPoints20m = nn20.size();
		int numPoints7m = nn7.size();
		double avgDist = nn20.averageDistance();
		double varDist = nn20.varianceDistance();
		double varHeading = nn20.varianceHeading();
		
		
		//Decision Tree represented as If Statements
		if (varDist <= 17.5137) {
			if (numPoints20m <= 35) {
				if (numPoints7m <= 0) return false;
				else {
					if (avgDist <= 10.926) {
						if (varDist <= 8.1697) {
							if(numPoints20m <= 13) return true;
							return false;
						} else return true;
					} else {
						if (numPoints20m <= 30) return false;
						return true;
					}
				}
			} else {
				if (avgDist <= 6.274) {
					if (numPoints20m <= 55) return true;
					return false;
				} else {
					if (varDist <= 16.257) return false;
					if (numPoints7m <= 12) {
						if (avgDist <= 12.294) {
							if (varDist <= 16.374) return true;
							return false;
						}
						return true;
					}
					return true;
				}
			}
		}
		return true;
	}
}
