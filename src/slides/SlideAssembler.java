package slides;

import java.io.File;
import java.util.ArrayList;

import media.Audio;
import media.AudioLayer;
import media.Graphics2D;
import media.Graphics3DLayer;
import media.ImageLayer;
import media.Model;
import media.Shape;
import media.SlideImage;
import media.SlideText;
import media.TextLayer;
import media.Video;
import media.VideoLayer;
import tools.XMLParser;

public class SlideAssembler {
	private static int slideNo;
	private ArrayList<Slide> slides;
	ArrayList<VideoLayer> videoLayers;
	ArrayList<Graphics2D> g2dLayers;
	ArrayList<Graphics3DLayer> graphics3dLayers;
	ArrayList<ImageLayer> imageLayers;
	ArrayList<TextLayer> textLayers;
	ArrayList<AudioLayer> audioLayers;
	
	ArrayList<Audio> audio;
	ArrayList<Graphics2D> graphics2d;
	ArrayList<Model> models;
	ArrayList<SlideImage> images;
	ArrayList<Shape> shapes;
	ArrayList<SlideText> slideTexts;
	ArrayList<Video> videos;
	
	
	public static void createSlides(String xml, ArrayList<Slide> slides, ArrayList<VideoLayer> videoLayers,
														ArrayList<Graphics2D> g2dLayers,
														ArrayList<Graphics3DLayer> graphics3dLayers,
														ArrayList<ImageLayer> imageLayers,
														ArrayList<TextLayer> textLayers,
														ArrayList<AudioLayer> audioLayers,
														ArrayList<Shape> shapes, 
														ArrayList<SlideImage> images, 
														ArrayList<Audio> audio, 
														ArrayList<SlideText> slideTexts,
														ArrayList<Video> videos, 
														ArrayList<Model> models){ //Slide/media arraylists

		XMLParser parser = new XMLParser(xml, slides, audioLayers, videoLayers, textLayers, imageLayers, g2dLayers, graphics3dLayers, audio, models, images, shapes, slideTexts, videos);
		slideNo = parser.getSlideCount();
		
		//May need to include if statements for blank layers
		for(int i = 0; i< slideNo;i++) {
			slides.get(i).setAudioLayer(audioLayers.get(i));
			slides.get(i).setGraphics2D(g2dLayers.get(i));
			slides.get(i).setTextLayer(textLayers.get(i));
			slides.get(i).setVideoLayer(videoLayers.get(i));
			slides.get(i).setImageLayer(imageLayers.get(i));
			slides.get(i).setGraphics3DLayer(graphics3dLayers.get(i));
			slides.get(i).applyLayers();
		}
	}
}
