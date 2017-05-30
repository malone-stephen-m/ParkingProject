import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;


public class InrixReader {
	public ArrayList<InrixTripLL> readCSV(String fileName) throws FileNotFoundException{
		ArrayList<InrixTripLL> Trips = new ArrayList<InrixTripLL>();
		Scanner scanner = new Scanner(new File(fileName));
	    String line = scanner.nextLine();
	    String nextline;
	    InrixTripLL curTrip = new InrixTripLL();
	    while(scanner.hasNext()){
	    	nextline = scanner.nextLine();
	    	String[] row = line.split(",");
	    	String[] nextrow = nextline.split(",");
	    	curTrip.add(row);
	    	if (!row[5].equals(nextrow[5])) {
	    		Trips.add(curTrip.deepCopy());
	    		curTrip.clear();
	    	}
	    	line = nextline;
	    }
	    scanner.close();
	    return Trips;
	}
	public ArrayList<InrixTripAR> readCSVAR(String fileName) throws FileNotFoundException{
		ArrayList<InrixTripAR> Trips = new ArrayList<InrixTripAR>();
		Scanner scanner = new Scanner(new File(fileName));
	    String line = scanner.nextLine();
	    String nextline;
	    InrixTripAR curTrip = new InrixTripAR();
	    while(scanner.hasNext()){
	    	nextline = scanner.nextLine();
	    	String[] row = line.split(",");
	    	String[] nextrow = nextline.split(",");
	    	curTrip.add(row);
	    	if (!row[5].equals(nextrow[5])) {
	    		Trips.add(curTrip.deepCopy());
	    		curTrip.clear();
	    	}
	    	line = nextline;
	    }
	    scanner.close();
	    return Trips;
	}
	public ArrayList<InrixTripLL> testCSV(String fileName) throws IOException{
		ArrayList<InrixTripLL> Trips = new ArrayList<InrixTripLL>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
		Scanner scanner = null;
 
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                System.out.print(Float.parseFloat(data) + ",");
            }
            System.out.print("\n");
        }
	    scanner.close();
	    reader.close();
	    return Trips;
	}
	public Hashtable<Double,InrixTripLL> readCSVhashtable(String fileName) throws FileNotFoundException {
		Hashtable<Double,InrixTripLL> trips = new Hashtable<Double,InrixTripLL>();
		Scanner scanner = new Scanner(new File(fileName));
	    String line = scanner.nextLine();
	    String nextline;
	    InrixTripLL curTrip = new InrixTripLL();
	    while(scanner.hasNext()){
	    	nextline = scanner.nextLine();
	    	String[] row = line.split(",");
	    	String[] nextrow = nextline.split(",");
	    	curTrip.add(row);
	    	if (!row[5].equals(nextrow[5])) {
	    		trips.put(curTrip.head.ID,curTrip.deepCopy());
	    		curTrip.clear();
	    	}
	    	line = nextline;
	    }
	    scanner.close();
	    return trips;
	}
}