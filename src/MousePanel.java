import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MousePanel extends JPanel {

	private Point location;
	private ArrayList<MovingDot> dotList;

	public MousePanel() {
		//setBackground(Color.BLACK);
		setPreferredSize(new Dimension(500,500));
		dotList = new ArrayList<MovingDot>();
		MousePlay mp = new MousePlay();
		addMouseListener(mp);
		addMouseMotionListener(mp);

	}

	private void generateDot(Point p){
		System.out.println("Generate Dot");


		MovingDot  d = new MovingDot(new Point(getWidth()/2,getHeight()/2), p, 1, this);
		dotList.add(d);
		Thread t = new Thread(d);
		t.start();
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (location != null) {
			Dot d = new Dot(location);
			d.setColor(Color.RED);
			d.paint(g);
 		}
		for (MovingDot d:dotList){

			if (d.getLeft() < 0 || d.getRight()>getWidth()){
				d.reflectX();
			}
			if (d.getTop() < 0 || d.getBottom() > getHeight() ){
				d.reflectY();
			}

			d.paint(g);
		}
	}



	private class MousePlay implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			System.out.println("MouseClicked");
			generateDot(e.getPoint());

			repaint();

		}

		@Override
		public void mousePressed(MouseEvent e) {
			/*System.out.println("MousePressed");
			location  =e.getPoint();

			for (Dot d: dotList){
				if (d.isInside(location)){
					dotList.remove(d);
					break;
				}
			}
			repaint();*/
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//Dot d = new Dot(e.getPoint());
			//dotList.add(d);
			//repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//System.out.println("MouseEntered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//System.out.println("MouseExited");
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			//location  =e.getPoint();
			//repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}
	}
}
