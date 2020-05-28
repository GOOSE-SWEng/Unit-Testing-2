package media;

import java.io.File;
import java.nio.file.Paths;

import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class for the audio handler
 * @author - Alex Kneller
 * @version - 1.1
 * @date - 21/05/20
 */
public class Audio {
	//global variables
	String source;
	Boolean controls = true;
	SubScene subScene;
	MediaPlayer player;
	//StackPane sp = new StackPane();
	int startTime;
	int slideNumber;
	boolean audioFail = false;
	
	public Audio(String urlName, int startTime, Boolean looping, Boolean controls, int width, 
				 int height, int slideNumber) {
		Media media = null;
		if(urlName.startsWith("https://")) {
			try {
				media = new Media(urlName);
			} catch (Exception e) {
				audioFail = true;
				System.out.println("Audio file not found, will not be added to the presentation");
				return;
			}
		}
		else if(urlName.startsWith("resources/")) {
			try {
				File audioFile = new File(urlName);
				media = new Media(audioFile.toURI().toString());
			} catch (Exception e) {
				audioFail = true;
				System.out.println("Audio file not found, will not be added to the presentation");
				return;
			}
		}
		else {
			System.out.println("Unknown audio origin.");
			return;
		}
		
		player = new MediaPlayer(media);
		if (looping) {
			player.setCycleCount(MediaPlayer.INDEFINITE);
		} 
		else {
			player.setCycleCount(1);
		}
		player.setVolume(0.1);
		source = urlName;
		//subScene = new SubScene(sp,width,height);
		this.startTime= startTime;
		this.slideNumber= slideNumber;
		
		// construct the SubScene in here
		if (controls) {
			
			//Creating the buttons
			Button playButton  = new Button("Play");
			Button pauseButton = new Button("Pause");
			Button resetButton = new Button("Reset");
			
			//Creating a GridPane with the 3 buttons in side-by-side
			GridPane gp = new GridPane();
			gp.add(playButton, 1, 0);
			gp.add(pauseButton, 1, 0);
			gp.add(resetButton, 2, 0);
			gp.setHgap(5);
			
			//Making the buttons all equal width inside the GridPane
			ColumnConstraints audioControls = new ColumnConstraints();
			audioControls.setPercentWidth(50);
			//If the percent widths of GridPane elements add up to more than 100, it scales them to a ratio of 100%
			gp.getColumnConstraints().addAll(audioControls, audioControls, audioControls);
			subScene = new SubScene(gp, width, height);
			//sp.getChildren().add(gp);
		}
	}
	
	public void start() {
		player.play();
	}
	public void stop() {
		player.stop();
	}
	public int getStartTime() {
		return(startTime);
	}

	public int getSlideNumber() {
		return(slideNumber);
	}
	public SubScene get() {
		return(subScene);
	}
}
