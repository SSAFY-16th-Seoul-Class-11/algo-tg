import java.util.*;

class Solution {

    static class Node implements Comparable<Node> {
        int to, dist;

        public Node(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    static List<List<Node>> graph;
    static final int INF = 1_000_000;

    public int solution(int N, int[][] road, int K) {
        initGraph(N, road);

        int[] dist = dijkstra(N, 1);

        int cnt = 0;
        for (int i = 1; i <= N; i++) {
            if (dist[i] <= K) cnt++;
        }

        return cnt;
    }

    private void initGraph(int N, int[][] road) {
        int[][] minRoad = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(minRoad[i], INF);
        }

        for (int[] r : road) {
            int a = r[0];
            int b = r[1];
            int w = r[2];

            if (w < minRoad[a][b]) {
                minRoad[a][b] = w;
                minRoad[b][a] = w;
            }
        }

        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 최솟값으로 정제된 간선만 인접 리스트에 추가
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (minRoad[i][j] != INF) {
                    graph.get(i).add(new Node(j, minRoad[i][j]));
                }
            }
        }
    }

    private int[] dijkstra(int N, int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.dist > dist[cur.to]) continue;

            for (Node next : graph.get(cur.to)) {
                int distSum = cur.dist + next.dist;
                if (dist[next.to] > distSum) {
                    dist[next.to] = distSum;
                    pq.offer(new Node(next.to, distSum));
                }
            }
        }

        return dist;
    }
}
// 시간복잡도 O(V^2 + E log(V))
