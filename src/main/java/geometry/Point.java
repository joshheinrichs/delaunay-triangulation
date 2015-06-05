package geometry;

import constants.Constants;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Point {

    /**
     * X position of the point.
     */
    public final double x;

    /**
     * Y position of the point.
     */
    public final double y;

    /**
     * Constructs a new point with the given x and y positions.
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs the distance from this point to the given point.
     * @param point
     * @return
     */
    public double distance(Point point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

    /**
     * Returns the midpoint between this point and the given point.
     * @param point
     * @return
     */
    public Point midpoint(Point point) {
        return new Point((this.x + point.x)/2, (this.y + point.y)/2);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Point point) {
        return Math.abs(this.x - point.x) < Constants.EPSILON
                && Math.abs(this.y - point.y) < Constants.EPSILON;
    }
}
