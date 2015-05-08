package geometry;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Point {

    public final double x;
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

    public Point midpoint(Point point) {
        return new Point((this.x + point.x)/2, (this.y + point.y)/2);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
