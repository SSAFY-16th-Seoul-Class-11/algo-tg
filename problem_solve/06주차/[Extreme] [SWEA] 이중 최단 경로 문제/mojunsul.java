import java.io.*;
import java.util.*;

public class Solution {

    static class Node implements Comparable<Node> {

        int to;
        int x;
        int y;

        Node(int to, int x, int y) {
            this.to = to;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.y, o.y);
        }
    }

    static final int INF = 1_000_000_000;
    static final int START = 1, GOAL = 2;
    static int N, M, MAX_X;
    static List<List<Node>> graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            initGraph(br);

            MAX_X = (N - 1) * 9;

            int[][] dist = dijkstra();

            int minCost = getMinCost(dist);

            sb.append("#").append(tc).append(" ").append(minCost == INF ? -1 : minCost).append("\n");
        }

        System.out.print(sb);
    }

    private static void initGraph(BufferedReader br) throws IOException {
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            graph.get(a).add(new Node(b, x, y));
            graph.get(b).add(new Node(a, x, y));
        }
    }

    private static int[][] dijkstra() {
        int[][] dist = new int[N + 1][MAX_X + 1];
        for (int i = 0; i <= N; i++) {
            Arrays.fill(dist[i], INF);
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();

        dist[START][0] = 0;
        pq.offer(new Node(START, 0, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (curr.y > dist[curr.to][curr.x]) {
                continue;
            }

            for (Node next : graph.get(curr.to)) {
                int nextX = curr.x + next.x;
                int nextY = curr.y + next.y;

                if (nextX <= MAX_X && nextY < dist[next.to][nextX]) {
                    dist[next.to][nextX] = nextY;
                    pq.offer(new Node(next.to, nextX, nextY));
                }
            }
        }
        return dist;
    }

    private static int getMinCost(int[][] dist) {
        int minCost = INF;
        for (int x = 1; x <= MAX_X; x++) {
            if (dist[GOAL][x] != INF) {
                minCost = Math.min(minCost, x * dist[GOAL][x]);
            }
        }
        return minCost;
    }
}
// 시간복잡도 O(V*E*log(V))
