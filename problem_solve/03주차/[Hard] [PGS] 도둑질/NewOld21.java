import java.util.*;
class Solution {
    public int solution(int[] money) {
        
        // 첫 번째 집 포함
        int[] dp1 = new int[money.length];
        dp1[1] = money[0];
        dp1[2] = money[0];
        
        // 첫 번쨰 집 제외
        int[] dp2 = new int[money.length+1];
        dp2[2] = money[1];
 
        
        for(int i=3; i<money.length ; i++) {
        	dp1[i] = Math.max(dp1[i-1], dp1[i-2]+ money[i-1]);
        }
        
        for(int i=3; i<money.length+1 ; i++) {
        	dp2[i] = Math.max(dp2[i-1], dp2[i-2]+ money[i-1]);
        }
       
        return  Math.max(
            dp1[dp1.length - 1],
            dp2[dp2.length - 1]
        );
    }
}