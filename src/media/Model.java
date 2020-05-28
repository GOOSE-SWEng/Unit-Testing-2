package media;

import java.io.File;
import java.util.ArrayList;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.PickResult;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import main.InteractiveLearningApp;

/**
 * 3D Model from interactive mesh - InteractiveMesh.org
 * @author - Tom Pound
 * @Version - 1.2
 * @date - 09/04/20
 */

public class Model {
	
	//Scene of the model with width, height and x,y position
	SubScene modelScene; 
	int width; //Width of SubScene
	int height; //Height of SubScenes
	int xStart; //X value for position
	int yStart; //Y value for position
	boolean modelFail = false;
	
	 //Group containing all 3D Elements
	Group modelGroup;
	//Global camera
	Camera camera; 
	//Timeline for animations
	Timeline timeline; 
	//Arraylist of interactive points
	ArrayList<InteractivePoints> points = new ArrayList<InteractivePoints>(); 
	ArrayList<Sphere> spheres = new ArrayList<Sphere>(); //Arraylist of Sphere buttons
	Boolean showControls = true;
	
	public Model(String url, int modelWidth, int modelHeight, int xStart, int yStart){
		//Width of SubScene
		this.width = modelWidth * InteractiveLearningApp.getDefaultWidth()/100; 
		//Height of SubScene
		this.height = modelHeight * InteractiveLearningApp.getDefaultHeight()/100; 
		System.out.println(width + ", " + height);
		this.xStart = xStart;
		this.yStart = yStart;
		//Create the and store scene
		modelScene = createModel(url); 
	}
	
	public Model(String url, int modelWidth, int modelHeight){
		//Width of SubScene
		this.width = modelWidth * InteractiveLearningApp.getDefaultWidth()/100; 
		//Height of SubScene
		this.height = modelHeight * InteractiveLearningApp.getDefaultHeight()/100; 
		showControls = false;
		System.out.println(width + ", " + height);
		//Create the and store scene
		modelScene = createModel(url); 
	}
	
