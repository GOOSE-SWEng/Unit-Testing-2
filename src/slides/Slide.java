package slides;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.InteractiveLearningApp;
import userInterface.ResizeBar;
import userInterface.ToolBar;
import media.*;
import tools.MediaElement;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Class for automating scene assembly
 */
public class Slide {
	private SubScene toolBar;// = ToolBar.createToolBar(1280);
	private SubScene resizeBar;
	//global media layer variables
	private VideoLayer videoLayer;
	private AudioLayer audioLayer;
	private ImageLayer imageLayer;
	private TextLayer textLayer;
	private Graphics2D graphics2D;
	private Graphics3DLayer graphics3DLayer;
	private Scene slide;

	//global variables for scene size 
	private BorderPane bp = new BorderPane();
	private StackPane sp = new StackPane();
	private int width;
	private int height;
	private double xOff;
	private double yOff;
	
	//id and duration of slide
	private String id;
	private int duration;
	//private ArrayList<Object> elements = new ArrayList<Object>();
	private ArrayList<Video> slideVideos = new ArrayList<Video>();
	private ArrayList<Audio> slideAudio = new ArrayList<Audio>();
	private ArrayList<SlideText> slideTexts = new ArrayList<SlideText>();
	private ArrayList<SlideImage> slideImages = new ArrayList<SlideImage>();
	private ArrayList<Shape> slideShapes = new ArrayList<Shape>();
	
