import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A class to make running MFM quiries using Overpass API easier by not having to mess with the underlying JSON queries and data
 * @author Stephen Malone, Comments by Reid Spence
 * @date 2015
 * @version 1.0
 */
public class OSM_Handle {

	//This list holds the sum of all queries added
	List<String> Queries = new ArrayList<String>();
	private static final String OVERPASS_API = "http://www.overpass-api.de/api/interpreter";
	private static final String OPENSTREETMAP_API_06 = "http://www.openstreetmap.org/api/0.6/";
	
	/**
	 *  Once you have created and formated a query. Use this method to send the
	 *  query to the server for which you will pass the URL. It will return a results file
	 * note: if no personal osm server set up, use "http://www.overpass-api.de/api/interpreter"
	 * as the url
	 * @param URL for the server
	 * @param xmlQuery from formatQuery()
	 * @return a document containing the results of the query
	 * @throws Exception
	 */
	public Document SendandReceive(String URL,File xmlQuery) throws Exception{

		//Initialize URL
		URL url = new URL(URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		//Set Parameters of Connection
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/xml");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		//Ready and post XML string to post from file
		String xml = convertXMLFileToString(xmlQuery.getAbsolutePath());
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(xml);
		wr.flush();
		wr.close();
		
		//Read response from server
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response1.append(inputLine);
		}
		in.close();
		
		//Convert response from server into document for analysis
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		return docBuilder.parse(new InputSource(new StringReader(response1.toString())));

		
	}
    
	/**
	 * use this method to search a results file for a certain tag like "highway"
	 * @param result
	 * @param valuetosearch
	 * @return if tag is present
	 */
	public boolean contains(Document result, String valuetosearch) {
		//Get nodes from URL POST Method and interpret results to find a match
		List<String> nodes = getNodes(result);
        for (String string : nodes) {
            if(string.matches(valuetosearch)){
                return true;
            }
        }
		return false;
	}
	
	/**
	 * returns if results were empty
	 * @param result
	 * @return true if empty
	 */
	public boolean isEmpty(Document result) {
		//Get nodes from URL POST Method and interpret results to find a match
		List<String> nodes = getNodes(result);
        if (nodes.isEmpty()) {return true;}
		return false;
	}
	
	/**
	 * Internal class for converting queries.
	 * @param fileName
	 * @return
	 */
	public String convertXMLFileToString(String fileName) 
	{ 
	   try{ 
		   //Takes an input file and converts it to a string for querying server
	       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
	       InputStream inputStream = new FileInputStream(new File(fileName)); 
	       org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
	       StringWriter stw = new StringWriter(); 
	       Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
	       serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
	       return stw.toString(); 
	   } 
	   catch (Exception e) { 
	       e.printStackTrace(); 
	   } 
	   return null; 
	}
	
	/**
	 * this returns the nodes of a result in string format
	 * @param xmlDocument
	 * @return
	 */
	public static List<String> getNodes(Document xmlDocument) {
		List<String> osmWays = new ArrayList<String>();
		// Search XML Doc for Way Values
		Node osmRoot = xmlDocument.getFirstChild();
		NodeList osmXMLWays = osmRoot.getChildNodes();
		for (int i = 1; i < osmXMLWays.getLength(); i++) {
			Node item = osmXMLWays.item(i);
			//Cycle through ways to find tag values desired
			if (item.getNodeName().equals("way")) {
				NodeList tagXMLNodes = item.getChildNodes();
				for (int j = 1; j < tagXMLNodes.getLength(); j++) {
					Node tagItem = tagXMLNodes.item(j);
					NamedNodeMap tagAttributes = tagItem.getAttributes();
					if (tagAttributes != null) {
						int numAttr = tagAttributes.getLength();
						for (int k = 0; k < numAttr; k++){
							Attr attr = (Attr) tagAttributes.item(k);
							String attrname = attr.getNodeName();
							String attrval = attr.getNodeValue();
							if (!attrname.equals("ref")){
								//Add value results to list for further analysis
								osmWays.add(attrval);
							}
						}
						
					}
				}
			}

		}
		return osmWays;
	}
	
