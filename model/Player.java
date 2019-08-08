package model;
import java.util.Scanner;

/*
 * 
 * Group Name: Group 19
 * Tutorial: T04 Tuesdays & Thursdays
 * Class Name: Player
 * What The Class Does: Controls the inputs that the Player does using 
 * a Scanner. Returns the input to the superClass.
 *
 */

public class Player extends Game {
	
	private Scanner scanner;
	
	public Player() {
		super();
		scanner=new Scanner(System.in);
	}
	
	/*public void startGame() {
		while(true) {
			super.startGame();
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
//						displayBoard();
					}
				}
			}
		}
		}*/
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
	
	public Scanner getScanner() {
		return scanner;
	}

}


