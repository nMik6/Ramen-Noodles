package main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import main.racing.Racer;


public class Server implements Runnable{
	
	static List<Racer> finished;
	static String raceType;
	static HttpServer server;
	
	public Server(List<Racer> f, String rt) {
		finished = f;
		raceType = rt;
	}
	
	@Override
	public void run() {
		// set up a simple HTTP server on our local host
        try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
	
	        // create a context to get the request to display the results
	        server.createContext("/displayresults", new DisplayHandler());
	
	        server.setExecutor(null); // creates a default executor
	
	        // get it going
	        System.out.println("Starting Server...");
	        server.start();
        } catch (IOException e) {
        	System.out.println("Failed to start server");
        }
	}	
	
	public void stop() {
		if(server != null) {
			server.stop(1);
		}
	}
	
	static class DisplayHandler implements HttpHandler {
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
	    		sb.append("<!DOCTYPE html>");
	    		sb.append("<html>");
	    		sb.append("<head></head>");
	    		sb.append("<body>");
	    		sb.append("<table>");
	    		sb.append("<tr>"+ raceType.toUpperCase() +"</tr>");
	    		sb.append("<tr>");
	    		sb.append("<th>Place</th>");
	    		sb.append("<th>Number</th>");
	    		sb.append("<th>Time</th>");
	    		sb.append("</tr>");
	    		int place = 1;
	    		for (Racer r : finished) {
	    			sb.append("<tr>");
	    			sb.append("<td id=\\\"place\\\">" + place++ + "</td>");
	    			sb.append("<td id=\\\"number\\\">" + r.getName() + "</td>");
	    			sb.append("<td id=\\\"time\\\">" + r.getTotal().printTime()+ "</td>");
	    			sb.append("</tr>");
	    		}
	    		sb.append("</table>");
	    		sb.append("</body>");
	    		sb.append("</html>");
	    		return sb.toString();
	        }
	    }
}
