import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class ButtonHandler implements EventHandler<ActionEvent>{
	private GUIWindow window;
	
	public ButtonHandler(GUIWindow w) {
		window=w;
	}
	@Override
	public void handle(ActionEvent e) {
		Scene temp=null;
		if(e.getSource()==window.getButton(0))
			temp=window.onePlayerScene();
		else if(e.getSource()==window.getButton(1))
			temp=window.difficultyScreen();
		else if(e.getSource()==window.getButton(2)) {
			window.easyCPU();
			temp=window.twoPlayerScene();
		}
		else if(e.getSource()==window.getButton(3)) {
			window.mediumCPU();
			temp=window.twoPlayerScene();
		}
		else if(e.getSource()==window.getButton(4)) {
			window.hardCPU();
			temp=window.twoPlayerScene();
		}
		else if(e.getSource()==window.getButton(5))
			temp=window.displayMenu();
		window.getStage().setScene(temp);
		window.getStage().show();	
		
	}
}
