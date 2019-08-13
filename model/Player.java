package model;
import java.util.Scanner;

/**
 * The Player class controls the game for the player.
 *
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class Player extends Game {
	
	/** The scanner used to read the player's inputs. */
	private Scanner scanner;
	
	/**
	 * Instantiates a new player.
	 */
	public Player() {
		super();
		scanner=new Scanner(System.in);
	}
	
	/**
	 * Read the player's next movement and move accordingly.
	 */
	//josh
	public void computeMovement() {
		char p = 'x';
		while(p!= 'w'|| p!= 'a' || p!='s' || p!='d')
		p = scanner.next().charAt(0);
		 
		if (p=='w' || p=='s') {
			moveVertical(p);
		}
		if (p=='a'|| p =='d') {
			moveHorizontal(p);
			}
		
	}
	
	/**
	 * Gets the scanner.
	 *
	 * @return the scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}

}


