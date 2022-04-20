public class GravityDotDecorator extends MovingDecorator{
    private MovingDot md;
    private double prevDY;

    public GravityDotDecorator(MovingDot m) {
        super(m);
        this.md = m;
        prevDY = getDy();
    }

    @Override
    public void move() {
        double dx = md.getDx();
        double dy = md.getDy();
        double GRAV = .003;


        md.setMotion(dx, dy+GRAV);
        md.move();
        prevDY = dy;
    }
}
