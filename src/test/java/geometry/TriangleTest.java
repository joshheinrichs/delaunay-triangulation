package geometry;

import constants.Constants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by joshheinrichs on 15-05-11.
 */
public class TriangleTest {


    @Ignore @Test
    public void getSegmentsTest() {

    }

    @Ignore @Test
    public void getCircumcircleTest() {

    }

    @Test
    public void containsTest() {
        Triangle triangle = new Triangle(new Point(0,0), new Point(0,1), new Point(1,0));
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));
        assertFalse(triangle.contains(new Point(0.6, 0.6)));
    }

    @Test
    public void areaTest() {
        Triangle triangle = new Triangle(new Point(0,0), new Point(0,1), new Point(1,0));
        assertEquals(0.5, triangle.area(), Constants.EPSILON);
    }

    @Test
    public void boundingTest() {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(0,1));
        points.add(new Point(0,-1));
        points.add(new Point(-1,0));
        points.add(new Point(-1,-1));
        points.add(new Point(-1,1));
        points.add(new Point(1,-1));

        Triangle triangle = new Triangle(points);
        for (Point point : points) {
            assertTrue(triangle.contains(point));
        }
    }


}
