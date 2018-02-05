
public class Bowling {
	private Frame[] game; 
	private int curFrame;
	boolean firstThrow;
	
	public Bowling() {
		curFrame = 0;
		firstThrow = true;
		game = new Frame[10];
		for(int i = 0; i< 10; i++) game[i] = new Frame();
	}
	
	
	//I made this boolean so we can test if roll occurred (11th frame case)
	public boolean roll(int x) {
		if(curFrame >= 10) return false; // played frames 0-9
		Frame f = game[curFrame];
		if(firstThrow) {			
			if(x < 0 || x > 10) return false;
			f.setThrow1(x);
			if(!f.isStrike())
				firstThrow = false; //if not strike stay on current frame
			else 
				++curFrame;			//increment current frame if Strike
		}
		else {
			if(x < 0 || x > 10-game[curFrame].getScore()) return false; //can't throw more than #of pins left
			f.setThrow2(x);
			++curFrame;
			firstThrow = true; 		//reset firstThrow to true after second throw
		}
		return true;
	}
	
	//Frames should number 1-10
	public int getScore(int frame) {
		frame--;
		if(frame < 0 || frame > 9) throw new IllegalArgumentException("Frame out of bounds: [1,10]");
		
		Frame f = game[frame];	//save current frame variable f
		//if(!f.hasPlayed()) throw new IllegalStateException("Frame not played yet"); 
		int score = f.getScore();
		if(f.isStrike()) { 	//check if frame is strike
			for(int i = frame+1; i < 10 && i <= frame+2; i++) score += (game[i].getScore());		
		} 
		else if(f.isSpare()) {	//check if spare
			for(int i = frame+1; i < 10 && i <= frame+1; i++) score += (game[i].getScore());
		}

		return score;
	}
	
	public int getTotalScore() {
		int ret = 0;
		//uses frame numbers because of offset in getScore
		for(int i = 1; i<11; i++) ret += getScore(i);
		
		return ret;
	}
	
	
	
	private class Frame{
		int throw1 = 0, throw2 = 0;
		
		public void setThrow1(int x) { throw1 = x; }
		
		public void setThrow2(int x) { throw2 = x; }
		
		public int getScore() {	return throw1+throw2; }
		
		public boolean isStrike() {	return throw1 == 10;}
		
		public boolean isSpare() {
			if(isStrike()) throw new IllegalStateException("Frame is strike, cannot check if spare");
			return (throw1 + throw2)==10;
		}
		
	}
}
