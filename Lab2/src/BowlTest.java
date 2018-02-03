import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlTest {

	public class BowlingTest {
		
		Bowling game;
		
		@Before
		public void setup() {
			game = new Bowling();
		}

		@Test
		public void testOneThrow() {
			assertTrue(game.roll(3));
			assertEquals("Frame score not equal to throw", 3, game.getScore(game.getFrame()));
		}
		
		@Test
		public void testTwoThrows() {
			assertTrue(game.roll(3));
			assertEquals("Frame score not equal to throw", 3, game.getScore(0));
			assertEquals(0, game.getFrame());
			assertTrue(game.roll(7));
			assertEquals("Frame score not equal to throw", 10, game.getScore(0));
			assertEquals(10, game.getTotalScore());
		}

		@Test
		public void testThreeThrows() {
			assertTrue(game.roll(3));
			assertEquals("Frame score not equal to throw", 3, game.getScore(0));
			assertEquals(0, game.getFrame());
			assertTrue(game.roll(7));
			assertEquals("Frame score not equal to throw", 10, game.getScore(0));
			assertEquals(10, game.getTotalScore());
			assertTrue(game.roll(10));
			assertEquals(1, game.getFrame());
			assertEqauls("Frame score not equal to throw", 10, game.getScore(1));
		}

		@Test
		public void testSpareCountsNextFrameScore() {
			fail("Not yet implemented");
		}

		@Test
		public void testStrikemovesToNextFrame() {
			fail("Not yet implemented");
		}

		@Test
		public void testStrikeCountsNextFrameScore() {
			fail("Not yet implemented");
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


}
