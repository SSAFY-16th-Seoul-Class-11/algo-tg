import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Solution {
    
    static final int DIV = 1_000_000_007;
    static final int MAX = 1001;
    static int[] dp = new int[MAX];
    static int[] facto = new int[MAX];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        Arrays.fill(dp, -1);
        Arrays.fill(facto, -1);
        dp[1] = 0;
        facto[0] = 1;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int ans = solve(N);

            sb.append("#")
              .append(tc)
              .append(" ")
              .append(ans)
              .append("\n");
        }

        System.out.print(sb);
        br.close();
    }
    
    private static int solve(int n) {
        if (dp[n] != -1) return dp[n];
        
        return dp[n] = (int) ((((long) solve(n - 1) * n) % DIV + ((long) factorial(n - 1) * (n / 2)) % DIV) % DIV);
    }
    
    private static int factorial(int n) {
        if (facto[n] != -1) return facto[n];
        
        return facto[n] = (int) (((long) factorial(n - 1) * n) % DIV);
    }
}
