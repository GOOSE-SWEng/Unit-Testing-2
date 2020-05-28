package main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import userInterface.*;
import slides.*;
import tools.Timer;
import tools.XMLParser;
import media.*;

/**
 * main class of the program
 * mostly contains getter and setters
 * majority of presentation is built and run in other packages
 * @author - Tom Pound, Alex Marchant, Ivy Price, Hugh Mayoh
 * @version - 1.6
 * @date - 21/05/20
 *
 */
public class InteractiveLearningApp extends Application{
	public static int defaultXSize = 1280;
	public static int defaultYSize = 720;
	//Current screen offset
	private double yOffset = 0;
	private double xOffset = 0;
	private static Scene start;
	private static Scene settings;
	private static Scene loading;
	private static Stage mainStage;
	public static int currentSlide;
	public static int slideCount;
	public static String style = "default";
	
	public static Boolean presRunning = false;
	
	public static String defaultLanguage = "English";
	public static String defaultFont = "Arial";
	public static int defaultTextSize = 16;
	public static String defaultBGColour = "White";
	public static String defaultLineColour = "Black";
	public static String defaultFontColour = "Black";
	public static String defaultFillColour = "Black";
	
	public static String chosenFont = "Arial";
	public static int chosenTextSize = 16;
	public static String chosenLanguage = "English";
	


	//Triggers Exhibit Mode
	private boolean exhibitMode = false;
	public static Timer timer;
	
	//arrays for the media objects and the layers that hold these media objects
	public static ArrayList<Slide> slides = new ArrayList<Slide>();
	static ArrayList<Audio> audio = new ArrayList<Audio>();
	static ArrayList<Graphics2D> graphics2d = new ArrayList<Graphics2D>();
	static ArrayList<Model> models = new ArrayList<Model>();
	static ArrayList<SlideImage> images = new ArrayList<SlideImage>();
	static ArrayList<Shape> shapes = new ArrayList<Shape>();
	static ArrayList<SlideText> slideText = new ArrayList<SlideText>();
	static ArrayList<Video> videos = new ArrayList<Video>();
	
	static ArrayList<VideoLayer> videoLayers = new ArrayList<VideoLayer>();
	static ArrayList<Graphics2D> graphics2dLayers = new ArrayList<Graphics2D>();
	static ArrayList<Graphics3DLayer> graphics3dLayers = new ArrayList<Graphics3DLayer>();
	static ArrayList<ImageLayer> imageLayers = new ArrayList<ImageLayer>();
	static ArrayList<TextLayer> textLayers = new ArrayList<TextLayer>();
	static ArrayList<AudioLayer> audioLayers = new ArrayList<AudioLayer>();
	
	static String xml;
	
	public static void main(String[] args) {
		System.out.println("Running...");
		launch(args);
		System.out.println("Finished...");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		mainStage.setMinWidth(defaultXSize*0.75);
		mainStage.setMinHeight(defaultYSize*0.75);
		start = StartScreen.createStartScreen(mainStage, defaultXSize, defaultYSize);
		settings = Settings.createSettings(mainStage, defaultXSize, defaultYSize);
		loading = LoadingScreen.createLoadingScreen(mainStage, defaultXSize, defaultYSize);
			
		/*LOADING PROCESS*/
		//LOADING SCREEN
		//SlideAssembler.createSlides(slideCount, slides, videoLayers, graphics2dLayers, graphics3dLayers, imageLayers, textLayers, audioLayers);
		mainStage.setScene(start);
		//mainStage.minWidthProperty().bind(start.heightProperty().multiply(2));
		//mainStage.minHeightProperty().bind(start.widthProperty().divide(2));
		mainStage.show();
	}
	
	//Run App
	public static void run() {
		presRunning = false;
		//Create File Browser
		FileChooser fileChooser = new FileChooser();
		//Change to another directory when we export as a jar
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setInitialFileName("defaultPresentation.xml");
		fileChooser.setTitle("Choose a file to present...");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Presentation Files (*.xml)","*.xml"));
		
		File file = fileChooser.showOpenDialog(mainStage);
		try{
			//Get File path
			xml = file.getPath(); 
			mainStage.setScene(loading);
			//Create slides using XML
			SlideAssembler.createSlides(xml, slides, videoLayers, graphics2d, 
										graphics3dLayers, imageLayers, textLayers, audioLayers, 
										shapes, images, audio, slideText, videos, models);
			//Show first slide of presentation
			showSlide(0); 
			presRunning = true;
		}catch(NullPointerException e) {
			showStart();
		}
		timer = new Timer();
		timer.start();
		//mainStage.setX((Screen.getPrimary().getVisualBounds().getWidth()-defaultXSize)/2);
		//mainStage.setY((Screen.getPrimary().getVisualBounds().getHeight()-defaultYSize)/2);
		//mainStage.setFullScreen(true);
	}
	
