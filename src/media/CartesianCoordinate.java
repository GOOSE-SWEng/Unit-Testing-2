package media;


public class CartesianCoordinate {
	private double x;
	private double y;
	private double z;
	
	CartesianCoordinate(double x, double y){
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	CartesianCoordinate(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
}
