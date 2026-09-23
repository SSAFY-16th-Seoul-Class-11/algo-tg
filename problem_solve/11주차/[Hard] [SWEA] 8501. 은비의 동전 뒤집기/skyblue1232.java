import java.io.*;
import java.util.*;

public class Solution {
    static final long MOD = 1_000_000_007L;
    static final int MAX_N = 1000;
    static long[] factorial = new long[MAX_N + 1];
    static long[] dp = new long[MAX_N + 1];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        factorial[0] = 1;

        for (int i = 1; i <= MAX_N; i++) {
            factorial[i] = factorial[i - 1] * i % MOD;
        }
        
        dp[1] = 0;

        for (int n = 2; n <= MAX_N; n++) {
            long previous = (dp[n - 1] * n) % MOD;
            long added = ((long) (n / 2) * factorial[n - 1]) % MOD;

            dp[n] = (previous + added) % MOD;
        }

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());

            sb.append("#").append(tc).append(" ").append(dp[N]).append("\n");
        }

        System.out.print(sb);
    }
}
