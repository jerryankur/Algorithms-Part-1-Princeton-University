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

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);
        ArrayList<Point[]> collinearPoints = new ArrayList<Point[]>();

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        for (int p = 0; p < sortedPoints.length; ++p) {
            for (int q = p + 1; q < sortedPoints.length; ++q) {
                double slopePQ = sortedPoints[p].slopeTo(sortedPoints[q]);
                for (int r = q + 1; r < sortedPoints.length; ++r) {
                    double slopePR = sortedPoints[p].slopeTo(sortedPoints[r]);
                    if (slopePR != slopePQ) continue;
                    for (int s = r + 1; s < sortedPoints.length; ++s) {
                        double slopePS = sortedPoints[p].slopeTo(sortedPoints[s]);
                        if (slopePS != slopePQ) continue;
                        collinearPoints.add(new Point[] { sortedPoints[p], sortedPoints[s] });
                    }
                }
            }
        }
        collinearPoints = getLineSegmentsPoints(collinearPoints);
        lineSegments = new LineSegment[collinearPoints.size()];
        for (int i = 0; i < collinearPoints.size(); ++i)
            lineSegments[i] = new LineSegment(collinearPoints.get(i)[0], collinearPoints.get(i)[1]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private ArrayList<Point[]> getLineSegmentsPoints(ArrayList<Point[]> collinearPoints) {

        ArrayList<Point[]> newCollinearPoints = new ArrayList<Point[]>();
        for (Point[] points: collinearPoints) {
            double slope = points[0].slopeTo(points[1]);
            int i;
            for (i = 0; i < newCollinearPoints.size(); ++i) {
                Point p01 = newCollinearPoints.get(i)[0], p02 = newCollinearPoints.get(i)[1];
                if (p01.slopeTo(p02) == slope && (p01.slopeTo(points[1]) == slope
                        || points[0].slopeTo(p02) == slope)) {
                    if (points[0].compareTo(p01) < 0) newCollinearPoints.get(i)[0] = points[0];
                    if (points[1].compareTo(p02) > 0) newCollinearPoints.get(i)[1] = points[1];
                    break;
                }
            }
            if (i == newCollinearPoints.size()) newCollinearPoints.add(new Point[] { points[0], points[1] });
        }
        return newCollinearPoints;
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
            if (args.length > 0 && !file.startsWith(args[0])) continue;
            In in = new In(file);
            int n = in.readInt();
            if (n > 100) continue;
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
            BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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