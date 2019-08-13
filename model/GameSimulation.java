package model;

/**
 * The GameSimulation class is a simulation of the game used to compute the AI class' next move.
 *
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class GameSimulation extends Game {

	/** The chosen direction for the AI class to move in. */
	private char chosenDir;
	
	/** The difficulty of the AI using this simulation. */
	private char difficulty;
	
	/** The number of times the simulation is run. */
	private final int numOfRuns = 1000;
	
	/** The saved board. */
	private int [][] savedBoard = new int [4][4];
	
	/** The saved current score. */
	private int savedCurrentScore;

	/**
	 * Instantiates a new game simulation.
	 *
	 * @param diff The difficulty of the AI using this simulation.
	 */
	public GameSimulation(char diff) {
		super();
		difficulty = diff;
	}

	/**
	 * Starts the simulation.
	 *
	 * @param board The board used for the simulation.
	 * @param currentScore The current score.
	 */
	public void startSim(int[][] board, int currentScore) {
		//copy contents of board to savedBoard
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				savedBoard[i][j]=board[i][j];
			}
		}
		//copy currentScore to savedCurrentScore
		savedCurrentScore = currentScore;
		//save the 3 highest scores achieved by the simulation
		double highest = 0, secondHighest = 0, thirdHighest = 0, chosen = 0;
		//finds the average scores when the first movement is in each
		double avgScoreUp = sim('w');
		double avgScoreDown = sim('s');
		double avgScoreLeft = sim('a');
		double avgScoreRight = sim('d');
				
		double [] manta = {avgScoreUp, avgScoreDown, avgScoreLeft, avgScoreRight};
		
		int count [] = {0,0,0,0};
		//count compares the average scores and ranks them in descending order. 4 denotes the highest score
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (manta[i] >= manta[j])
					count[i]++;
			}
		}
		//assign values to each variable based on results in count array
		for (int i = 0; i < 4; i++) {
			if (count[i] > 3)
				highest = manta[i];
			else if (count[i] > 2)
				secondHighest = manta[i];
			else if (count[i] > 1)
				thirdHighest = manta[i];
		}
		//easy chooses 3rd best option
		if (difficulty == 'e')
			chosen = thirdHighest;
		//medium chooses 2nd best option
		else if (difficulty == 'm')
			chosen = secondHighest;
		//hard chooses best option
		else if  (difficulty == 'h')
			chosen = highest;
		//choose the direction to move in based on chosen variable above
		if (chosen == avgScoreUp)
			setChosenDir('w');
		else if (chosen == avgScoreDown)
			setChosenDir('s');
		else if (chosen == avgScoreLeft)
			setChosenDir('a');
		else if (chosen == avgScoreRight)
			setChosenDir('d');

	}

	/**
	 * Simulates the game a number of times based on numOfRuns variable. The first move is always in the direction specified by dir.
	 *
	 * @param dir The direction of the first movement for the simulation.
	 * @return The average score achieved by this simulation.
	 */
	public double sim(char dir) {
		
		double avgScore = 0;
		for (int i = 0; i < numOfRuns; i++) {
			
			setBoard(savedBoard);
			setCurrentScore(savedCurrentScore);

			individualSim(dir);

			avgScore += currentScore;

		}
		avgScore /= numOfRuns;
		return avgScore;

	}

	/**
	 * A single simulation with the first move in the direction specified by dir.
	 *
	 * @param dir The direction of the first movement for the simulation.
	 */
	public void individualSim(char dir) {
		//the first move is in direction dir
		move (dir);
		//while the board is not in a defeat state, move randomly
		while (!defeatCheck()) {
			randomMove();
        	generateNewTile();
		}

	}

	/**
	 * Gets the direction chosen by the simulation.
	 *
	 * @return The chosen direction.
	 */
	public char getChosenDir() {
		return chosenDir;
	}

	/**
	 * Sets the direction chosen by the simulation.
	 *
	 * @param chosenDir The newly chosen direction.
	 */
	public void setChosenDir(char chosenDir) {
		this.chosenDir = chosenDir;
	}
}
