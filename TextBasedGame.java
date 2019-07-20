import java.util.Scanner;

public class TextBasedGame {
	private int[][] board;
	private int highScore;
	private int currentScore;
	private Scanner scanner;
	
	public TextBasedGame() {
		int[][] temp=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				board[i][j]=0;
			}
		}
		setBoard(temp);
		setHighScore(0);
		setCurrentScore(0);
		scanner=new Scanner(System.in);
		
		generateNewTile();
		while(!victoryScreen()&&!defeatScreen()) {
			generateNewTile();
			displayBoard();
			computeMovement();
		}
		if(victoryScreen()) {
			
		}
			
		if(defeatScreen()) {
			
		}
	}
	//josh
	public void generateNewTile() {
		
	}
	//josh
	public void computeMovement() {
		
	}
	//bennie
	public void moveUp() {
		
		
	}
	
	public void moveDown() {
		
	}
	
	public void moveLeft() {
	
	}
	
	public void moveRight() {
	
	}
	//bennie
	public int[] checkCollisions(int[] line, char dir) {
		
		return null;
	}
	//jason
	public void displayBoard() {
		
	}
	//jason
	public boolean victoryScreen() {
		return false;
	}
	//ammar
	public boolean defeatScreen() {
		return false;
	}

	public int[][] getBoard() {
		int[][] temp=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				temp[i][j]=board[i][j];
			}
		}
		return temp;
	}

	public int getHighScore() {
		return highScore;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setBoard(int[][] b) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				board[i][j]=b[i][j];
			}
		}
	}

	public void setHighScore(int h) {
		if(h>=0&&h>=getCurrentScore())
			this.highScore = h;
	}

	public void setCurrentScore(int c) {
		if(c>=0)
			this.currentScore = c;
	}
}
