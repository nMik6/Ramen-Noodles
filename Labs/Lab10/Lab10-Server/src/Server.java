

/**
	 * Simple HTTP handler for testing ChronoTimer
	 */
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
	import java.util.Collection;
	import java.util.Collections;
	import java.io.File;
	
	import com.google.gson.Gson;
	import com.google.gson.JsonSyntaxException;
	import com.google.gson.reflect.TypeToken;
	import com.sun.net.httpserver.HttpExchange;
	import com.sun.net.httpserver.HttpHandler;
	import com.sun.net.httpserver.HttpServer;

	public class Server {
	
	/**
	 * Simple HTTP handler for testing ChronoTimer
	 */


	    // a shared area where we get the POST data and then use it in the other handler
	    static String sharedResponse = "";
	    static boolean gotMessageFlag = false;
	    static ArrayList<Employee> master = new ArrayList<>();

	    public static void main(String[] args) throws Exception {

	        // set up a simple HTTP server on our local host
	        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

	        // create a context to get the request to display the results in HTML format
	        server.createContext("/displayresults/directory", new HtmlHandler());
	        
	        // create a context to get the request to display the results
	        server.createContext("/displayresults", new DisplayHandler());

	        // create a context to get the request for the POST
	        server.createContext("/sendresults",new PostHandler());
	        server.setExecutor(null); // creates a default executor
	        
	        

	        // get it going
	        System.out.println("Starting Server...");
	        server.start();
	    }
	    
	    
	    static class HtmlHandler implements HttpHandler {
	    	public void handle(HttpExchange transmission) throws IOException {
	    		String response = buildHtml();
	    		
	    		// assume that stuff works all the time
	            transmission.sendResponseHeaders(300, response.length());

	            // set up a stream to write out the body of the response
	            OutputStream outputStream = transmission.getResponseBody();

	            // write it and return it
	            outputStream.write(response.getBytes());

	            outputStream.close();
	    	}
	    	
	    	private String buildHtml() {
	    		StringBuilder sb = new StringBuilder();
	    		//String cssfileloc = ".."+File.pathSeparator+"css"+File.pathSeparator+"theme.css";
	    		//URL url = getClass().getResource("theme.css");
	    		String cssfileloc = "theme.css";
	    		sb.append("<!DOCTYPE html>");
	    		sb.append("<html>");
	    		sb.append("<head><link rel=\"stylesheet\" href=\""+ cssfileloc +"\"></head>");
	    		System.out.print("here:" + cssfileloc);
	    		sb.append("<body>");
	    		sb.append("<table>");
	    		sb.append("<tr>");
	    		sb.append("<th>Title</th>");
	    		sb.append("<th>First Name</th>");
	    		sb.append("<th>Last Name</th>");
	    		sb.append("<th>Department</th>");
	    		sb.append("<th>Phone</th>");
	    		sb.append("<th>Gender</th>");
	    		sb.append("</tr>");
	    		for (Employee e : master) {
	    			sb.append("<tr>");
	    			sb.append("<td id=\\\"title\\\">" + e.getTitle() + "</td>");
	    			sb.append("<td id=\\\"first\\\">" + e.getFirstName() + "</td>");
	    			sb.append("<td id=\\\"last\\\">" + e.getLastName() + "</td>");
	    			sb.append("<td id=\\\"department\\\">" + e.getDepartment() + "</td>");
	    			sb.append("<td id=\\\"phone\\\">" + e.getPhoneNumber() + "</td>");
	    			sb.append("<td id=\\\"gender\\>" + e.isFemale() + "</td>");
	    			sb.append("</tr>");
	    		}
	    		sb.append("</table>");
	    		sb.append("</body>");
	    		sb.append("</html>");
	    		return sb.toString();
	    	}

	    	
	    }
	    
	    static class DisplayHandler implements HttpHandler {
	        public void handle(HttpExchange t) throws IOException {

	            String response = "Begin of response\n";
				Gson g = new Gson();
				// set up the header
	            System.out.println(response);
				try {
					if (!sharedResponse.isEmpty()) {
						ArrayList<Employee> fromJson = g.fromJson(sharedResponse,
								new TypeToken<Collection<Employee>>() {
								}.getType());

						System.out.println(response);
						response += "Before sort\n";
						for (Employee e : fromJson) {
							response += e + "\n";
						}
						Collections.sort(fromJson);
						response += "\nAfter sort\n";
						for (Employee e : fromJson) {
							response += e + "\n";
						}
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
	            response += "End of response\n";
	            System.out.println(response);
	            // write out the response
	            t.sendResponseHeaders(200, response.length());
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
	        }
	    }

	    static class PostHandler implements HttpHandler {
	        public void handle(HttpExchange transmission) throws IOException {

	            //  shared data that is used with other handlers
	            sharedResponse = "";

	            // set up a stream to read the body of the request
	            InputStream inputStr = transmission.getRequestBody();

	            // set up a stream to write out the body of the response
	            OutputStream outputStream = transmission.getResponseBody();

	            // string to hold the result of reading in the request
	            StringBuilder sb = new StringBuilder();

	            // read the characters from the request byte by byte and build up the sharedResponse
	            int nextChar = inputStr.read();
	            while (nextChar > -1) {
	                sb=sb.append((char)nextChar);
	                nextChar=inputStr.read();
	            }

	            // create our response String to use in other handler
	            sharedResponse = sharedResponse+sb.toString();
	            Gson g = new Gson();
            	ArrayList<Employee> employees = new ArrayList<Employee>();
            	
	            
	            if (sharedResponse.contains("ADD"))

	            {	
	            	String[] splitString = sharedResponse.split("ADD");
	            	System.out.println(splitString[1].toString());
	            	employees = g.fromJson(splitString[1],  new TypeToken<Collection<Employee>>() {}.getType());
	            	
	            	master.addAll(employees);
	            	sharedResponse = splitString[1];
	            }
	            
	            else if (sharedResponse.contains("PRINT"))
	            {
	            	sharedResponse = g.toJson(master);
	            	
	            }
	            
	            else if (sharedResponse.contains("CLEAR"))
	            {
	            	master.clear();
	            	sharedResponse = "";
	            }
	            
	            System.out.println("response: " + sharedResponse);

	            //Desktop dt = Desktop.getDesktop();
	            //dt.open(new File("raceresults.html"));

	            // respond to the POST with ROGER
          		String postResponse = "ROGER JSON RECEIVED";

	            // assume that stuff works all the time
	            transmission.sendResponseHeaders(300, postResponse.length());

	            // write it and return it
	            outputStream.write(postResponse.getBytes());

	            outputStream.close();
	        }
	    }

	

}
