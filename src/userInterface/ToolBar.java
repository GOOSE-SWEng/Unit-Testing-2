package userInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.InteractiveLearningApp;
import media.Shape;

/**
 * @author - Ivy Price
 * @version 1.2
 * @date - 28/02/20
 * Class used in the creation of the toolbar
 * Returns a subscene containing the toolbar object
 */

public class ToolBar {
	//height of tool bar integer value
	public static int toolBarHeight = InteractiveLearningApp.defaultYSize/20;
	
	//Global button/title names
	Button exitButton;
	Button openButton;
	Button newFileButton;
	Button optionsButton;
	Button settingsButton;
	Button minimizeWindowButton;
	Button maximizeWindowButton;
	
	//gridpane containing toolbar
	static GridPane gridPane;
	
	//title of presentation
	static Text title;
	
	/**
	 * Method to create toolbar
	 * @param winWidth width of the window at the current time toolbar is made
	 * @param id title of the current slide
	 * @return the toolbar as a subscene
	 */
	public static SubScene createToolBar(int winWidth, String id) {
		//instantiate the new grid pane, then instantiate the buttons to fill it
		GridPane gridPane = new GridPane();
		
		//Adds coloured background to tool bar
		//x axis size is 2x default x window size
		Rectangle toolBarBackground = new Rectangle(0,0,InteractiveLearningApp.defaultXSize*2, toolBarHeight);
		toolBarBackground.setId("toolBarBG");
        //toolBarBackground.setFill(Color.web("0x3aa9b8"));// !!! need to get this from style sheet
		gridPane.add(toolBarBackground,0,0);
		
		title = new Text(id);
		title.setId("title");
		//title.setFill(Color.web("white"));// !!! need to get this from style sheet
		//title.setStyle("-fx-font: 30 arial;");// !!! need to get this from style sheet
		
		//instantiate buttons
		Button homeButton = new Button();
		Button prevButton = new Button();
		Button nextButton = new Button();
		Button minimizeWindowButton = new Button();
		Button maximizeWindowButton = new Button();
		Button exitButton = new Button();
		
		//Add images to previously made blank buttons
		addImageToButton(homeButton, "resources/toolbar/homeicon.jpg");
		addImageToButton(prevButton, "resources/toolbar/previcon.jpg");
		addImageToButton(nextButton, "resources/toolbar/nexticon.jpg");
		addImageToButton(maximizeWindowButton, "resources/toolbar/maxicon.jpg");
		addImageToButton(minimizeWindowButton, "resources/toolbar/minicon.jpg");
		addImageToButton(exitButton, "resources/toolbar/exiticon.jpg");
		
		//connects each button to its corresponding event
		homeButton.setOnAction(e -> InteractiveLearningApp.homeSlide());
		prevButton.setOnAction(e -> InteractiveLearningApp.prevSlide());
		nextButton.setOnAction(e -> InteractiveLearningApp.nextSlide());
		minimizeWindowButton.setOnAction(e -> MinimizeButtonPressed());
		maximizeWindowButton.setOnAction(e -> MaximizeButtonPressed());
		exitButton.setOnAction(e -> ExitButtonPressed());
		
		//adds 10 pixel padding to the top, bottom, left and right of the tool bar
		gridPane.setPadding(new Insets(10,0,10,0));
		
		//sets the text so it is aligned in the centre of  its bounding box
		title.setTextAlignment(TextAlignment.CENTER);
		gridPane.setAlignment(Pos.CENTER);
		
		//sets the title to always be in the centre
		//and resize button to be on the left of the tool bar
		GridPane.setHalignment(title, HPos.CENTER);
		gridPane.setHgap(0);
		
		//adding buttons to grid pane
		gridPane.add(homeButton, 0, 0);
		gridPane.add(prevButton, 1, 0);
		gridPane.add(nextButton, 2, 0);
		gridPane.add(title, 3, 0);
		gridPane.add(minimizeWindowButton, 4, 0);
		gridPane.add(maximizeWindowButton, 5, 0);
		gridPane.add(exitButton, 6, 0);
		gridPane.setGridLinesVisible(false);
		
		//Sets to middle of each cell
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(homeButton, HPos.CENTER);
		GridPane.setHalignment(prevButton, HPos.CENTER);
		GridPane.setHalignment(nextButton, HPos.CENTER);
		GridPane.setHalignment(minimizeWindowButton, HPos.CENTER);
		GridPane.setHalignment(maximizeWindowButton, HPos.CENTER);
		GridPane.setHalignment(exitButton, HPos.CENTER);
		
		//creates column constraints so if the tool bar is resized, the distance between buttons 
		//remains consistent
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(4);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(4);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(4);
		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(76);
		ColumnConstraints column4 = new ColumnConstraints();
		column4.setPercentWidth(4);
		ColumnConstraints column5 = new ColumnConstraints();
		column5.setPercentWidth(4);
		ColumnConstraints column6 = new ColumnConstraints();
		column6.setPercentWidth(4);

		gridPane.getColumnConstraints().addAll(column0, column1, column2, column3, 
											   column4, column5, column6);
		
		//create a sub-scene on top of the grid pane, with a width defined by the window width and tool bar height variables
		SubScene toolBar = new SubScene(gridPane, winWidth, toolBarHeight);
		
		//binding the toolbar to the stage of the program is used when resizing the window
		toolBar.widthProperty().bind(InteractiveLearningApp.getStage().widthProperty());
		
		toolBar.setUserAgentStylesheet("style/Extras/toolBar.css");
		return toolBar;
	}
	
	public static void MinimizeButtonPressed() {
		System.out.println("Minimize Window Button Pressed");
		InteractiveLearningApp.getStage().setIconified(true);
	}

	public static void MaximizeButtonPressed() {
		System.out.println("Maximize Window Button Pressed");
		
		//if window is not maximised, maximises the window and sets the value to true
		if (InteractiveLearningApp.getStage().isMaximized() == false) {
			InteractiveLearningApp.getStage().setMaximized(true);
		}
		
		//if window is maximized, returns to the default window size and sets the the value to false
		else {
			InteractiveLearningApp.getStage().setWidth(InteractiveLearningApp.defaultXSize);
			InteractiveLearningApp.getStage().setHeight(InteractiveLearningApp.defaultYSize);
			InteractiveLearningApp.getStage().setMaximized(false);
		}

	}
	
	public void setTitle(String id) {
		title.setText(id);
	}
	
	public static void ExitButtonPressed() {
		System.out.println("Exit Button Pressed");
		InteractiveLearningApp.getStage().close();
		System.exit(1);
	}
	
	
	/**
	 * adds image from file path to a button
	 * @param button - id of button to contain image
	 * @param filepath - filepath of the image to be used
	 */
	public static void addImageToButton(Button button, String filePath) {
		try {
			Image homeIcon = new Image(new FileInputStream(filePath));
			ImageView imageView = new ImageView(homeIcon);
			imageView.setFitHeight(toolBarHeight);
			imageView.setPreserveRatio(true);
			button.setGraphic(imageView);		
		}catch(IOException ioe) {
			System.out.println("Image not found");
		}
	}
	
}
