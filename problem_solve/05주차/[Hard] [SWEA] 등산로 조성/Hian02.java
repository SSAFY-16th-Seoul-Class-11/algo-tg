import java.io.*;
import java.util.*;

public class Solution {

    static int N, K;
    static int[][] map;
    static boolean[][] visited;
    static int answer;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static void dfs(int x, int y, int length, boolean cut) {
        answer = Math.max(answer, length);
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            // 범위를 벗어나거나 이미 방문한 곳이면 제외
            if (nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny]) {
                continue;
            }

            // 1. 그냥 내려갈 수 있는 경우
            if (map[nx][ny] < map[x][y]) {
                visited[nx][ny] = true;
                dfs(nx, ny, length + 1, cut);
                visited[nx][ny] = false;
            }

            // 2. 그냥 갈 수 없지만, 아직 공사를 안 한 경우
            else if (!cut && map[nx][ny] - K < map[x][y]) {
                int original = map[nx][ny];
                // 현재 위치보다 딱 1 낮게 깎음
                map[nx][ny] = map[x][y] - 1;
                visited[nx][ny] = true;
                dfs(nx, ny, length + 1, true);
                visited[nx][ny] = false;
                // 원래 높이로 복구
                map[nx][ny] = original;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            int maxHeight = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(maxHeight, map[i][j]);
                }
            }
            answer = 0;
            // 가장 높은 봉우리에서만 시작
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == maxHeight) {
                        visited = new boolean[N][N];
                        visited[i][j] = true;
                        dfs(i, j, 1, false);
                    }
                }
            }
            System.out.println("#" + tc + " " + answer);
        }
    }
}
