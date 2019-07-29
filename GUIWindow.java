import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/*
 * 
 * Group Name: Group 19
 * Tutorial: T04 Tuesdays & Thursdays
 * Class Name: GUIWindow
 * What The Class Does: Creates all the scenes used in the application with appropriate formatting. 
 * Uses the ButtonHandler and KeyHandler to deal with different inputs from the user
 *
 */
public class GUIWindow extends Application{
	//stage used to set scenes in the application
	private Stage stage;
	//array of all buttons used in the application
	private Button[] buttons;
	//array of all scenes used in the application
	private Scene[] scenes;
	//handler used for all ActionEvents caused by buttons
	private ButtonHandler bhandler;
	//handler used for all KeyEvents caused by key presses
	private KeyHandler khandler;
	//instance of textbasedgame used by player
	private Game single;
	//instance of textbasedgame used by cpu
	private Game cpu;
	//hold highscore when game has been restarted
	private int highestScore;
	//timer used to refresh board when cpu moves
	private Timer timer;
	@Override
	public void start(Stage stage) throws Exception {
		this.stage=stage;
		//create an array with all buttons used
		buttons=new Button[] {new Button("Play Alone"), new Button("Play With Computer"), new Button("Easy"), 
				new Button("Medium"), new Button("Hard"), new Button("Main Menu")};
		//create button and key handlers, which pass the guiwindow as an argument
		bhandler=new ButtonHandler(this);
		khandler=new KeyHandler(this);
		//all the buttons will be handled by the button handler
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setOnAction(bhandler);
		}
		//create timer for ai movement
		timer=new Timer();
		//create create an array of scenes so some scenes wont have to be recreated every time you visit them
		scenes=new Scene[6];
		//menu will be the first scene displayed upon running
		scenes[0]=displayMenu();
		//title is 2048 game
		stage.setTitle("2048 Game");
		stage.setScene(scenes[0]);
		stage.show();
	}
	
	//creates the scene for the menu and returns it
	public Scene displayMenu() {
		
		//if menu scene does not exist, create it from scratch
		if(scenes[0]==null) {
			
			//root pane to contain everything
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(50,0,25,0));
			
			//set the background theme
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
			
			//game name on the top of the screen
			Label name=new Label("2048 Game");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//vbox for menu buttons, which are for 1p and vs computer
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
		
			menu.getChildren().addAll(buttons[0], buttons[1]);
			root.setCenter(menu);
			
			//vbox for group and our names
			VBox credits=new VBox();
			Label group=new Label("Group 19");
			Label members=new Label("Ammar, Bennie, Jason, Josh");
			group.setTextFill(Color.WHITESMOKE);
			members.setTextFill(Color.WHITESMOKE);
		
			credits.getChildren().addAll(group, members);
			root.setBottom(credits);
			credits.setAlignment(Pos.BOTTOM_CENTER);
			
			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[0]=scene;
		}
		//when you return to menu screen, reset 1p and vs cpu screens
		scenes[1]=null;
		scenes[3]=null;
		//save high score if player has already played
		if(single!=null)
			highestScore=single.getHighScore();
		//restart the player's game, both 1p and cpu modes use this for the player
		single=new Player();
		single.setHighScore(highestScore);
		single.startGame();
		//stop timer to ensure cpu isn't running
		timer.cancel();
		return scenes[0];
	}
	
	//creates scene for single player play
	public Scene onePlayerScene() {
		
		//if scene doesn't exist, create it from scratch
		if(scenes[1]==null) {
			
			//root pane to contain everything
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(25,0,25,0));

			//set the background theme
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);

			//game mode on the top of the screen
			Label name=new Label("Single Player Mode");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//display current board state, rectangles with numbers on top of them, 0s are not displayed
			GridPane board=new GridPane();
			int[][] gameBoard=getSingle().getBoard();
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane temp1=new StackPane();
					
					Rectangle temp2=new Rectangle(50,50,Color.DIMGRAY);
					temp2.setStroke(Color.WHITESMOKE);
					temp1.getChildren().add(temp2);
					//if number isn't a zero, create new label to be displayed
					if(gameBoard[i][j]!=0) {
						Label temp3=new Label(Integer.toString(gameBoard[i][j]));
						temp3.setTextFill(Color.WHITESMOKE);
						temp1.getChildren().add(temp3);
					}
					temp1.setAlignment(Pos.CENTER);
					//add each stack pane into the gridpane using 2 "for" loops to recreate the board
					board.add(temp1, j, i);
				}
			}
			root.setCenter(board);
			board.setAlignment(Pos.CENTER);
			
			//bottom bar to contain scores and menu button
			HBox bottomBar=new HBox();
			
			//vbox contains all the scores on the right side
			VBox scores=new VBox();
			Label cscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label hscore=new Label("High Score: "+getSingle().getHighScore());
			cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);
			scores.getChildren().addAll(cscore, hscore);
			
			//button is displayed to the right of the scores
			bottomBar.getChildren().addAll(buttons[5], scores);
			
			root.setBottom(bottomBar);
			bottomBar.setAlignment(Pos.TOP_CENTER);
			bottomBar.setSpacing(50);
			
			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[1]=scene;
		}
		else {
			//in case the button is used in a different scene, it needs to be readded
			HBox bottomBar=new HBox();
			
			VBox scores=new VBox();
			Label cscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label hscore=new Label("High Score: "+getSingle().getHighScore());
			cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);
			scores.getChildren().addAll(cscore, hscore);
			
			bottomBar.getChildren().addAll(buttons[5], scores);
			
			((BorderPane) scenes[1].getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.TOP_CENTER);
			bottomBar.setSpacing(50);
		}
		//when keyboard keys are pressed, let key handler deal with it
		scenes[1].setOnKeyPressed(khandler);
		return scenes[1];
	}
	
	//screen to choose difficulty when playing vs cpu mode
	public Scene difficultyScreen() {
		
		//if scene doesn't exist, create it from scratch
		if(scenes[2]==null) {
			
			//root pane to contain everything
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(50,0,25,0));

			//set the background theme
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);

			//prompt on the top of the screen
			Label name=new Label("Choose A Difficulty");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//vbox containing buttons for all possible difficulties
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
		
			menu.getChildren().addAll(buttons[2], buttons[3], buttons[4]);
			root.setCenter(menu);

			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[2]=scene;
		}
		return scenes[2];
	}

	//create a cpu with the difficulty chosen by player
	public void cpuDiff(char difficulty) {
		cpu=new AI(difficulty);
        cpu.startGame();
		//timer so board updates when cpu moves
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater(()->{
            		updateBoard();
            		//call functions for victory/defeat check for cpu
            		//if cpu loses, display victory scene
            		//if cpu wins, display defeat scene
            	});
            }
        },
        0, 500); //starts updating board every 0.5 second	
	}
	
	//create scene for vs cpu mode
	public Scene twoPlayerScene() {

		//if scene doesn't exist, create it from scratch
		if(scenes[3]==null) {

			//root pane to contain everything
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(25,0,25,0));

			//set the background theme
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);

			//game mode on the top of the screen
			Label name=new Label("Versus CPU");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//hbox to contain the below vboxes
			HBox boards=new HBox();
			boards.setPadding(new Insets(20,0,0,0));
			boards.setSpacing(25);
			
			//vboxes, each one stores a player's board and their scores
			//player has both current and high score, but cpu only has current score
			VBox pboard=new VBox();
			VBox cboard=new VBox();
			
			Label pboardLabel=new Label("Your Board");
			Label cboardLabel=new Label("CPU's Board");
			pboardLabel.setTextFill(Color.WHITESMOKE);
			cboardLabel.setTextFill(Color.WHITESMOKE);
			
			//use same method as singlePlayer scene to reflect each player's board
			int[][] playerBoard=getSingle().getBoard();
			int[][] cpuBoard=getCPU().getBoard();
			GridPane playerGrid=new GridPane();
			GridPane cpuGrid=new GridPane();
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane ptemp1=new StackPane();
					
					Rectangle ptemp2=new Rectangle(50,50,Color.DIMGRAY);
					ptemp2.setStroke(Color.WHITESMOKE);
					ptemp1.getChildren().add(ptemp2);
					
					if(playerBoard[i][j]!=0) {
						Label ptemp3=new Label(Integer.toString(playerBoard[i][j]));
						ptemp3.setTextFill(Color.WHITESMOKE);
						ptemp1.getChildren().add(ptemp3);
					}
					ptemp1.setAlignment(Pos.CENTER);
					
					playerGrid.add(ptemp1, j, i);

					StackPane ctemp1=new StackPane();
					
					Rectangle ctemp2=new Rectangle(50,50,Color.DIMGRAY);
					ctemp2.setStroke(Color.WHITESMOKE);
					ctemp1.getChildren().add(ctemp2);
					
					if(cpuBoard[i][j]!=0) {
						Label ctemp3=new Label(Integer.toString(cpuBoard[i][j]));
						ctemp3.setTextFill(Color.WHITESMOKE);
						ctemp1.getChildren().add(ctemp3);
					}
					ctemp1.setAlignment(Pos.CENTER);
					
					cpuGrid.add(ctemp1, j, i);
				}
			}
			//scores of the players
			Label pcscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label phscore=new Label("High Score: "+getSingle().getHighScore());
			Label ccscore=new Label("Current Score: "+getCPU().getCurrentScore());
			Label spacing=new Label(" ");
			pcscore.setTextFill(Color.WHITESMOKE);
			phscore.setTextFill(Color.WHITESMOKE);
			ccscore.setTextFill(Color.WHITESMOKE);
			spacing.setTextFill(Color.WHITESMOKE);
			
			//put the created boards and score labels into the above vboxes
			pboard.getChildren().addAll(pboardLabel, playerGrid, pcscore, phscore);
			cboard.getChildren().addAll(cboardLabel, cpuGrid, ccscore, spacing);
			pboard.setAlignment(Pos.CENTER);
			cboard.setAlignment(Pos.CENTER);	
			
			//put the vboxes into the above hbox
			boards.getChildren().addAll(pboard, cboard);
			boards.setAlignment(Pos.CENTER);
			root.setCenter(boards);
			
			root.setBottom(buttons[5]);
			BorderPane.setAlignment(buttons[5], Pos.CENTER);
			

			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[3]=scene;
		}
		else {
			//readd button in case it was used in another scene
			((BorderPane) scenes[3].getRoot()).setBottom(buttons[5]);
			BorderPane.setAlignment(buttons[5], Pos.CENTER);
		}
		//when keyboard keys are pressed, let key handler deal with it
		scenes[3].setOnKeyPressed(khandler);
		return scenes[3];
	}
	
	//update what the boards are displaying
	public void updateBoard() {
		
		//if singleplayer mode update singleplayer scene
		if(getStage().getScene().equals(scenes[1])) {
			//new gridpane with updated board values, created in the same way as in the singleplayer scene
			GridPane board=new GridPane();
			int[][] gameBoard=getSingle().getBoard();
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane temp1=new StackPane();
					
					Rectangle temp2=new Rectangle(50,50,Color.DIMGRAY);
					temp2.setStroke(Color.WHITESMOKE);
					temp1.getChildren().add(temp2);
					
					if(gameBoard[i][j]!=0) {
						Label temp3=new Label(Integer.toString(gameBoard[i][j]));
						temp3.setTextFill(Color.WHITESMOKE);
						temp1.getChildren().add(temp3);
					}
					temp1.setAlignment(Pos.CENTER);
					
					board.add(temp1, j, i);
				}
			}
			//update board within the scene
			((BorderPane) scenes[1].getRoot()).setCenter(board);
			board.setAlignment(Pos.CENTER);
			//readd the button in case another scene has used it, update current and high score
			HBox bottomBar=new HBox();
			
			VBox scores=new VBox();
			Label cscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label hscore=new Label("High Score: "+getSingle().getHighScore());
			cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);
			scores.getChildren().addAll(cscore, hscore);
			
			bottomBar.getChildren().addAll(buttons[5], scores);
			
			((BorderPane) scenes[1].getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.TOP_CENTER);
			bottomBar.setSpacing(50);
		}
		
		//if vs cpu mode, update vs cpu scene
		else if(getStage().getScene().equals(scenes[3])) {
			//recreate hbox containing the vboxes
			HBox boards=new HBox();
			boards.setPadding(new Insets(20,0,0,0));
			boards.setSpacing(25);
			
			//recreate the vboxes, each containing a board
			VBox pboard=new VBox();
			VBox cboard=new VBox();
			
			Label pboardLabel=new Label("Your Board");
			Label cboardLabel=new Label("CPU's Board");
			pboardLabel.setTextFill(Color.WHITESMOKE);
			cboardLabel.setTextFill(Color.WHITESMOKE);
			
			
			int[][] playerBoard=getSingle().getBoard();
			int[][] cpuBoard=getCPU().getBoard();
			GridPane playerGrid=new GridPane();
			GridPane cpuGrid=new GridPane();
			//new gridpanes with updated board values, created in the same way as in the twoPlayer scene
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane ptemp1=new StackPane();
					
					Rectangle ptemp2=new Rectangle(50,50,Color.DIMGRAY);
					ptemp2.setStroke(Color.WHITESMOKE);
					ptemp1.getChildren().add(ptemp2);
					
					if(playerBoard[i][j]!=0) {
						Label ptemp3=new Label(Integer.toString(playerBoard[i][j]));
						ptemp3.setTextFill(Color.WHITESMOKE);
						ptemp1.getChildren().add(ptemp3);
					}
					ptemp1.setAlignment(Pos.CENTER);
					
					playerGrid.add(ptemp1, j, i);

					StackPane ctemp1=new StackPane();
					
					Rectangle ctemp2=new Rectangle(50,50,Color.DIMGRAY);
					ctemp2.setStroke(Color.WHITESMOKE);
					ctemp1.getChildren().add(ctemp2);
					
					if(cpuBoard[i][j]!=0) {
						Label ctemp3=new Label(Integer.toString(cpuBoard[i][j]));
						ctemp3.setTextFill(Color.WHITESMOKE);
						ctemp1.getChildren().add(ctemp3);
					}
					ctemp1.setAlignment(Pos.CENTER);
					
					cpuGrid.add(ctemp1, j, i);
				}
			}
			//readd the button in case another scene has used it, update current and high score
			Label pcscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label phscore=new Label("High Score: "+getSingle().getHighScore());
			Label ccscore=new Label("Current Score: "+getCPU().getCurrentScore());
			Label spacing=new Label(" ");
			pcscore.setTextFill(Color.WHITESMOKE);
			phscore.setTextFill(Color.WHITESMOKE);
			ccscore.setTextFill(Color.WHITESMOKE);
			spacing.setTextFill(Color.WHITESMOKE);
			
			pboard.getChildren().addAll(pboardLabel, playerGrid, pcscore, phscore);
			cboard.getChildren().addAll(cboardLabel, cpuGrid, ccscore, spacing);
			pboard.setAlignment(Pos.CENTER);
			cboard.setAlignment(Pos.CENTER);	
			
			boards.getChildren().addAll(pboard, cboard);
			boards.setAlignment(Pos.CENTER);
			((BorderPane) scenes[3].getRoot()).setCenter(boards);
			
			((BorderPane) scenes[3].getRoot()).setBottom(buttons[5]);
			BorderPane.setAlignment(buttons[5], Pos.CENTER);
		}
	}
	//save into scenes[5]
	public Scene victoryScene() {
		return null;
	}
	//save into scenes[6]
	public Scene defeatScene() {
		return null;
	}
	
	//return the stage being used
	public Stage getStage() {
		return stage;
	}
	
	//return a list of the buttons used
	public Button getButton(int i) {
		return buttons[i];
	}
	
	//return the singleplayer scene
	public Game getSingle() {
		return single;
	}
	
	//return the twoplayer scene
	public Game getCPU() {
		return cpu;
	}
	
	//main method to launch the application
	public static void main(String[] args) {
		launch(args);	
	}
}
