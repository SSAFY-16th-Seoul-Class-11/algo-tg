class Solution {
    private final int MAXFARE = 100000;
    
    private int N;
    private int S;
    private int A;
    private int B;
    private int[][] roads;
    private int[] dp;
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        init(n, s, a, b, fares);
        return stoKtoAnB();
    }
    
    private int stoKtoAnB() {
        for(int i=1; i<=N; i++)
            dp[i]=MAXFARE;
        catchTaxi(S, 0, dp);
        
        int minFee = MAXFARE;
        
        for(int k=1; k<=N; k++) {
            int tmpFee = dp[k];
            int[] tmpDp = new int[N+1];
            for(int i=1; i<=N; i++)
                tmpDp[i]=MAXFARE;
            catchTaxi(k, 0, tmpDp);
            tmpFee += (tmpDp[A]+tmpDp[B]);
            minFee = tmpFee<minFee ? tmpFee:minFee;
        }
        
        return minFee;
    }
    
    private void catchTaxi(int cp, int sumFee, int[] dp) { // cp: current point, sumFee: sum of fee
        if(dp[cp]<=sumFee) return;
        else dp[cp]=sumFee;
        
        for(int i=1; i<=N; i++) {
            if(roads[cp][i]==0) continue;
            catchTaxi(i, sumFee+roads[cp][i], dp);
        }
    }
    
    private void init(int n, int s, int a, int b, int[][] fares) {
        N=n; S=s;
        A=a; B=b;
        roads = new int[N+1][N+1];
        for(int[] fare : fares) {
            roads[fare[0]][fare[1]] = fare[2];
            roads[fare[1]][fare[0]] = fare[2];
        }
        dp = new int[N+1];
    }
}

/**
 * we have 2 cases. read down below!
 * Case#1. S->K, K->A + K->B
 * Case#2. S->A, S->B
 * so, we can detect what is minimum fee in this 2 of cases.
 * Time Complexity: O(N!)
 */
