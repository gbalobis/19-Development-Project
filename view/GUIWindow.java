package view;
import java.util.Timer;
import java.util.TimerTask;

import controller.ButtonHandler;
import controller.KeyHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import model.AI;
import model.Game;
import model.Player;




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
	//instance of game used by player
	private Game single;
	//instance of game used by cpu
	private Game cpu;
	//hold highscore when game has been restarted
	private int highestScore;
	//boolean where true denotes singleplayer and false AI
	private boolean isSingle;
	//timer used to refresh board when cpu moves
	private Timer timer;
	//an array of colors 
	private Color[] colors;
	//determines if game is paused
	private boolean paused;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage=stage;
		
		//Array of Colors for all the tiles of 2048
		colors = new Color[] {Color.DARKSLATEBLUE, Color.GREEN, Color.DARKORANGE,Color.YELLOWGREEN,Color.BLUE,Color.INDIGO,Color.VIOLET,
				Color.CADETBLUE,Color.DARKSEAGREEN,Color.BROWN,Color.DARKCYAN};
		
		//creating buttons with images, code adapted from http://tutorials.jenkov.com/javafx/button.html
		//create icons to put into buttons
		ImageView user=new ImageView(new Image("https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-128.png",30,30,true,false));
		ImageView comp=new ImageView(new Image("https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678077-computer-128.png",30,30,true,false));
		//create an array with all buttons used
		buttons=new Button[] {new Button("Play Alone", user), new Button("Play Against CPU", comp), new Button("Easy"), 
				new Button("Medium"), new Button("Hard"), new Button("Main Menu"), new Button("Play Again"), new Button("Change Difficulty"),
				new Button("Pause"), new Button("Play")};
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
	
		
		//popup containing instructions
		//code adapted from https://code.makery.ch/blog/javafx-dialogs-official/
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Instructions");
		alert.setGraphic(null);
		alert.setHeaderText(null);
		alert.setContentText("Welcome to 2048!\nUse W, A, S and D to move the board up, left, down and right respectively.");
		alert.initModality(Modality.NONE);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
		//title is 2048 game
		stage.setTitle("2048 Game");
		stage.setScene(scenes[0]);
		stage.setResizable(false);
		//exit the program when gui is closed
		stage.setOnCloseRequest(e->{
			Platform.exit();
			System.exit(0);
		});

		stage.show();
		alert.showAndWait();
		//initializes the music chosen
		AudioClip note = new AudioClip(this.getClass().getResource("/04. Mii Plaza.wav").toString());
		//the music will play 5000 times
		note.setCycleCount(5000);
		//plays the music
		note.play();
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
			/*name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);*/
			name.setId("name");
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//vbox for menu buttons, which are for 1p and vs computer
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
		
			menu.getChildren().addAll(buttons[0], buttons[1]);
			root.setCenter(menu);
			menu.setId("main");
			
			//vbox for group and our names
			VBox credits=new VBox();
			Label group=new Label("Group 19");
			Label members=new Label("Ammar, Bennie, Jason, Josh");
			/*group.setTextFill(Color.WHITESMOKE);
			members.setTextFill(Color.WHITESMOKE);*/
		
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
		scenes[0].getStylesheets().add("Style.css");
		return scenes[0];
		
		
	}
	
	//creates scene for single player play
	public Scene onePlayerScene() {
		
		//set boolean to true for singleplayer
		isSingle = true;
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
			/*name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);*/
			root.setTop(name);
			name.setId("name");
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//display current board state, rectangles with numbers on top of them, 0s are not displayed
			GridPane board=new GridPane();
			int[][] gameBoard=getSingle().getBoard();
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane temp1=new StackPane();
					//checks the board for a number and assigns a color to it
					Color color  = Color.DIMGRAY;
					int tileNumber = gameBoard[i][j];
					int counter = 0;
					while (tileNumber != 0) {
						tileNumber /= 2;
						if (tileNumber == 1) {
							color = colors[counter];
							break;
						}
						counter++;
						 
					}
					Rectangle temp2=new Rectangle(50,50,color);
			
					temp2.setStroke(Color.WHITESMOKE);
					temp1.getChildren().add(temp2);
					
					
					//if number isn't a zero, create new label to be displayed
					if(gameBoard[i][j]!=0) {
						Label temp3=new Label(Integer.toString(gameBoard[i][j]));
						/*temp3.setTextFill(Color.WHITESMOKE);*/
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
			/*cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);*/
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
			/*cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);*/
			scores.getChildren().addAll(cscore, hscore);
			
			bottomBar.getChildren().addAll(buttons[5], scores);
			
			((BorderPane) scenes[1].getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.TOP_CENTER);
			bottomBar.setSpacing(50);
		}
		//when keyboard keys are pressed, let key handler deal with it
		scenes[1].setOnKeyPressed(khandler);
		scenes[1].getStylesheets().add("Style.css");
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
			/*name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);*/
			root.setTop(name);
			name.setId("name");
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//vbox containing buttons for all possible difficulties
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
		
			menu.getChildren().addAll(buttons[2], buttons[3], buttons[4]);
			root.setCenter(menu);
			menu.setId("diff");

			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[2]=scene;
		}
		scenes[2].getStylesheets().add("Style.css");
		return scenes[2];
	}

	//create a cpu with the difficulty chosen by player
	public void cpuDiff(char difficulty) {
		cpu=new AI(difficulty);
        cpu.startGame();
		//timer so board updates when cpu moves
		scheduleTimerTasks();
	}

	//create scene for vs cpu mode
	public Scene twoPlayerScene() {

		//set boolean to false for vs AI
				isSingle = false;
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
			/*name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);*/
			root.setTop(name);
			name.setId("name");
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
			/*pboardLabel.setTextFill(Color.WHITESMOKE);
			cboardLabel.setTextFill(Color.WHITESMOKE);*/
			
			//use same method as singlePlayer scene to reflect each player's board
			int[][] playerBoard=getSingle().getBoard();
			int[][] cpuBoard=getCPU().getBoard();
			GridPane playerGrid=new GridPane();
			GridPane cpuGrid=new GridPane();
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane ptemp1=new StackPane();
					//checks the board for a number and assigns a color to it
					Color color  = Color.DIMGRAY;
					int tileNumber = playerBoard[i][j];
					int counter = 0;
					while (tileNumber != 0) {
						tileNumber /= 2;
						if (tileNumber == 1) {
							color = colors[counter];
							break;
						}
						counter++;
						 
					}
					Rectangle ptemp2=new Rectangle(50,50,color);
					ptemp2.setStroke(Color.WHITESMOKE);
					ptemp1.getChildren().add(ptemp2);
					
					
					if(playerBoard[i][j]!=0) {
						Label ptemp3=new Label(Integer.toString(playerBoard[i][j]));
						/*ptemp3.setTextFill(Color.WHITESMOKE);*/
						ptemp1.getChildren().add(ptemp3);
					}
					ptemp1.setAlignment(Pos.CENTER);
					
					playerGrid.add(ptemp1, j, i);

					StackPane ctemp1=new StackPane();
					//checks the board for a number and assigns a color to it
					Color color1  = Color.DIMGRAY;
					int tileNumber1 = cpuBoard[i][j];
					int counter1 = 0;
					while (tileNumber1 != 0) {
						tileNumber1 /= 2;
						if (tileNumber1 == 1) {
							color = colors[counter1];
							break;
						}
						counter1++;
						 
					}
					Rectangle ctemp2=new Rectangle(50,50,color1);
					ctemp2.setStroke(Color.WHITESMOKE);
					ctemp1.getChildren().add(ctemp2);
					
					if(cpuBoard[i][j]!=0) {
						Label ctemp3=new Label(Integer.toString(cpuBoard[i][j]));
						/*ctemp3.setTextFill(Color.WHITESMOKE);*/
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
			/*pcscore.setTextFill(Color.WHITESMOKE);
			phscore.setTextFill(Color.WHITESMOKE);
			ccscore.setTextFill(Color.WHITESMOKE);
			spacing.setTextFill(Color.WHITESMOKE);*/
			
			//put the created boards and score labels into the above vboxes
			pboard.getChildren().addAll(pboardLabel, playerGrid, pcscore, phscore);
			cboard.getChildren().addAll(cboardLabel, cpuGrid, ccscore, spacing);
			pboard.setAlignment(Pos.CENTER);
			cboard.setAlignment(Pos.CENTER);	
			
			//put the vboxes into the above hbox
			boards.getChildren().addAll(pboard, cboard);
			boards.setAlignment(Pos.CENTER);
			root.setCenter(boards);
			
			HBox bottomBar=new HBox();
			bottomBar.getChildren().addAll(buttons[8], buttons[5]);
			bottomBar.setAlignment(Pos.CENTER);
			bottomBar.setSpacing(50);
			
			root.setBottom(bottomBar);
			

			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[3]=scene;
		}
		else {
			//readd button in case it was used in another scene
			HBox bottomBar=new HBox();
			bottomBar.getChildren().addAll(buttons[8], buttons[5]);
			
			((BorderPane) scenes[3].getRoot()).setBottom(bottomBar);
			bottomBar.setAlignment(Pos.CENTER);
			bottomBar.setSpacing(50);
		}
		//when keyboard keys are pressed, let key handler deal with it
		scenes[3].setOnKeyPressed(khandler);
		scenes[3].getStylesheets().add("Style.css");
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
					//checks the board for a number and assigns a color to it
					Color color  = Color.DIMGRAY;
					int tileNumber = gameBoard[i][j];
					int counter = 0;
					while (tileNumber != 0) {
						tileNumber /= 2;
						if (tileNumber == 1) {
							color = colors[counter];
							break;
						}
						counter++;
						 
					}
						
					Rectangle temp2=new Rectangle(50,50,color);
					
					temp2.setStroke(Color.WHITESMOKE);
					temp1.getChildren().add(temp2);
					
					if(gameBoard[i][j]!=0) {
						Label temp3=new Label(Integer.toString(gameBoard[i][j]));
						/*temp3.setTextFill(Color.WHITESMOKE);*/
						temp1.getChildren().add(temp3);
					}
					temp1.setAlignment(Pos.CENTER);
					/*int x=Character.getNumericValue(getSingle().getLastGenerated().charAt(0));
					int y=Character.getNumericValue(getSingle().getLastGenerated().charAt(1));
					System.out.println(x+" "+y);*/
					if((Character.getNumericValue(getSingle().getLastGenerated().charAt(0))==i)&&(Character.getNumericValue(getSingle().getLastGenerated().charAt(1))==j)) {			
						FadeTransition fadeTransition = new FadeTransition();
						fadeTransition.setDuration(Duration.millis(750));
						fadeTransition.setNode(temp2);
						fadeTransition.setFromValue(0.2);
						fadeTransition.setToValue(1.0);
						fadeTransition.setCycleCount(1);
						fadeTransition.setAutoReverse(false);
						fadeTransition.play();
					}
					
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
			/*cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);*/
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
			/*pboardLabel.setTextFill(Color.WHITESMOKE);
			cboardLabel.setTextFill(Color.WHITESMOKE);*/
			
			int[][] playerBoard=getSingle().getBoard();
			int[][] cpuBoard=getCPU().getBoard();
			GridPane playerGrid=new GridPane();
			GridPane cpuGrid=new GridPane();
			//new gridpanes with updated board values, created in the same way as in the twoPlayer scene
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					StackPane ptemp1=new StackPane();
					//checks the board for a number and assigns a color to it
					Color color1  = Color.DIMGRAY;
					int tileNumber1 = playerBoard[i][j];
					int counter1 = 0;
					while (tileNumber1 != 0) {
						tileNumber1 /= 2;
						if (tileNumber1 == 1) {
							color1 = colors[counter1];
							break;
						}
						counter1++;
						 
					}
						
			
					Rectangle ptemp2=new Rectangle(50,50,color1);
					ptemp2.setStroke(Color.WHITESMOKE);
					ptemp1.getChildren().add(ptemp2);
					
					if(playerBoard[i][j]!=0) {
						Label ptemp3=new Label(Integer.toString(playerBoard[i][j]));
						/*ptemp3.setTextFill(Color.WHITESMOKE);*/
						ptemp1.getChildren().add(ptemp3);
					}
					if((Character.getNumericValue(getSingle().getLastGenerated().charAt(0))==i)&&(Character.getNumericValue(getSingle().getLastGenerated().charAt(1))==j)) {			
						getSingle().setLastGenerated("55");
						FadeTransition fadeTransition = new FadeTransition();
						fadeTransition.setDuration(Duration.millis(750));
						fadeTransition.setNode(ptemp2);
						fadeTransition.setFromValue(0.2);
						fadeTransition.setToValue(1.0);
						fadeTransition.setCycleCount(1);
						fadeTransition.setAutoReverse(false);
						fadeTransition.play();
					}
					ptemp1.setAlignment(Pos.CENTER);
					
					playerGrid.add(ptemp1, j, i);

					StackPane ctemp1=new StackPane();
					//checks the board for a number and assigns a color to it
					Color color2  = Color.DIMGRAY;
					int tileNumber2 = cpuBoard[i][j];
					int counter2 = 0;
					while (tileNumber2 != 0) {
						tileNumber2 /= 2;
						if (tileNumber2 == 1) {
							color2 = colors[counter2];
							break;
						}
						counter2++;
						 
					}
					Rectangle ctemp2=new Rectangle(50,50,color2);
					ctemp2.setStroke(Color.WHITESMOKE);
					ctemp1.getChildren().add(ctemp2);
					
					if(cpuBoard[i][j]!=0) {
						Label ctemp3=new Label(Integer.toString(cpuBoard[i][j]));
						/*ctemp3.setTextFill(Color.WHITESMOKE);*/
						ctemp1.getChildren().add(ctemp3);
					}
					ctemp1.setAlignment(Pos.CENTER);
					if((Character.getNumericValue(getCPU().getLastGenerated().charAt(0))==i)&&(Character.getNumericValue(getCPU().getLastGenerated().charAt(1))==j)) {			
						getCPU().setLastGenerated("55");
						FadeTransition fadeTransition = new FadeTransition();
						fadeTransition.setDuration(Duration.millis(750));
						fadeTransition.setNode(ctemp2);
						fadeTransition.setFromValue(0.2);
						fadeTransition.setToValue(1.0);
						fadeTransition.setCycleCount(1);
						fadeTransition.setAutoReverse(false);
						fadeTransition.play();
					}
					cpuGrid.add(ctemp1, j, i);
				}
			}
			//readd the button in case another scene has used it, update current and high score
			Label pcscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label phscore=new Label("High Score: "+getSingle().getHighScore());
			Label ccscore=new Label("Current Score: "+getCPU().getCurrentScore());
			Label spacing=new Label(" ");
			/*pcscore.setTextFill(Color.WHITESMOKE);
			phscore.setTextFill(Color.WHITESMOKE);
			ccscore.setTextFill(Color.WHITESMOKE);
			spacing.setTextFill(Color.WHITESMOKE);*/
			
			pboard.getChildren().addAll(pboardLabel, playerGrid, pcscore, phscore);
			cboard.getChildren().addAll(cboardLabel, cpuGrid, ccscore, spacing);
			pboard.setAlignment(Pos.CENTER);
			cboard.setAlignment(Pos.CENTER);	
			
			boards.getChildren().addAll(pboard, cboard);
			boards.setAlignment(Pos.CENTER);
			((BorderPane) scenes[3].getRoot()).setCenter(boards);
			
			
			HBox bottomBar=new HBox();
			bottomBar.getChildren().addAll(buttons[8], buttons[5]);
			bottomBar.setAlignment(Pos.CENTER);
			bottomBar.setSpacing(50);
			
			((BorderPane) scenes[3].getRoot()).setBottom(bottomBar);
		}
	}
	//save into scenes[4]
	public Scene victoryScene() {
		//if scene doesn't exist, create it from scratch
		if(scenes[4]==null) {
					
			//root pane to contain everything
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(50,0,25,0));

			//set the background theme
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
					
			//victory message at top of screen
			VBox message=new VBox();
			Label vict=new Label("VICTORY!");
			Label name=new Label("Well, well, well, look at you go");
			/*name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);*/
			message.getChildren().addAll(vict,name);
			message.setAlignment(Pos.CENTER);
			
			root.setTop(message);
			vict.setId("name");
			name.setId("result");
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
					
			//vbox containing buttons for all possible options
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
			if(isSingle)
				menu.getChildren().addAll(buttons[6], buttons[5]);
			else
				menu.getChildren().addAll(buttons[6], buttons[7], buttons[5]);
			root.setCenter(menu);
			menu.setId("victory");

			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[4]=scene;
		}
		else {
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
					
			if(isSingle)
				menu.getChildren().addAll(buttons[6], buttons[5]);
			else
				menu.getChildren().addAll(buttons[6], buttons[7], buttons[5]);
			((BorderPane)scenes[4].getRoot()).setCenter(menu);
			menu.setId("victory");
		}
		scenes[4].getStylesheets().add("Style.css");
		return scenes[4];
	}
	//save into scenes[5]
	public Scene defeatScene() {
		//if scene doesn't exist, create it from scratch
		if(scenes[5]==null) {
			
			//root pane to contain everything
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(50,0,25,0));

			//set the background theme
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
			
			//defeat message at top of screen
			VBox message=new VBox();
			Label def=new Label("DEFEAT!");
			Label name=new Label("You just got completely destroyed!");
			/*name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);*/
			message.getChildren().addAll(def,name);
			message.setAlignment(Pos.CENTER);

			root.setTop(message);
			def.setId("name");
			name.setId("result");
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			//vbox containing buttons for all possible options
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
			if(isSingle)
				menu.getChildren().addAll(buttons[6], buttons[5]);
			else
				menu.getChildren().addAll(buttons[6], buttons[7], buttons[5]);
			root.setCenter(menu);
			menu.setId("defeat");

			//create scene with above root pane
			Scene scene=new Scene(root,600,400);
			scenes[5]=scene;
		}
		else {
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
			
			if(isSingle)
				menu.getChildren().addAll(buttons[6], buttons[5]);
			else
				menu.getChildren().addAll(buttons[6], buttons[7], buttons[5]);
			menu.setId("defeat");
			((BorderPane)scenes[5].getRoot()).setCenter(menu);
		}
		scenes[5].getStylesheets().add("Style.css");
		return scenes[5];
	}

	public void scheduleTimerTasks() {
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater(()->{
            		getCPU().computeMovement();
            	});
            }
        },
        50, 500); //starts generating cpu movements every 0.5 seconds after waiting 0.05 seconds
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater(()->{
            		updateBoard();
            		//call functions for victory/defeat check for cpu
            		if (cpu.defeatCheck() && single.getCurrentScore() > cpu.getCurrentScore()) {
            			timer.cancel();
                		stage.setScene(victoryScene());
            			stage.show();
            		}
            		else if (cpu.victoryCheck() || (cpu.defeatCheck()) && single.defeatCheck() && cpu.getCurrentScore() > single.getCurrentScore() ) {
            			timer.cancel();
                		stage.setScene(defeatScene());
            			stage.show();
            		}
            	});
            }
        },
        0, 500); //starts updating board every 0.5 second	
	}
	

	//return the stage being used
	public Stage getStage() {
		return stage;
	}
	
	//return a list of the buttons used
	public Button getButton(int i) {
		return buttons[i];
	}
	
	//return a list of the scenes used
	public Scene getScene(int i) {
		return scenes[i];
	}
	
	//return the singleplayer scene
	public Game getSingle() {
		return single;
	}
	
	//return the twoplayer scene
	public Game getCPU() {
		return cpu;
	}
	
	public boolean getIsSingle() {
		return isSingle;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public KeyHandler getKHandler() {
		return khandler;
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public void setPaused(boolean x) {
		paused=x;
	}
	
	//main method to launch the application
	public static void main(String[] args) {
			launch(args);
	}
}
