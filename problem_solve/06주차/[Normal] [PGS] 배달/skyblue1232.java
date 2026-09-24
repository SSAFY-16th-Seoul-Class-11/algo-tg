import java.util.*;

class Solution {

    public int solution(int N, int[][] road, int K) {
        int INF = 1_000_000_000;
        int[][] dist = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // 같은 두 마을 사이에 여러 도로가 있을 수 있어서 최소값만 저장
        for (int[] r : road) {
            int a = r[0];
            int b = r[1];
            int time = r[2];

            dist[a][b] = Math.min(dist[a][b], time);
            dist[b][a] = Math.min(dist[b][a], time);
        }

        // k번 마을을 거쳐가는 경우가 더 빠른지 확인
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    dist[i][j] = Math.min(
                        dist[i][j],
                        dist[i][k] + dist[k][j]
                    );
                }
            }
        }

        int answer = 0;

        for (int i = 1; i <= N; i++) {
            if (dist[1][i] <= K)
                answer++;
        }

        return answer;
    }
}
