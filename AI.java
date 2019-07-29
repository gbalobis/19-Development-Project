import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/*
 * 
 * Group Name: Group 19
 * Tutorial: T04 Tuesdays & Thursdays
 * Class Name: AI
 * What The Class Does: Has the algorithms for the levels of difficulty for the CPU. 
 * Also has the inputs for the CPU to use when controlling it's instance of the board
 * from Game.   
 *
 */

public class AI extends Game{
	
	private char difficulty; // h for hard, e for easy etc.
	private boolean direction; //for alternating directions each turn
	private Timer timer = new Timer();
	
	public AI (char difficulty) {
		super();
		this.difficulty = difficulty;
	}

	public void startGame() {
		super.startGame();
		computeMovement();
	}/*
	}
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
					cont = super.getScanner().next().charAt(0);
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
					cont = super.getScanner().next().charAt(0);
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
		}*/
	public void computeMovement() {
		timer.scheduleAtFixedRate(new TimerTask() {
//			            @Override		not sure whether this line is necessary
			            public void run() {
			            	if (difficulty == 'h') {
				            	hardMovement();
			            	}
			            	else if (difficulty == 'e') {
			            		easyMovement();
			            	}
			            	else if (difficulty == 'm') {
			            		mediumMovement();
			            	}
			            	if (defeatCheck() || victoryCheck())
		            			cancel();
			            	generateNewTile();
			            }
			        },
			        0, 500); //starts immediately and repeats every 0.5 seconds	
//		if ()
	}
	
	public void hardMovement() {
		if (direction)
			moveVertical('s');
		else
			moveHorizontal('d');
		direction = !direction;
	}
	
	public void easyMovement() {

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
	
	public void mediumMovement() {
	//using random class for the movements
	Random RNG = new Random();	
	// 80% chance to make a random movement
	int chance = RNG.nextInt(10);
	// 20% chance to make a coordinated movement	
		if (chance == 8 || chance == 9) {
			if (direction)
				moveVertical('s');
			else
				moveHorizontal('d');
			direction = !direction;
		}
		//20% chance for a random movement going up
		else if (chance == 7 || chance == 6 ){
			moveVertical('w');
		}
		//20% chance for a random movement going left
		else if (chance == 5 || chance == 4 ){
			moveHorizontal('a');
		}
		//20% chance for a random movement going down
		else if (chance == 3 || chance == 2 ){
			moveVertical('s');
		}
		//20% chance for a random movement going right
		else if (chance == 1 || chance == 0 ){
			moveHorizontal('d');
		}
						
	}

	public char getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
	}
	
}	
