import java.util.Random;
import java.util.Scanner;


public class Game {
	
	protected int[][] board;
	protected int highScore;
	protected int currentScore;
	protected Scanner scanner;
	
	public Game() {
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
	//set up board with 2 new tiles and display it in the console
	generateNewTile();
	generateNewTile();
//	displayBoard();
	//while the board is not in a victory or defeat screen, repeat following functions
	/*while(!victoryCheck()&&!defeatCheck()) {
		//configure board based on movement inputed
		computeMovement();
		//create a new tile in an empty space and display board in console
		generateNewTile();
//		displayBoard();
		}*/
	}
	
	public void computeMovement(){
		
	}
	
	//josh
	public void generateNewTile() {
			
			int count1=0, count2=0;
			int tileNumber;
			
			//random number generator allocates either a 2 or a four to the tile 
			Random randomGenerator = new Random();
			  int chance = randomGenerator.nextInt(10);
			  if (chance == 0) {
				  tileNumber = 4;
			  }
			  else {
				  tileNumber = 2;
			  }
			  
	       //first for loop goes through the board and counts the number of empty spaces
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					
					if (board[i][j]==0) {
						count1++;
					}
				}
			}
			if(count1==0) {
				return;
			}
			//the line below picks a random spot to place the new tile
			chance = randomGenerator.nextInt(count1);
			
			//the second for loop places the tile in the randomly generated number created in the above line
			
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if (board[i][j]==0) {
						if(count2==chance)
							board[i][j]=tileNumber;
						count2++;
					}
				}
			}
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
	
	public void move(char dir) {
		switch (dir) {
		case 'w':
		case 's':
			moveVertical(dir);
			break;
		case 'a':
		case 'd':
			moveHorizontal(dir);
			break;
		}
	}
	
	public void randomMove() {
		Random RNG = new Random();
		int chance = RNG.nextInt(4);
		
		if (chance == 0)
			moveVertical('s');
		else if (chance == 1)
			moveVertical('w');
		else if (chance == 2)
			moveHorizontal('a');
		else if (chance == 3)
			moveHorizontal('d');
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
	
	public Scanner getScanner() {
		return scanner;
	}
	
}

	
	

	
