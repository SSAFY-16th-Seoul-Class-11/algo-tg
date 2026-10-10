import java.util.*;

class Solution {

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        for(int i=0; i<dp.length; i++)
            dp[i] = 1;
    
        int maxCnt = 1;
        for(int i=1; i<dp.length; i++) {
            for(int j=0; j<i; j++) {
                if(nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            maxCnt = Math.max(maxCnt, dp[i]);
        }
        return maxCnt;
    }
}

// Using Algorithm: DP
// Time Complexity: O(n^2)

