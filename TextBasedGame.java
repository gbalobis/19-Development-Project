import java.util.Scanner;

public class TextBasedGame {
	private int[][] board;
	private int highScore;
	private int currentScore;
	private Scanner scanner;
	
	public TextBasedGame() {
		//create empty board with no high scores
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
			//set up board with 2 new tiles and display it in the console
			generateNewTile();
			generateNewTile();
			displayBoard();
			//while the board is not in a victory or defeat screen, repeat following functions
			while(!victoryCheck()&&!defeatCheck()) {
				//configure board based on movement inputed
				computeMovement();
				//create a new tile in an empty space and display board in console
				generateNewTile();
				displayBoard();
			}
			char cont='z';
			//if board is in a defeat state, ask player if they would like to restart
			if(defeatCheck()) {
				System.out.println("DEFEAT!!!!\n Would you like to restart? (y / n)");
				//wait for a valid response of y or n
				while(cont!='y'&&cont!='n')
					cont=getScanner().next().charAt(0);
				//if they do not restart, end the function
				if(cont=='n')
					return;
				//if they do restart, reset board and current score, but not high score
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
			//if board is in a victory state, ask player if they would like to continue in endless mode
			if(victoryCheck()) {
				System.out.println("VICTORY!!!!\n Would you like to continue? (y / n)");
				//wait for a valid response of y or n
				while(cont!='y'&&cont!='n')
					cont=getScanner().next().charAt(0);
				//if they do not continue, end the function
				if(cont=='n') {
					return;
				}
				//if they do continue, ignore this check
				if(cont=='y') {
					//continue the game, checking only for a defeat
					while(!defeatCheck()) {
						//configure board based on movement inputed
						computeMovement();
						//create a new tile in an empty space and display board in console
						generateNewTile();
						displayBoard();
					}
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
	public void moveVertical(char dir) {
		//newBoard is flipped version of board, so arrays are grouped by columns
		int[][] newBoard=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				newBoard[i][j]=getBoard()[j][i];
			}
		}
		//convert each column to new configuration
		newBoard[0]=checkCollisions(newBoard[0], dir);
		newBoard[1]=checkCollisions(newBoard[1], dir);
		newBoard[2]=checkCollisions(newBoard[2], dir);
		newBoard[3]=checkCollisions(newBoard[3], dir);
		
		//flip board back to regular orientation
		for(int i=0;i<4;i++) {
			for(int j=0;j<i;j++) {
				int temp=newBoard[i][j];
				newBoard[i][j]=newBoard[j][i];
				newBoard[j][i]=temp;
			}
		}
		
		//update board configuration
		setBoard(newBoard);
	}
	//bennie
	public void moveHorizontal(char dir) {
		int[][] newBoard=getBoard();
		
		//convert each row to new configuration
		newBoard[0]=checkCollisions(newBoard[0], dir);
		newBoard[1]=checkCollisions(newBoard[1], dir);
		newBoard[2]=checkCollisions(newBoard[2], dir);
		newBoard[3]=checkCollisions(newBoard[3], dir);
		
		//update board configuration
		setBoard(newBoard);
	}
	//bennie
	public int[] checkCollisions(int[] line, char dir) {
		//up/left movements combine in same manner
		if(dir=='w'||dir=='a') {
			//variable for position last unmerged number
			int uncombined=-1;
			for(int i=0;i<4;i++) {
				//ignore any 0s
				if(line[i]!=0) {
					//if no unmerged number, current number is last unmerged number
					if(uncombined==-1)
						uncombined=i;
					//if same as unmerged number, double unmerged number and clear current position
					else if(line[uncombined]==line[i]){
						line[uncombined]*=2;
						setCurrentScore(getCurrentScore()+line[uncombined]);
						if(getCurrentScore()>getHighScore())
							setHighScore(getCurrentScore());
						line[i]=0;
						uncombined=-1;
					}
					//if not the same as unmerged number, current number is last unmerged number
					else if(line[uncombined]!=line[i]) {
						uncombined=i;
					}
				}
			}
			//shift all non-zero numbers as far left as possible
			for(int j=0;j<4;j++) {
				//ensure there are no non-zero numbers to the right of a zero
				if(line[j]==0) {
					for(int k=j+1;k<4;k++) {
						if(line[k]!=0) {
							line[j]=line[k];
							line[k]=0;
							break;
						}
					}
				}
			}
		}
		//down/right movements combine in same manner
		else if(dir=='s'||dir=='d') {
			//variable for position last unmerged number
			int uncombined=-1;
			for(int i=3;i>=0;i--) {
				//ignore any 0s
				if(line[i]!=0) {
					//if no unmerged number, current number is last unmerged number
					if(uncombined==-1)
						uncombined=i;
					//if same as unmerged number, double unmerged number and clear current position
					else if(line[uncombined]==line[i]){
						line[uncombined]*=2;
						setCurrentScore(getCurrentScore()+line[uncombined]);
						if(getCurrentScore()>getHighScore())
							setHighScore(getCurrentScore());
						line[i]=0;
						uncombined=-1;
					}
					//if not the same as unmerged number, current number is last unmerged number
					else if(line[uncombined]!=line[i]) {
						uncombined=i;
					}
				}
			}
			//shift all non-zero numbers as far right as possible
			for(int j=3;j>=0;j--) {
				//ensure there are no non-zero numbers to the left of a zero
				if(line[j]==0) {
					for(int k=j-1;k>=0;k--) {
						if(line[k]!=0) {
							line[j]=line[k];
							line[k]=0;
							break;
						}
					}
				}
			}
		}
		return line;
	}
	/*
	 * This method is used to show the current state of the board and is called every time a new change is made to the board
	 */
	public void displayBoard() {
		for (int positionX = 0; positionX < 4; positionX++) {
			for (int positionY = 0; positionY < 4; positionY++) {
				System.out.print(getBoard()[positionX][positionY]+ " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	/*
	 * This method checks the board if the tile "2048" is present and returns true if it is found and false otherwise.  
	 */
	public boolean victoryCheck() {
		for (int positionX = 0; positionX < 4; positionX++) {
			for (int positionY = 0; positionY < 4; positionY++) {
				if(board[positionX][positionY] == 2048)
					return true;
			}
		}
		return false;
	}
	//ammar
	public boolean defeatCheck() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0)
					return false;
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (equalityCheck(i,j))
					return false;
			}
		}
		return true;
	}
	
	//following function checks if a tile is equal to an adjacent tile 
	public boolean equalityCheck(int xPos, int yPos) {
		if (xPos == 0) {
			if(checkRight(xPos,yPos))
				return true;
			if (yPos > 0) {
				if(checkUp(xPos,yPos))
					return true;
			}
			if (yPos < 3) {
				if(checkDown(xPos,yPos))
					return true;
			}
		}
		else if (xPos == 3) {
			if(checkLeft(xPos,yPos))
				return true;
			if (yPos > 0) {
				if(checkUp(xPos,yPos))
					return true;
			}
			if (yPos < 3) {
				if(checkDown(xPos,yPos))
					return true;
			}
		}
		else {
			if(checkRight(xPos,yPos))
				return true;
			if(checkLeft(xPos,yPos))
				return true;
			if (yPos > 0) {
				if(checkUp(xPos,yPos))
					return true;
			}
			if (yPos < 3) {
				if(checkDown(xPos,yPos))
					return true;
			}
		}
		return false;
	}
	// these check if adjacent tile in specified direction is equal
	public boolean checkUp(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos][yPos - 1])
			return true;
		return false;
	}
	public boolean checkDown(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos][yPos + 1])
			return true;
		return false;
	}
	public boolean checkLeft(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos - 1][yPos])
			return true;
		return false;
	}
	public boolean checkRight(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos + 1][yPos])
			return true;
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
