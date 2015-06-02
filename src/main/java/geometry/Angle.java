package geometry;

import constants.Constants;

/**
 * Created by joshheinrichs on 15-06-02.
 */
public class Angle {

    /**
     * Retruns the angle from 0 to the start of angle B
     * @param A
     * @param B
     * @param C
     * @return
     */
    static double getAngleStart(Point A, Point B, Point C) {

        double angle1 = Math.toDegrees(Math.atan2(A.y - B.y, A.x - B.x));
        double angle2 = Math.toDegrees(Math.atan2(C.y - B.y, C.x - B.x));

        if(angle1 < 0) {
            angle1 += 360.d;
        }
        if(angle2 < 0) {
            angle2 += 360.d;
        }

        if( Math.abs(((angle1 + getAngle(A, B, C)) % 360) - angle2) < Constants.EPSILON ) {
            return angle1;
        } else {
            assert( Math.abs(((angle2 + getAngle(A, B, C)) % 360) - angle1) < Constants.EPSILON ) : angle1 + ", " + angle2;
            return angle2;
        }
    }

    /**
     * Returns the inner angle B from the given triangle
     * @param A
     * @param B
     * @param C
     * @return
     */
    static double getAngle(Point A, Point B, Point C) {
        //law of cosines
        double b = A.distance(C);
        double a = C.distance(B);
        double c = B.distance(A);
        return Math.toDegrees(Math.acos((a * a + c * c - b * b) / (2.d * a * c)));
    }
}
