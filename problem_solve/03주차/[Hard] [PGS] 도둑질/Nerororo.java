import java.io.*;
import java.util.*;

// 뭔가 좀 더러운데 아무튼 통과함..(???)
class Solution {
    static int len;
    public int solution(int[] money) {
        len = money.length;
        int[] dp1 = new int[len];
        int[] dp2 = new int[len];

        dp1[0] = money[0];
        dp1[1] = Math.max(money[0], money[1]);
        dp2[1] = money[1];
        dp2[2] = Math.max(money[1], money[2]);

        int answer = 0;
        answer = Math.max(dpCalc(dp1, money, 0), dpCalc(dp2, money, 1));

        return answer;
    }

    public int dpCalc(int[] dp, int[] money, int idx) {
        for (int i = idx + 2; i < len - 1 + idx; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + money[i]);
        }

        return dp[len - 2 + idx];
    }
}