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
		buttons=new Button[] {new Button("Play Alone"), new Button("Play With Computer"), new Button("Easy"), 
				new Button("Medium"), new Button("Hard"), new Button("Main Menu")};
		bhandler=new ButtonHandler(this);
		khandler=new KeyHandler(this);
		for(int i=0;i<buttons.length;i++) {
			buttons[i].setOnAction(bhandler);
		}
		timer=new Timer();
		scenes=new Scene[4];
		scenes[0]=displayMenu();
		stage.setTitle("2048 Game");
		stage.setScene(scenes[0]);
		stage.show();
	}
	
	public Scene displayMenu() {
		if(scenes[0]==null) {
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(50,0,25,0));
		
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
		
			Label name=new Label("2048 Game");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
		
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
		
			menu.getChildren().addAll(buttons[0], buttons[1]);
			root.setCenter(menu);
		
			VBox credits=new VBox();
			Label group=new Label("Group 19");
			Label members=new Label("Ammar, Bennie, Jason, Josh");
			group.setTextFill(Color.WHITESMOKE);
			members.setTextFill(Color.WHITESMOKE);
		
			credits.getChildren().addAll(group, members);
			root.setBottom(credits);
			credits.setAlignment(Pos.BOTTOM_CENTER);
		
			Scene scene=new Scene(root,600,400);
			scenes[0]=scene;
		}
		scenes[1]=null;
		scenes[3]=null;
		if(single!=null)
			highestScore=single.getHighScore();
		single=new Player();
		single.setHighScore(highestScore);
		single.startGame();
		timer.cancel();
		return scenes[0];
	}
	
	public Scene onePlayerScene() {
		if(scenes[1]==null) {
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(25,0,25,0));
			
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
		
			Label name=new Label("Single Player Mode");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
		
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
			root.setCenter(board);
			board.setAlignment(Pos.CENTER);
		
			HBox bottomBar=new HBox();
			
			VBox scores=new VBox();
			Label cscore=new Label("Current Score: "+getSingle().getCurrentScore());
			Label hscore=new Label("High Score: "+getSingle().getHighScore());
			cscore.setTextFill(Color.WHITESMOKE);
			hscore.setTextFill(Color.WHITESMOKE);
			scores.getChildren().addAll(cscore, hscore);
			
			bottomBar.getChildren().addAll(buttons[5], scores);
			
			root.setBottom(bottomBar);
			bottomBar.setAlignment(Pos.TOP_CENTER);
			bottomBar.setSpacing(50);
			
			Scene scene=new Scene(root,600,400);
			scenes[1]=scene;
		}
		else {
			//readd button in case it was used in another scene
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
		scenes[1].setOnKeyPressed(khandler);
		return scenes[1];
	}
	
	public Scene difficultyScreen() {
		if(scenes[2]==null) {
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(50,0,25,0));
		
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
		
			Label name=new Label("Choose A Difficulty");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
		
			VBox menu=new VBox();
			menu.setSpacing(15);
			menu.setAlignment(Pos.CENTER);		
		
			menu.getChildren().addAll(buttons[2], buttons[3], buttons[4]);
			root.setCenter(menu);
		
			Scene scene=new Scene(root,600,400);
			scenes[2]=scene;
		}
		return scenes[2];
	}

	public void cpuDiff(char difficulty) {
		cpu=new AI(difficulty);
		timer=new Timer();
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
        		cpu.startGame();
            }
        },
        0); //starts game immediately
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater(()->{
            		cpu.generateNewTile();
            		updateBoard();
            	});
            }
        },
        0, 1000); //starts updating board every 1 second	
	}

	public Scene twoPlayerScene() {
		if(scenes[3]==null) {
			BorderPane root=new BorderPane();
			root.setPadding(new Insets(25,0,25,0));
			
			Image img=new Image("https://opengameart.org/sites/default/files/SpaceBackground1.png");
			BackgroundImage bimg=new BackgroundImage(img,null,null,null,null);
			Background bkg=new Background(bimg);
			root.setBackground(bkg);
		
			Label name=new Label("Versus CPU");
			name.setFont(Font.font("Verdana", 20));
			name.setTextFill(Color.WHITESMOKE);
			root.setTop(name);
			BorderPane.setAlignment(name, Pos.BOTTOM_CENTER);
			
			HBox boards=new HBox();
			boards.setPadding(new Insets(20,0,0,0));
			boards.setSpacing(25);
			
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
			root.setCenter(boards);
			
			root.setBottom(buttons[5]);
			BorderPane.setAlignment(buttons[5], Pos.CENTER);
		
			Scene scene=new Scene(root,600,400);
			scenes[3]=scene;
		}
		else {
			//readd button in case it was used in another scene
			((BorderPane) scenes[3].getRoot()).setBottom(buttons[5]);
			BorderPane.setAlignment(buttons[5], Pos.CENTER);
		}
		scenes[3].setOnKeyPressed(khandler);
		return scenes[3];
	}
	
	public void updateBoard() {
		if(getStage().getScene().equals(scenes[1])) {
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
			((BorderPane) scenes[1].getRoot()).setCenter(board);
			board.setAlignment(Pos.CENTER);
			
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
		else if(getStage().getScene().equals(scenes[3])) {
			HBox boards=new HBox();
			boards.setPadding(new Insets(20,0,0,0));
			boards.setSpacing(25);
			
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
	
	public Stage getStage() {
		return stage;
	}

	public Button getButton(int i) {
		return buttons[i];
	}
	
	public Game getSingle() {
		return single;
	}
	
	public Game getCPU() {
		return cpu;
	}
	
	public static void main(String[] args) {
		launch(args);	
	}
}
