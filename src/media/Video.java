package media;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

/**
 * Class for the video player
 * @author - Rimas Radziunas and Cezara-Lidia Jalba
 * @version - 1.2
 * @date - 20/04/20
 */
public class Video {
	//global variables
	int xstart;
	int ystart;
	int startTime;
	int canvasWidth;
	int canvasHeight;
	String urlName;
	Boolean loop;
	SubScene subScene;
	boolean videoFail = false;

	private MediaPlayer mediaPlayer;
	private Duration totTime;
	private Pane toolbarNode;
	private Slider volumeSlider;
	private Label currentTimeLabel;
	private Label totTimeLabel;

	/**
	 * Method to create the video object
	 * @param urlName - URL of video file to be played
	 * @param startTime - how long after the slide is opened should the video start
	 * @param loop - should the video loop
	 * @param xStart - x coordinate of the top left corner of the video object
	 * @param yStart - y coordinate of the top left corner of the video object
	 * @param canvasWidth - width of canvas
	 * @param canvasHeight - height of canvas
	 * @throws IOException - if video cannot be found
	 */
	public Video(String urlName, int startTime, Boolean loop, int xStart, int yStart, int canvasWidth, int canvasHeight)
			throws IOException {
		// Loads the media player layout from a FXML file
		BorderPane root = FXMLLoader.load(getClass().getClassLoader().getResource("media/videoPlayer.fxml"));

		// creates a subscene
		subScene = new SubScene(root, 600, 400);
		// Set subscene position
		//subScene.setLayoutX(xStart);
		//subScene.setLayoutY(yStart);

		// Video control bar, retrieved from the root
		toolbarNode = (Pane) root.getBottom();
		currentTimeLabel = (Label) toolbarNode.getChildren().get(7);
		totTimeLabel = (Label) toolbarNode.getChildren().get(9);

		// video media file
		Media media = null;
		if(urlName.startsWith("https://")) {
			try {
				media = new Media(urlName);
			} catch (Exception e) {
				videoFail = true;
				System.out.println("Video file not found, will not be added to the presentation");
				return;
			}	
		}
		else if(urlName.startsWith("resources/")) {
			try {
				File vidFile = new File(urlName);
				media = new Media(vidFile.toURI().toString());
			} catch (Exception e) {
				videoFail = true;
				System.out.println("Video file not found, will not be added to the presentation");
				return;
			}
			
		}
		else {
			System.out.println("Unknown video origin.");
		}
		
		// Create media player with the video media file
		mediaPlayer = new MediaPlayer(media);

		// Volume slider
		volumeSlider = (Slider) toolbarNode.getChildren().get(6);
		volumeSlider.setValue(mediaPlayer.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				mediaPlayer.setVolume(volumeSlider.getValue() / 100);
			}
		});

		// Set the video to auto play[Not sure if needed, video is auto played when the
		// there is a delay set
		// mp.setAutoPlay(true);

		// Set video to loop
		if (loop) {
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		}

		// Video control bar
		toolbarNode = (Pane) root.getBottom();

		// Playback slider
		Slider playbackSlider = (Slider) toolbarNode.getChildren().get(5);

		// Set the current play time to the time specified by the play back slider
		playbackSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {

				if (playbackSlider.isValueChanging() || playbackSlider.isPressed()) {
					if (mediaPlayer.getStatus() == Status.PLAYING || mediaPlayer.getStatus() == Status.PAUSED) {

						mediaPlayer.seek(totTime.multiply(playbackSlider.getValue() / 100));
						setTimeLabel(mediaPlayer.getCurrentTime(), currentTimeLabel);
					}
					// When the video is stopped and the slider is moved then set media player to
					// paused since seek function cannot be used while the status is STOPPED
					else if (mediaPlayer.getStatus() == Status.STOPPED) {
						mediaPlayer.pause();
						mediaPlayer.seek(totTime.multiply(playbackSlider.getValue() / 100));
					}
				}
			}
		});

		// when the video is paused, change the icon of the play button to play icon
		mediaPlayer.setOnPaused(new Runnable() {
			@Override
			public void run() {
				Button play = (Button) toolbarNode.getChildren().get(0);
				ImageView playImg = (ImageView) play.getGraphic();
				playImg.setImage(new Image(getClass().getResourceAsStream("/graphics/play.png")));
				setTimeLabel(mediaPlayer.getCurrentTime(), currentTimeLabel);
			}
		});

		// Set on halted: when critical error occurs
		mediaPlayer.setOnHalted(new Runnable() {
			@Override
			public void run() {
				System.out.println("HALTED");
			}
		});

		// when video is stopped, time should go back to 0
		mediaPlayer.setOnStopped(new Runnable() {
			@Override
			public void run() {
				playbackSlider.setValue(0);
				// Sets the current time to 0
				setTimeLabel(mediaPlayer.getCurrentTime(), currentTimeLabel);
				Button play = (Button) toolbarNode.getChildren().get(0);
				ImageView playImg = (ImageView) play.getGraphic();
				//play button should have play icon on
				playImg.setImage(new Image(getClass().getResourceAsStream("/graphics/play.png")));
			}
		});

		// when the video is ready (loaded), set the total time of the video label
		mediaPlayer.setOnReady(new Runnable() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						totTime = mediaPlayer.getMedia().getDuration();
						setTimeLabel(totTime, totTimeLabel);
					}
				});
			}
		});

		// set the slider to the beginning after video ends and stop the player
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				if (!loop) {
					mediaPlayer.stop();
					mediaPlayer.seek(totTime.multiply(playbackSlider.getValue() / 100));
				}
				
			}
		});

		// Set the play button to display pause icon when the video is playing
		mediaPlayer.setOnPlaying(new Runnable() {
			@Override
			public void run() {
				Button play = (Button) toolbarNode.getChildren().get(0);
				ImageView playImg = (ImageView) play.getGraphic();
				playImg.setImage(new Image(getClass().getResourceAsStream("/graphics/pause.png")));
			}
		});

		// Set the current time label and play back slider to follow the current time of
		// the video being played
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				Duration currentTime = mediaPlayer.getCurrentTime();
				playbackSlider.setValue(currentTime.toMillis() / totTime.toMillis() * 100);
				setTimeLabel(currentTime, currentTimeLabel);
			}
		});

		// Add media player to media view
		Pane p = (Pane) root.getCenter();
		p.getChildren().get(0);
		MediaView mv = (MediaView) p.getChildren().get(0);
		mv.setMediaPlayer(mediaPlayer);
	}

	public SubScene get() {
		return subScene;
	}

	// removes video from the subscene
	public void remove() {
		BorderPane root = (BorderPane) subScene.getRoot();
		root.getChildren().clear();
	}

	// Set the text of the label to display the time
	private void setTimeLabel(Duration time, Label label) {
		int min = (int) time.toMinutes();
		int sec = (int) (time.toSeconds() - 60 * min);
		if (sec < 10) {
			label.setText(min + ":0" + sec);
		} else {
			label.setText(min + ":" + sec);
		}
	}
		
	public void play() {
		mediaPlayer.play();
		System.out.println("VIDEO MEDIA PLAYING" + urlName);
	}
	public void stop() {
		mediaPlayer.stop();
		System.out.println("VIDEO MEDIA STOPPING" + urlName);
	}
	
	public MediaPlayer getPlayer() {
		return mediaPlayer;
	}
}
