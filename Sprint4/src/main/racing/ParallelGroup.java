package main.racing;

import java.util.List;
import java.util.Queue;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import main.Time;


/**
 * 
 * @author Hart Sinterhauf
 * 
 * Parallel Group is used for races that have a common start but multiple finishes
 * with different finish lanes (such as swimming).  
 * The finish will occur on channels 1-8 inclusive (note that channel 1 serves two purposes). 
 * Up to 8 competitor numbers may be entered.  The first number is assigned to lane (channel) 1, 
 * the second to lane (channel) 2, etc. Any numbers other than the first 8 are ignored.  
 * Upon a start (trigger on channel 1), the times for all racers begin.  Up to 8 pads may be 
 * connected to the finish channels.  If there are not enough sensors connected for the racers, 
 * some times will not be recorded (a DNF)unless a manual trigger is used.  If there are fewer 
 * racer numbers entered than the number of channels connected, only the times for the racers 
 * entered will be recorded (if there is a trigger on a channel with no racer, it is ignored)
 */

public class ParallelGroup implements Race{
	private Time groupStart;
	static List<Racer> finished;
	private int place;
	private boolean raceEnded;
	private ArrayList<Racer> ready;
	private ArrayList<Racer> running;
	
	public ParallelGroup() {
		this.place = 0;
		raceEnded = false;
		this.ready = new ArrayList<Racer>();
		this.running = new ArrayList<Racer>();
		this.finished = new LinkedList<Racer>();
	}
	
	/**
	 * Adds Racer r to the queue of ready racers. Only eight racers are recorded. 
	 * @param r
	 * @return true if successfully added, else false
	 */
	public boolean addReady(Racer r) {
		if(ready.size() >= 8)
			return false;
		ready.add(r);
		return true;
	}
		

	/**
	 * Returns a queue containing all racers that are ready to begin a race.
	 * @return queue of ready racers
	 */
	public ArrayList<Racer> getReadyRacers(){
		return ready;
	}
	
	
	/**
	 * Returns a queue containing all racers who are currently running a race.
	 * @return queue of current racers
	 */
	public ArrayList<Racer> getCurrentRacers(){
		return running;
	}
	
	/**
	 * Returns a list of all racers who have finished a race.
	 * @return list of finished racer
	 */
	public List<Racer> getFinishedRacers(){
		return finished;
	}

	
	/**
	 * Returns a list of all racers who did not finish the race.
	 * @return list of dnf racers
	 */
	public List<Racer> getDNFRacers(){
		List<Racer> out = new ArrayList<Racer>();
		for(Iterator<Racer> it = finished.iterator();it.hasNext();) {
			Racer r = it.next();
			if(r.getDnf())out.add(r);
		}
		return out;
	}
	
	
	/**
	 * Removes a racer from the ready position
	 * @param racer to remove
	 * @return true if racer was removed from ready position, else false
	 */
	public boolean cancel(Racer racer) {
		return ready.remove(racer);
	}
	
	
	/**
	 * Overloaded Start with default channel set to 1
	 * @param Time
	 */
	public void start(Time time) {
		this.start(1, time);
	}
	
	/**
	 * Assigns the start time to the race
	 * @param time at which the racer starts
	 */
	public void start(int channel, Time time) {
		groupStart = time;
		running = ready;
		ready = new ArrayList<Racer>();
	}
	
	/**
	 * Assigns the racers finishing time and adds them to finishers.
	 * NOTE: does not remove from current racers as the racer order must be preserved for channel specification.
	 * Constraints for the time and channel limits should be handled by a higher layer class
	 * @param time that the racer finishes at
	 */
	public boolean finish(int channel, Time time) {
		Racer temp = running.get(channel-1);
		if(temp == null || temp.getDnf() || temp.getFinish() != null) {
			return false;
		}
		temp.start(groupStart);
		temp.finish(time); 
		return finished.add(temp); //should return true
	}
	
	/**
	 * removes all running racers, marking them dnf and moving them to the finished list.
	 * Sends results in tabular format to local host http server
	 * @param time that the racer finishes at
	 */
	public void end() {
		for(Racer r: running) {
			if(r.isRacing()) {
				r.setDnf();
				finished.add(r);
			}
		}
		// set up a simple HTTP server on our local host
        try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
	
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
	
	public boolean containsBib(int bib) {
		for(Racer r: ready) {
			if(r.getName() == bib)
				return true;
		}
		
		for(Racer r: running) {
			if(r.getName() == bib) return true;
		}
		
		return false;
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
	
	/**
	 * returns the displayable ready, current, finished racers(not the full arrays, see end of S3 PDF)
	 **/
	public String getDisplay() {
		String ret = "";
		
		if(finished.size() != 0) 
			ret += finished.get(finished.size()-1).toString(new Time());
		return ret;
	}


}
