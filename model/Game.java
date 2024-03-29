package model;
import java.util.Random;
import java.util.Scanner;

/**
 * The superclass for the Game that both the player and cpu versions of the game inherit from
 * 
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class Game {
	
	/** The board being played on. */
	protected int[][] board;
	
	/** The high score. */
	protected int highScore;
	
	/** The current score. */
	protected int currentScore;
	
	/** The scanner used to read player inputs. */
	protected Scanner scanner;
	
	/** The position of the tile most recently generated. 
	 *  It is "55" when no tile has been generated.
	 */
	protected String lastGenerated;
	
	/**
	 * Instantiates a new game with an empty board and scores of 0.
	 * A new scanner is created to read inputs.
	 */
	public Game() {
		//create empty board with no high scores
		emptyBoard();
		setHighScore(0);
		setCurrentScore(0);
		scanner=new Scanner(System.in);
	}
	
	/**
	 * Starts game by generating 2 new tiles.
	 */
	public void startGame() {
	//set up board with 2 new tiles and display it in the console
	generateNewTile();
	generateNewTile();
	}
	
	/**
	 * Generates a new tile on the board of value '2' or '4' in a random empty position.
	 * If no empty positions exist, no tile is generated.
	 * The position of the most recently generated tile is stored in lastGenerated.
	 */
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
				setLastGenerated("55");
				return;
			}
			//the line below picks a random spot to place the new tile
			chance = randomGenerator.nextInt(count1);
			
			//the second for loop places the tile in the randomly generated number created in the above line
			
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if (board[i][j]==0) {
						if(count2==chance) {
							board[i][j]=tileNumber;
							setLastGenerated(Integer.toString(i)+Integer.toString(j));
							return;
						}
						count2++;
					}
				}
			}
		}
	
	/**
	 * Moves the board in a vertical direction.
	 *
	 * @param dir The direction the board is moved in. It must be either 'w' or 's'.
	 */
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
	
	/**
	 * Moves the board in a horizontal direction.
	 *
	 * @param dir The direction the board is moved in. It must be either 'a' or 'd'.
	 */
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
	
	/**
	 * Calls moveHorizontal or moveVertical depending on the direction input by the user.
	 *
	 * @param dir The direction the board is to be moved in. It must be one of: 'w', 'a', 's' or 'd'.
	 */
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
	
	/**
	 * A move in a random direction.
	 */
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

	/**
	 * Shifts the row or column provided in the specified direction and combines any tiles that should be combined.
	 *
	 * @param line The row or column collisions are being checked for.
	 * @param dir The direction the row or column must be moved in.
	 * @return The row or column with its new orientation.
	 */
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
	
	/**
	 * Checks for a victory. The player wins when they create a 2048 tile.
	 *
	 * @return true, if the player's board is in a victory state.
	 */
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

	/**
	 * Checks for a defeat. The player is defeated when they fill their board and movement in any direction will create an empty space
	 *
	 * @return true, if the player's board is in a defeat state.
	 */
	public boolean defeatCheck() {
		//if any empty spaces exist, return false
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == 0)
					return false;
			}
		}
		//if any tiles can be combined, return false
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (equalityCheck(i,j))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks for a victory when against a CPU.
	 * You win if you create a '2048' tile or when the CPU's board is in a defeat state and you have a higher score than it.
	 *
	 * @param cpu The CPU opponent.
	 * @return true, if you have reached the victory conditions.
	 */
	public boolean victoryCheck(Game cpu) {
		if (victoryCheck())
			return true;
		if (cpu.defeatCheck() && currentScore >= cpu.getCurrentScore())
			return true;
		return false;
		
	}
	
	/**
	 * Checks for a defeat when against a CPU.
	 * You lose if the CPU creates a '2048' tile or when your board is in a defeat state and it has a higher score than you.
	 *
	 * @param cpu The CPU opponent.
	 * @return true, if you have reached the defeat conditions.
	 */
	public boolean defeatCheck(Game cpu) {
		if (cpu.victoryCheck())
			return true;
		if (defeatCheck() && cpu.getCurrentScore() > currentScore)
			return true;
		return false;
	}

	
	/**
	 * Compares the value of a tile with the specified coordinates with the values of neighbouring tiles. 
	 *
	 * @param xPos The x coordinate of the tile being checked.
	 * @param yPos The y coordinate of the tile being checked.
	 * @return true, if there is a neighbouring tile containing a value equal to the tile in the specified coordinates.
	 */
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
	
	/**
	 * Checks the tile above the one with the specified coordinates.
	 *
	 * @param xPos The x coordinate of the tile being checked.
	 * @param yPos The y coordinate of the tile being checked.
	 * @return true, if the tile above contains a value equal to the tile in the specified coordinates.
	 */
	// these check if adjacent tile in specified direction is equal
	public boolean checkUp(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos][yPos - 1])
			return true;
		return false;
	}
	
	/**
	 * Checks the tile below the one with the specified coordinates.
	 *
	 * @param xPos The x coordinate of the tile being checked.
	 * @param yPos The y coordinate of the tile being checked.
	 * @return true, if the tile below contains a value equal to the tile in the specified coordinates.
	 */
	public boolean checkDown(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos][yPos + 1])
			return true;
		return false;
	}
	
	/**
	 * Checks the tile to the left of the one with the specified coordinates.
	 *
	 * @param xPos The x coordinate of the tile being checked.
	 * @param yPos The y coordinate of the tile being checked.
	 * @return true, if the tile to the left contains a value equal to the tile in the specified coordinates.
	 */
	public boolean checkLeft(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos - 1][yPos])
			return true;
		return false;
	}
	
	/**
	 * Checks the tile to the right of the one with the specified coordinates.
	 *
	 * @param xPos The x coordinate of the tile being checked.
	 * @param yPos The y coordinate of the tile being checked.
	 * @return true, if the tile to the right contains a value equal to the tile in the specified coordinates.
	 */
	public boolean checkRight(int xPos, int yPos) {
		if (board[xPos][yPos] == board[xPos + 1][yPos])
			return true;
		return false;
	}
	
	/**
	 * Empties the board for this game.
	 */
	public void emptyBoard() {
		int[][] temp=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				temp[i][j]=0;
			}
		}
		setBoard(temp);
	}
	
	
	/**
	 * Gets the board.
	 *
	 * @return The board.
	 */
	public int[][] getBoard() {
		int[][] temp=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				temp[i][j]=board[i][j];
			}
		}
		return temp;
	}

	/**
	 * Gets the high score.
	 *
	 * @return The high score.
	 */
	public int getHighScore() {
		return highScore;
	}

	/**
	 * Gets the current score.
	 *
	 * @return The current score.
	 */
	public int getCurrentScore() {
		return currentScore;
	}

	/**
	 * Gets the position of the tile last generated.
	 *
	 * @return The position of the tile last generated.
	 */
	public String getLastGenerated() {
		return lastGenerated;
	}

	/**
	 * Sets the board.
	 *
	 * @param b The new board.
	 */
	public void setBoard(int[][] b) {
		board=new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				board[i][j]=b[i][j];
			}
		}
	}

	/**
	 * Sets the position of the tile last generated.
	 *
	 * @param gen the new position of the tile last generated.
	 */
	public void setLastGenerated(String gen) {
		lastGenerated=gen;
	}
	
	/**
	 * Sets the high score.
	 *
	 * @param h The new high score.
	 */
	public void setHighScore(int h) {
		if(h>=0&&h>=getCurrentScore())
			this.highScore = h;
	}

	/**
	 * Sets the current score.
	 *
	 * @param c The new current score.
	 */
	public void setCurrentScore(int c) {
		if(c>=0)
			this.currentScore = c;
	}
	
	/**
	 * Gets the scanner.
	 *
	 * @return The scanner.
	 */
	public Scanner getScanner() {
		return scanner;
	}
	
	/**
	 * Computes movement.
	 */
	public void computeMovement() {
		
	}
	
}

	
	

	
