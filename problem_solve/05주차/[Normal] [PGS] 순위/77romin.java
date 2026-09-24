class Solution {
    private boolean[][] win;
    private boolean[][] lose;
    private int N;
    
    public int solution(int n, int[][] results) {
        win = new boolean[n+1][n+1]; // {[winner_index][loser_index]}
        lose = new boolean[n+1][n+1];
        N = n;
        
        for(int i=0; i<results.length; i++) {
            int winner = results[i][0];
            int loser = results[i][1];
            win[winner][loser] = true; // write the result of fighting with loser
            lose[loser][winner] = true;
        }
        
        for(int i=1; i<=N; i++) {
            boolean[] visited = new boolean[n+1]; // for dfs
            dfs(i, i, visited); // (curIndex, rootIndex, visited arr)
        }
        
        int answer = 0;
        
        for(int i=1; i<n+1; i++) {
            int chkCnt = 0;
            for(int j=1; j<n+1; j++) {
                if(j==i) continue; // skip myself
                if(win[i][j]) chkCnt++;
                if(lose[i][j]) chkCnt++;
            }
            if(chkCnt==n-1)
                answer++;
        }
        return answer;
    }
    
    private void dfs(int player, int root, boolean[] visited) {
        visited[player] = true;
        
        for(int i=1; i<=N; i++) {
            if(i==player) continue; // skip oneself
            if(!win[player][i]) continue; // if the one who lose
            
            win[root][i] = true;
            lose[i][root] = true;
            
            if(!visited[i]) {
                dfs(i, root, visited);
            }
        }
    }
}

/**
 * Logic: if who has full of rounds result, he would correct his position.
 */
