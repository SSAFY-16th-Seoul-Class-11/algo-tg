import java.util.*;

class Solution {

    static int[][] graph;
    static int[] dist;

    public int solution(int N, int[][] road, int K) {
        int answer = 0;

        graph = new int[N + 1][N + 1];
        dist = new int[N + 1];

        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int i = 0; i < road.length; i++) {
            int from = road[i][0];
            int to = road[i][1];
            int len = road[i][2];

            // 더 작은 경로만 저장
            graph[from][to] = graph[from][to] == 0
                    ? len
                    : Math.min(graph[from][to], len);

            graph[to][from] = graph[to][from] == 0
                    ? len
                    : Math.min(graph[to][from], len);
        }

        // 모든 노드의 최단 거리 계산
        dijkstra(N);

        for (int i = 1; i <= N; i++) {
            if (dist[i] <= K) answer++;
        }
        return answer;
    }

    private void dijkstra(int N) {
        dist[1] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        pq.offer(new int[] {1, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();

            int from = cur[0];
            int len = cur[1];

            // 거리가 저장되어 있는 거리보다 크면 이후 반복을 돌 필요가 없음
            if (len > dist[from]) continue;

            // 현재 위치에서 갈 수 있는 노드 확인, 도착한 곳의 거리가 현재 내 경로보다 긴지 확인
            for (int i = 1; i <= N; i++) {
                if (graph[from][i] != 0 && dist[i] > len + graph[from][i]) {
                    dist[i] = len + graph[from][i];
                    pq.offer(new int[] {i, dist[i]});
                }
            }
        }
    }
}

/*
 * 다익스트라 구현에 우선순위 큐를 사용했지만 List, 노드와 거리 정보를 담는 별도의 클래스(Edge)를 사용하는게 아닌
 * 2차원 배열을 사용하여 매 while문 마다 O(n)의 시간이 걸린다. 때문에 시간 복잡도 O(n^2)를 가진다.
 */