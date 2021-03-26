import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LaunchPanel extends JPanel {

	ArrayList<MovingDot> dots;
	Obstacle o;
	Dot launchPoint;
	Point s;


	public LaunchPanel() {
		setPreferredSize(new Dimension(500,500));
		dots = new ArrayList<MovingDot>();
		o = new Obstacle(new Point(150,150));
		s = new Point(250,250);
		launchPoint =new Dot(s);
		launchPoint.setColor(Color.GREEN);
		addMouseListener(new MousePlay());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int h = this.getHeight();
		int w = this.getWidth();
		Point c = new Point(w/2, h/2);
		launchPoint.setCenter(c);

		launchPoint.paint(g);
		for (MovingDot d: dots) {
			if ((d.top() < 0) || (d.bottom() > getHeight())){
				d.reflectY();
			}
			if ((d.left() < 0) || d.right() > getWidth()) {
				d.reflectX();
			}
			if (d.getRegion().intersects(o.getRegion())) {
				o.hitBy(d);
			}
			d.move();
			d.paint(g);
		}
		o.paint(g);

		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}

	private void generateDot(Point p){
		MovingDot d = new MovingDot(launchPoint.center, p, 5);
		dots.add(d);
	}

	private class MousePlay implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			generateDot(e.getPoint());
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
