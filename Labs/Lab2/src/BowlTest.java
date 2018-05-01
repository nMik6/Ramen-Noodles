import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlTest {

	Bowling game;

	@Before
	public void setup() {
		game = new Bowling();
	}

	@Test
	public void testOneThrow() {
		assertTrue(game.roll(3));
		assertEquals("Frame score not equal to only throw", 3, game.getScore(1));
		assertEquals("Total score not equal to only throw", 3, game.getTotalScore());
	}

	@Test
	public void testTwoThrows() {
		assertTrue(game.roll(3));
		assertTrue(game.roll(6));
		assertEquals("Frame score not equal to throw", 9, game.getScore(1));
		assertEquals("Total score not equal to only throws",9, game.getTotalScore());
	}

	@Test
	public void testThreeThrows() {
		assertTrue(game.roll(3));
		assertTrue(game.roll(6));
		assertTrue(game.roll(9));
		assertEquals("Frame score not equal to throws", 9, game.getScore(1));
		assertEquals("Frame score not equal to throw", 9, game.getScore(2)); 
		assertEquals("Frame score not equal to throw", 18, game.getTotalScore());
	}

	@Test
	public void testSpareCountsNextFrameScore() {
		assertTrue(game.roll(3));
		assertTrue(game.roll(7));
		assertTrue(game.roll(9));
		assertTrue(game.roll(0));
		assertTrue(game.roll(6)); //make sure spare isn't overshooting
		assertEquals("Frame score not equal to throws", 19, game.getScore(1));
		assertEquals("Frame score not equal to throw", 6, game.getScore(3)); 
		assertEquals("Frame score not equal to throw", 34, game.getTotalScore());
	}

	@Test
	public void testStrikemovesToNextFrame() {
		assertTrue(game.roll(10));
		assertTrue(game.roll(6));
		assertEquals(6, game.getScore(2));
	}

	@Test
	public void testStrikeCountsNextFrameScore() {
		assertTrue(game.roll(10));
		assertTrue(game.roll(9));
		assertTrue(game.roll(0));
		assertTrue(game.roll(6));
		assertTrue(game.roll(3));
		assertTrue(game.roll(7));//make sure spare isn't overshooting
		assertEquals("Frame score not equal to throws", 28, game.getScore(1));
		assertEquals("Frame score not equal to throw", 7, game.getScore(4)); 
		assertEquals("Frame score not equal to throw", 53, game.getTotalScore());
	}

	@Test
	public void testSpareOnLastFrame() {
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(5));//10th frame
		assertTrue(game.roll(5));
		assertEquals(270, game.getTotalScore());
	}

	@Test
	public void testStrikeOnLastFrames() {
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));//10th frame
		assertEquals(270, game.getTotalScore());
	}

	@Test
	public void testThrowOn11thFrame() {
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));
		assertTrue(game.roll(10));//10th frame
		assertFalse(game.roll(10));//11th frame

	}




}
