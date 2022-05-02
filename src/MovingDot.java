import javax.swing.*;
import java.awt.*;

public class MovingDot extends Dot implements Runnable{

    private double dx;
    private double dy;
    private double speed;
    private Point direction;

    public MovingDot() {
        super(new Point(0,0));
    }

    public MovingDot(Point center, Point direction, double speed) {
        super(center);
        this.direction = direction;
        this.speed = speed;

        double ang = Math.acos((direction.x-center.x)/direction.distance(center));
        double dx = (speed*Math.cos(ang));
        double dy = (speed*Math.sin(ang));
        if (direction.y < center.y){
            dy = -dy;
        }
        setMotion(dx, dy);
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getSpeed() {
        return speed;
    }

    public Point getDirection() {
        return direction;
    }

    public void setMotion(double x, double y){
        dx = x;
        dy = y;
    }
    public void move(){
        x += dx;
        y += dy;
    }

    public void hitBy(Obstacle b) {
        //System.out.println("DTop: " + getTop() + " DBottom: " + getBottom());
        //System.out.println("BTop: " + b.top() + " BBottom: " + b.bottom());
        //System.out.println("DLeft: " + getLeft() + " DRight: " + getRight());
        //System.out.println("BLeft: " + b.left() + " BRight: " + b.right());
        //System.out.println(b.getRegion());
        if ((getTop() > b.top()) && (getBottom() < b.bottom())) {
            setMotion(-getDx(), getDy());
        } else {
            if ((getLeft() > b.left()) && (getRight() < b.right())) {
                setMotion(getDx(), -getDy());
            }
            else{
                setMotion(-getDx(), getDy());
                setMotion(getDx(), -getDy());
            }
        }
    }

    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }

    }
}
