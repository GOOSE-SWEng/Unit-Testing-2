package media;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UnitTestVideo extends Application {
	
	// Testing window size
	private final static int testWidth=1280;
	private final static int testHeight=720;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Window setting
		primaryStage.setResizable(false);
		primaryStage.setWidth(testWidth);
		primaryStage.setHeight(testHeight+35);// +35 window upper frame
		// Build scene and initialize contents
		Scene scene = new Scene(createContent(),testWidth,testHeight);
		primaryStage.setTitle("Unit Test");
		primaryStage.setScene(scene);
		// Start displaying and colouring
		primaryStage.show();
	}
	
	public BorderPane createContent() throws IOException{
		
		String urlName = "resources/unit_testing/tododance.mp4";
		BorderPane borderPane = new BorderPane();
		Video video = new Video(urlName,0,true,70,80,0,0);
		borderPane.setCenter(video.get());
		
		return borderPane;
		
	}
}
