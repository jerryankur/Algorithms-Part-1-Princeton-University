/* *****************************************************************************
 *  Name:              Priyanshu Dwivedi
 *  Coursera User ID:  ankurdwivedi75@gmail.com
 *  Last modified:     24/08/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFull;     // for isFull() function as we need to know the union only from top, uf may link the union from down
    private final int sz;
    private final int n;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        grid = new boolean[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                grid[i][j] = false;

        this.n = n;
        openSites = 0;
        int nSquare = n*n;
        sz = nSquare + 2;               // two virtual node
        uf = new WeightedQuickUnionUF(sz);
        ufFull = new WeightedQuickUnionUF(sz);
        for (int i = 1; i <= n; ++i) {
            uf.union(0, i);
            uf.union(sz - 1, sz - 1 - i);
            ufFull.union(0, i);
        }

    }

    // opens the site (row, col) if it is not open already      1-indexing
    public void open(int row, int col) {
        validArgs(row, col);

        if (isOpen(row, col)) return;

        grid[row-1][col-1] = true;
        ++openSites;

        int index = posToIndex(row, col);

        if (row > 1 && isOpen(row-1, col)) {             // up
            uf.union(posToIndex(row - 1, col), index);
            ufFull.union(posToIndex(row - 1, col), index);
        }

        if (row < n && isOpen(row+1, col)) {              // down
            uf.union(posToIndex(row + 1, col), index);
            ufFull.union(posToIndex(row + 1, col), index);
        }
        if (col > 1 && isOpen(row, col-1)) {                // left
            uf.union(posToIndex(row, col - 1), index);
            ufFull.union(posToIndex(row, col - 1), index);
        }
        if (col < n && isOpen(row, col+1)) {            // right
            uf.union(posToIndex(row, col + 1), index);
            ufFull.union(posToIndex(row, col + 1), index);
        }
    }

    // is the site (row, col) open?                            1-indexing
    public boolean isOpen(int row, int col) {
        validArgs(row, col);
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?                            1-indexing
    public boolean isFull(int row, int col) {
        validArgs(row, col);
        return isOpen(row, col) && ufFull.find(posToIndex(row, col)) == ufFull.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return isOpen(1, 1);
        return uf.find(0) == uf.find(sz - 1);
    }

    // change grid position to index for union array
    private int posToIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    // validate arguments
    private void validArgs(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("row index out of bounds");

        if (col < 1 || col > n)
            throw new IllegalArgumentException("col index out of bounds");

    }
/*
    // test client (optional)
    public static void main(String[] args) {

    }

 */
}
