package test;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

import model.Game;

/**
 * The GameTest class contains tests for the Game class.
 *
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class GameTest {

	/** The instance of game to be tested. */
	Game test = new Game();

	/**
	 * Confirm that the board is in a defeat state.
	 */
	@Test
	public void test_defeatCheckTrue() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 2 } };
		test.setBoard(testBoard);

		assertTrue("Was able to detect the board was in a defeat state", test.defeatCheck());
	}

	/**
	 * Confirm that the board is not in a defeat state.
	 */
	@Test
	public void test_defeatCheckFalse() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 8, 4 } };
		test.setBoard(testBoard);

		assertFalse("Was able to detect the board was not a defeat state", test.defeatCheck());
	}

	/**
	 * Confirm that the board is in a victory state.
	 */
	@Test
	public void test_victoryCheckTrue() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 0, 4, 2 }, { 2, 0, 2, 4 }, { 4, 2, 0, 2048 } };
		test.setBoard(testBoard);

		assertTrue("Was able to detect the board was in a victory state", test.victoryCheck());
	}

	/**
	 * Confirm that the board is not in a victory state.
	 */
	@Test
	public void test_victoryCheckFalse() {
		int[][] testBoard = new int[][] { { 32, 0, 2, 0 }, { 4, 256, 4, 2 }, { 2, 64, 2, 2 }, { 4, 4, 4, 128 } };
		test.setBoard(testBoard);

		assertFalse("Was able to detect the board was not a victory state", test.victoryCheck());
	}

	/**
	 * Confirm tile is generated in empty space when only one empty space is remaining.
	 */
	@Test
	public void testGenerateNewTileOneSpaceLeft() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 0 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 2 } };
		test.setBoard(testBoard);
		test.generateNewTile();
		
		assertEquals("Tile is generated with coordinates of [0][3]","03",test.getLastGenerated());
	}
	
	/**
	 * Confirm no tile is generated when no empty spaces exist.
	 */
	@Test
	public void testGenerateNewTileNoSpacesLeft() {
		int[][] testBoard = new int[][] { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 2 } };
		test.setBoard(testBoard);
		test.generateNewTile();
		
		assertEquals("Confirm no tile is generated","55",test.getLastGenerated());
	}
	
	/**
	 * Confirm that a tile is generated when empty spaces exist
	 */
	@Test
	public void testGenerateNewTileAnySpace() {
		int[][] testBoard = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		test.setBoard(testBoard);
		test.generateNewTile();
		
		assertNotEquals("Position of tile generated exists","55",test.getLastGenerated());
	}

	/**
	 * Confirm checkCollisions moves tiles properly and tiles with empty spaces in between them are properly combined.
	 */
	@Test
	public void testCheckCollisionsRightSeparated() {
		int[] before=new int[] { 2, 0, 0, 2 };
		before=test.checkCollisions(before, 'd');
		int [] after=new int[] { 0, 0, 0, 4 };
		assertTrue("Confirm array of 2, 0, 0, 2 shifts right into 0, 0, 0, 4", Arrays.equals(after, before) );
	}
	
	/**
	 * Confirm checkCollisions moves tiles properly and tiles with no empty spaces in between them are properly combined.
	 */
	@Test
	public void testCheckCollisionsRightTogether() {
		int[] before=new int[] { 0, 0, 2, 2 };
		before=test.checkCollisions(before, 'd');
		int [] after=new int[] { 0, 0, 0, 4 };
		assertTrue("Confirm array of 0, 0, 2, 2 shifts right into 0, 0, 0, 4", Arrays.equals(after, before) );
	}
	
	/**
	 * Confirm checkCollisions combines tiles properly when the array is full.
	 */
	@Test
	public void testCheckCollisionsRightFull() {
		int[] before=new int[] { 2, 2, 2, 2 };
		before=test.checkCollisions(before, 'd');
		int [] after=new int[] { 0, 0, 4, 4 };
		assertTrue("Confirm array of 2, 2, 2, 2 shifts right into 0, 0, 4, 4", Arrays.equals(after, before) );
	}

	/**
	 * Confirm checkCollisions does not combine tiles that should not be combined
	 */
	@Test
	public void testCheckCollisionsRightNoMerges() {
		int[] before=new int[] { 2, 8, 4, 2 };
		before=test.checkCollisions(before, 'd');
		int [] after=new int[] { 2, 8, 4, 2 };
		assertTrue("Confirm array of 2, 8, 4, 2 shifts right into 2, 8, 4, 2", Arrays.equals(after, before) );
	}
	
	/**
	 * Confirm checkCollisions moves tiles properly and tiles with empty spaces in between them are properly combined.
	 */
	@Test
	public void testCheckCollisionsLeftSeparated() {
		int[] before=new int[] { 2, 0, 0, 2 };
		before=test.checkCollisions(before, 'a');
		int [] after=new int[] { 4, 0, 0, 0 };
		assertTrue("Confirm array of 2, 0, 0, 2 shifts right into 4, 0, 0, 0", Arrays.equals(after, before) );
	}
	
	/**
	 * Confirm checkCollisions moves tiles properly and tiles with no empty spaces in between them are properly combined.
	 */
	@Test
	public void testCheckCollisionsLeftTogether() {
		int[] before=new int[] { 0, 0, 2, 2 };
		before=test.checkCollisions(before, 'a');
		int [] after=new int[] { 4, 0, 0, 0 };
		assertTrue("Confirm array of 0, 0, 2, 2 shifts right into 0, 0, 0, 4", Arrays.equals(after, before) );
	}
	
	/**
	 * Confirm checkCollisions combines tiles properly when the array is full.
	 */
	@Test
	public void testCheckCollisionsLeftFull() {
		int[] before=new int[] { 2, 2, 4, 4 };
		before=test.checkCollisions(before, 'a');
		int [] after=new int[] { 4, 8, 0, 0 };
		assertTrue("Confirm array of 2, 2, 4, 4 shifts right into 4, 8, 0, 0", Arrays.equals(after, before) );
	}

	/**
	 * Confirm checkCollisions does not combine tiles that should not be combined.
	 */
	@Test
	public void testCheckCollisionsLeftNoMerges() {
		int[] before=new int[] { 2, 8, 2, 8 };
		before=test.checkCollisions(before, 'a');
		int [] after=new int[] { 2, 8, 2, 8 };
		assertTrue("Confirm array of 2, 8, 2, 8 shifts right into 2, 8, 2, 8", Arrays.equals(after, before) );
	}
	
	/**
	 * Confirm equality check can find an equivalent tile around tile at specified coordinate.
	 */
	@Test
	public void testEqualityCheckSame() {
		int[][] testBoard = new int[][] { { 0, 0, 0, 2 }, { 0, 0, 0, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		test.setBoard(testBoard);
		assertTrue("Confirm there is an equivalent tile around '2' tile at [1][3]", test.equalityCheck(1,3));
	}
	
	/**
	 * Confirm equality check does not return true for non-matching tiles around tile at specified coordinate.
	 */
	@Test
	public void testEqualityCheckDifferent() {
		int[][] testBoard = new int[][] { { 0, 0, 0, 4 }, { 0, 0, 4, 2 }, { 0, 0, 2, 4 }, { 0, 0, 0, 0 } };
		test.setBoard(testBoard);
		assertFalse("Confirm there is no equivalent tile around '2' tile at [1][3]", test.equalityCheck(1,3));
	}
	
	/**
	 * Confirm equality check does not return true for empty tiles around tile at specified coordinate.
	 */
	@Test
	public void testEqualityCheckNone() {
		int[][] testBoard = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 2 }, { 0, 0, 2, 0 }, { 0, 0, 0, 0 } };
		test.setBoard(testBoard);
		assertFalse("Confirm there is no equivalent tile around '2' tile at [1][3]", test.equalityCheck(1,3));
	}


}
