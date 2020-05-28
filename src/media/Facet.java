package media;

public class Facet {
	private CartesianCoordinate point0;
	private CartesianCoordinate point1;
	private CartesianCoordinate point2;
	private CartesianCoordinate normal;
	private double tempx;
	private double tempy;
	private double tempz;
	
	public Facet(double xa, double ya, double za, double xb, double yb, double zb, 
			     double xc, double yc, double zc, double xn, double yn, double zn) {
		point0.setX(xa);
		point0.setY(ya);
		point0.setZ(za);
		
		point1.setX(xb);
		point1.setY(yb);
		point1.setZ(zb);
		
		point2.setX(xc);
		point2.setY(xc);
		point2.setZ(xc);
		
		normal.setX(xn);
		normal.setY(yn);
		normal.setZ(zn);
		
	}
	public Facet(CartesianCoordinate p0, CartesianCoordinate p1, CartesianCoordinate p2, CartesianCoordinate n) {
		point0 = p0;
		point1 = p1;
		point2 = p2;
		normal = n;
	}
	public void rotateFacet(char axis, double angle, CartesianCoordinate center) {
		rotate(axis,angle,center,point0);
		rotate(axis,angle,center,point1);
		rotate(axis,angle,center,point2);
		rotate(axis,angle,center,normal);
	}
	private void rotate(char axis, double angle, CartesianCoordinate center, CartesianCoordinate p) {
		double currx = p.getX()-center.getX();
		double curry = p.getY()-center.getY();
		double currz = p.getZ()-center.getZ();
		
		if (axis == 'x') {
			tempx = currx;
			tempy = (curry*Math.cos(angle))-(currz*Math.sin(angle));
			tempz = (curry*Math.sin(angle))+(currz*Math.cos(angle));
		}
		else if(axis == 'y') {
			tempx = (currx*Math.cos(angle))+(currz*Math.sin(angle));
			tempy = curry;
			tempz = (-currx*Math.sin(angle))+(currz*Math.cos(angle));
		}
		else if(axis == 'z') {
			tempx = (currx*Math.cos(angle))-(curry*Math.sin(angle));
			tempy = (currx*Math.sin(angle))+(curry*Math.cos(angle));
			tempz = currz;
		}
		else {}
		
		p.setX(tempx+center.getX());
		p.setY(tempy+center.getY());
		p.setZ(tempz+center.getZ());
	}
	public CartesianCoordinate getPoint0() {
		return point0;
	}
	public void setPoint0(CartesianCoordinate point0) {
		this.point0 = point0;
	}
	public CartesianCoordinate getPoint1() {
		return point1;
	}
	public void setPoint1(CartesianCoordinate point1) {
		this.point1 = point1;
	}
	public CartesianCoordinate getPoint2() {
		return point2;
	}
	public void setPoint2(CartesianCoordinate point2) {
		this.point2 = point2;
	}
	public CartesianCoordinate getNormal() {
		return normal;
	}
	public void setNormal(CartesianCoordinate normal) {
		this.normal = normal;
	}
	
	
}
