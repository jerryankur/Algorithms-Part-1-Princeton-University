/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.Scanner;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1),
                    slope2 = slopeTo(p2);
            return Double.compare(slope1, slope2);
        }
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        validatePoint(that);
        double slope = (double) (that.y - this.y) / (that.x- this.x);
        if (slope == 0.0 || slope == Double.NEGATIVE_INFINITY) return Math.abs(slope);
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        validatePoint(that);
        if (this.y == that.y) return Integer.compare(this.x, that.x);
        return Integer.compare(this.y, that.y);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
/*//      both way it's correct
        return (Point p1, Point p2) -> {
            double slope1 = slopeTo(p1),
                    slope2 = slopeTo(p2);
            if (slope1 < slope2) return -1;
            else if (slope1 == slope2) return 0;
            else return 1;
        };
*/
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private void validatePoint(Point that) {
        if (that == null) throw new NullPointerException();
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Scanner sc = new Scanner(System.in, "UTF-8");
        int n = sc.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; ++i) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        }
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j) {
                int f = points[i].compareTo(points[j]);
                System.out.println(
                        points[i].toString() + (f > 0 ? " > " : f < 0 ? " < " : " == ") + points[j].toString());
                System.out.println("Slope of "+points[i].toString()+" to "+points[j].toString()+" is: "+points[i].slopeTo(points[j]));
                points[i].drawTo(points[j]);
            }
    }
}
