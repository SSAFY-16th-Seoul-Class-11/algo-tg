
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine().trim());
            double[][] p = new double[N][N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    p[i][j] = Integer.parseInt(st.nextToken()) / 100.0;
                }
            }

            int size = 1 << N;
            double[] dp = new double[size];
            dp[0] = 1.0;

            for (int mask = 0; mask < size - 1; mask++) {
                if (dp[mask] == 0.0) continue;

                int employee = Integer.bitCount(mask);
                int remaining = (size - 1) ^ mask;

                while (remaining != 0) {
                    int bit = remaining & -remaining;
                    int job = Integer.numberOfTrailingZeros(bit);

                    int next = mask | bit;

                    dp[next] = Math.max(
                        dp[next],
                        dp[mask] * p[employee][job]
                    );

                    remaining -= bit;
                }
            }

            sb.append(String.format(
                Locale.US, "#%d %.6f%n",
                tc, dp[size - 1] * 100.0
            ));
        }

        System.out.print(sb);
    }
}
