package geometry;

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
     * Returns a line which is perpendicular to this segment, which passes through (0,0).
     * @return
     */
    public Line perpendicular() {
        Line line = new Line(start, end);
        return line.perpendicular();
    }

    /**
     * Returns a line which is perpendicular to this segment which passes through the given point.
     * @param point
     * @return
     */
    public Line perpendicular(Point point) {
        Line line = new Line(start, end);
        return line.perpendicular(point);
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

    public Line getLine() {
        return new Line(start, end);
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}
