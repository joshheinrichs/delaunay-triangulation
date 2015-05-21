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

    @Test
    public void containsTest() {
        Triangle triangle = new Triangle(new Point(0,0), new Point(0,1), new Point(1,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0)));
        assertTrue(triangle.contains(new Point(0, 1)));
        assertTrue(triangle.contains(new Point(1, 0)));
        assertTrue(triangle.contains(new Point(0.5, 0.5)));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));

        triangle = new Triangle(new Point(0,0), new Point(1,0), new Point(0,1));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0)));
        assertTrue(triangle.contains(new Point(0, 1)));
        assertTrue(triangle.contains(new Point(1, 0)));
        assertTrue(triangle.contains(new Point(0.5, 0.5)));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));

        triangle = new Triangle(new Point(1,0), new Point(0,1), new Point(0,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0)));
        assertTrue(triangle.contains(new Point(0, 1)));
        assertTrue(triangle.contains(new Point(1, 0)));
        assertTrue(triangle.contains(new Point(0.5, 0.5)));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));

        triangle = new Triangle(new Point(1,0), new Point(0,0), new Point(0,1));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0)));
        assertTrue(triangle.contains(new Point(0, 1)));
        assertTrue(triangle.contains(new Point(1, 0)));
        assertTrue(triangle.contains(new Point(0.5, 0.5)));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));

        triangle = new Triangle(new Point(0,1), new Point(1,0), new Point(0,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0)));
        assertTrue(triangle.contains(new Point(0, 1)));
        assertTrue(triangle.contains(new Point(1, 0)));
        assertTrue(triangle.contains(new Point(0.5, 0.5)));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));

        triangle = new Triangle(new Point(0,1), new Point(0,0), new Point(1,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3)));
        assertFalse(triangle.contains(new Point(0.3, -0.3)));
        assertFalse(triangle.contains(new Point(-0.3, 0.3)));
        assertFalse(triangle.contains(new Point(-0.3, -0.3)));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0)));
        assertTrue(triangle.contains(new Point(0, 1)));
        assertTrue(triangle.contains(new Point(1, 0)));
        assertTrue(triangle.contains(new Point(0.5, 0.5)));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));
    }

    @Test
    public void interiorTest() {
        Triangle triangle = new Triangle(new Point(0,0), new Point(0,1), new Point(1,0));

        //checking signs
        assertTrue(triangle.interior(new Point(0.3, 0.3)));
        assertFalse(triangle.interior(new Point(0.3, -0.3)));
        assertFalse(triangle.interior(new Point(-0.3, 0.3)));
        assertFalse(triangle.interior(new Point(-0.3, -0.3)));

        //checking edge cases
        assertFalse(triangle.interior(new Point(0, 0)));
        assertFalse(triangle.interior(new Point(0, 1)));
        assertFalse(triangle.interior(new Point(1, 0)));
        assertFalse(triangle.interior(new Point(0.5, 0.5)));

        assertTrue(triangle.interior(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON)));
        assertTrue(triangle.interior(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON)));
        assertTrue(triangle.interior(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON)));
    }

}
