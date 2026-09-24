import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int INF = Integer.MAX_VALUE;

        for (int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int X = Integer.parseInt(st.nextToken());

            int[][] dist = new int[N + 1][N + 1];

            // 초기화
            for (int i = 1; i <= N; i++) {
                Arrays.fill(dist[i], INF);
                dist[i][i] = 0;
            }

            // 입력
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                dist[from][to] = cost;
            }

            // 플로이드 워셜
            for (int k = 1; k <= N; k++) {
                for (int i = 1; i <= N; i++) {
                    // 가지치기
                    if (dist[i][k] == INF) continue;

                    for (int j = 1; j <= N; j++) {
                        if (dist[k][j] == INF) continue;
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }

            // X까지 갔다가 X에서 돌아오는 시간의 최댓값
            int max = 0;
            for (int i = 1; i <= N; i++) {
                int cur = dist[i][X] + dist[X][i];
                max = Math.max(max, cur);
            }

            sb.append(max).append('\n');
        }

        System.out.println(sb.toString());
    }
}

/* 플로이드 위셜은 n^3으로 이번 문제에선 시간 초과의 우려가 있다.
 * 또한 모든 노드간 최소 거리를 구할 필요가 없기에 사용할 알고리즘에서 제외한다.
 * 다익스트라 2개로 각각 가는방향, 오는방향으로 해결하면 2*n^log n 으로 해결 가능할 것 같다.
 * 근데.. 다익스트라 2개 구현이 너무 어려워서.. 일단.. 플로이드 위셜로 제출...
 * 일부 정답 맞는거 보면 N이 작을 경우는 통과하는 것 같다
 */