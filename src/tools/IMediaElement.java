package tools;

import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;

public interface IMediaElement {
    int starttime = 0, endtime = Integer.MAX_VALUE;
    int height = 720, width = 1280;
    int X = 0, Y = 0;
    int slideNumber = 0;
    StackPane sp = new StackPane();
    SubScene subScene = null;
    public TYPE type = TYPE.EMPTY;

    void add();
    void remove();
    void start();
    void stop();
    int getStarttime();
    int getEndtime();
    int getSlideNumber();
    int getX();
    int getY();
    SubScene get();
    TYPE getType();
    Object getDrawableContent();

    enum TYPE{
        TEXT, AUDIO, GRAPHICS2D, GRAPHICS3D, IMAGE, SHAPE, VIDEO, EMPTY
    }
}
