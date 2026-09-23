
import java.io.*;

class Solution {

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        long num = 1_000_000_007;
        long[] dp = new long[1001];
        long[] fan = new long[1001];
        fan[1] = 1L;

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());

            for (int i = 2; i <= N; i++) {
                fan[i] = fan[i - 1] * i % num;
                dp[i] = ((dp[i - 1] * i) + (i / 2 * fan[i - 1])) % num;
            }

            System.out.println("#" + test_case + " " + dp[N]);
        }
    }
}
