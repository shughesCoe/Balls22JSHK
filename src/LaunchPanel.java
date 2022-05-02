import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class LaunchPanel extends JPanel {

    ArrayList<MovingDot> dots;
    ArrayList<Obstacle> obstacles;
    ArrayList<Obstacle> destroyedObstacles;
    Obstacle o;
    Obstacle o2;
    Dot launchPoint;
    Point s;
    Point bottomRight;
    Color[] colors;


    public LaunchPanel() {
        setPreferredSize(new Dimension(500,500));
        dots = new ArrayList<MovingDot>();
        obstacles = new ArrayList<Obstacle>();
        destroyedObstacles = new ArrayList<Obstacle>();
        colors = new Color[] {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.MAGENTA};
        generateObstacles(2, new Point(54, 22), 96, 22, 4, colors, 10, 500);

        s = new Point(250,250);
        launchPoint =new Dot(s);
        launchPoint.setColor(Color.GREEN);
        bottomRight = new Point(500, 500);

        addMouseListener(new LaunchPanel.MousePlay());
    }

    private void generateObstacles(int numObstacles, Point start, int obstacleWidth, int obstacleHeight, int obstacleGap, Color[] colors, int HP, int windowWidth) {
        Point currentCenter = new Point(start.x + (obstacleWidth/2), start.y + (obstacleHeight/2));

        Random rand = new Random();
        int numObstaclesPerRow = Math.floorDiv(windowWidth, (obstacleWidth + obstacleGap));
        int numRows = (int) Math.ceil(numObstacles/numObstaclesPerRow);
        if(numObstaclesPerRow > numObstacles){
            numRows = 1;
        }
        for(int g = 0; g < numRows; g++) {
            for (int h = 0; h < numObstaclesPerRow; h++) {
                System.out.println("Current Center: " + currentCenter);
                if (h < numObstacles) {
                    int colorIndex = rand.nextInt(colors.length);
                    obstacles.add(new Obstacle(currentCenter, HP, obstacleWidth, obstacleHeight, colors[colorIndex]));
                    currentCenter.setLocation(currentCenter.x + obstacleWidth + obstacleGap, currentCenter.y);
                }
            }
            currentCenter.setLocation(start.x + (obstacleHeight/2), currentCenter.y + obstacleHeight + obstacleGap);
        }

        /*int currentX = start.x;
        int currentY = start.y;
        Random random = new Random();
        int numObstaclesPerRow = Math.floorDiv(windowWidth, (obstacleWidth + obstacleGap));

        int numRows = (int) Math.ceil(numObstacles/numObstaclesPerRow);
        if(numObstaclesPerRow > numObstacles){
            numRows = 1;
        }
        for(int g = 0; g < numRows; g++) {
            for (int h = 0; h < numObstaclesPerRow; h++) {
                System.out.println(currentX + " " + currentY);
                if(h < numObstacles) {
                    int colorIndex = random.nextInt(colors.length);
                    obstacles.add(new Obstacle(new Point(currentX+obstacleWidth/2, currentY+obstacleHeight/2), HP, obstacleWidth, obstacleHeight, colors[colorIndex]));
                    currentX += obstacleWidth + obstacleGap;
                }
            }
            currentX = start.x;
            currentY += obstacleHeight + obstacleGap;
        }*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = this.getHeight();
        int w = this.getWidth();
        Point c = new Point(w/2, h-30);
        launchPoint.setCenter(c);
        launchPoint.paint(g);
        MovingDot dotToRemove = null;

        for (MovingDot d: dots) {
            for (Obstacle o: obstacles) {

                if (d.getRegion().intersects(o.getRegion())) {
                    d.hitBy(o);
                    o.hitByHP();
                    if(o.isOverHP()){
                        destroyedObstacles.add(o);
                    }
                }
            }
            for (Obstacle o: destroyedObstacles){
                obstacles.remove(o);
            }
            destroyedObstacles.clear();
            if(d.getCenter().y > bottomRight.y){
                System.out.println("Dot Removed");
                dotToRemove = d;
            }
            else {
                d.move();
                d.paint(g);
            }
        }
        if(dotToRemove != null) {
            dots.remove(dotToRemove);
        }
        for (Obstacle o: obstacles){
            o.paint(g);
        }

        //o.paint(g);


        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    private void generateDot(Point p){
        System.out.println("Generate Dot");
        MovingDot  d = new MovingDot(launchPoint.getCenter(), p, 1);
        //d = new GravityDotDecorator(d);
        d = new BoundedDotDecorator(d, new Point(getWidth(),getHeight()) );
        dots.add(d);
        Thread t = new Thread(d);
        t.start();
    }

    private class MousePlay implements MouseListener {
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

