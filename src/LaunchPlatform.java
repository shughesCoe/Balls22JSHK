import java.awt.*;

public class LaunchPlatform{
    private Point topLeft;
    private Point center;
    private Point bottomRight;
    private Color color;
    private Rectangle region;
    private int width;
    private int height;
    private double dx;
    private double dy;
    private double speed;


    LaunchPlatform(Point center, int width, int height, Color color, double speed){
        this.center = center;
        this.color = color;
        topLeft = new Point(center.x-(width/2), center.y-(height/2));
        bottomRight = new Point(center.x+(width/2), center.y+(height/2));
        region = new Rectangle(topLeft.x, topLeft.y, width, height);
        dx = 1;
        dy = 1;
    }

    public Point getCenter(){ return center; }

    public void setCenter(Point p){
        this.center = center;
        topLeft = new Point(center.x-(width/2), center.y-(height/2));
        bottomRight = new Point(center.x+(width/2), center.y+(height/2));
    }

    public Rectangle getRegion(){ return region;}

    void paint(Graphics g){
        g.setColor(color);
        g.fillRect(region.x, region.y, region.width, region.height);
    }

    public void moveLeft(){
        if(region.x != 0){
            region.x -= dx;
        }

    }

    public void moveRight(){
        if(region.x+width != 500){
            region.x += dx;
        }
    }

    public int top(){
        //System.out.println("top" + region.y);
        return region.y;
    }
    public int bottom(){
        //System.out.println("bottom" + (region.y+region.height));
        return region.y+region.height;
    }
    public int left(){
        //System.out.println("left" + region.x);
        return region.x;
    }
    public int right(){
        //System.out.println("right" + (region.x +region.width));
        return region.x +region.width;
    }
}
