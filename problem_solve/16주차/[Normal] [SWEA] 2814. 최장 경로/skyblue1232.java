
import java.util.*;

public class Solution {
    static int N, M, max;
    static boolean[][] graph;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            N = sc.nextInt();
            M = sc.nextInt();

            graph = new boolean[N + 1][N + 1];
            visited = new boolean[N + 1];
            max = 1;

            for (int i = 0; i < M; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();

                graph[x][y] = true;
                graph[y][x] = true;
            }

            for (int i = 1; i <= N; i++) {
                dfs(i, 1);
            }

            System.out.println("#" + tc + " " + max);
        }

        sc.close();
    }

    static void dfs(int current, int count) {
        visited[current] = true;

        max = Math.max(max, count);

        for (int next = 1; next <= N; next++) {
            if (graph[current][next] && !visited[next]) {
                dfs(next, count + 1);
            }
        }

        visited[current] = false;
    }
}
