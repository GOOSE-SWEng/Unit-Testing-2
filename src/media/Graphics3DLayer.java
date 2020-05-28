package media;

import java.util.ArrayList;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import main.InteractiveLearningApp;

public class Graphics3DLayer {
	int paneHeight;
	int paneWidth;
	StackPane sp = new StackPane();
	ArrayList<Model> models;
	SubScene window;
	
	public Graphics3DLayer(int width,int height, ArrayList<Model> models){
		this.paneHeight = height*InteractiveLearningApp.getDefaultHeight()/100;
		this.paneWidth = width*InteractiveLearningApp.getDefaultWidth()/100;
		this.models = models;
		System.out.println(paneWidth + ", " + paneHeight);
		sp.setPickOnBounds(false);
		//sp.getChildren().add(canvas);
		sp.setMinSize(paneWidth, paneHeight);
		window = new SubScene(sp, paneWidth, paneHeight);
	}
	
	public void add(String url, int modelWidth, int modelHeight, int xStart, int yStart) {
		Model model =  new Model(url, modelWidth, modelHeight, xStart, yStart);
		if(model.modelFail == false) {
			models.add(model);
			sp.getChildren().add(model.getModelScene());
		}
		
	}
	
	public void remove(Model object) {
		sp.getChildren().remove(object);
	}
	
	public SubScene get() {
		return window;
	}
}