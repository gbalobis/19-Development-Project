package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.AI;
import view.GUIWindow;
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
		//if Change Difficulty button was pushed, allow player to choose a difficulty
		else if(e.getSource()==window.getButton(8)) {
			window.setPaused(true);
			window.getTimer().cancel();
			window.getScene(3).removeEventHandler(KeyEvent.KEY_PRESSED, window.getKHandler());
			HBox bottomBar=new HBox();
			bottomBar.setSpacing(80);
			bottomBar.getChildren().addAll(window.getButton(5), window.getButton(9));
			
			((BorderPane) window.getScene(3).getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.CENTER);
			temp=window.getScene(3);
		}
		//if Change Difficulty button was pushed, allow player to choose a difficulty
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
			
		//set the scene to the one decided upon, based on the above if statements
		window.getStage().setScene(temp);
		window.getStage().show();
		
	}
}
