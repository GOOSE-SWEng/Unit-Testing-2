package tools;

public class MediaElement {
	int slideNumber = -1;
	int startTime = -1;
	int endTime = -1;
	int mediaId = 0;
    enum TYPE{
        TEXT,
        AUDIO,
        LINE,
        GRAPHICS3D,
        IMAGE,
        SHAPE,
        VIDEO,
        EMPTY
    }
	TYPE mediaType = null;
	
	public MediaElement(int slideNumber, int startTime, int endTime, String type, int id) {
		this.slideNumber = slideNumber;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mediaId = id;
        switch (type){
        case "text":
        	this.mediaType = TYPE.TEXT;
            break;
        case "audio":
        	this.mediaType = TYPE.AUDIO;
            break;
        case "image":
        	this.mediaType = TYPE.IMAGE;
            break;
        case "shape":
        	this.mediaType = TYPE.SHAPE;
            break;
        case "video":
        	this.mediaType = TYPE.VIDEO;
            break;
        case "g3d":
        	this.mediaType = TYPE.GRAPHICS3D;
        	break;
        default:
        	this.mediaType = TYPE.EMPTY;
            break;
        }
	}
}
