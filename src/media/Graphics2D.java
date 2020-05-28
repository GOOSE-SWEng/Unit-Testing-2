package media;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Class for creating the 2D graphics
 * @author Alex Marchant
 * @version - 1.0
 * @date - 28/04/20
 *
 */
public class Graphics2D {
	//global variables
	int paneHeight;
	int paneWidth;
	SubScene Window;
	public ArrayList<Shape> shapes = new ArrayList<Shape>();
	int currentPoly = 0;
	StackPane sp = new StackPane();
	
	// constructor
	public Graphics2D(int width, int height, ArrayList<Shape> shapes) {
		this.paneHeight = height;
		this.paneWidth = width;
		// clones arrayList
		this.shapes = shapes; 
		sp.setPickOnBounds(false);
		//creates the layer subscene
		Window = new SubScene(sp,paneWidth,paneHeight);
	}
	
	public void registerLine(float xStart, float xEnd, float yStart, float yEnd, String lineColour, 
			                 int startTime, int endTime, int slideNumber) {
		//converts the hash map to a colour
		Color lc = Color.web(lineColour);
		int lineWidth = 5;
		// creates shape object
		Shape shape = new Shape(lc,lc,paneWidth,paneHeight,lineWidth, startTime, endTime, slideNumber);
		//creates start point of the line
		shape.addPoint(xStart,yStart);
		// creates end point of the line
		shape.addPoint(xEnd,yEnd);
		// add shape to the array list
		shapes.add(shape);
		sp.getChildren().add(shape.get());
	}
	
	/**draw rectangle with solid colour */
	public void registerRectangle(float xStart, float yStart, float width, float height, String fillColour, 
			                      String id, int startTime, int endTime, int slideNumber) {
		Color fc = Color.web(fillColour);
		//creates the shape object for a solid colour shape
		Shape shape = new Shape(fc,fc,paneWidth,paneHeight,0, startTime, endTime, slideNumber);
		//add the 4 points to the rectangle
		shape.addPoint(xStart,yStart);
		shape.addPoint(xStart+width,yStart);
		shape.addPoint(xStart+width,yStart+height);
		shape.addPoint(xStart,yStart+height);
		shapes.add(shape);
		sp.getChildren().add(shape.get());
	}
	
	/**draw rectangle with gradient fill */
	public void registerRectangle(float xStart, float yStart, float width, float height, float shading_x1, 
			                      float shading_y1, String shading_colour1, float shading_x2, float shading_y2, 
			                      String shading_colour2, Boolean shading_cyclic, int startTime, int endTime, 
			                      int slideNumber) {
		Color c1 = Color.web(shading_colour1);
		Color c2 = Color.web(shading_colour2);
		//creates shape object for gradient fill shape
		Shape shape = new Shape(paneWidth, paneHeight, 0, c1, c2, shading_x1, shading_y1, shading_x2, shading_y2,
				                shading_cyclic, startTime, endTime, slideNumber);
		shape.addPoint(xStart,yStart);
		shape.addPoint(xStart+width,yStart);
		shape.addPoint(xStart+width,yStart+height);
		shape.addPoint(xStart,yStart+height);
		shapes.add(shape);
		sp.getChildren().add(shape.get());

	}
	/**draw solid colour oval */
	public void registerOval(float xStart, float yStart, float width, float height, String fillColour, 
			                 int startTime, int endTime, int slideNumber) {
		Color fc = Color.web(fillColour);
		Shape shape = new Shape(fc,fc,paneWidth,paneHeight,0, startTime, endTime, slideNumber);
		//oval constructor
		shape.drawOval((int)width,(int)height,(int)xStart,(int)yStart);
		shapes.add(shape);
		sp.getChildren().add(shape.get());
	}
	
	/**draw gradient fill oval */
	public void registerOval(float xStart, float yStart, float width, float height, float shading_x1, 
			                 float shading_y1, String shading_colour1, float shading_x2, float shading_y2, 
			                 String shading_colour2, Boolean shading_cyclic, int startTime, int endTime, 
			                 int slideNumber) {
		Color c1 = Color.web(shading_colour1);
		Color c2 = Color.web(shading_colour2);
		Shape shape = new Shape(paneWidth, paneHeight, 0, c1, c2, shading_x1, shading_y1, shading_x2, shading_y2,
				                shading_cyclic, startTime, endTime, slideNumber);
		shape.drawOval((int)width,(int)height,(int)xStart,(int)yStart);
		shapes.add(shape);
		sp.getChildren().add(shape.get());
	}
	//returns the subscene
	public SubScene get() {
		return Window;
	}
}
		
		