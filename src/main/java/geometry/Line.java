package geometry;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Line {

    public final double slope;
    public final double yIntercept, xIntercept;

    public Line(Point point1, Point point2) {
        this.slope = (point2.y - point1.y)/(point2.x - point1.x);
        this.yIntercept = point1.y - this.slope * point1.x;
        this.xIntercept = - this.yIntercept / this.slope;
    }

    public Line(double slope, double yIntercept) {
        assert(!Double.isInfinite(slope));
        this.slope = slope;
        this.yIntercept = yIntercept;
        this.xIntercept = - this.yIntercept / this.slope;
    }

    /**
     * This constructor is needed when it is possible for the slope to be infinite.
     * @param slope
     * @param xIntercept
     * @param yIntercept
     */
    public Line(double slope, double xIntercept, double yIntercept) {
        assert(!Double.isInfinite(slope) || Double.isNaN(yIntercept));
        this.slope = slope;
        this.xIntercept = xIntercept;
        this.yIntercept = yIntercept;
    }

    /**
     * Returns the value of y for the given x value, or {@link Double#NaN} if the slope is infinite.
     * @param x
     * @return
     */
    public double y(double x) {
        if(Double.isFinite(slope)) {
            return slope * x + yIntercept;
        } else {
            return Double.NaN;
        }
    }

    /**
     * Returns the value of x for the given y value.
     * @param y
     * @return
     */
    public double x(double y) {
        if(Double.isFinite(slope)) {
            return (y - yIntercept)/ slope;
        } else {
            return xIntercept;
        }

    }

    /**
     * Returns the line's slope.
     * @return
     */
    public double m() {
        return slope;
    }

    /**
     * Returns the line's y intercept.
     * @return
     */
    public double b() {
        return yIntercept;
    }

    /**
     * Returns a perpendicular line which passes through (0,0)
     * @return
     */
    public Line perpendicular() {
        return perpendicular(new Point(0,0));
    }

    /**
     * Returns a perpendicular line which passes through the given point.
     * @param point
     * @return
     */
    public Line perpendicular(Point point) {

        double xIntercept, yIntercept;
        double slope = -1.d / this.slope;

        if(Double.isFinite(slope)) {
            yIntercept = point.y - slope * point.x;
            xIntercept = - yIntercept / slope;
        } else {
            xIntercept = point.x;
            yIntercept = Double.NaN;
        }

        return new Line(slope, xIntercept, yIntercept);
    }

    /**
     * Returns the point at which this line intersects the given line
     * @param line
     * @return
     */
    public Point intersect(Line line) {
        double x, y;
        if(this.isParallel(line)) {
            x = Double.NaN;
            y = Double.NaN;
        } else if(Double.isInfinite(this.slope)) {
            x = this.xIntercept;
            y = line.y(x);
        } else if (Double.isInfinite(line.slope)) {
            x = line.xIntercept;
            y = this.y(x);
        } else {
            x = (line.yIntercept - this.yIntercept)/(this.slope - line.slope);
            y = this.slope * x + this.yIntercept;
        }
        return new Point(x, y);
    }

    public boolean isParallel(Line line) {
        if(Double.isInfinite(this.slope) && Double.isInfinite(line.slope)) {
            return true;
        } else if(this.slope == line.slope) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return "y = " + slope + "x + " + yIntercept;
    }
}