	//Method to create model scene
	public SubScene createModel(String url) {
		//new camera for subscene
		camera = new PerspectiveCamera(); 
		
		if(url.startsWith("https://")) {
			System.out.println("Online source");
		}
		else if(url.startsWith("resources")) {
			try {
				File modelFile = new File(url);
				url = modelFile.toURI().toString();
			} catch (Exception e) {
				modelFail = true;
				System.out.println("3D Model not found, will not be added to presentation");
				return null;
			}
			
		}
		else {
			System.out.println("Unknown model origin.");
		}
		
		//FOR 3DS MODELS
		if(url.endsWith(".3ds")) {
			//3DS importer
			ModelImporter tdsImporter = new TdsModelImporter(); 
			tdsImporter.read(url);
			//Store in a node array
			Node[] tdsMesh = (Node[]) tdsImporter.getImport(); 
			tdsImporter.close();
			modelGroup = new Group();
			modelGroup.getChildren().addAll(tdsMesh);
			//Add clickable points
	        addPoints(); 
		}
		//FOR STL MODELS
		else if(url.endsWith(".stl")) {
			//STL importer
			StlMeshImporter stlImporter = new StlMeshImporter(); 
	        stlImporter.read(url);
	        //Store in a mesh
	        TriangleMesh cylinderHeadMesh = stlImporter.getImport(); 
	        //Creates new Mesh view
	        MeshView cylinderHeadMeshView = new MeshView();
	        //Sets material of model
	        cylinderHeadMeshView.setMaterial(new PhongMaterial(Color.BEIGE));
	        //Sets the mesh for the mesh view
	        cylinderHeadMeshView.setMesh(cylinderHeadMesh); 
	        stlImporter.close();
			modelGroup = new Group();
			modelGroup.getChildren().addAll(cylinderHeadMeshView);
			
			//Add clickable points
	        //addPoints(); 
		}
		else if(url.endsWith(".obj")) {
			System.out.print("The OBJ file type is not supported right now.");
		}
		else if(url.endsWith(".X3D")) {
			System.out.print("The X3D file type is not supported right now.");
		}
		else {
			System.out.println("Unable to open: " + url);
		}
		
        // Create Shape3D
		System.out.println("Model Imported");
		
		//modelGroup.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

		//Create pivot
        Translate pivot = new Translate(); 
        //Setup rotates
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
		camera.getTransforms().addAll(pivot, yRotate, zRotate, xRotate);
		//modelGroup.getTransforms().add(new Translate(width/2, -height/2),0));
		camera.getTransforms().add(new Translate(-width/2,-height/2,0));
		
		//Setup Animation
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        //Start with angle of 0
                        new KeyValue(yRotate.angleProperty(), 0) 
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        //Finish with angle of 360
                        new KeyValue(yRotate.angleProperty(), 360) 
                )
        );
        //Loop animation	
        timeline.setCycleCount(Timeline.INDEFINITE); 
        //Run animation
        timeline.play(); 
        
        //Return 3D mouse click point
        modelGroup.setOnMouseClicked(e->{
        	PickResult pr = e.getPickResult();
            System.out.println(pr.getIntersectedPoint());
        });
        
        //Pause when mouse is on model
        modelGroup.setOnMouseEntered(e->timeline.pause());
        //Play when mouse is off model
        modelGroup.setOnMouseExited(e->timeline.play());
        
        modelGroup.setOnScroll(e->{
        	if(e.getDeltaY() > 0) {
        		scale(1.1,1.1,1.1);
        	}else{
        		scale(0.9,0.9,0.9);
        	}
        });

        //Set pivot points
        pivot.setX(modelGroup.getTranslateX());
        pivot.setY(modelGroup.getTranslateY());
        pivot.setZ(modelGroup.getTranslateZ());
        
        BorderPane bp = new BorderPane();
        
        //Create subscene
		SubScene modelSubScene = new SubScene(modelGroup, width, height-40, true, SceneAntialiasing.BALANCED);
		System.out.println(width + ", " + (height-40));
		//Apply the camera
		modelSubScene.setCamera(camera); 
		bp.setCenter(modelSubScene);
		if(showControls) {
	        GridPane controls = createControls();
			bp.setBottom(controls);
			bp.setAlignment(controls, Pos.CENTER);
		}
		SubScene graphics3d = new SubScene(bp, width, height);
		return graphics3d;
	}
	
	public Group get() {
		return modelGroup;
	}
	public SubScene getScene() {
		return modelScene;
	}
	
	/** method for creating the control panel for the 3D models */
	public GridPane createControls() {
		GridPane gp = new GridPane();
		Button zoomIn = new Button("+");
		Button zoomOut = new Button("-");
		Button rotateLeft = new Button("<");
		Button rotateRight = new Button(">");
		Button play = new Button("Pause");
		
		zoomIn.setOnMouseClicked(e->scale(1.1, 1.1, 1.1));
		zoomOut.setOnMouseClicked(e->scale(0.9, 0.9, 0.9));
		rotateLeft.setOnMouseClicked(e->rotate(0, 0, 20));
		rotateRight.setOnMouseClicked(e->rotate(0, 0, -20));
		play.setOnMouseClicked(e->playAnimation(play));
		
		gp.add(zoomIn, 0,0);
		gp.add(zoomOut, 1,0);
		gp.add(rotateLeft, 2,0);
		gp.add(rotateRight, 3,0);
		gp.add(play, 4, 0);
		gp.setAlignment(Pos.CENTER);
		return gp;
	}
	
	public void playAnimation(Button play) {
		if(timeline.getStatus() == Status.RUNNING) {
			timeline.pause();
			play.setText("Play");
		}else{
			timeline.play();
			play.setText("Pause");
		}
	}
	
	public void addPoints() {
		for(int i=0;i<100;i++) {
			
		}
		for(int i=0;i<100;i++) {
			
		}
		points.add(new InteractivePoints(-160,-170,30));
		points.add(new InteractivePoints(-40,200,100));
		points.add(new InteractivePoints(60,-160,50));
		points.add(new InteractivePoints(180,-100,90));
		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.rgb(180, 180, 0, 0.75));
		Sphere point1 = new Sphere();
		Sphere point2 = new Sphere();
		Sphere point3 = new Sphere();
		Sphere point4 = new Sphere();
		point1.setMaterial(mat);
		point2.setMaterial(mat);
		point3.setMaterial(mat);
		point4.setMaterial(mat);
		
		point1.setRadius(40);
		point1.getTransforms().add(new Translate(points.get(0).getX(),points.get(0).getY(),points.get(0).getZ()));
		
		point2.setRadius(40);
		point2.getTransforms().add(new Translate(points.get(1).getX(),points.get(1).getY(),points.get(1).getZ()));

		point3.setRadius(40);
		point3.getTransforms().add(new Translate(points.get(2).getX(),points.get(2).getY(),points.get(2).getZ()));

		point4.setRadius(40);
		point4.getTransforms().add(new Translate(points.get(3).getX(),points.get(3).getY(),points.get(3).getZ()));
		modelGroup.getChildren().addAll(point1, point2,point3, point4);
	}
	
	/**Rotate Camera function */
	public void rotateCam(int Xangle, int Yangle, int Zangle) {
		modelScene.getCamera().getTransforms().add(new Rotate(Xangle, Rotate.X_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Yangle, Rotate.Y_AXIS));
		modelScene.getCamera().getTransforms().add(new Rotate(Zangle, Rotate.Z_AXIS));
	}
	/**Move Camera function */
	public void moveCam(int x,int y, int z) {
		camera.getTransforms().add(new Translate(x,y,z));
	}
	
	/**Rotate function */
	public void rotate(int Xangle, int Yangle, int Zangle) {
		modelGroup.getTransforms().add(new Rotate(Xangle, Rotate.X_AXIS));
		modelGroup.getTransforms().add(new Rotate(Yangle, Rotate.Y_AXIS));
		modelGroup.getTransforms().add(new Rotate(Zangle, Rotate.Z_AXIS));
	}
	/**Move function */
	public void move(int x,int y, int z) {
		modelGroup.getTransforms().add(new Translate(x,y,z));
	}
	
	/** Scale function */
	public void scale(double x,double y, double z) {
		modelGroup.getTransforms().add(new Scale(x,y,z));
	}
	
	public SubScene getModelScene() {
		return modelScene;
	}

	public void setModelScene(SubScene modelScene) {
		this.modelScene = modelScene;
	}
	
	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public void clickOnModel() {
        System.out.println();
	}
}
