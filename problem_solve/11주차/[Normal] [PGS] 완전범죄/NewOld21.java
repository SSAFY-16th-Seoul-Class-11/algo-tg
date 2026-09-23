import java.util.*;

class Solution {
    public int solution(int[][] info, int n, int m) {
        
        int[] dp = new int[m];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for(int i=0; i<info.length; i++){
            
            int[] next = new int[m];
            Arrays.fill(next, Integer.MAX_VALUE);
            
            int a = info[i][0];
            int b = info[i][1];
            
            for(int j=0; j<m; j++){
                
                if(dp[j] == Integer.MAX_VALUE)
                    continue;
                
                // A가 훔치는 경우
                if(dp[j] + a < n){
                    next[j] = Math.min(next[j], dp[j] + a);
                }
                
                // B가 훔치는 경우
                if(j + b < m){
                    next[j+b] = Math.min(next[j+b], dp[j]);
                }
            }
            
            dp = next;
        }
        
        int ans = Integer.MAX_VALUE;
        
        for(int i=0; i<m; i++){
            ans = Math.min(ans, dp[i]);
        }
        
        if(ans == Integer.MAX_VALUE)
            return -1;
        
        return ans;
    }
}