	public static Stage getStage() {
		return mainStage;
	}
	
	/** Show settings screen */
	public static void showSettings() {
		mainStage.setScene(settings); 
	}
	
	/** Show settings screen */
	public static void showStart() {
		mainStage.setScene(start); 
	}
	
	public static void showMap() {
		//mainStage.setScene(map);
	}
	
	/** sets slide index to home slide */
	public static void homeSlide() {
		System.out.println("HOME SLIDE");
		// whatever index is needed for home page**
		mainStage.setScene(slides.get(0).getSlide()); 
		currentSlide = 0;
	}
	
	public static void nextSlide() {
		try {
			System.out.println("NEXT SLIDE");
			currentSlide++;
			mainStage.setScene(slides.get(currentSlide).getSlide());
			timer.resetTimer(currentSlide);
		}catch(NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("Presentation Restarted");
			mainStage.setScene(slides.get(0).getSlide());
			currentSlide = 0;
			timer.resetTimer(currentSlide);
		}
	}
	
	public static void prevSlide() {
		try {
			System.out.println("PREV SLIDE");
			currentSlide--;
			mainStage.setScene(slides.get(currentSlide).getSlide());
			timer.resetTimer(currentSlide);
		}catch(NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("Presentation Restarted");
			mainStage.setScene(slides.get(0).getSlide());
			currentSlide = 0;
			timer.resetTimer(currentSlide);
		}
	}

	public static void setMainStage(Stage mainStage) {
		InteractiveLearningApp.mainStage = mainStage;
	}

	public static void showSlide(int index) {
		mainStage.setScene(slides.get(index).getSlide());
		currentSlide = index;
	}
	
	public static int getDefaultWidth() {
		return defaultXSize;
	}

	public static void setDefaultWidth(int defaultXSize) {
		InteractiveLearningApp.defaultXSize = defaultXSize;
	}

	public static int getDefaultHeight() {
		return defaultYSize;
	}

	public static void setDefaultHeight(int defaultYSize) {
		InteractiveLearningApp.defaultYSize = defaultYSize;
	}

	public static String getDefaultLanguage() {
		return defaultLanguage;
	}

	public static void setDefaultLanguage(String defaultLanguage) {
		InteractiveLearningApp.defaultLanguage = defaultLanguage;
	}

	public static String getDefaultFont() {
		return defaultFont;
	}

	public static void setDefaultFont(String defaultFont) {
		InteractiveLearningApp.defaultFont = defaultFont;
	}

	public static int getDefaultTextSize() {
		return defaultTextSize;
	}

	public static void setDefaultTextSize(int defaultTextSize) {
		InteractiveLearningApp.defaultTextSize = defaultTextSize;
	}

	public String getDefaultBGColour() {
		return defaultBGColour;
	}

	public static void setDefaultBGColour(String defaultBGColour) {
		InteractiveLearningApp.defaultBGColour = defaultBGColour;
	}

	public String getDefaultLineColour() {
		return defaultLineColour;
	}

	public static void setDefaultLineColour(String defaultLineColour) {
		InteractiveLearningApp.defaultLineColour = defaultLineColour;
	}

	public String getDefaultFontColour() {
		return defaultFontColour;
	}

	public static void setDefaultFontColour(String defaultFontColour) {
		InteractiveLearningApp.defaultFontColour = defaultFontColour;
	}

	public static String getDefaultFillColour() {
		return defaultFillColour;
	}

	public static void setDefaultFillColour(String defaultFillColour) {
		InteractiveLearningApp.defaultFillColour = defaultFillColour;
	}
	public static String getChosenFont() {
		return chosenFont;
	}

	public static void setChosenFont(String chosenFont) {
		InteractiveLearningApp.chosenFont = chosenFont;
	}

	public static int getChosenTextSize() {
		return chosenTextSize;
	}

	public static void setChosenTextSize(int chosenTextSize) {
		InteractiveLearningApp.chosenTextSize = chosenTextSize;
	}

	public String getChosenLanguage() {
		return chosenLanguage;
	}

	public static void setChosenLanguage(String chosenLanguage) {
		InteractiveLearningApp.chosenLanguage = chosenLanguage;
	}
}
