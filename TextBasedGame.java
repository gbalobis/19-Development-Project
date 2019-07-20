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
				temp[i][j]=0;
			}
		}
		setBoard(temp);
		setHighScore(0);
		setCurrentScore(0);
		scanner=new Scanner(System.in);
	}
	
	public void startGame() {
		while(true) {
			generateNewTile();
			while(!victoryScreen()&&!defeatScreen()) {
				generateNewTile();
				displayBoard();
				computeMovement();
			}
			char cont='z';
			if(defeatScreen()) {
				System.out.println("Would you like to restart? (y / n)");
				while(cont!='y'&&cont!='n')
					cont=getScanner().next().charAt(0);
				if(cont=='n')
					return;
				if(cont=='y') {
					int[][] temp=new int[4][4];
					for(int i=0;i<4;i++) {
						for(int j=0;j<4;j++) {
							temp[i][j]=0;
						}
					}
					setBoard(temp);
					setCurrentScore(0);
				}
			}
		
			if(victoryScreen()) {
				System.out.println("Would you like to continue? (y / n)");
				while(cont!='y'&&cont!='n')
					cont=getScanner().next().charAt(0);
				if(cont=='n') {
					return;
				}
				if(cont=='y') {
					continue;
				}
			}
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
		return true;
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
		board=new int[4][4];
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
