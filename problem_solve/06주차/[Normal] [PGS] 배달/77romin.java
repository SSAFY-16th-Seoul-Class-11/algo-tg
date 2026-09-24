class Solution {
    private int n; // N: num of Viallages
    private int k; // K: hour to be possible to deliver
    private int[][] cost;
    private int pCnt;
    private boolean[] counted; // already counted village including pCnt
    
    public int solution(int N, int[][] road, int K) {
        init(N, road, K); // initalization
        int[] dist = new int[n+1];
        for(int i=0; i<n+1; i++)
            dist[i] = Integer.MAX_VALUE;
        deliver(1, 0, dist); // village 1 is a first departure point
        return pCnt;
    }
    
    private void deliver(int cv, int sh, int[] dist) { // cv: current village, sh: sum of hours, visted: visited
        if(sh>k) return;
        
        if(sh>=dist[cv]) return;
        
        dist[cv] = sh;
        
        if(!counted[cv]) {
            pCnt++;
            counted[cv]=true;
        }
        
        for(int nv=1; nv<=n; nv++)
            if(cost[cv][nv]!=0)
                deliver(nv, sh+cost[cv][nv], dist);
    }
    
    private void init(int N, int[][] road, int K) { // Initialization
        pCnt=0;
        n=N;
        k=K;
        cost = new int[N+1][N+1];
        counted = new boolean[N+1];
        for(int i=0; i<road.length; i++)
            wCost(road[i][0], road[i][1], road[i][2]);
    }
    
    private void wCost(int v1, int v2, int h) { // write cost of hour
        if(cost[v1][v2]==0) {
            cost[v1][v2] = h;
            cost[v2][v1] = h;
        } else if(cost[v1][v2]>h) { // if it duplicate, update min cost
            cost[v1][v2] = h;
            cost[v2][v1] = h;
        }
    }
}

/**
 * dist 배열을 이용하여 이미 방문한 마을인지 확인하고, 방문한 마을이라면 더 적은 시간으로 도달할 수 있는지 확인한다.
 * Time Complexity: O(n^2)
 */
