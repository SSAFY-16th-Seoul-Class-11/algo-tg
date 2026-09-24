/*
문제 정의

N개의 정점으로 이루어진 무향 그래프가 있고,
각 간선에는 X 가중치와 Y 가중치 두 개가 존재합니다.

1번 정점에서 2번 정점까지 이동할 때 경로의 비용은
(X 가중치의 총합) * (Y 가중치의 총합)

1번에서 2번으로 갈 수 있는 모든 경로 중
비용이 가장 작은 값을 구하는 문제입니다.

만약 1번에서 2번으로 갈 수 없다면 -1을 출력합니다.
*/

/*
접근 방법

다익스트라! -> ???

X의 합 * Y의 합
X의 누적합을 상태로 사용합니다.

dp[x][v]
->1번 정점에서 v번 정점까지 이동했을 때
  X의 합이 정확히 x인 경로 중
  Y의 합의 최솟값

간선의 X 값은 항상 1 이상이므로 간선을 하나 이동할 때마다 X의 누적합은 반드시 증가
따라서 X 합이 작은 상태부터 순서대로 확인하면서 다음 정점의 상태를 갱신할 수 있습니다.

또한 최적 경로에는 같은 정점을 반복해서 방문할 필요가 없습니다.
모든 X, Y 값이 양수이므로사이클을 한 번 돌면 X의 합과 Y의 합이 모두 증가하기 때문입니다.

따라서 최적 경로는 최대 N-1개의 간선을 사용합니다.
각 X의 최대값은 9이므로 X의 최대 누적합은
9 * (N - 1)까지만 확인하면 됩니다.

마지막으로 2번 정점에 도착한 모든 상태에 대해
x * dp[x][2]를 계산하고 그 중 최솟값을 구합니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    static class Edge {
        int to;
        int x;
        int y;

        Edge(int to, int x, int y) {
            this.to = to;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
          
            ArrayList<Edge>[] graph = new ArrayList[N + 1];

            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                // 무향 그래프이므로 양쪽 모두 저장
                graph[a].add(new Edge(b, x, y));
                graph[b].add(new Edge(a, x, y));
            }

            // 최적 경로는 최대 N-1개의 간선을 사용
            // 각 X의 최대값은 9
            int maxX = 9 * (N - 1);
            int INF = Integer.MAX_VALUE / 2;
            // dp[xSum][node]
            // X의 합이 xSum일 때 node까지 가는 Y 합의 최솟값
            int[][] dp = new int[maxX + 1][N + 1];

            for (int i = 0; i <= maxX; i++) {
                Arrays.fill(dp[i], INF);
            }

            // 시작점
            // 아직 간선을 사용하지 않았으므로 X 합 = 0, Y 합 = 0
            dp[0][1] = 0;

            // X 누적합이 작은 상태부터 탐색
            for (int xSum = 0; xSum <= maxX; xSum++) {
                for (int now = 1; now <= N; now++) {
                    if (dp[xSum][now] == INF) {
                        continue;
                    }
                    for (Edge edge : graph[now]) {
                        int nextX = xSum + edge.x;
                        // 최대 범위를 넘어가면 탐색하지 않음
                        if (nextX > maxX) {
                            continue;
                        }
                        int nextY = dp[xSum][now] + edge.y;

                        // 같은 X 합이라면 Y 합이 작은 경로만 저장
                        if (nextY < dp[nextX][edge.to]) {
                            dp[nextX][edge.to] = nextY;
                        }
                    }
                }
            }

            int answer = INF;

            // 2번 정점에 도착한 모든 경우 확인
            for (int xSum = 1; xSum <= maxX; xSum++) {
                if (dp[xSum][2] == INF) {
                    continue;
                }
                int cost = xSum * dp[xSum][2];
                answer = Math.min(answer, cost);
            }

            if (answer == INF) {
                answer = -1;
            }

            System.out.println("#" + tc + " " + answer);
        }
    }
}


/*
시간복잡도

N : 정점의 개수
M : 간선의 개수

X의 최대 누적합은 9 * (N - 1)
각 X 누적합마다 각 정점과 연결된 간선을 확인
따라서 시간복잡도는 O(9 * N * M)
*/
