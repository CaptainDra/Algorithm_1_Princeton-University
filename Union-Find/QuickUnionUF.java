public class QuickUnionUF {
    private int[] id;
    public QuickUnionUF(int N) // Trees will be too tall
    {
        id = new int[N];
        for(int i = 0; i < N; i++)  id[i] = i;
    }

    private int root(int i)
    {
        while (i != id[i])
        {
            //id[i] = id[id[i]]; //improvement 2: Path compression to keep the tree flat
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q)
    {   return root(p) == root(q);   }

    public void union(int p, int q) //change root of p to point of root of q
    {
        int i = root(p);
        int j = root(q);
        id[i] = j;

        /* improvements 1: weight union
        if (i == j) return;
        if(sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; } //sz is size of union, need init before use
        else              { id[j] = i; sz[i] += sz[j]; }
         */
    }
}
