import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class LaunchPanel extends JPanel {

    ArrayList<MovingDot> dots;
    ArrayList<Obstacle> obstacles;
    ArrayList<Obstacle> destroyedObstacles;
    Obstacle o;
    Obstacle o2;
    //Dot launchPoint;
    //LaunchPlatform launchPlatform;
    PlatformDot platform;
    Point s;
    Point bottomRight;
    Color[] colors;
    int numBallsAllowed;


    public LaunchPanel(int numBallsAllowed) {
        setPreferredSize(new Dimension(500,500));
        dots = new ArrayList<MovingDot>();
        obstacles = new ArrayList<Obstacle>();
        destroyedObstacles = new ArrayList<Obstacle>();
        this.numBallsAllowed = numBallsAllowed;
        colors = new Color[] {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.MAGENTA};
        //Obstacle o = new Obstacle(new Point(150, 150), 20, 150, 50, colors[3]);
        //obstacles.add(o);
        generateObstacles(30, new Point(50, 22), 96, 22, 4, colors, 1, 500);

        s = new Point(250,450);
        //launchPoint =new Dot(s);
        platform = new PlatformDot(s, new Point(0, s.y), new Point(500, s.y));
        getInputMap().put(KeyStroke.getKeyStroke("A"), "moveLeft");
        getActionMap().put("moveLeft", new LeftAction());
        getInputMap().put(KeyStroke.getKeyStroke("D"), "moveRight");
        getActionMap().put("moveRight", new RightAction());
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "fireBall");
        getActionMap().put("fireBall", new SpaceAction());
        //launchPlatform = new LaunchPlatform(new Point(250,250), 100, 50, Color.BLACK, 1);
        //launchPoint.setColor(Color.GREEN);
        bottomRight = new Point(500, 500);

        //addKeyListener(new LaunchPanel.KeyPlay());
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
                //System.out.println("Current Center: " + currentCenter);
                if (h < numObstacles) {
                    int colorIndex = rand.nextInt(colors.length);
                    obstacles.add(new Obstacle(currentCenter, HP, obstacleWidth, obstacleHeight, colors[colorIndex]));
                    currentCenter.setLocation(currentCenter.x + obstacleWidth + obstacleGap, currentCenter.y);
                }
            }
            currentCenter.setLocation(start.x + (obstacleHeight/2), currentCenter.y + obstacleHeight + obstacleGap);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = this.getHeight();
        int w = this.getWidth();
        //Point c = new Point(w/2, h-30);
        //launchPoint.setCenter(c);
        //launchPoint.paint(g);
        platform.paint(g);
        //launchPlatform.paint(g);
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
            if(d.getRegion().intersects(platform.getRegion())){
                d.hitByPlatform(platform);
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
        MovingDot  d = new MovingDot(new Point(p.x, p.y-platform.getRegion().height), new Point(p.x, 0), 1);
        //d = new GravityDotDecorator(d);
        d = new BoundedDotDecorator(d, new Point(getWidth(),getHeight()) );
        dots.add(d);
        Thread t = new Thread(d);
        t.start();
    }

    private class MousePlay implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            //generateDot(e.getPoint());
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
   /* private class KeyPlay implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println(e.getKeyChar() + " typed");
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                System.out.println("Spacebar!!!");

            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyChar() + " pressed");
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                System.out.println("Left!!");
                launchPlatform.moveLeft();
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                System.out.println("Right!!");launchPlatform.moveRight();
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println(e.getKeyChar() + " released");

        }
    }*/
    private class SpaceAction extends AbstractAction {
       @Override
       public void actionPerformed(ActionEvent e) {
           if(dots.size() < numBallsAllowed){
               generateDot(platform.getCenter());
               repaint();
           }
       }
   }

    private class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            platform.moveLeft();
            repaint();
        }
    }
    private class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            platform.moveRight();
            repaint();
        }
    }
}

