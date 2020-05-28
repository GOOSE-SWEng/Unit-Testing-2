package media;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;

public class Shape {
	private Color fill = null;
	private Color line = null;
	private Group group;//to be returned
	private Canvas canvas; 
	private GraphicsContext gc; 
	private List<Double> pointsX = new ArrayList<Double>(); //points for the polygons
	private List<Double> pointsY = new ArrayList<Double>();
	private boolean oval = false;
	private int oheight = 0;//oval height
	private int owidth = 0;
	private int ocx = 0;//oval reference coordinates
	private int ocy = 0;
	private float width;//frame size
	private float height;
	private int startTime = 0;
	private int endTime;
	private int slideNumber;

	
	public Shape(Color lineColour, Color fillColour,int w, int h, int lw, int startTime, int endTime, int slideNumber) { // constructor for a solid colour shape
		group = new Group();
		fill = fillColour;
		line = lineColour;
		width = w;
		height = h;
		canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(fill);
		gc.setStroke(line);
		gc.setLineWidth(lw);
		this.startTime = startTime;
		this.endTime = endTime;
		this.slideNumber = slideNumber;
	}
	public Shape(int w, int h, int lw, Color c1, Color c2, float c1x, float c1y, float c2x, float c2y, Boolean Cyclical, int startTime, int endTime, int slideNumber) { //constructor for a shape with a colour gradient
		group = new Group();
		width = w;
		height = h;
		canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		this.startTime = startTime;
		this.endTime = endTime;
		this.slideNumber = slideNumber;
		
		if (Cyclical == true) {//sets up a cyclical gradient pattern
			Stop[] stops = new Stop[] {new Stop(0,c1), new Stop(1,c2)};
			LinearGradient lg = new LinearGradient(c1x,c1y,c2x,c2y,false,CycleMethod.REFLECT,stops);
			gc.setFill(lg);
			gc.setStroke(Color.TRANSPARENT);
		}
		else {//standard linear gradient
			Stop[] stops = new Stop[] {new Stop(0,c1), new Stop(1,c2)};
			LinearGradient lg = new LinearGradient(c1x,c1y,c2x,c2y,false,CycleMethod.NO_CYCLE,stops);
			gc.setFill(lg);
			gc.setStroke(Color.TRANSPARENT);
		}
	}
	
	public void addPoint(double x, double y) {//adds point to the polygon
		pointsX.add(x);
		pointsY.add(y);
	}
	
	public void drawOval (int width, int height, int cx, int cy) {// draws an oval, cx,cy correspond to distance from the top left corner to enclosing box
		oval = true; // changes shape from polygon to oval
		oheight = height;
		owidth = width;
		ocx = cx;
		ocy = cy;
	}
	
	public void create() {
		if (oval == true) {
			gc.fillOval(ocx,ocy,owidth,oheight);
			gc.strokeOval(ocx,ocy,owidth,oheight);
			group.getChildren().add(canvas);
		}
		else {
			double[] pointX = new double[pointsX.size()];// used to create an array of points from an arraylist
			double[] pointY = new double[pointsY.size()];
			
			//adding arraylist points to an array
			for (int i = 0; i<pointsX.size(); i++) {
				pointX[i] = pointsX.get(i);
			}
			
			for (int i = 0; i<pointsY.size(); i++) {
				pointY[i] = pointsY.get(i);
			}
			
			gc.strokePolygon(pointX,pointY,pointsX.size());//creates a polygon outline
			gc.fillPolygon(pointX,pointY,pointsX.size());//creates the polygon solid
			group.getChildren().add(canvas);//adds canvas to the group
		}
		
	}
	public Group get() {
		return group;
	}

	public void destroy() {//removes the canvas from the group
		group.getChildren().remove(canvas);
	}
	
	public int getSlideNumber() {
		return(slideNumber);
	}
	
	public int getStartTime() {
		return(startTime);
	}
	
	public int getEndTime() {
		return(endTime);
	}
	
	// Object serialization for test
	public String serialization() {
		String data="";
		String fillS="null";
		String lineS="null";
		if(fill!=null) {
			fillS=fill.toString();
		}
		if(line!=null) {
			lineS=line.toString();
		}
		data=data+fillS+","+lineS+","+pointsX.toString()+","
				 +pointsY.toString()+","+oval+","+oheight+","+owidth+","+ocx+","+ocy
				 +","+width+","+height+","+startTime+","+endTime+","+slideNumber;
		return data;
	}
}
