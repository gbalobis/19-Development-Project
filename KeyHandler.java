import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent>{
	private GUIWindow window;
	
	public KeyHandler(GUIWindow w) {
		window=w;
	}
	@Override
	public void handle(KeyEvent e) {
		KeyCode key=e.getCode();
		if(key==KeyCode.W) {
			window.getSingle().moveVertical('w');
		}
		else if(key==KeyCode.A) {
			window.getSingle().moveHorizontal('a');
		}
		else if(key==KeyCode.S) {
			window.getSingle().moveVertical('s');
		}
		else if(key==KeyCode.D) {
			window.getSingle().moveHorizontal('d');
		}
		window.getSingle().generateNewTile();
		window.updateBoard();	
	}
	
}
