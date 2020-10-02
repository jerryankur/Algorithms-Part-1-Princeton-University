/* *****************************************************************************
 *  Name:              Priyanshu Dwivedi
 *  Coursera User ID:  ankurdwivedi75@gmail.com
 *  Last modified:     24/08/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] results;
//    private final int n;
    private final int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

//        this.n = n;
        t = trials;
        double nSquare = n * n;
        results = new double[t];
        Percolation perc;
        for (int k = 0; k < trials; ++k) {
            perc = new Percolation(n);

            int i, j, openNum = 0;
            while (!perc.percolates()) {
                i = StdRandom.uniform(1, n+1);
                j = StdRandom.uniform(1, n+1);
                if (perc.isOpen(i, j))
                    continue;
                perc.open(i, j);
                ++openNum;
            }
            results[k] = openNum / nSquare;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
//        Stopwatch watch = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.printf("mean = %f%n", ps.mean());
        StdOut.printf("stddev = %f%n", ps.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]%n", ps.confidenceLo(), ps.confidenceHi());
//        StdOut.printf("Elapsed Time = %f sec%n", watch.elapsedTime());
    }
}
