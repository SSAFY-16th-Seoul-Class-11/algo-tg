import java.io.*;
import java.util.*;

public class skyblue1 {
    static int N, min, hx, hy;
    static int[][] customer;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cx = Integer.parseInt(st.nextToken()), cy = Integer.parseInt(st.nextToken());
            hx = Integer.parseInt(st.nextToken()); hy = Integer.parseInt(st.nextToken());

            customer = new int[N][2];
            for (int i = 0; i < N; i++) {
                customer[i][0] = Integer.parseInt(st.nextToken());
                customer[i][1] = Integer.parseInt(st.nextToken());
            }

            visited = new boolean[N];
            min = Integer.MAX_VALUE;
            dfs(cx, cy, 0, 0);
            System.out.println("#" + tc + " " + min);
        }
    }

    static void dfs(int x, int y, int cnt, int dist) {
        if (dist >= min) return;

        if (cnt == N) {
            min = Math.min(min, dist + distance(x, y, hx, hy));
            return;
        }

        for (int i = 0; i < N; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            dfs(customer[i][0], customer[i][1], cnt + 1, dist + distance(x, y, customer[i][0], customer[i][1]));
            visited[i] = false;
        }
    }

    static int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
