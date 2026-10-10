import java.util.*;
import java.io.*;

class Solution {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int T = sc.nextInt();

        StringBuilder sb = new StringBuilder();

        for (int test_case = 1; test_case <= T; test_case++) {
            int n = sc.nextInt();
            int[][] dist = new int[n][n];
            int INF = 1000000; // 충분히 큰 값 (무한대 대용)

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int val = sc.nextInt();
                    if (i == j) {
                        dist[i][j] = 0;
                    } else if (val == 1) {
                        dist[i][j] = 1;
                    } else {
                        dist[i][j] = INF;
                    }
                }
            }

            // 플로이드-워샬 알고리즘 (모든 노드 간 최단 거리)
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                        }
                    }
                }
            }

            int minCnt = Integer.MAX_VALUE;

            // 각 노드별 CC(i) = 다른 모든 노드까지의 거리 합 계산
            for (int i = 0; i < n; i++) {
                int sumCnt = 0;
                for (int j = 0; j < n; j++) {
                    sumCnt += dist[i][j];
                }
                minCnt = Math.min(minCnt, sumCnt);
            }

            sb.append("#").append(test_case).append(" ").append(minCnt).append("\n");
        }
        System.out.print(sb);
        sc.close();
    }
}