	/**
	 * internal class for finding the lat long points of a bounding box
	 * given a certain radius and center
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	public String boundingboxgen(double latitude, double longitude, double radius){		
		//Constant earth rad (km) and convert from deg to rad
		double radiusofearth = 6371.01;
		double radLat=(latitude*Math.PI)/180;
		double radLong=(longitude*Math.PI)/180;
		double radDist = radius/radiusofearth;
		
		//Radian Limits
		double MIN_LAT = -Math.PI/2;
		double MAX_LAT = Math.PI/2;
		double MIN_LONG = -Math.PI;
		double MAX_LONG = Math.PI;

		//Add dist to lat long to figure out if in north or south hemishphere
		double minLat = radLat - radDist;
		double maxLat = radLat + radDist;
		double minLong = MIN_LONG;
		double maxLong = MAX_LONG;
		
		//Deal with Southern/Northern Hemisphere Coordinates
		if (minLat > MIN_LAT && maxLat < MAX_LAT){
		    double delta_long = Math.asin(Math.sin(radDist) / Math.cos(radLat)) ;
		    minLong = radLong - delta_long;
		    if (minLong < MIN_LONG){
		        minLong = minLong + 2*Math.PI;
		    }   
		    maxLong = radLong + delta_long;
		    if (maxLong > MAX_LONG){
		        maxLong = maxLong - 2*Math.PI;
		    }
		}
		else{
			//keep northern hemisphere
		    minLat = Math.max(minLat, MIN_LAT);
		    maxLat = Math.min(maxLat, MAX_LAT);
		    minLong = -Math.PI;
		    maxLong = Math.PI;
		}
		
		//Convert from rad to deg
		minLat = minLat*57.2957795;
		minLong = minLong *57.2957795;
		maxLat = maxLat*57.2957795;
		maxLong = maxLong*57.2957795;
		
		//Return resulting bbox in string format
		return minLat + "," + minLong + "," + maxLat + "," + maxLong;
	}
	
	/**
	 * this method is used to add certain tags to a query. The first
	 * call to this will create a new query.
	 * @param lat of search point 
	 * @param lon of search point
	 * @param radius of search
	 * @param key of OSM tag
	 * @param value of OSM tag
	 * @param type "node", "way", or "relation"
	 * @param modv set as true to exclude results with the given tag
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public void addQuery(double lat, double lon, double radius, String key, String value, String type, boolean modv) throws ParserConfigurationException, TransformerException {
		//Create bounding box for query
		String bboxString = boundingboxgen(lat,lon,radius);
		
		//Build XML Document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		//Add Elements of Query
		Element query = doc.createElement("query");
		query.setAttribute("type", type);
		doc.appendChild(query);
		
		Element haskv = doc.createElement("has-kv");
		haskv.setAttribute("k",key);
		haskv.setAttribute("v", value);
		query.appendChild(haskv);
		
		//If excluding results
		if (modv){
			Element haskv2 = doc.createElement("has-kv");
			haskv2.setAttribute("k","service");
			haskv2.setAttribute("modv", "not");
			haskv2.setAttribute("regv", ".");
			query.appendChild(haskv2);
		}
		
		//Create lat/lng coordinates for query from bounding box
		String[] splitbbox = bboxString.split(",");
		Element bbox = doc.createElement("bbox-query");
		bbox.setAttribute("s", splitbbox[0]);
		bbox.setAttribute("w", splitbbox[1]);
		bbox.setAttribute("n", splitbbox[2]);
		bbox.setAttribute("e", splitbbox[3]);
		query.appendChild(bbox);
		
		//Transform XML Element to String for Query 
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        //create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        String xmlString = sw.toString();
        
		Queries.add(xmlString);
	}
	
	
	/**
	 * once you have added all queries using addQuery() use this method
	 * to return a queryxml file which can be sent using sendandRecieve()
	 * @param queryfileName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public File formatQuery(String queryfileName) throws SAXException, IOException, TransformerException, ParserConfigurationException {
		//Initialize final Document for query
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		// root elements
		Element rootElement = doc.createElement("osm-script");
		rootElement.setAttribute("output", "xml");
		rootElement.setAttribute("timeout", "30");
		doc.appendChild(rootElement);
 
		// staff elements
		Element union = doc.createElement("union");
		rootElement.appendChild(union);
		
		//meat of query
		for (int i = 0; i < Queries.size(); i++) {
			Document doc2 = docBuilder.parse(new ByteArrayInputStream(Queries.get(i).getBytes()));
			Node node = doc.importNode(doc2.getDocumentElement(), true);
			union.appendChild(node);
		}
		
		//output mode
		Element print = doc.createElement("print");
		rootElement.appendChild(print);
 
		// write the content into xml file
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		
		// Output to console for testing
		File xmlFile = new File(queryfileName + ".xml");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));
	    writer.write(result.getWriter().toString());
	    writer.close();		    

		return xmlFile;
	}
	
	/**
	 * if you want to change what you are quering, use this to clear
	 * all previous tags from your query
	 */
	public void clearQuery() {
		Queries.clear();
	}

}

	

