package test;
import static org.junit.Assert.*;

import org.junit.*;

import model.Game;

public class GameTest {

	Game test = new Game();

	@Test
	public void test_defeatCheckTrue() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 2 } };
		test.setBoard(testBoard);

		assertTrue("Was able to detect the board was in a defeat state", test.defeatCheck());
	}

	@Test
	public void test_defeatCheckFalse() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 8, 4 } };
		test.setBoard(testBoard);

		assertFalse("Was able to detect the board was not a defeat state", test.defeatCheck());
	}

	@Test
	public void test_victoryCheckTrue() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 0, 4, 2 }, { 2, 0, 2, 4 }, { 4, 2, 0, 2048 } };
		test.setBoard(testBoard);

		assertTrue("Was able to detect the board was in a victory state", test.victoryCheck());
	}

	@Test
	public void test_victoryCheckFalse() {
		int[][] testBoard = new int[][] { { 32, 0, 2, 0 }, { 4, 256, 4, 2 }, { 2, 64, 2, 2 }, { 4, 4, 4, 128 } };
		test.setBoard(testBoard);

		assertFalse("Was able to detect the board was not a victory state", test.victoryCheck());
	}

//	@Test
//	public void testGame() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testStartGame() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testComputeMovement() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGenerateNewTile() {
//		fail("Not yet implemented");
//	}
//
//
//	@Test
//	public void testCheckCollisions() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testEqualityCheck() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckUp() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckDown() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckLeft() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckRight() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetBoard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetHighScore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetCurrentScore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetBoard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetHighScore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetCurrentScore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetScanner() {
//		fail("Not yet implemented");
//	}

}
