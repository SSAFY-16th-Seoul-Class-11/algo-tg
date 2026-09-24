/*
문제 정의
N개의 집이 있고, 집들은 단방향 도로로 연결되어 있습니다.

모든 사람은 자신의 집에서 인수의 집 X까지 이동한 뒤,
다시 자신의 집으로 돌아갑니다.
각 사람은 갈 때와 돌아올 때 모두 최단 경로를 이용합니다.

도로가 단방향이므로
i → X 의 최단거리와 X → i 의 최단거리는 서로 다를 수 있습니다.

각 집에 대해 집 → X + X → 집의 왕복 시간을 구하고,
그 중 가장 오래 걸리는 시간을 구하자
*/


/*
접근 방법
다익스트라 알고리즘을 사용

거리1. X → 각 집
거리2. 각 집 → X

1번은 X를 시작점으로 다익스트라를 실행
2번은 모든 도로의 방향을 반대로 저장한 reverseGraph

원래 그래프에서 X부터 다익스트라 1번
역방향 그래프에서 X부터 다익스트라 1번

마지막으로 모든 집 i에 대해
distFromX[i] + distToX[i]이 가장 큰 값을 찾습니다.
*/


/*
문제 풀이
*/
import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    // 다익스트라
    static int[] dijkstra(ArrayList<int[]>[] graph, int start) {

        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        // {현재 노드, 현재까지 거리}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        dist[start] = 0;
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int now = current[0];
            int cost = current[1];
            // 이미 더 짧은 경로가 존재한다면 넘어감
            if (cost > dist[now]) {
                continue;
            }

            // 현재 노드와 연결된 도로 확인
            for (int[] next : graph[now]) {
                int nextNode = next[0];
                int nextCost = cost + next[1];
                // 더 짧은 경로를 발견한 경우
                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode,nextCost});
                }
            }
        }
        return dist;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int X = Integer.parseInt(st.nextToken());
            // 원래 방향 그래프
            ArrayList<int[]>[] graph = new ArrayList[N + 1];
            // 반대 방향 그래프
            ArrayList<int[]>[] reverseGraph = new ArrayList[N + 1];
          
            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
                reverseGraph[i] = new ArrayList<>();
            }

            // 도로 정보 입력
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                // 원래 방향
                graph[start].add(new int[]{end,cost});
                // 반대 방향
                reverseGraph[end].add(new int[]{start,cost});
            }

            // X → 각 집
            int[] distFromX = dijkstra(graph, X);
            // 각 집 → X
            // 역방향 그래프에서는 X → 각 집으로 바뀜
            int[] distToX = dijkstra(reverseGraph, X);

            int answer = 0;
            // 각 집의 왕복 거리 계산
            for (int i = 1; i <= N; i++) {
                int total = distFromX[i] + distToX[i];
                answer = Math.max(answer, total);
            }
            System.out.println("#" + tc + " " + answer);
        }
    }
}


/*
시간복잡도

N : 집의 개수
M : 도로의 개수

원래 그래프에서 다익스트라 1번
역방향 그래프에서 다익스트라 1번

O(2 × (N + M) log N)
*/
