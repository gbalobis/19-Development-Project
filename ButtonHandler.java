import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
/*
 * 
 * Group Name: Group 19
 * Tutorial: T04 Tuesdays & Thursdays
 * Class Name: ButtonHandler
 * What The Class Does: Deals with all button presses in a GUIWindow
 *
 */
public class ButtonHandler implements EventHandler<ActionEvent>{
	//the application it is handling inputs for
	private GUIWindow window;
	
	//create a new button handler for this guiwindow
	public ButtonHandler(GUIWindow w) {
		window=w;
	}
	@Override
	public void handle(ActionEvent e) {
		//create a scene, depending on which button was pressed, display different scenes
		Scene temp=null;
		//compare the source of the action event to the array of buttons to determine which button was pushed
		//if Play Alone button was pushed, display onePlayerScene
		if(e.getSource()==window.getButton(0))
			temp=window.onePlayerScene();
		//if Play With Computer button was pushed, allow player to choose a difficulty
		else if(e.getSource()==window.getButton(1))
			temp=window.difficultyScreen();
		//depending on which difficulty button was pushed, create a new cpu and display twoPlayerScene
		else if(e.getSource()==window.getButton(2)) {
			window.cpuDiff('e');
			temp=window.twoPlayerScene();
		}
		else if(e.getSource()==window.getButton(3)) {
			window.cpuDiff('m');
			temp=window.twoPlayerScene();
		}
		else if(e.getSource()==window.getButton(4)) {
			window.cpuDiff('h');
			temp=window.twoPlayerScene();
		}
		//if Main Menu button was pushed, return to the main menu
		else if(e.getSource()==window.getButton(5))
			temp=window.displayMenu();
		//if Play Again is clicked 
		else if(e.getSource()==window.getButton(6)) {
			if (window.getIsSingle()) 		//if singleplayer
				temp=window.onePlayerScene();
			else							//if vs AI
				temp=window.twoPlayerScene();
		}
	
		else if(e.getSource()==window.getButton(7))
			temp=window.onePlayerScene();
		
		else if(e.getSource()==window.getButton(8))
			temp=window.difficultyScreen();
			
		//set the scene to the one decided upon, based on the above if statements
		window.getStage().setScene(temp);
		window.getStage().show();
		
	}
}
