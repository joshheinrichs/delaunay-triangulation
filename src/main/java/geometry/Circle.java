package geometry;

import constants.Constants;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Circle {

    /**
     * Center of the circle.
     */
    public final Point center;

    /**
     * Radius of the circle.
     */
    public final double radius;

    /**
     * Constructs a circumcricle from the given points.
     * @param a
     * @param b
     * @param c
     */
    public Circle(Point a, Point b, Point c) {
        Segment seg1 = new Segment(a, b);
        Segment seg2 = new Segment(b, c);

        Line perp1 = seg1.perpendicular(seg1.midpoint());
        Line perp2 = seg2.perpendicular(seg2.midpoint());

        this.center = perp1.intersect(perp2);
        this.radius = center.distance(a);
    }

    /**
     * Constructs a new circle with the given center and radius.
     * @param center
     * @param radius
     */
    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns true if the given point is contained within the circle, false otherwise.
     * @param p
     * @return
     */
    public boolean contains(Point p) {
        if(Double.isNaN(center.distance(p))) {
            return true;
        } else {
            return center.distance(p) < radius - Constants.EPSILON;
        }
    }
}
