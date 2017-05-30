/**
 * A class to represent lat long points with helpful methods for distance and bearing
 * @author Reid Spence
 * @date 4/28/2016
 * @version 1.0
 */
public class Location {
	double lat,lon;
	
	public Location (double lat,double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	/**
	 * calculates distance traveled between two points
	 * using Haversine distance; 
	 * @param l2 the distance will be found to this point
	 * @return double distance traveled between points
	 */
	public double distanceH(Location l2) {
		double lat1,lat2,lon1,lon2,dLat,dLon,a,c,dkm;
		lat1 = this.lat;
		lon1 = this.lon;
		lat2 = l2.lat;
		lon2 = l2.lon;
		dLat = Math.toRadians(lat2 - lat1);
        dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        c = 2 * Math.asin(Math.sqrt(a));
        return 6372.8 * c * 1000;
	}
	
	/**
	 * Returns the bearing angle from this location to the input location
	 * @param l2
	 * @return bearing angle (degrees)
	 */
	public double bearing(Location l2){
		double lat1 = this.lat;
		double lon1 = this.lon;
		double lat2 = l2.lat;
		double lon2 = l2.lon;
		double longDiff= lon2-lon1;
		double y = Math.sin(longDiff)*Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(longDiff);
		return Math.toDegrees((Math.atan2(y, x))+360)%360;
		}
	
	public String toString() {
		return lat + "," + lon;
	}

}
