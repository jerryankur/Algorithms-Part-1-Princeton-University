/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<Point[]> collinearPoints;
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        validatePoints(points);

        collinearPoints = new ArrayList<Point[]>();
        Point[] copyPoints = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(copyPoints);


        for (int p = 0; p < points.length; ++p) {
            Point[] orderPoints = Arrays.copyOfRange(copyPoints, p, points.length);
            Arrays.sort(orderPoints, orderPoints[0].slopeOrder());
            Point p1 = orderPoints[0];
            Point p2 = copyPoints[0];
            int count = 0;
            double slope = p1.slopeTo(p2);
            for (int q = 1; q < orderPoints.length; ++q) {
                double slopePQ = p1.slopeTo(orderPoints[q]);
                if (slopePQ == slope) {
                    ++count;
                    if (orderPoints[q].compareTo(p2) > 0) p2 = orderPoints[q];
                }
                else {
                    if (count > 2)
                        collinearPoints.add(new Point[] { p1, p2 });
                    p2 = orderPoints[q];
                    slope = slopePQ;
                    count = 1;
                }
            }
            if (count > 2)
                collinearPoints.add(new Point[] { p1, p2 });
        }
        createUniqueSegmentPoints();
        lineSegments = new LineSegment[collinearPoints.size()];
        for (int i = 0; i < collinearPoints.size(); ++i)
            lineSegments[i] = new LineSegment(collinearPoints.get(i)[0], collinearPoints.get(i)[1]);
    }

    public int numberOfSegments() {        // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() {               // the line segments
        return lineSegments.clone();
    }

    private void createUniqueSegmentPoints() {
        if (collinearPoints.isEmpty()) return;
        ArrayList<Point[]> newCollinearPoints = new ArrayList<Point[]>();
        collinearPoints.sort((a, b) -> a[1].compareTo(b[1]));
        collinearPoints.sort((a, b) -> Double.compare(a[0].slopeTo(a[1]), b[0].slopeTo(b[1])));
        Point p0 = collinearPoints.get(0)[1];
        newCollinearPoints.add(collinearPoints.get(0));
        double slope = collinearPoints.get(0)[0].slopeTo(collinearPoints.get(0)[1]);
        for (int i = 1; i < collinearPoints.size(); ++i) {
            Point p1 = collinearPoints.get(i)[0], p2 = collinearPoints.get(i)[1];
            double newSlope = p1.slopeTo(p2);
            if (Double.compare(newSlope, slope) == 0 && p2.compareTo(p0) == 0) continue;
            newCollinearPoints.add(collinearPoints.get(i));
            slope = newSlope;
            p0 = p2;
        }
        collinearPoints = newCollinearPoints;
    }

    private void validatePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int p = 0; p < points.length; ++p) {
            if (points[p] == null) throw new IllegalArgumentException();
            for (int q = p + 1; q < points.length; ++q) {
                if (points[q] == null || points[p].compareTo(points[q]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        File directoryPath = new File(".");
        String[] contents = directoryPath.list();
        if (contents == null || contents.length == 0) return;
        for (String file : contents) {
            if (!file.endsWith(".txt")) continue;
            if (args.length > 0 && !file.startsWith(args[0])) continue;      // only execute file given as argument
            In in = new In(file);
            int n = in.readInt();
            System.out.println(file);
            Out out = new Out("./output/output_" + file);
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }

            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show();

            // print and draw the line segments
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                System.out.println(segment);
                out.println(segment);
                segment.draw();
            }
            StdDraw.show();
            StdDraw.save("./output/output_" + file.substring(0, file.indexOf(".txt")) + ".png");
            StdDraw.clear();
            in.close();
            out.close();
        }
        System.out.println("--finished--");
    }
}