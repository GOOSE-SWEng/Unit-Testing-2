package userInterface;

import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for the loading screen
 * @author - Tom Poumd
 * @version - 1.0
 * @date - Unknown
 */
public class LoadingScreen {
	//global variables
	static SubScene toolBar;
	static SubScene resizeBar;
	
	/**
	 * 
	 * @param stage - main stage of program to place this scene on top of
	 * @param width - width of window
	 * @param height - height of window
	 * @return returns a scene containing the loading screen
	 */
	public static Scene createLoadingScreen(Stage stage, int width, int height) {
		//create toolbar and resize bar for scene
		toolBar = ToolBar.createToolBar(width, "Loading...");
		resizeBar = ResizeBar.CreateResizeBar(width);
		
		//create border pane with loading text
		BorderPane bp = new BorderPane();
		bp.setTop(toolBar);
		Text prezLoad = new Text("Your presentation is Loading");
		bp.setCenter(prezLoad);
		
		Scene loadingScene = new Scene(bp,width,height);
		loadingScene.getStylesheets().add("style/ContentScreen/contentScreen.css");
		return loadingScene;
	}
}
