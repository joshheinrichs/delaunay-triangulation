package geometry;

import constants.Constants;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by joshheinrichs on 15-05-11.
 */
public class TriangleTest {

    @Test
    public void containsTest() {
        Triangle triangle = new Triangle(new Point(0,0), new Point(0,1), new Point(1,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(0.3, -0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), true));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0), true));
        assertTrue(triangle.contains(new Point(0, 1), true));
        assertTrue(triangle.contains(new Point(1, 0), true));
        assertTrue(triangle.contains(new Point(0.5, 0.5), true));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), true));

        triangle = new Triangle(new Point(0,0), new Point(1,0), new Point(0,1));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(0.3, -0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), true));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0), true));
        assertTrue(triangle.contains(new Point(0, 1), true));
        assertTrue(triangle.contains(new Point(1, 0), true));
        assertTrue(triangle.contains(new Point(0.5, 0.5), true));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), true));

        triangle = new Triangle(new Point(1,0), new Point(0,1), new Point(0,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(0.3, -0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), true));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0), true));
        assertTrue(triangle.contains(new Point(0, 1), true));
        assertTrue(triangle.contains(new Point(1, 0), true));
        assertTrue(triangle.contains(new Point(0.5, 0.5), true));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), true));

        triangle = new Triangle(new Point(1,0), new Point(0,0), new Point(0,1));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(0.3, -0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), true));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0), true));
        assertTrue(triangle.contains(new Point(0, 1), true));
        assertTrue(triangle.contains(new Point(1, 0), true));
        assertTrue(triangle.contains(new Point(0.5, 0.5), true));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), true));

        triangle = new Triangle(new Point(0,1), new Point(1,0), new Point(0,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(0.3, -0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), true));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0), true));
        assertTrue(triangle.contains(new Point(0, 1), true));
        assertTrue(triangle.contains(new Point(1, 0), true));
        assertTrue(triangle.contains(new Point(0.5, 0.5), true));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), true));

        triangle = new Triangle(new Point(0,1), new Point(0,0), new Point(1,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(0.3, -0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), true));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), true));

        //checking edge cases
        assertTrue(triangle.contains(new Point(0, 0), true));
        assertTrue(triangle.contains(new Point(0, 1), true));
        assertTrue(triangle.contains(new Point(1, 0), true));
        assertTrue(triangle.contains(new Point(0.5, 0.5), true));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), true));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), true));
    }

    @Test
    public void interiorTest() {
        Triangle triangle = new Triangle(new Point(0,0), new Point(0,1), new Point(1,0));

        //checking signs
        assertTrue(triangle.contains(new Point(0.3, 0.3), false));
        assertFalse(triangle.contains(new Point(0.3, -0.3), false));
        assertFalse(triangle.contains(new Point(-0.3, 0.3), false));
        assertFalse(triangle.contains(new Point(-0.3, -0.3), false));

        //checking edge cases
        assertFalse(triangle.contains(new Point(0, 0), false));
        assertFalse(triangle.contains(new Point(0, 1), false));
        assertFalse(triangle.contains(new Point(1, 0), false));
        assertFalse(triangle.contains(new Point(0.5, 0.5), false));

        assertTrue(triangle.contains(new Point(0.5 - Constants.EPSILON, 0.5 - Constants.EPSILON), false));
        assertTrue(triangle.contains(new Point(1 - Constants.EPSILON, 0 + Constants.EPSILON), false));
        assertTrue(triangle.contains(new Point(0 + Constants.EPSILON, 0 + Constants.EPSILON), false));
    }

}
