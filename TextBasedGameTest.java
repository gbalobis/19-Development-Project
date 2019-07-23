import static org.junit.Assert.*;

import org.junit.Test;

public class TextBasedGameTest {

	TextBasedGame test=new TextBasedGame();
	@Test
	public void test_defeatCheckTrue() {
		int[][] testBoard=new int[][] {{2,4,2,4},{4,2,4,2},{2,4,2,4},{4,2,4,2}};
		test.setBoard(testBoard);
		
		assertTrue("Was able to detect the board was in a defeat state",test.defeatCheck());
	}

	@Test
	public void test_defeatCheckFalse() {
		int[][] testBoard=new int[][] {{2,4,2,4},{4,2,4,2},{2,4,2,4},{4,2,8,4}};
		test.setBoard(testBoard);
		
		assertFalse("Was able to detect the board was not a defeat state",test.defeatCheck());
	}
	
	@Test
	public void test_victoryCheckTrue() {
		int[][] testBoard=new int[][] {{2,4,2,4},{4,0,4,2},{2,0,2,4},{4,2,0,2048}};
		test.setBoard(testBoard);
		
		assertTrue("Was able to detect the board was in a victory state",test.victoryCheck());
	}
	
	@Test
	public void test_victoryCheckFalse() {
		int[][] testBoard=new int[][] {{32,0,2,0},{4,256,4,2},{2,64,2,2},{4,4,4,128}};
		test.setBoard(testBoard);
		
		assertFalse("Was able to detect the board was not a victory state",test.victoryCheck());
	}
}
