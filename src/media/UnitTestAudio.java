package media;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class UnitTestAudio extends Application {
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
			
		String urlName = "resources/unit_testing/HCTWJ.mp3";
		BorderPane borderPane = new BorderPane();
		Audio audio = new Audio(urlName, 0, true, true, 33, 10, 0);
		borderPane.setCenter(audio.get());
			
		return borderPane;
			
	}
}


