package media;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * SlideImage class created for Goose-SWEng, as per contract
 *
 * @author CUBIXEL
 *
 */
public class SlideImage {


  private Canvas toDisplay = null;
  private int slideNumber = 0;
  private Group group = null;
  private int startTime = 0;
  private int endTime = 0;
  boolean imageFail = false;

  public SlideImage(String url, float floatX, float floatY, float floatW, float floatH,
      int startTime, int endTime, int slideNumber, int sceneWidth, int sceneHeight) {

    // Set variables using provided data
    this.startTime = startTime;
    this.endTime = endTime;
    this.slideNumber = slideNumber;

    // Create Group
    this.group = new Group();

    // Calculate pixel values for x, y, w and h
    int x = Math.toIntExact(Math.round((floatX / 100) * sceneWidth));
    int y = Math.toIntExact(Math.round((floatY / 100) * sceneHeight));
    int w = Math.toIntExact(Math.round((floatW / 100) * sceneWidth));
    int h = Math.toIntExact(Math.round((floatH / 100) * sceneHeight));

    System.out.println("Image Added: " + x + ", " + y + " On a subScene of size: " + sceneWidth + "," + sceneHeight);
    //Load image and create canvas
    
	if(url.startsWith("https://")) {

	}else if(url.startsWith("resources/")) {
		try {
			File imgFile = new File(url);
			url = imgFile.toURI().toString();
		} catch (Exception e) {
			imageFail = true;
			System.out.println("Image not found, will not be added to presentation");
			return;
		}
		
	}else {
		System.out.println("Unknown image origin.");
	}
	
    Image picture = new Image(url, w, h, false, true);
    toDisplay = new Canvas((double) w + x, (double) h + y);

    //Draw image to canvas
    GraphicsContext gc = toDisplay.getGraphicsContext2D();
    gc.drawImage(picture, x, y);
  }

  public Group get() {
    return group;
  }

  public int getStartTime() {
    return startTime;
  }

  public int getEndTime() {
    return endTime;
  }

  public int getSlideNo() {
    return slideNumber;
  }

  public void start() {
    if (!group.getChildren().contains(toDisplay)) {
      group.getChildren().add(toDisplay);
    } else {
      System.err.println("Tried to add duplicate object; ignored");
    }
      
  }

  public void remove() {
    if (group.getChildren().contains(toDisplay)) {
      group.getChildren().remove(toDisplay);
    } else {
      System.err.println("Tried to remove non-existant object; ignored");
    }
  }
}
