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
     * Constructs a triangle which bounds the given points. This triangle is not guaranteed to be minimal.
     * @param points
     */
    public Triangle(ArrayList<Point> points) {
        double minX, minY;
        double maxX, maxY;

        minX = maxX = points.get(0).x;
        minY = maxY = points.get(0).y;

        for (int i = 1; i < points.size(); i++) {
            minX = Math.min(minX, points.get(i).x);
            minY = Math.min(minY, points.get(i).y);
            maxX = Math.max(maxX, points.get(i).x);
            maxY = Math.max(maxY, points.get(i).y);
        }

        //constructs a triangle with double the height and width of the bounding box of the points
        this.a = new Point(minX-100000, minY-100000);
        this.b = new Point(minX-100000, maxY + (maxY - minY)+100000);
        this.c = new Point(maxX + (maxX - minX)+100000, minY-100000);
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
     * Returns true if the point is contained within the triangle, false otherwise.
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        double area = (1.d/2.d) * (-b.y*c.x + a.y*(-b.x + c.x) + a.x*(b.y - c.y) + b.x*c.y);

        double s = 1.d/(2.d*area)*(a.y*c.x - a.x*c.y + (c.y - a.y)*point.x + (a.x - c.x)*point.y);
        double t = 1.d/(2.d*area)*(a.x*b.y - a.y*b.x + (a.y - b.y)*point.x + (b.x - a.x)*point.y);

        return (0 <= s && s <= 1) && (0 <= t && t <= 1) && (s + t <= 1);
    }

    public double area() {
        return Math.abs((1.d/2.d) * (-b.y*c.x + a.y*(-b.x + c.x) + a.x*(b.y - c.y) + b.x*c.y));
    }

}
