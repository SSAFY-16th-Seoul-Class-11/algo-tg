import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Solution {

    static final int DIV = 1_000_000_007;
    static final int MAX = 1001;
    static long[] dp = new long[MAX];
    static long[] facto = new long[MAX];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        Arrays.fill(dp, -1);
        Arrays.fill(facto, -1);
        dp[1] = 0;
        facto[1] = 1;

        for (int i = 2; i < MAX; i++) {
            facto[i] = facto[i - 1] * i % DIV;
            dp[i] = (dp[i - 1] * i % DIV + facto[i - 1] * (i / 2) % DIV) % DIV;
        }

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());

            sb.append("#")
                    .append(tc)
                    .append(" ")
                    .append(dp[N])
                    .append("\n");
        }

        System.out.print(sb);
        br.close();
    }
}
