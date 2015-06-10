package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class Triangle {

    public final Point a, b, c;

    /**
     * Constructs a triangle from the given points.
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Returns the triangle's segments.
     * @return
     */
    public List<Segment> getSegments() {
        ArrayList<Segment> list = new ArrayList<>(3);
        list.add(new Segment(a, b));
        list.add(new Segment(b, c));
        list.add(new Segment(c, a));
        return list;
    }

    /**
     * Returns the circumcircle of the triangle.
     * @return
     */
    public Circle getCircumcircle() {
        return new Circle(a, b, c);
    }


    public double area() {
        return (1.d/2.d) * (-b.y*c.x + a.y*(-b.x + c.x) + a.x*(b.y - c.y) + b.x*c.y);
    }

    /**
     * Returns true if the given point is contained within the triangle, false otherwise.
     * @param point
     * @param inclusive
     * @return
     */
    public boolean contains(Point point, boolean inclusive) {
        double alpha = ((b.y - c.y)*(point.x - c.x) + (c.x - b.x)*(point.y - c.y)) /
                ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));
        
        double beta = ((c.y - a.y)*(point.x - c.x) + (a.x - c.x)*(point.y - c.y)) /
                ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));
        
        double gamma = 1.0d - alpha - beta;

        if (inclusive) {
            return (alpha >= 0 && beta >= 0 && gamma >= 0);
        } else {
            return (alpha > 0 && beta > 0 && gamma > 0);
        }
    }

    /**
     * Returns angle CAB in degrees. Always an interior angle (i.e. < 180deg).
     * @return
     */
    public double getAngleA() {
        return Angle.getAngle(c, a, b);
    }

    /**
     * Returns the clockwise angle from 0deg to the beginning of angle CAB in degrees.
     * This doesn't have much use other than displaying angles visually.
     * @return
     */
    public double getAngleAStart() {
        return Angle.getAngleStart(c, a, b);
    }

    /**
     * Returns angle ABC in degrees. Always an interior angle (i.e. < 180deg).
     * @return
     */
    public double getAngleB() {
        return Angle.getAngle(a, b, c);
    }

    /**
     * Returns the clockwise angle from 0deg to the beginning of angle ABC in degrees.
     * This doesn't have much use other than displaying angles visually.
     * @return
     */
    public double getAngleBStart() {
        return Angle.getAngleStart(a, b, c);
    }

    /**
     * Returns angle BCA in degrees. Always an interior angle (i.e. < 180deg).
     * @return
     */
    public double getAngleC() {
        return Angle.getAngle(b, c, a);
    }

    /**
     * Returns the clockwise angle from 0deg to the beginning of angle BCA in degrees.
     * This doesn't have much use other than displaying angles visually.
     * @return
     */
    public double getAngleCStart() {
        return Angle.getAngleStart(b, c, a);
    }

    /**
     * Returns true if this triangle is obtuse, false otherwise.
     * @return
     */
    public boolean isObtuse() {
        return (getAngleA() > 90.d || getAngleB() > 90.d || getAngleC() > 90.d);
    }
}
