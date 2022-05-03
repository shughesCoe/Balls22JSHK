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
        if(dx > speed){
            dx = speed;
        }
        if(dx < -speed){
            dx = -speed;
        }

    }
    public void move(){
        x += dx;
        y += dy;
        //System.out.println("dx: " + dx + " dy: " + dy);
    }

    public void hitBy(Obstacle b) {
        //System.out.println("DTop: " + getTop() + " DBottom: " + getBottom());
        //System.out.println("BTop: " + b.top() + " BBottom: " + b.bottom());
        //System.out.println("DLeft: " + getLeft() + " DRight: " + getRight());
        //System.out.println("BLeft: " + b.left() + " BRight: " + b.right());
        //System.out.println(b.getRegion());
        if ((getTop() > b.top()) && (getBottom() < b.bottom())) {
            setMotion(-getDx(), getDy());
            System.out.println("UP");
        } else {
            if ((getLeft() > b.left()) && (getRight() < b.right())) {
                setMotion(getDx(), -getDy());
                System.out.println("DOWN");
            }
            else{
                setMotion(-getDx(), getDy());
                setMotion(getDx(), -getDy());
                System.out.println("LEFT>");
            }
        }
    }

    public void hitByPlatform(PlatformDot pd){
        int dotXOrigin = getRegion().x;
        int platformXOrigin = pd.getRegion().x;
        int segmentWidth = pd.getRegion().width/4;
        int first = platformXOrigin + segmentWidth;
        int second = first + segmentWidth;
        int third = second + segmentWidth;
        int fourth = third + segmentWidth;

        if(dotXOrigin < first){
            setMotion(getDx()-1, -getDy());
        }
        if(dotXOrigin >= first && dotXOrigin < second){
            setMotion(getDx()-.5, -getDy());
        }
        if(dotXOrigin >= second && dotXOrigin < third){
            setMotion(0, -getDy());
        }
        if(dotXOrigin >= third && dotXOrigin < fourth){
            setMotion(getDx()+.5,-getDy());
        }
        if(dotXOrigin > fourth){
            setMotion(getDx() + 1, -getDy());
        }




        /*if ((getTop() > pd.getTop()) && (getBottom() < pd.getBottom())) {
            if(pd.x > x){
                setMotion(-getDx(), getDy()+ (pd.x-x));
            }
            else {
                setMotion(-getDx(), getDy() + (pd.x+x));
            }
        } else {
            //System.out.println("dTop: " + getTop() + " pdTop: " + pd.getTop());
            //System.out.println("dBottom: " + getBottom() + " pdBottom: " + pd.getBottom());
            //System.out.println("dLeft: " + getLeft() + " pdLeft: " + pd.getLeft());
            //System.out.println("dRight: " + getRight() + " pdRight: " + pd.getRight());
            if ((getLeft() > pd.getLeft()) && (getRight() < pd.getRight())) {
                setMotion(getDx(), -getDy());
            }
            else{
                *//*if(pd.x > x){
                    setMotion(-(getDx() + getDx()), getDy());
                    setMotion(getDx(), -(getDy()+getDy()));
                }
                else {
                    setMotion(-(getDx() - getDx()*2),getDy());
                    setMotion(getDx(), -(getDy()-(getDy()*2)));
                }*//*
                setMotion(-getDx(), getDy());
                setMotion(getDx(), -getDy());
            }
        }*/
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
