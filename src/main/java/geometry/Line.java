package geometry;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Line {
    double m;
    double b;

    public Line(Point point1, Point point2) {
        m = (point2.y - point1.y)/(point2.x - point1.x);
        b = point1.y - m * point1.x;
    }

    public Line(double m, double b) {
        this.m = m;
        this.b = b;
    }

    public double y(double x) {
        return m*x + b;
    }

    /**
     *
     * @param y
     * @return
     */
    public double x(double y) {
        return (y - b)/m;
    }

    /**
     * Returns the line's slope
     * @return
     */
    public double m() {
        return m;
    }

    /**
     * Returns the line's constant
     * @return
     */
    public double b() {
        return b;
    }

    /**
     * Returns a perpendicular line which passes through (0,0)
     * @return
     */
    public Line perpendicular() {
        return new Line(-1.d/m, 0.d);
    }

    /**
     * Returns a perpendicular line which passes through the given point
     * @param point
     * @return
     */
    public Line perpendicular(Point point) {
        double b = point.y - (-1.d/m) * point.x;
        return new Line(-1.d/m, b);
    }

    /**
     * Returns the point at which this line intersects the given line
     * @param line
     * @return
     */
    public Point intersect(Line line) {
        double x = (line.b - this.b)/(this.m - line.m);
        return new Point(x, this.m * x + this.b);
    }


    @Override
    public String toString() {
        return "y = " + m + "x + " + b;
    }
}
