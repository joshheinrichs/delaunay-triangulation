package geometry;

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


    public double area() {
        return (1.d/2.d) * (-b.y*c.x + a.y*(-b.x + c.x) + a.x*(b.y - c.y) + b.x*c.y);
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
        return Angle.getAngle(c, a, b);
    }

    public double getAngleAStart() {
        return Angle.getAngleStart(c, a, b);
    }

    public double getAngleB() {
        return Angle.getAngle(a, b, c);
    }

    public double getAngleBStart() {
        return Angle.getAngleStart(a, b, c);
    }

    public double getAngleC() {
        return Angle.getAngle(b, c, a);
    }

    public double getAngleCStart() {
        return Angle.getAngleStart(b, c, a);
    }

    public boolean isObtuse() {
        return (getAngleA() > 90.d || getAngleB() > 90.d || getAngleC() > 90.d);
    }
}
