// v.플로이드 워셜

import java.io.*;
import java.util.*;

public class Solution {

    static int N, M, X;
    static int[][] graph;
    static final int INF = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            initGraph(br);

            int min = getMaxCycle();

            sb.append("#").append(tc).append(" ").append(min).append("\n");
        }

        System.out.print(sb);
        br.close();
    }

    private static void initGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(graph[i], INF);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph[x][y] = c;
        }
    }

    private static int getMaxCycle() {
        for (int k = 1; k <= N; k++) {
            graph[k][k] = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        int max = 0;
        for (int i = 1; i <= N; i++) {
            if(graph[X][i] >= INF || graph[i][X] >= INF) continue;
            max = Math.max(max, graph[X][i] + graph[i][X]);
        }

        return max;
    }
}

// 시간 복잡도 O(V^3)
