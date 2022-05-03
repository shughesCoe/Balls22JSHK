import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlatformDot extends Dot{
    Point left;
    Point right;
    double dx;
    double dy;
    Rectangle region;

    public PlatformDot(Point center, Point left, Point right) {
        super(center);
        this.left = left;
        this.right = right;
        dy = 1;
        //region = new Rectangle(getTop(), getLeft(), radius*10, radius*2)
        region = new Rectangle(center.x -((radius*20)/2), center.y - radius, radius*20, radius*2);

    }


    public void setLeftMotion(){
        dx = -1;
        //System.out.println("LEFTMOTION dx: " + dx + " dy: " + dy);
    }

    public void setRightMotion(){
        dx = 1;
        //System.out.println("RIGHTMOTION dx: " + dx + " dy: " + dy);
    }

    @Override
    public Rectangle getRegion() {
        return region;
    }

    @Override
    void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(region.x, region.y, region.width, region.height);
    }

    public void moveLeft() {
        if (getLeft() != 0) {
            //System.out.println("Center Pre Left: " + getCenter());
            setLeftMotion();
            region.x += dx * 15;
            x+=dx *15;

            //System.out.println("New Center Left: " + getCenter());

        }
    }

    public void moveRight(){
        if (getRight() != 500) {
            //System.out.println("Center Pre Right: " + getCenter());
            setRightMotion();
            x+=dx *15;
            region.x += dx * 15;

            //System.out.println("New Center Right: " + getCenter());
        }
    }
}
