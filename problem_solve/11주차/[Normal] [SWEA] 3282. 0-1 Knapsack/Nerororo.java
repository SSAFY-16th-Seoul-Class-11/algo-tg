import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] dp = new int[K + 1];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int V = Integer.parseInt(st.nextToken());
                int C = Integer.parseInt(st.nextToken());

                // 베낭 문제 핵심 점화식 (dp[w] = max(dp[w], dp[w-v] + c))
                for (int idx = K; idx >= V; idx--) {
                    dp[idx] = Math.max(dp[idx], dp[idx - V] + C);
                }
            }

            sb.append(dp[K]).append('\n');
        }

        System.out.print(sb);
    }
}

/*
시간복잡도
O(N * K)
 */