	private ArrayList<MediaElement> slideElements = new ArrayList<MediaElement>();
	SubScene test;
	Graphics2D graphics;
	/**
	 * Method for instantiating the objects to be placed on the scene
	 * @param mainStage - main stage of window
	 * @param width - width of window
	 * @param height - height of window
	 * @param xOffset - X coordinate offset 
	 * @param yOffset - Y coordinate offset
	 * @param vl - video layer
	 * @param al - audio layer
	 * @param il - image layer
	 * @param tl - text layer
	 * @param G2D - 2D graphics layer
	 * @param G3D - 3D graphics layer
	 * @param shapes - shapes
	 * @param images - images
	 * @param audio - audio
	 * @param slideTexts - text
	 */
	public Slide(Stage mainStage, int width, int height, double xOffset, double yOffset,
			     ArrayList<VideoLayer> vl, 
			     ArrayList<AudioLayer> al, 
			     ArrayList<ImageLayer> il, 
			     ArrayList<TextLayer> tl, 
			     ArrayList<Graphics2D> G2D, 
			     ArrayList<Graphics3DLayer> G3D,
			     ArrayList<Shape> shapes, 
			     ArrayList<SlideImage> images, 
			     ArrayList<Audio> audio, 
			     ArrayList<SlideText> slideTexts) {
		//stores variables from main program 
		this.width = width;
		this.height = height;
		
		//create toolbar and resize bar
		toolBar = ToolBar.createToolBar(width, id);
		resizeBar = ResizeBar.CreateResizeBar(width);

		xOff = xOffset;
		yOff = yOffset;
		
		sp.setMinSize(width,height);
		sp.setAlignment(Pos.TOP_LEFT);
		
		/*videoLayer = new VideoLayer(this.width,this.height);
		audioLayer = new AudioLayer(this.width,this.height,audio,sp);
		imageLayer = new ImageLayer(this.width,this.height,images,sp);
		textLayer = new TextLayer(this.width,this.height,slideTexts,sp);
		graphics2D = new Graphics2D(this.width,this.height,shapes,sp);
		graphics3DLayer = new Graphics3DLayer(this.width,this.height);*/
		
		vl.add(videoLayer);
		al.add(audioLayer);
		il.add(imageLayer);
		tl.add(textLayer);
		G2D.add(graphics2D);
		G3D.add(graphics3DLayer);
		
		///////// taken from the start screen class
		toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOff = event.getSceneX();
				yOff = event.getSceneY();
			}
		});
		
		//Move window with mouse
		toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setX(event.getScreenX() - xOff);
				mainStage.setY(event.getScreenY() - yOff);	
			}
		});
	}
	
	public Slide(int width, int height, String id, int duration) {
		this.width = width;
		this.height = height;
		this.id = id;
		this.duration = duration;
		toolBar = ToolBar.createToolBar(width, id);
		resizeBar = ResizeBar.CreateResizeBar(width);

		bp.setTop(toolBar);
		bp.setCenter(sp);
		bp.setBottom(resizeBar);
		toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOff = event.getSceneX();
				yOff = event.getSceneY();
			}
		});
		
		//Move window with mouse
		toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				InteractiveLearningApp.getStage().setX(event.getScreenX() - xOff);
				InteractiveLearningApp.getStage().setY(event.getScreenY() - yOff);	
			}
		});
		sp.setPickOnBounds(false);
		slide = new Scene(bp,width, height);
		if(InteractiveLearningApp.style.equals("default")) {
			slide.getStylesheets().add("style/ContentScreen/contentScreen.css");
		}else if(InteractiveLearningApp.style.equals("nightmode")) {
			slide.getStylesheets().add("style/ContentScreen/contentScreenNight.css");
		}else if(InteractiveLearningApp.style.equals("colourblind")) {
			slide.getStylesheets().add("style/ContentScreen/contentScreenCB.css");
		}else {
			System.out.println("Unknown style scheme.");
		}
	}
	
	public void applyLayers() {
		sp.getChildren().add(graphics2D.get());
		sp.getChildren().add(audioLayer.get());
		sp.getChildren().add(videoLayer.get());
		sp.getChildren().add(textLayer.get());
		sp.getChildren().add(imageLayer.get());
		sp.getChildren().add(graphics3DLayer.get());

	}
	
	/*public void addMediaElements(String type, List elements) {
		for(int i; i<elements.size();i++) {
			slideElements.add(elements.get(i).getS
		}
	}*/
	
	public void defaultStyle() {
		slide.getStylesheets().clear();
		slide.getStylesheets().add("style/ContentScreen/contentScreen.css");
	}
	
	public void nightmodeStyle() {
		slide.getStylesheets().clear();
		slide.getStylesheets().add("style/ContentScreen/contentScreenNight.css");
	}
	
	public void colourblindStyle() {
		slide.getStylesheets().clear();
		slide.getStylesheets().add("style/ContentScreen/contentScreenCB.css");
	}
	
	
	
	
	public ArrayList<Video> getSlideVideos() {
		return slideVideos;
	}

	public void setSlideVideos(ArrayList<Video> slideVideos) {
		this.slideVideos = slideVideos;
	}

	public ArrayList<Audio> getSlideAudio() {
		return slideAudio;
	}

	public void setSlideAudio(ArrayList<Audio> slideAudio) {
		this.slideAudio = slideAudio;
	}

	public ArrayList<SlideText> getSlideTexts() {
		return slideTexts;
	}

	public void setSlideTexts(ArrayList<SlideText> slideTexts) {
		this.slideTexts = slideTexts;
	}

	public ArrayList<SlideImage> getSlideImages() {
		return slideImages;
	}

	public void setSlideImages(ArrayList<SlideImage> slideImages) {
		this.slideImages = slideImages;
	}

	public ArrayList<Shape> getSlideShapes() {
		return slideShapes;
	}

	public void setSlideShapes(ArrayList<Shape> slideShapes) {
		this.slideShapes = slideShapes;
	}

	public void setSlideElements(ArrayList<MediaElement> slideElements) {
		this.slideElements = slideElements;
	}

	/*public ArrayList<Object> getElements() {
		return elements;
	}

	public void setElements(ArrayList<Object> elements) {
		this.elements = elements;
	}*/

	public ArrayList<MediaElement> getSlideElements() {
		return slideElements;
	}

	public Scene getSlide() {
		return slide;
	}

	public void setSlide(Scene slide) {
		this.slide = slide;
	}

	public VideoLayer getVideoLayer() {
		return videoLayer;
	}

	public void setVideoLayer(VideoLayer videoLayer) {
		this.videoLayer = videoLayer;
	}

	public AudioLayer getAudioLayer() {
		return audioLayer;
	}

	public void setAudioLayer(AudioLayer audioLayer) {
		this.audioLayer = audioLayer;
	}

	public ImageLayer getImageLayer() {
		return imageLayer;
	}

	public void setImageLayer(ImageLayer imageLayer) {
		this.imageLayer = imageLayer;
	}

	public TextLayer getTextLayer() {
		return textLayer;
	}

	public void setTextLayer(TextLayer textLayer) {
		this.textLayer = textLayer;
	}

	public Graphics2D getGraphics2D() {
		return graphics2D;
	}

	public void setGraphics2D(Graphics2D graphics2d) {
		graphics2D = graphics2d;
	}

	public Graphics3DLayer getGraphics3DLayer() {
		return graphics3DLayer;
	}

	public void setGraphics3DLayer(Graphics3DLayer graphics3dLayer) {
		graphics3DLayer = graphics3dLayer;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/*public Scene update(){
		bp.setTop(toolBar);
		bp.setCenter(sp);
		slide = new Scene(sp);
		return slide;
	}*/
}
