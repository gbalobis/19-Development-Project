package view;

import model.*;

/**
 * The view for the text-based version of the game.
 * 
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class TextBasedGame extends Game {

	/** The player. */
	private Player player;
	
	/** The CPU the player may play against. */
	private AI comp;
	
	/** The game mode selected by the player. */
	private char input;
	
	/** The difficulty level of the CPU. */
	private char difficulty;
	
	/** Remains true as long as the player does not wish to exit the game. */
	private static boolean play = true;

	/**
	 * Instantiates a new text based game.
	 */
	public TextBasedGame() {
		super();
	}

	/**
	 * Starts the game. The player is asked to select a game mode and the method facilitates play for the game mode selected.
	 */
	@Override
	public void startGame() {

		player = new Player();
		System.out.println("\nMenu Instructions:\ns for singleplayer\nc to play against the computer\nx to exit");
		input = 'z';
		while (input != 's' && input != 'c' && input != 'x') {
			input = getScanner().next().toLowerCase().charAt(0);
		}
		if (input == 'x') {
			play = false;
			return;
		}
		//gameplay for singleplayer mode
		if (input == 's') {
			while (true) {
				// set up board with 2 new tiles and display it in the console
				setupBoard(player);
				// while the board is not in a victory or defeat screen, repeat following
				// functions
				while (!player.victoryCheck() && !player.defeatCheck()) {
					playGame(player);
				}
				char cont = 'z';
				// if board is in a defeat state, ask player if they would like to restart
				if (player.defeatCheck()) {
					System.out.println("DEFEAT!!!!\nWould you like to restart? (y / n)");
					// wait for a valid response of y or n
					while (cont != 'y' && cont != 'n')
						cont = getScanner().next().toLowerCase().charAt(0);
					// if they do not restart, return to main menu
					if (cont == 'n')
						break;
				}

				if (player.victoryCheck()) {
					System.out.println("VICTORY!!!!\nWould you like to continue? (y / n)");
					// wait for a valid response of y or n
					while (cont != 'y' && cont != 'n')
						cont = getScanner().next().charAt(0);
					// if they do not continue, end the function
					if (cont == 'n')
						break;

					// if they do continue, ignore this check
					if (cont == 'y')
						// continue the game, checking only for a defeat
						endlessMode();
				}
			}
		}
		//gameplay for vs CPU mode
		else if (input == 'c') {
			System.out.println("\nPick your difficulty:\ne for easy\nm for medium\nh for hard");
			difficulty = 'x';
			while (difficulty != 'e' && difficulty != 'm' && difficulty != 'h') {
				difficulty = getScanner().next().toLowerCase().charAt(0);
			}
			comp = new AI(difficulty);
			while (true) {
				// set up board with 2 new tiles and display it in the console
				setupBoard(player);
				setupBoard(comp);
				// while the board is not in a victory or defeat screen, repeat following
				// functions
				while (!player.victoryCheck(comp) && !player.defeatCheck(comp)) {
					//if player's board is not in a defeat state, request a movement input from them
					if(!player.defeatCheck()) {
						// configure board based on movement inputed
						playGame(player);
					}
					//if player's board is in a defeat state, display the board
					else {
						displayBoard(player);
					}
					// configure board based on movement inputed
					comp.computeMovement();
					// create a new tile in an empty space and display board in console
					comp.generateNewTile();
					displayBoard(comp);
				}
				char cont = 'z';
				if (player.defeatCheck(comp)) {
					System.out.println("DEFEAT!!!!\nWould you like to restart? (y / n)");
					// wait for a valid response of y or n
					while (cont != 'y' && cont != 'n')
						cont = getScanner().next().toLowerCase().charAt(0);
					// if they do not restart, end the function
					if (cont == 'n')
						break;
				}

				if (player.victoryCheck(comp)) {
					System.out.println("VICTORY!!!!\nWould you like to play again? (y / n)");
					// wait for a valid response of y or n
					while (cont != 'y' && cont != 'n')
						cont = getScanner().next().toLowerCase().charAt(0);
					// if they do not continue, end the function
					if (cont == 'n') {
						break;
					}
				}
			}
		}
	}

	/**
	 * Sets up board with 2 tiles and a current score of 0.
	 *
	 * @param game The game which the board is being setup for.
	 */
	public void setupBoard(Game game) {
		game.emptyBoard();
		game.generateNewTile();
		game.generateNewTile();
		player.setCurrentScore(0);
		displayBoard(game);

	}

	/**
	 * Complete a turn for the player.
	 *
	 * @param game The game being played by the player.
	 */
	public void playGame(Game game) {

		// configure board based on movement inputed
		computeMovement(game);
		// create a new tile in an empty space and display board in console
		game.generateNewTile();
		displayBoard(game);

	}

	/**
	 * Starts endless mode for the player. It does not check for the 2048 tile as a victory condition.
	 */
	public void endlessMode() {
		while (!defeatCheck()) {
			computeMovement(player);
		}
		startGame();
	}

	/**
	 * Reads the movement desired by the player.
	 *
	 * @param game The game being played by the player.
	 */
	public void computeMovement(Game game) {
		char p = 'x';
		while (p != 'w' && p != 'a' && p != 's' && p != 'd') {
			p = scanner.next().toLowerCase().charAt(0);
		}
		game.move(p);
	}

	/**
	 * Displays the current state of the board, along with a label and your current/high scores.
	 *
	 * @param game The game being played by the player.
	 */
	public void displayBoard(Game game) {
		if(game==comp) {
			System.out.println("CPU's Board");
			System.out.println("Current Score: "+game.getCurrentScore());
			System.out.println();
		}
		else if(game==player){
			System.out.println("Your Board");
			System.out.println("Current Score: "+game.getCurrentScore());
			System.out.println("High Score: "+game.getHighScore());
			System.out.println();
		}
		for (int positionX = 0; positionX < 4; positionX++) {
			for (int positionY = 0; positionY < 4; positionY++) {
				int x = game.getBoard()[positionX][positionY];
				System.out.print(x + "  ");
				if (x > 999)
					System.out.print(" ");
				if (x < 999)
					System.out.print(" ");
				if (x < 99)
					System.out.print(" ");
				if (x < 9)
					System.out.print(" ");
			}
			System.out.println();
			System.out.println();

		}
		System.out.println();
	}

	/**
	 * The main method which starts the text-based version of the game.
	 *
	 * @param args Arguments provided from the command line.
	 */
	public static void main(String[] args) {
		System.out.println(
				"Welcome to 2048!\nUse W, A, S and D to move the board up, left, down and right respectively.");
		TextBasedGame test = new TextBasedGame();
		while(play)test.startGame();
		System.out.println("Thanks for playing!");
	}
}