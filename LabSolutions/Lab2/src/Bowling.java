
public class Bowling { 
	private Frame[] game =  new Frame[10]; 
	int curFrame = 0;			//current frame of game[]. first frame is at index 0, second at index 1, & so on...
	boolean firstThrow = true;	
	
	
	public int getScore(int frame) {
		if(frame < 1 || frame > 10) throw new IllegalArgumentException("Frame out of bounds: [1,10]");
		Frame f = game[frame];	//save current frame variable f
		if(!f.hasPlayed()) throw new IllegalStateException("Frame not played yet"); 
		int score = f.getScore();
		if(f.isStrike() && frame < game.length-2) { 	//If frame is strike check if more frames are after it in array
			score += game[frame+1].getScore();		
			if(frame < game.length-3)					
				score += game[frame+1].getScore();		
		} 
		else if(f.isSpare() && frame < game.length-2)	//check if spare & frame exists after spare frame
			score += game[frame+1].getScore();
		
		return score;
	}
	
	public void roll(int x) {
		if(x < 0 || x > 10) throw new IllegalArgumentException("Number of pins out of bounds [0,10]");
		Frame f = game[curFrame];
		if(firstThrow) {			
			f.setThrow1(x);
			if(!f.isStrike())
				firstThrow = false; //if not strike stay on current frame
			else 
				++curFrame;			//increment current frame if Strike
		}
		else {
			f.setThrow2(x);
			++curFrame;
			firstThrow = true; 		//reset firstThrow to true after second throw
		}
	}
	
	private class Frame{
		int throw1, throw2, score;
		boolean played = false;
		
		public void setThrow1(int x) {
			throw1 = x;
			if(this.isStrike())	
				played = true;		//Frame has been played fully if Strike
		}
		public void setThrow2(int x) {
			throw2 = x;
			played = true;			//Frame has been played fully if proceeded to throw2
		}
		public int getScore() {
			if(!hasPlayed()) throw new IllegalStateException("Frame not fully played yet");
			return throw1+throw2;
		}
		public boolean isStrike() {
			return throw1 == 10;
		}
		public boolean isSpare() {
			if(isStrike()) throw new IllegalStateException("Frame is strike, cannot check if spare");
			return (throw1 + throw2)==10;
		}
		int getThrow1() {
			return throw1;
		}
		int getThrow2() {
			if(isStrike()) throw new IllegalStateException("Frame is strike, no second roll");
			return throw2;
		}
		boolean hasPlayed() {
			return played;
		}
	}
}
