import java.awt.*;

public class Obstacle {

	private Point center;
	private Rectangle region;
	private int size;
	private Color color;

	public Obstacle(Point center) {
		this.center = center;
		size = 50;
		region = new Rectangle(center.x-size/2,center.y-size/2, size, size);
		color = color.RED;
	}

	public void paint(Graphics g){
		g.setColor(color);
		g.fillRect(region.x,region.y, region.width,region.height);
	}

	public Rectangle getRegion() {
		return region;
	}

	public void hitBy(MovingDot d) {
		if ((d.top() > top()) && (d.bottom() < bottom())) {
			d.reflectX();
		} else {
			if ((d.left() > left()) && (d.right() < right())) {
				d.reflectY();
			}
			else{
				d.reflectX();
				d.reflectY();
			}
		}

	}

	public int top(){
		return region.y;
	}
	public int bottom(){
		return region.y+region.height;
	}
	public int left(){
		return region.x;
	}
	public int right(){
		return region.x +region.width;
	}
}
