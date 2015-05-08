package geometry;

import constants.Constants;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Circle {

    Point center;
    double radius;

    /**
     * Takes three points along the edge and calculates the center and radius
     * @param a
     * @param b
     * @param c
     */
    public Circle(Point a, Point b, Point c) {
        Segment seg1 = new Segment(a, b);
        Segment seg2 = new Segment(b, c);

        Line perp1 = seg1.perpendicular(seg1.midpoint());
        System.out.println(perp1);
        Line perp2 = seg2.perpendicular(seg2.midpoint());
        System.out.println(perp2);

        this.center = perp1.intersect(perp2);
        this.radius = center.distance(a);
    }

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean contains(Point p) {
        System.out.println("distance: " + center.distance(p));
        System.out.println("center: " + center);
        if(Double.isNaN(center.distance(p))) {
            return true;
        } else {
            return center.distance(p) < radius - Constants.EPSILON;
        }
    }
}
