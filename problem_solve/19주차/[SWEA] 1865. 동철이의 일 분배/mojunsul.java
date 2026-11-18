    import java.util.*;
    import java.io.*;

    class Solution {

        static final int bias = 1000000000;
        static int N;
        static double[][] P;
        static double ans;

        public static void main(String args[]) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder sb = new StringBuilder();
            StringTokenizer st;

            int T = Integer.parseInt(br.readLine());
            for (int tc = 1; tc <= T; tc++) {
                N = Integer.parseInt(br.readLine());

                P = new double[N][N];

                for (int i = 0; i < N; i++) {
                    st = new StringTokenizer(br.readLine());
                    for (int j = 0; j < N; j++) {
                        P[i][j] = (double)Integer.parseInt(st.nextToken()) / 100;
                    }
                }

                ans = 0;
                dfs(0, 0, bias);
                sb.append("#").append(tc).append(" ").append(String.format("%.6f", ans * 100 / bias)).append("\n");
            }

            System.out.println(sb);

            br.close();
        }

        private static void dfs(int idx, int mask, double probability) {
            if(probability <= ans) return;
            if(idx == N) {
                ans = Math.max(ans, probability);
                return;
            }

            for (int k = 0; k < N; k++) {
                if((mask & (1<<k)) != 0) continue;

                dfs(idx + 1, mask | (1 << k), probability * P[idx][k]);
            }
        }
    }
