import java.util.*;

class Solution {

    static class Node implements Comparable<Node> {
        int to, weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    static final int INF = 200_000_000;

    static int N;
    static List<List<Node>> graph;

    public int solution(int n, int s, int a, int b, int[][] fares) {
        N = n;
        initGraph(fares);

        // 시작 -> 합승 -> 각자 목적지
        // 문제 분리
        // 1) 시작 -> 합승
        // 2) a 목적지 -> 합승
        // 3) b 목적지 -> 합승

        // 1) 시작 -> 합승
        int[] distS = dijkstra(s);
        // 2) a 목적지 -> 합승
        int[] distA = dijkstra(a);
        // 3) b 목적지 -> 합승
        int[] distB = dijkstra(b);

        int minFare = INF;
        for (int i = 1; i <= n; i++) {
            minFare = Math.min(minFare, distS[i] + distA[i] + distB[i]);
        }

        return minFare;
    }

    private void initGraph(int[][] fares) {
        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] fare : fares) {
            int n1 = fare[0];
            int n2 = fare[1];
            int f = fare[2];

            graph.get(n1).add(new Node(n2, f));
            graph.get(n2).add(new Node(n1, f));
        }
    }

    private int[] dijkstra(int start) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.weight > dist[cur.to]) {
                continue;
            }

            for (Node next : graph.get(cur.to)) {
                int nextDist = dist[cur.to] + next.weight;
                if (dist[next.to] > nextDist) {
                    dist[next.to] = nextDist;
                    pq.offer(new Node(next.to, nextDist));
                }
            }
        }

        return dist;
    }
}
// 시간복잡도 O(E log(V))
