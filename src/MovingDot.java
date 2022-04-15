import javax.swing.*;
import java.awt.*;

public class MovingDot extends Dot implements Runnable{


    private double dx;
    private double dy;
    private double speed;
    private JPanel p;

    public MovingDot(Point center, Point direction, double speed, JPanel p) {
        super(center);
        double ang = Math.acos((direction.x-center.x)/direction.distance(center));

        double dx = (speed*Math.cos(ang));
        double dy = (speed*Math.sin(ang));
        System.out.println(ang+ " "+direction.distance(center));
        if (direction.y < center.y){
            dy = -dy;
        }
        setMotion(dx, dy);

        this.p = p;
    }

    public void reflectX(){
        dx = -dx;
    }
    public void reflectY(){
        dy = -dy;
    }

    public void setMotion(double x,double y){
        dx = x;
        dy = y;
    }
    public void move(){
       x += dx;
       y += dy;
    }


    public void run() {
        while (true) {
            move();
            p.repaint();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }

    }
}
