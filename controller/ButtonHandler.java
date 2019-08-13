package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.AI;
import view.GUIWindow;
// TODO: Auto-generated Javadoc

/**
 * The ButtonHandler class handles all button presses from a GUIWindow.
 *
 * @author Group 19
 * @version 3
 * @since 2019-08-12
 */
public class ButtonHandler implements EventHandler<ActionEvent>{
	
	/** The application this handler is handling button inputs for. */
	private GUIWindow window;
	
	/**
	 * Create a new button handler for this GUIWindow.
	 *
	 * @param w The application this handler is handling button inputs for.
	 */
	public ButtonHandler(GUIWindow w) {
		window=w;
	}
	/**
	 * Decides on which scene to show depending on the button that was pushed.
	 * 
	 * @param e The event that occurred after a button was pushed.
	 */
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
			//reset the current boards
			window.getStage().setScene(window.displayMenu());
			if (window.getIsSingle())		//if singleplayer, start new single player game
				temp=window.onePlayerScene();
			else {							//if vs AI, reset ai and start new vs cpu game
				window.cpuDiff(((AI) window.getCPU()).getDifficulty());
				temp=window.twoPlayerScene();
			}
		}
		//if Change Difficulty button was pushed, allow player to choose a difficulty
		else if(e.getSource()==window.getButton(7)) {
			window.getStage().setScene(window.displayMenu());
			temp=window.difficultyScreen();
		}
		//if the Pause button was pushed, stop the cpu moves scheduled by the timer and switch Pause button with Play button
		else if(e.getSource()==window.getButton(8)) {
			window.setPaused(true);
			window.getTimer().cancel();
			HBox bottomBar=new HBox();
			bottomBar.setSpacing(80);
			bottomBar.getChildren().addAll(window.getButton(5), window.getButton(9));
			
			((BorderPane) window.getScene(3).getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.CENTER);
			temp=window.getScene(3);
		}
		//if the Play button was pushed, schedule cpu moves with the timer and switch the Play button with Pause button
		else if(e.getSource()==window.getButton(9)) {
			window.setPaused(false);
			window.scheduleTimerTasks();
			HBox bottomBar=new HBox();
			bottomBar.setSpacing(80);
			bottomBar.getChildren().addAll(window.getButton(5), window.getButton(8));
			
			((BorderPane) window.getScene(3).getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.CENTER);
			temp=window.getScene(3);
		}
		//if the Credits button was pushed, display the Credits Scene
		else if(e.getSource()==window.getButton(10))
			temp=window.creditScene();	
		//if the Instructions button was pushed, display the Instructions Scene
		else if(e.getSource()==window.getButton(11))
			temp=window.instructionScene();
		//set the scene to the one decided upon, based on the above if statements
		window.getStage().setScene(temp);
		window.getStage().show();
		
	}
}
