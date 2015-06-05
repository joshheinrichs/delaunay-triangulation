package geometry;

import constants.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by joshheinrichs on 15-05-11.
 */
public class SegmentTest {

    @Test
    public void midpointTest() {
        Segment segment = new Segment(new Point(0,0), new Point(1,0));
        assertEquals(0.5, segment.midpoint().x, Constants.EPSILON);
        assertEquals(0, segment.midpoint().y, Constants.EPSILON);

        segment = new Segment(new Point(0,0), new Point(1,1));
        assertEquals(0.5, segment.midpoint().x, Constants.EPSILON);
        assertEquals(0.5, segment.midpoint().y, Constants.EPSILON);

        segment = new Segment(new Point(0,0), new Point(-1,-1));
        assertEquals(-0.5, segment.midpoint().x, Constants.EPSILON);
        assertEquals(-0.5, segment.midpoint().x, Constants.EPSILON);
    }

    @Test
    public void lengthTest() {
        Segment segment = new Segment(new Point(0,0), new Point(1,0));
        assertEquals(1, segment.length(), Constants.EPSILON);

        segment = new Segment(new Point(0,0), new Point(1,1));
        assertEquals(Math.sqrt(2), segment.length(), Constants.EPSILON);

        segment = new Segment(new Point(0,0), new Point(-1,-1));
        assertEquals(Math.sqrt(2), segment.length(), Constants.EPSILON);
    }

    @Test
    public void intersectsTest() {
        Segment seg1 = new Segment(new Point(0,0), new Point(1,1));
        Segment seg2 = new Segment(new Point(1,1), new Point(2,0));
        assertFalse(seg1.intersects(seg2));
        assertFalse(seg2.intersects(seg1));

        seg1 = new Segment(new Point(1,1), new Point(0,0));
        seg2 = new Segment(new Point(1,1), new Point(2,0));
        assertFalse(seg1.intersects(seg2));
        assertFalse(seg2.intersects(seg1));

        seg1 = new Segment(new Point(0,0), new Point(1,1));
        seg2 = new Segment(new Point(2,0), new Point(1,1));
        assertFalse(seg1.intersects(seg2));
        assertFalse(seg2.intersects(seg1));

        seg1 = new Segment(new Point(0,0), new Point(1,1));
        seg2 = new Segment(new Point(2,0), new Point(1,1));
        assertFalse(seg1.intersects(seg2));
        assertFalse(seg2.intersects(seg1));

        seg1 = new Segment(new Point(1,1), new Point(0,0));
        seg2 = new Segment(new Point(0,1), new Point(1,0));

        System.out.println(seg1.getLine());
        System.out.println(seg2.getLine());

        assertTrue(seg1.intersects(seg2));
        assertTrue(seg2.intersects(seg1));

    }
}
