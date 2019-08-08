package test;
import static org.junit.Assert.*;

import org.junit.*;

import model.Game;

class GameTest {

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

	@Test
	void testGenerateNewTile() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 0 }, { 4, 0, 4, 2 }, { 2, 4, 2, 2 }, { 4, 0, 4, 2 } };
		test.setBoard(testBoard);

		assertTrue("Was able to detect a new tile on the board", test.victoryCheck());
	}
	


	@Test
	void testCheckCollisions() {
		fail("Not yet implemented");
	}

	@Test
	void testEqualityCheck() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 0 }, { 4, 0, 4, 2 }, { 2, 4, 2, 0 }, { 4, 0, 4, 2 } };
		test.setBoard(testBoard);

		assertTrue("Was able to detect a tile was equal to an adjacent tile", test.victoryCheck());
	}

	@Test
	void testCheckUp() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckDown() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckLeft() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckRight() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBoard() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHighScore() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentScore() {
		fail("Not yet implemented");
	}

	@Test
	void testSetBoard() {
		fail("Not yet implemented");
	}

	@Test
	void testSetHighScore() {
		fail("Not yet implemented");
	}

	@Test
	void testSetCurrentScore() {
		fail("Not yet implemented");
	}

	@Test
	void testGetScanner() {
		fail("Not yet implemented");
	}

}
