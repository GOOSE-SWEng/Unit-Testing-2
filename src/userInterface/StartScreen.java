package userInterface;

import java.nio.file.Paths;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.InteractiveLearningApp;
import media.Model;


/**
 * Class containing the start screen 
 * this is seen whenever the user starts the program
 * @author - Tom Pound
 * @version - 1.1
 * @date - 04/05/20
 */
public class StartScreen {
	//screen X and Y offsets
	private static double xOffset = 0;	
	private static double yOffset = 0;	
	
	private static Scene startScreen;
	private static String title = "Start Screen";
	
	/**
	 * Method used to create the start screen
	 * @param mainStage - Main window of the program. Start screen is placed upon this stage
	 * @param defaultXSize - default width of the program
	 * @param defaultYSize - default height of the program
	 * @return Returns a scene with the elements that make up the start screen upon it
	 */
	public static Scene createStartScreen(Stage mainStage, int defaultXSize, int defaultYSize) {
		//Create versions of the toolbar and the resize bar
		SubScene toolBar = ToolBar.createToolBar(defaultXSize, title); 
		SubScene resizeBar = ResizeBar.CreateResizeBar(defaultXSize); 
		
		//Get coordinate offsets of window when mouse press
		toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		
		//Move window with mouse
		toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setX(event.getScreenX() - xOffset);
				mainStage.setY(event.getScreenY() - yOffset);	
			}
		});
		
		//Setting stage style to transparent removes the default toolbar
		mainStage.initStyle(StageStyle.TRANSPARENT);
		BorderPane borderPane = new BorderPane();
		
		//Setup left side of the screen
		//Create buttons
		Button openButton =  new Button("Open");
		Button settingsButton = new Button("Settings");
		Button quitButton = new Button("Quit");
		
		//Setup button actions
		openButton.setOnMouseClicked(e->InteractiveLearningApp.run());
		settingsButton.setOnMouseClicked(e->InteractiveLearningApp.showSettings());
		quitButton.setOnMouseClicked(e->System.exit(1));
		
		//Create button menu
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(100,100,100,100));
		gp.add(openButton, 0, 0);
		gp.add(settingsButton, 0, 1);	
		gp.add(quitButton, 0, 2);	
		
		//Center gridpane to center of location
		gp.setAlignment(Pos.CENTER);	
		//Set the gap between buttons in the menu
		gp.setVgap(20); 
		
		//Adds the toolbar to the top of the scene
		//and resize bar to the bottom
		borderPane.setTop(toolBar); 
		borderPane.setBottom(resizeBar);
		
		//Import 3D goose model
		Model gooseModel = new Model(Paths.get("resources/3D_Models/startScreenGoose.stl").toUri().toString(), 50,90);
		//Zoom into model
		gooseModel.moveCam(0, 15, 600); 
		
		//Add buttons to the left of the screen
		borderPane.setLeft(gp);
		//Add model to the right of the screen
		borderPane.setRight(gooseModel.getModelScene());
		borderPane.setAlignment(gooseModel.getModelScene(), Pos.CENTER);
		startScreen = new Scene(borderPane, defaultXSize, defaultYSize);
		startScreen.getStylesheets().add("style/StartScreen/startScreen.css");	//Default
		borderPane.prefWidthProperty().bind(startScreen.widthProperty());
		borderPane.prefHeightProperty().bind(startScreen.heightProperty());
		//startScreen.getStylesheets().add("style/StartScreen/startScreenNight.css");	//Nightmode
		//startScreen.getStylesheets().add("style/StartScreen/startScreenCB.css");	//Colourblind?
		return startScreen;
	}
	
	/** sets style of startscreen to default */
	public static void defaultStyle() {
		startScreen.getStylesheets().clear();
		startScreen.getStylesheets().add("style/StartScreen/startScreen.css");
	}
	
	/** sets style of startscreen to night mode */
	public static void nightmodeStyle() {
		startScreen.getStylesheets().clear();
		startScreen.getStylesheets().add("style/StartScreen/startScreenNight.css");
	}
	
	/** sets style of startscreen to colour blind mode */
	public static void colourblindStyle() {
		startScreen.getStylesheets().clear();
		startScreen.getStylesheets().add("style/StartScreen/startScreenCB.css");
	}
}
