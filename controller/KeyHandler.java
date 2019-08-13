package controller;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.GUIWindow;


/**
 * The KeyHandler class handles all key presses from a GUIWindow.
 *
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class KeyHandler implements EventHandler<KeyEvent>{
	
	/** The application this handler is handling key inputs for. */
	private GUIWindow window;

	/**
	 * Create a new key handler for this GUIWindow.
	 *
	 * @param w The application this handler is handling key inputs for.
	 */
	public KeyHandler(GUIWindow w) {
		window=w;
	}
	
	/**
	 * Decides which direction to move the board when a key is pushed and checks for defeats or victories.
	 *
	 * @param e The event that occurred after a key was pushed.
	 */
	@Override
	public void handle(KeyEvent e) {
		//if paused, do nothing
		if(window.getPaused())
			return;	
		//find out which button was pressed
		KeyCode key=e.getCode();
		//if w was pressed, move board up
		if(key==KeyCode.W) {
			window.getSingle().moveVertical('w');
		}
		//if a was pressed, move board left
		else if(key==KeyCode.A) {
			window.getSingle().moveHorizontal('a');
		}
		//if s was pressed, move board down
		else if(key==KeyCode.S) {
			window.getSingle().moveVertical('s');
		}
		//if d was pressed, move board right
		else if(key==KeyCode.D) {
			window.getSingle().moveHorizontal('d');
		}
		//if none of the above buttons was pressed, do nothing
		else
			return;
		//create a new tile after each movement and update the board so the player can see the changes
		window.getSingle().generateNewTile();
		window.updateBoard();
		//defeat check for player
		if(window.getSingle().defeatCheck()) {
			//if vs cpu, don't display defeat screen until cpu beats your score
			if(!window.getIsSingle()) {
				Timer waiting=new Timer();
				waiting.scheduleAtFixedRate(new TimerTask() {
		            @Override
		            public void run() {
		            	Platform.runLater(()->{
		            		if(window.getSingle().getCurrentScore() < window.getCPU().getCurrentScore()) {
		            			waiting.cancel();
		            			window.getStage().setScene(window.defeatScene());
		                		window.getStage().show();
		            		}
		            	});
		            }
		        },
		        0, 500); //starts comparing your score to cpu score every 0.5 second
			}
			//if single player, display defeat screen
			else {
				window.getStage().setScene(window.defeatScene());
				window.getStage().show();
			}
		}
		//victory check for player, if you have 2048 tile you win
    	else if (window.getSingle().victoryCheck()) {
    		window.getStage().setScene(window.victoryScene());
    		window.getStage().show();
    	}
	}
	
}
