import java.awt.*;
import java.util.ArrayList;

public class Obstacle {

    private Point center;
    private Rectangle region;
    private int sizeX;
    private int sizeY;
    private Color color;
    private Integer hitPoints;
    private LaunchPanel observer;
    private boolean overHP;

    public Obstacle(Point center, int HP, int sizeX, int sizeY, Color color) {
        this.center = center;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        //System.out.println(center);
        //System.out.println(sizeX);
        //System.out.println(sizeY);


        region = new Rectangle(center.x-(sizeX/2),center.y-(sizeY/2), sizeX, sizeY);
        //System.out.println(region);
        this.color = color;
        hitPoints = HP;
        observer = null;
        overHP = false;
    }

    public boolean isOverHP() {return overHP;}

    //TAKEN FROM https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);

    }
    public void paint(Graphics g){
            g.setColor(color);
            g.fillRect(region.x, region.y, region.width, region.height);
            //System.out.println(region.x + " " + region.y + " " + region.width + " " + region.height);
            g.setColor(color.black);
            drawCenteredString(g, hitPoints.toString(), region, new Font("Verdana", Font.BOLD, 12));

        //g.drawString(hitPoints.toString(), center.x, center.y);
    }

    public Rectangle getRegion() {
        return region;
    }

    public void hitByHP(){
        if(hitPoints > 1) {
            hitPoints -= 1;
        }
        else{
            overHP = true;
        }
    }

    /*public synchronized void hitBy(MovingDot d) {
        if ((d.getTop() > top()) && (d.getBottom() < bottom())) {
            d.setMotion(-d.getDx(), d.getDy());
        } else {
            if ((d.getLeft() > left()) && (d.getRight() < right())) {
                d.setMotion(d.getDx(), -d.getDy());
            }
            else{
                d.setMotion(-d.getDx(), d.getDy());
                d.setMotion(d.getDx(), -d.getDy());
            }
        }

    }*/

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
