package geometry;

import constants.Constants;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Segment {

    /**
     * Start point of the segment.
     */
    public final Point start;

    /**
     * End point of the segment.
     */
    public final Point end;

    /**
     * Constructs a new Segment from the given points.
     * @param point1
     * @param point2
     */
    public Segment(Point point1, Point point2) {
        this.start = point1;
        this.end = point2;
    }

    /**
     * Returns the midpoint along this segment.
     * @return
     */
    public Point midpoint() {
        return start.midpoint(end);
    }

    /**
     * Returns the length of this segment.
     * @return
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns true if the given point lies along this segment, false otherwise.
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        if(start.x <= point.x && point.x <= end.x) {
            return new Line(start, end).contains(point);
        } else {
            return false;
        }
    }

    public boolean intersects(Segment segment) {

        Line thisLine = this.getLine();
        Line segmentLine = segment.getLine();

        Point intersect = thisLine.intersect(segmentLine);

        System.out.println(intersect);

        if (((this.start.x < intersect.x - Constants.EPSILON && intersect.x + Constants.EPSILON < this.end.x)
                    || (this.start.x == this.end.x && this.start.y < intersect.y - Constants.EPSILON && intersect.y + Constants.EPSILON < this.end.y))
                && ((segment.start.x < intersect.x - Constants.EPSILON && intersect.x + Constants.EPSILON < segment.end.x)
                    || segment.start.x == segment.end.x && segment.start.y < intersect.y - Constants.EPSILON && intersect.y + Constants.EPSILON < segment.end.y)) {
            return true;
        } else {
            return false;
        }
    }

    public Line getLine() {
        return new Line(start, end);
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }

    public boolean equals(Segment segment) {
        return (this.start.equals(segment.start) && this.end.equals(segment.end))
                || (this.start.equals(segment.end) && this.end.equals(segment.start));
    }

    public Circle getCircumcircle() {
        return new Circle(this.start, this.end);
    }
}
