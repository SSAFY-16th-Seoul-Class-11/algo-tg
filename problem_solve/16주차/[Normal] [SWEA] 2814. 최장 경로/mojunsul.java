import java.util.*;
import java.io.*;

class Solution {

    static int N, M;
    static boolean[][] connected;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            connected = new boolean[N][N];

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken()) - 1;

                connected[x][y] = true;
                connected[y][x] = true;
            }

            sb.append("#").append(tc).append(" ").append(solve()).append("\n");
        }

        System.out.println(sb);

        br.close();
    }
    static int solve() {
        int ret = 0;

        for (int i = 0; i < N; i++) {
            ret = Math.max(ret, dfs(i, (1<<i)));
        }

        return ret;
    }

    static int dfs(int cur, int mask) {
        int max = 1;

        for (int next = 0; next < N; next++) {
            if(!connected[cur][next]) continue;
            if((mask & (1<<next)) != 0) continue;

            int nextMask = mask | (1 << next);
            max = Math.max(max, dfs(next, nextMask) + 1);
        }

        return max;
    }
}
