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
			assertEquals("Game score not equal to throw", 3, game.getScore(game.getCurFrame()));
		}
		
		@Test
		public void testTwoThrows() {
			fail("Not yet implemented");
		}

		@Test
		public void testThreeThrows() {
			fail("Not yet implemented");
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
