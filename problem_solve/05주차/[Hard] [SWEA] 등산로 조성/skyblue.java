import java.io.*;
import java.util.*;

public class skyblue {
    static int N, K, max, ans;
    static int[][] map, d = {{0,-1},{0,1},{-1,0},{1,0}};
    static boolean[][] v;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); K = Integer.parseInt(st.nextToken());
            map = new int[N][N]; v = new boolean[N][N]; max = ans = 0;

            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    map[r][c] = Integer.parseInt(st.nextToken());
                    max = Math.max(max, map[r][c]);
                }
            }

            for (int r = 0; r < N; r++) for (int c = 0; c < N; c++)
                if (map[r][c] == max) {
                    v[r][c] = true;
                    dfs(r, c, 1, false);
                    v[r][c] = false;
                }

            System.out.println("#" + tc + " " + ans);
        }
    }

    static void dfs(int r, int c, int len, boolean cut) {
        ans = Math.max(ans, len);

        for (int i = 0; i < 4; i++) {
            int nr = r + d[i][0], nc = c + d[i][1];
            if (nr < 0 || nr >= N || nc < 0 || nc >= N || v[nr][nc]) continue;

            if (map[nr][nc] < map[r][c]) {
                v[nr][nc] = true;
                dfs(nr, nc, len + 1, cut);
                v[nr][nc] = false;
            } else if (!cut && map[nr][nc] - K < map[r][c]) {
                int tmp = map[nr][nc];
                map[nr][nc] = map[r][c] - 1;
                v[nr][nc] = true;
                dfs(nr, nc, len + 1, true);
                v[nr][nc] = false;
                map[nr][nc] = tmp;
            }
        }
    }
}
