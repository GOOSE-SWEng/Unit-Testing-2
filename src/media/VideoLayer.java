package media;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.jmx.snmp.internal.SnmpSubSystem;

import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public class VideoLayer {
	int height;
	int width;
	StackPane sp = new StackPane();
	public ArrayList<Video> videos;
	SubScene window;

	public VideoLayer(int width, int height, ArrayList<Video> videos) {
		this.height = height;
		this.width = width;
		this.videos = videos;
		sp.setPickOnBounds(false);
		sp.setMinSize(width, height);
		window = new SubScene(sp, width, height);
	}

	public void addVideo(String urlName, int startTime, Boolean loop, int xStart, int yStart, int slideNo) throws IOException {
		// creates the video object and its subscene
		Video video = new Video(urlName, startTime, loop, xStart, yStart, 0, 0);
		
		if(video.videoFail == false) {
			// adds the video object to the array list
			videos.add(video);
			// adds the SubScene(created with the constructor) to the video layer stack pane
			sp.getChildren().add(video.get());
			video.get().setTranslateX(-(width/2 - video.get().getWidth()/2) + xStart/2 *width/100);
			video.get().setTranslateX(-(height/2 - video.get().getHeight()/2) + yStart/2 *height/100);
		}
		
	}
		

	public void removeVideo(Video video) {
		sp.getChildren().remove(video.get());
	}

	//please comment on what this exactly is
	public SubScene get() {
		return (window);
	}
}
