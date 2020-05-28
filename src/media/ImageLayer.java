package media;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class ImageLayer {
	int sceneHeight;
	int sceneWidth;
	StackPane sp = new StackPane();
	public ArrayList<SlideImage> images;
	SubScene window;
	
	public ImageLayer(int width,int height, ArrayList<SlideImage> images){
		sceneHeight = height;
		sceneWidth = width;
		sp.setMinSize(sceneWidth,sceneHeight);
		sp.setPickOnBounds(false);
		sp.setAlignment(Pos.TOP_LEFT);
		this.images = images;
		System.out.println("Image Layer created: " + width + ", " + height);
	}
	
	public void add(String urlName, int xStart, int yStart, int width, int height, int startTime, int endTime, int slideNumber) {
		//constructor for the image object
		SlideImage image = new SlideImage(urlName, xStart, yStart, width, height, startTime, endTime, slideNumber, sceneWidth, sceneHeight);
		if(image.imageFail == false) {
			images.add(image);
			sp.getChildren().add(image.get());
			//image.get().setLayoutX(xStart);
			//image.get().setLayoutY(yStart);
		}
		
	}
	
	public void remove(SlideImage object) {
		sp.getChildren().remove(object.get());
	}
	
	public StackPane get() {
    //window = new SubScene(sp,sceneWidth,sceneHeight);
		return (sp);
  }
  
  public ArrayList<SlideImage> getList() {
    return images;
  }
}