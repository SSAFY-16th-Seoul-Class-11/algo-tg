import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] volume = new int[N + 1];
            int[] value = new int[N + 1];

            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());

                volume[i] = Integer.parseInt(st.nextToken());
                value[i] = Integer.parseInt(st.nextToken());
            }

            int[][] dp = new int[N + 1][K + 1];

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= K; j++) {

                    if (j < volume[i]) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        dp[i][j] = Math.max(
                                dp[i - 1][j],
                                dp[i - 1][j - volume[i]] + value[i]
                        );
                    }
                }
            }

            System.out.println("#" + tc + " " + dp[N][K]);
        }
    }
}
