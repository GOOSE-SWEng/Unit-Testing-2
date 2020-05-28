package userInterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.InteractiveLearningApp;
import slides.Slide;


/**
 * Class for the creation of settings scene
 * @author - Tom Pound
 * @version - 1.0
 * @date - 01/04/20
 */
public class Settings {
	//Screen X and Y offsets
	private static double xOffset = 0;	
	private static double yOffset = 0;
	
	private static String title = "Settings";
	
	//instantiate the scene and the subscenes
	private static Scene settings;
	private static SubScene toolBar;
	private static SubScene resizeBar;
	
	//global variables for the settings defulats
	private static String currentLanguage = "English";
	private Boolean colourBlind = false;
	private Boolean darkMode = false;
	private static String currentFont = "Arial";
	private static int currentTextSize = 16;
	
		
	/**
	 * Method to create the settings screen
	 * @param mainStage - main stage of the window
	 * @param defaultXSize - deafult width of window
	 * @param defaultYSize - default height of window
	 * @return returns a completed settings screen as a scene 
	 */
	public static Scene createSettings(Stage mainStage,int defaultXSize, int defaultYSize) {
		//Create top and bottom tool bars
		toolBar = ToolBar.createToolBar(defaultXSize, title);
		resizeBar = ResizeBar.CreateResizeBar(defaultXSize);
		
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
		
		//Create Layouts
		BorderPane bp = new BorderPane();
		GridPane gp =  new GridPane();
		
		//Create Labels
		Text colourBlindFilter = new Text("Colour Blind Filter");
		Text nightMode = new Text("Dark Mode");
		Text textSize = new Text("Text Size");
		Text font = new Text("Font");
		Text language = new Text("Language");
		nightMode.setId("fields");
		textSize.setId("fields");
		font.setId("fields");
		language.setId("fields");
		
		//Create Check Boxes
		CheckBox colourBlindBox = new CheckBox();
		CheckBox nightModeBox = new CheckBox();
		CheckBox captionsBox = new CheckBox();
		CheckBox audioDescBox = new CheckBox();
		
		//Create text size drop down menu
		ComboBox<String> textSizeMenu = new ComboBox<String>();
		textSizeMenu.getItems().addAll("11pt",
									   "16pt",
									   "24pt",
									   "36pt");
		//set current value 
		textSizeMenu.setValue("11pt"); 
		//Create event listener
		textSizeMenu.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				newValue = newValue.replace("pt", "");
				currentTextSize = Integer.parseInt(newValue);
			}
		});
		
		//Create font drop down menu
		ComboBox<String> fontMenu = new ComboBox<String>();
		fontMenu.getItems().addAll("Comic Sans MS",
									"Arial",
									"Verdana",
									"Helvetica");
		//Set current value
		fontMenu.setValue("Arial"); 
		//Create event listener
		fontMenu.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				currentFont = newValue;
			}
		});
		
		///Create language drop down menu
		ComboBox<String> languageMenu = new ComboBox<String>();
		languageMenu.getItems().addAll("English",
									   "Spanish",
									   "German",
									   "French");
		//Set current value
		languageMenu.setValue("English"); 
		//Create event listener
		languageMenu.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				currentLanguage = newValue;
			}
		});
		
		//COULD ADD THIS TO AN APPLY METHOD
		//night mode and colour blind boxes update style sheet 
		nightModeBox.setOnAction(e->{
			if(nightModeBox.isSelected()) {
				StartScreen.nightmodeStyle();
				Settings.nightmodeStyle();
				InteractiveLearningApp.style = "nightmode";
			}else {
				StartScreen.defaultStyle();
				Settings.defaultStyle();
				InteractiveLearningApp.style = "default";
			}
		});
		colourBlindBox.setOnAction(e->{
			if(colourBlindBox.isSelected()) {
				StartScreen.colourblindStyle();
				Settings.colourblindStyle();
				InteractiveLearningApp.style = "colourblind";
			}else {
				StartScreen.defaultStyle();
				Settings.defaultStyle();
				InteractiveLearningApp.style = "default";
			}
		});
		
		//Apply, default and back buttons
		Button apply = new Button("Apply");
		Button setDefault = new Button("Return to Default");
		Button back = new Button("Back");
		
		//Apply event listeners
		apply.setOnAction(e -> applyButtonPressed());
		setDefault.setOnAction(e -> defaultButtonPressed());
		back.setOnAction(e-> backButtonPressed());
		
		//Add all elements to the grid pane
		gp.add(textSize, 0, 0);
		gp.add(textSizeMenu, 1, 0);
		gp.add(font, 0, 1);
		gp.add(fontMenu, 1, 1);
		gp.add(language, 0, 2);
		gp.add(languageMenu, 1, 2);
		gp.add(nightMode, 0, 3);
		gp.add(nightModeBox, 1, 3);	
		gp.add(apply, 0, 7);
		gp.add(setDefault, 1, 7);
		gp.add(back, 2, 7);
		
		//Set insets and layout
		gp.setVgap(10);
		gp.setHgap(50);
		gp.setPadding(new Insets(10,10,10,10));
		gp.setAlignment(Pos.CENTER);
		
		//gp.setGridLinesVisible(true);
		//Add to the borderpane scene
		bp.setTop(toolBar);
		bp.setBottom(resizeBar);
		bp.setCenter(gp);
		
		//Create the scene
		settings  = new Scene(bp,defaultXSize,defaultYSize);
		settings.getStylesheets().add("style/SettingsScreen/settingsScreen.css");
		return settings;
	}
	
	/** method for applying the settings */
	public static void applyButtonPressed() {
	
		//new window pops up
		Stage settingsPopUp = new Stage();
		
		//adding text and buttons
		Text text = new Text("To apply these settings, the presentation must restart.");
		Button apply = new Button("Apply");
		Button cancel = new Button("Cancel");
		GridPane gp = new GridPane();
		gp.add(text, 0, 0);
		gp.setColumnSpan(text, 2);
		gp.add(apply, 0, 1);
		gp.add(cancel, 1, 1);
		
		//creates new scene 
		Scene applyPopUp = new Scene(gp,400,100);
		settingsPopUp.setScene(applyPopUp);
		settingsPopUp.show();
		
		//button links for apply and cancel buttons
		apply.setOnMouseClicked(e->{
			InteractiveLearningApp.setChosenFont(currentFont);
			InteractiveLearningApp.setChosenTextSize(currentTextSize);
			InteractiveLearningApp.setChosenLanguage(currentLanguage);
			settingsPopUp.hide();
			InteractiveLearningApp.showStart();
		});
		cancel.setOnMouseClicked(e->settingsPopUp.hide());
	}
	
	/** revert settings to default */
	public static void defaultButtonPressed() {
		currentFont = InteractiveLearningApp.defaultFont;
		currentTextSize = InteractiveLearningApp.defaultTextSize;
		currentLanguage = InteractiveLearningApp.defaultLanguage;
	}
	
	/** go back to start screen or current slide of open presentation */
	public static void backButtonPressed() {
		if(InteractiveLearningApp.presRunning == true) {
			InteractiveLearningApp.showSlide(InteractiveLearningApp.currentSlide);
		}else {
			InteractiveLearningApp.showStart();
		}
	}
	
	/** sets style of settings screen to default */
	public static void defaultStyle() {
		settings.getStylesheets().clear();
		settings.getStylesheets().add("style/SettingsScreen/settingsScreen.css");
	}
	
	/** sets style of settings screen to night mode */
	public static void nightmodeStyle() {
		settings.getStylesheets().clear();
		settings.getStylesheets().add("style/SettingsScreen/settingsScreenNight.css");
	}
	
	/** sets style of settings screen to colour blind */
	public static void colourblindStyle() {
		settings.getStylesheets().clear();
		settings.getStylesheets().add("style/SettingsScreen/settingsScreenCB.css");
	}
}
