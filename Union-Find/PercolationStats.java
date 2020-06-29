import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class PercolationStats {

    private double[] res;
    private int trails;
    private int size;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new java.lang.IllegalArgumentException("N <= 0");
        if (T <= 0) throw new java.lang.IllegalArgumentException("T <= 0");

        size = N;
        trails = T;
        res = new double[trails];

        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(size);
            while (!perc.percolates())
            {
                int row = StdRandom.uniform(1, size + 1);
                int col = StdRandom.uniform(1, size + 1);
                perc.open(row, col);
            }
            int num = perc.numberOfOpenSites();
            double result = (double) num / (size * size);
            res[i] = result;

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(res);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(res);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(trails);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trails);
    }

    // test client, described below
    public static void main(String[] args) {
        int N = 2;
        int T = 100000;
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean                     = %f\n", stats.mean());
        StdOut.printf("stddev                   = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval  = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}