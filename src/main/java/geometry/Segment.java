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
     * Returns true if the given segment intersects this segment, false otherwise.
     * @param segment
     * @return
     */
    public boolean intersects(Segment segment) {

        Line thisLine = this.getLine();
        Line segmentLine = segment.getLine();

        Point intersect = thisLine.intersect(segmentLine);

//        System.out.println(intersect);

        double thisMinX = Math.min(this.start.x, this.end.x);
        double thisMaxX = Math.max(this.start.x, this.end.x);
        double thisMinY = Math.min(this.start.y, this.end.y);
        double thisMaxY = Math.max(this.start.y, this.end.y);

        double segmentMinX = Math.min(segment.start.x, segment.end.x);
        double segmentMaxX = Math.max(segment.start.x, segment.end.x);
        double segmentMinY = Math.min(segment.start.y, segment.end.y);
        double segmentMaxY = Math.max(segment.start.y, segment.end.y);

        if (((thisMinX < intersect.x - Constants.EPSILON && intersect.x + Constants.EPSILON < thisMaxX)
                    || (Math.abs(thisMinX - thisMaxX) < 2*Constants.EPSILON
                        && thisMinY < intersect.y - Constants.EPSILON && intersect.y + Constants.EPSILON < thisMaxY))
                && ((segmentMinX < intersect.x - Constants.EPSILON && intersect.x + Constants.EPSILON < segmentMaxX)
                    || (Math.abs(segmentMinX - segmentMaxX) < 2*Constants.EPSILON
                        && segmentMinY < intersect.y - Constants.EPSILON && intersect.y + Constants.EPSILON < segmentMaxY))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the line which passes through this segment
     * @return
     */
    public Line getLine() {
        return new Line(start, end);
    }

    /**
     * Returns a circle that passes through the two points in this segment.
     * @return
     */
    public Circle getCircumcircle() {
        return new Circle(this.start, this.end);
    }

    /**
     * Returns true if the given segment is equivelent to this segment, false otherwise.
     * @param segment
     * @return
     */
    public boolean equals(Segment segment) {
        return (this.start.equals(segment.start) && this.end.equals(segment.end))
                || (this.start.equals(segment.end) && this.end.equals(segment.start));
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}
