package media;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UnitTest2D extends Application{
	// Error messages level
	private static int reportLevel=3;// 0: no information shown 1: Error 2: Warning 3: Info 
	// Testing window size
	private final static int testWidth=1280;
	private final static int testHeight=720;
	
	// Main function
	public static void main(String[] args) {
		boolean basicPass=false;
		try {
			reportInfo("Basic test start!");
			// Test whether initialization normal or not
			Shape shapetest=new Shape(Color.RED,Color.BLUE,10,20,3,1,2,3);
			if(!shapetest.serialization().equals("0x0000ffff,0xff0000ff,[],[],false,0,0,0,0,10.0,20.0,1,2,3"))
			{
				reportError(shapetest.serialization());
				throw new UnitTestException("Shape test 1 failed");
			}
			else
			{
				reportInfo(shapetest.serialization());
			}
			
			shapetest=new Shape(10,20,30,Color.RED,Color.BLUE,11,12,21,22,true, 1,2,3);
			if(!shapetest.serialization().equals("null,null,[],[],false,0,0,0,0,10.0,20.0,1,2,3"))
			{
				reportError(shapetest.serialization());
				throw new UnitTestException("Shape test 2 failed");
			}
			else
			{
				reportInfo(shapetest.serialization());
			}
			
			shapetest.addPoint(10, 20);
			if(!shapetest.serialization().equals("null,null,[10.0],[20.0],false,0,0,0,0,10.0,20.0,1,2,3"))
			{
				reportError(shapetest.serialization());
				throw new UnitTestException("Shape test 3 failed");
			}
			else
			{
				reportInfo(shapetest.serialization());
			}
			
			basicPass=true;
		}
		catch (UnitTestException e) {
			reportError("Basic test failed");
			e.notify();// Inform other series wrong in multi-threaded environment
		}
		catch (Exception e) {
			// Exception handling printing exception trace
			reportError("Unknow error, check stack trace.");
			e.printStackTrace();
			e.notify();
		}
		finally {
			reportInfo("Basic test end!");
		}
		if(basicPass) {
			reportInfo("javafx test start!");
			// Start javafx test
			Application.launch(args);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Window setting
		primaryStage.setResizable(false);
		primaryStage.setWidth(testWidth);
		primaryStage.setHeight(testHeight+35);// +35 window upper frame
		// Build scene and initialize contents
		Scene scene = new Scene(createContent(), testWidth, testHeight);  
		primaryStage.setTitle("Unit Test");
		primaryStage.setScene(scene);
		// Start displaying and colouring
		primaryStage.show();
	}
	
	public Parent createContent() throws Exception {  
		try {
		// Declare 2D rendering objects
		Graphics2D test = new Graphics2D(1280,720,new ArrayList<Shape>());
		// Register line, square and circles
		test.registerLine(0, 1280, 0, 720, "red", 0, 0, 0);
		test.registerLine(1280, 0, 0, 720, "red", 0, 0, 0);
		test.registerLine(0, 1280, 360, 360, "blue", 0, 0, 0);
		test.registerLine(640, 640, 0, 720, "blue", 0, 0, 0);
		// Simple quadrangle
		test.registerRectangle(320, 180, 100, 100, "yellow", "0", 0, 0,0);
		// Fountain fill
		test.registerRectangle(960, 180, 200, 100, 10, 10, "yellow", 0, 0, "green", true, 0, 0, 0);
		test.registerRectangle(960, 80, 200, 100, 10, 10, "yellow", 0, 0, "green", false, 0, 0, 0);
		// Simple circle
		test.registerOval(320, 540, 100, 100, "orange", 0, 0, 0);
		// Fountain fill
		test.registerOval(960, 540, 200, 100, 0, 0, "yellow", 10, 10, "green", true, 0, 0, 0);
		test.registerOval(960, 440, 200, 100, 0, 0, "yellow", 10, 10, "green", false, 0, 0, 0);
		// Generate registered shapes
		for(Shape element:test.shapes) {
			element.create();
		}
		// Fill background with black
		test.get().setFill(Color.BLACK);  
        Group group = new Group();  
        group.getChildren().add(test.get());  
        return group;
		}
		catch (Exception e) {
			// Exception handling printing exception trace
			reportError("createContent failed, check stack trace.");
			e.printStackTrace();
		}
        return null; 
    }  
	
	// Print the information
	static public void reportError(String info) {
		if(reportLevel>0) {
			System.out.println("ERROR:"+info);
		}
	}
	
	static public void reportError(String info,String data) {
		reportError(info+" DATA="+data);
	}
	
	static public void reportWarning(String info) {
		if(reportLevel>1) {
			System.out.println("Warning:"+info);
		}
	}
	
	static public void reportWarning(String info,String data) {
		reportWarning(info+" DATA="+data);
	}
	
	static public void reportInfo(String info) {
		if(reportLevel>2) {
			System.out.println("Info:"+info);
		}
	}
	
	static public void reportInfo(String info,String data) {
		reportInfo(info+" DATA="+data);
	}
}
