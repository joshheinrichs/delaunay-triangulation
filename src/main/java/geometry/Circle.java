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

        Line perp1 = seg1.getLine().perpendicular(seg1.midpoint());
        Line perp2 = seg2.getLine().perpendicular(seg2.midpoint());

        this.center = perp1.intersect(perp2);
        this.radius = center.distance(a);
    }

    public Circle(Point a, Point b) {
        Segment segment = new Segment(a, b);
        this.center = segment.midpoint();
        this.radius = segment.length() / 2.d;
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
     * Returns true if the given point is contained within the circle or lies upon its edge, false otherwise.
     * @param p
     * @param inclusive
     * @return
     */
    public boolean contains(Point p, boolean inclusive) {
        if(Double.isNaN(center.distance(p))) {
            return true;
        } else if (inclusive) {
            return center.distance(p) <= radius;
        } else {
            return center.distance(p) < radius - Constants.EPSILON;
        }
    }

    public boolean equals(Circle circle) {
        return this.center.equals(circle.center)
                && Math.abs(this.radius - circle.radius) < Constants.EPSILON;
    }
}
