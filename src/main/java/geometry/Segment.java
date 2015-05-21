package geometry;

import constants.Constants;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Segment {

    public final Point start;
    public final Point end;

    public Segment(Point point1, Point point2) {
        this.start = point1;
        this.end = point2;
    }

    public Point midpoint() {
        return start.midpoint(end);
    }

    public Line perpendicular() {
        Line line = new Line(start, end);
        return line.perpendicular();
    }

    public Line perpendicular(Point point) {
        Line line = new Line(start, end);
        return line.perpendicular(point);
    }

    public double length() {
        return start.distance(end);
    }

    public boolean contains(Point point) {
        if(start.x <= point.x && point.x <= end.x) {
            return new Line(start, end).contains(point);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}
