package tools;

import java.awt.Graphics2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import media.Audio;

public class Render extends Canvas {
    public static int currentSlide = 0;
    public static int elapsedTime = 0;
    public List<IMediaElement> elementList = new ArrayList<>();
    private int startTime = (int) System.currentTimeMillis() / 1000;

    public void resetTime(){
        elapsedTime = 0;
        startTime = (int) System.currentTimeMillis() / 1000;
    }

    // Update for new slide with specified number
    public void updateSlide(List<IMediaElement> elements, int slideNumber){
        resetTime();
        currentSlide = slideNumber;
    }

    // Update for new slide
    public void updateSlide(List<IMediaElement> elements){
        resetTime();
        currentSlide++;
    }

    public Render() {
        super();
    }

    public Render(GraphicsConfiguration config) {
        super(config);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        elapsedTime = (int) System.currentTimeMillis() / 1000 - startTime;
        Graphics2D g2 = (Graphics2D) g;

        for(IMediaElement element: elementList){
            if (element.getStarttime() < elapsedTime || element.getEndtime() > elapsedTime)
                continue; // Not in range...
            switch (element.getType()){
                case TEXT:
                    g2.drawString((String) element.getDrawableContent(), element.getX(), element.getY());
                    break;
                case AUDIO:
                    ((Audio) element).start();
                    break;
                case IMAGE:
                    // TODO: add render options for these
                    break;
                case SHAPE:
                    break;
                case VIDEO:
                    break;
                case GRAPHICS2D:
                    break;
                case GRAPHICS3D:
                    break;
                case EMPTY: // Pass
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }
}
