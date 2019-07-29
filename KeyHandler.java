import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/*
 * 
 * Group Name: Group 19
 * Tutorial: T04 Tuesdays & Thursdays
 * Class Name: KeyHandler
 * What The Class Does: Deals with all the keyboard presses from a GUIWindow
 *
 */
public class KeyHandler implements EventHandler<KeyEvent>{
	//the application it is handling inputs for
	private GUIWindow window;

	//create a new key handler for this guiwindow
	public KeyHandler(GUIWindow w) {
		window=w;
	}
	@Override
	public void handle(KeyEvent e) {
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
    		window.getStage().setScene(window.getScene(4));
    		window.getStage().show();
    	}
		//victory check for player
    	else if (window.getSingle().victoryCheck()) {
    		window.getStage().setScene(window.getScene(5));
    		window.getStage().show();
    	}
	}
	
}
