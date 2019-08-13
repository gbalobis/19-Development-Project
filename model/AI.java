package model;

/**
 * The Class AI controls the CPU that the player plays against.
 * 
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class AI extends Game{
	
	/** The difficulty of the ai. h for hard, m for medium, e for easy. */
	private char difficulty;
	
	/**
	 * Instantiates a new ai.
 	 *
 	 * @param difficulty The difficulty of the ai. h for hard, m for medium, e for easy.
 	 */
	public AI (char difficulty) {
		super();
		this.difficulty = difficulty;
	}

	/**
	 * Starts the game for the CPU.
	 */
	public void startGame() {
		super.startGame();
		computeMovement();
	}
	/**
	 * Completes a full turn for the CPU.
	 */
	public void computeMovement() {
		GameSimulation sim = new GameSimulation(difficulty);
		sim.startSim(board, currentScore);
		move(sim.getChosenDir());
		generateNewTile();
	}

	/**
	 * Gets the difficulty level of the CPU.
	 *
	 * @return the difficulty of the CPU.
	 */
	public char getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets the difficulty for the CPU.
	 *
	 * @param difficulty The new difficulty for the CPU.
	 */
	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
	}
}	