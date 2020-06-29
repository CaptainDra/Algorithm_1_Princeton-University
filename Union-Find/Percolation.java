import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int N;
    private int[] open;
    private WeightedQuickUnionUF uf;
    private StdRandom random;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        N = n;
        size = n * n;
        open = new int[size];
        uf = new WeightedQuickUnionUF(size+2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        islegal(row, col);
        int loc = location1D(row,col);
        open[loc] = 1;
        connect(row, col);
    }

    private void islegal(int row, int col)
    {
        if (row <= 0)
        {
            throw new java.lang.IllegalArgumentException("<=0");
        }
        if (col <= 0)
        {
            throw new java.lang.IllegalArgumentException("<=0");
        }
    }

    private int location1D(int row, int col)
    {
        return ((row - 1) * N) + col - 1;
    }

    private void connect(int row, int col)
    {
        int loc = location1D(row, col);
        if(col < N)
        {
            union(row, col + 1, loc);
        }
        if(col > 1)
        {
            union(row, col -1, loc);
        }

        if (row < N)
        {
            union(row + 1, col, loc);
        }
        else
        {
            uf.union(loc, size + 1); //union with down bound
        }

        if (row > 1)
        {
            union(row - 1 , col, loc);
        }
        else
        {
            uf.union(loc, size); //union with up bound, if (size,size + 1)connected,finish
        }

    }

    private void union(int row, int col, int loc)
    {
        if(isOpen(row, col))
        {
            uf.union(location1D(row, col), loc);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        islegal(row, col);
        int loc = location1D(row, col);
        return open[loc] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        islegal(row, col);
        return connected(size, location1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        int sum = 0;
        for( int i = 0; i < size; i++)
        {
            if(open[i] == 1) sum += 1;
        }
        return sum;
    }

    private boolean connected(int p, int q)
    {
        return uf.find(p) == uf.find(q);
    }

    // does the system percolate?
    public boolean percolates()
    {
        return connected(size, size + 1);
    }

    // test client (optional)
    public static void main(String[] args)
    {
        int N = 20;
        Percolation perc = new Percolation(N);
        while(!perc.percolates())
        {
            int i = perc.random.uniform(1,N+1);
            int j = perc.random.uniform(1,N+1);
            perc.open(i, j);
            //StdOut.printf("%d,%d\n",i,j);

        }
        int num = perc.numberOfOpenSites();
        double persontage = (double)(num) / (double)(perc.size);
        StdOut.printf("number of opensite:%d\n",num);
        StdOut.printf("percentage:%.2f\n",persontage);
    }
}
