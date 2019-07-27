public class AI extends Game {
	
	private char difficulty; // h for hard, e for easy etc.
	private boolean direction; //for alternating directions each turn
	
	public AI (char difficulty) {
		super();
		this.difficulty = difficulty;
	}
	
	public void startGame() {
		while(true) {
			super.startGame();
			char cont='z';
			//if board is in a defeat state, ask player if they would like to restart
			if(defeatCheck()) {
				if (difficulty == 'e')
					System.out.println("I was going easy on you.\nPlay again? (y / n)");
//possibility to automatically switch to hard after this
				else if (difficulty == 'h')
					System.out.println("Impossible...\nPlay again? (y / n)");
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
				if (difficulty == 'e')
					System.out.println("How pathetic.\nPlay again? (y / n)");
				else if (difficulty == 'h')
					System.out.println("I'll go easy on you next time.\nPlay again? (y / n)");
//possibility to automatically switch to easy after this
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
//						displayBoard();
					}
				}
			}
		}
		}
	public void computeMovement() {
		if (difficulty == 'h') {
			if (direction)
				moveVertical('s');
			else
				moveHorizontal('d');
			direction = !direction;
		}
	}

	public char getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
	}
	
}	