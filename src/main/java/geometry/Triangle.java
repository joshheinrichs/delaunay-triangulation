package geometry;

import constants.Constants;

import java.util.ArrayList;

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
     * Returns the edges of the triangle.
     * @return
     */
    public ArrayList<Segment> getSegments() {
        ArrayList<Segment> list = new ArrayList<Segment>(3);
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


    /**
     * Returns true if the given point is contained within the triangle, including points which lie along its edges.
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        double alpha = ((b.y - c.y)*(point.x - c.x) + (c.x - b.x)*(point.y - c.y)) /
                ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));
        
        double beta = ((c.y - a.y)*(point.x - c.x) + (a.x - c.x)*(point.y - c.y)) /
                ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));
        
        double gamma = 1.0d - alpha - beta;
        
        return (alpha >= 0 && beta >= 0 && gamma >= 0);
    }

    /**
     * Returns true if the given point is within the the triangle, not including points which lie along its edges.
     * @param point
     * @return
     */
    public boolean interior(Point point) {
        double alpha = ((b.y - c.y)*(point.x - c.x) + (c.x - b.x)*(point.y - c.y)) /
                ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));

        double beta = ((c.y - a.y)*(point.x - c.x) + (a.x - c.x)*(point.y - c.y)) /
                ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));

        double gamma = 1.0d - alpha - beta;

        return (alpha > 0 && beta > 0 && gamma > 0);
    }

    public double getAngleA() {
        return getAngle(c, a, b);
    }

    public double getAngleAStart() {
        return getAngleStart(c, a, b);
    }

    public double getAngleB() {
        return getAngle(a, b, c);
    }

    public double getAngleBStart() {
        return getAngleStart(a, b, c);
    }

    public double getAngleC() {
        return getAngle(b, c, a);
    }

    public double getAngleCStart() {
        return getAngleStart(b, c, a);
    }

    public boolean isObtuse() {
        return (getAngleA() > 90.d || getAngleB() > 90.d || getAngleC() > 90.d);
    }

    /**
     * Retruns the angle from 0 to the start of angle B
     * @param A
     * @param B
     * @param C
     * @return
     */
    static double getAngleStart(Point A, Point B, Point C) {

        double angle1 = Math.toDegrees(Math.atan2(A.y - B.y, A.x - B.x));
        double angle2 = Math.toDegrees(Math.atan2(C.y - B.y, C.x - B.x));

        if(angle1 < 0) {
            angle1 += 360.d;
        }
        if(angle2 < 0) {
            angle2 += 360.d;
        }

        if( Math.abs(((angle1 + getAngle(A, B, C)) % 360) - angle2) < Constants.EPSILON ) {
            return angle1;
        } else {
            assert( Math.abs(((angle2 + getAngle(A, B, C)) % 360) - angle1) < Constants.EPSILON ) : angle1 + ", " + angle2;
            return angle2;
        }
    }

    /**
     * Returns the inner angle B from the given triangle
     * @param A
     * @param B
     * @param C
     * @return
     */
    static double getAngle(Point A, Point B, Point C) {
        //law of cosines
        double b = A.distance(C);
        double a = C.distance(B);
        double c = B.distance(A);
        return Math.toDegrees(Math.acos((a * a + c * c - b * b) / (2.d * a * c)));
    }
}
