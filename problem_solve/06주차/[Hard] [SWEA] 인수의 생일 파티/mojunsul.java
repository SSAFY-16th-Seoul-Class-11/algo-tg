// v.다익스트라

import java.io.*;
import java.util.*;

public class Solution {

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

    static int N, M, X;
    static List<List<Node>> graph, backGraph;
    static final int INF = 1_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            initGraph(br);

            int maxCycle = getMaxCycle();

            sb.append("#").append(tc).append(" ").append(maxCycle).append("\n");
        }

        System.out.print(sb);
        br.close();
    }

    private static void initGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        backGraph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
            backGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph.get(from).add(new Node(to, weight));
            backGraph.get(to).add(new Node(from, weight));
        }
    }

    private static int getMaxCycle() {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.offer(new Node(X, 0));

        int[] weight = new int[N + 1];
        Arrays.fill(weight, INF);
        weight[X] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            for (Node node : graph.get(cur.to)) {
                int nextWeight = cur.weight + node.weight;
                if (weight[node.to] > nextWeight) {
                    weight[node.to] = nextWeight;
                    pq.offer(new Node(node.to, nextWeight));
                }
            }
        }

        pq.clear();
        pq.offer(new Node(X, 0));

        int[] backWeight = new int[N + 1];
        Arrays.fill(backWeight, INF);
        backWeight[X] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            for (Node node : backGraph.get(cur.to)) {
                int nextWeight = cur.weight + node.weight;
                if (backWeight[node.to] > nextWeight) {
                    backWeight[node.to] = nextWeight;
                    pq.offer(new Node(node.to, nextWeight));
                }
            }
        }

        int max = 0;
        for (int i = 1; i <= N; i++) {
            max = Math.max(max, weight[i] + backWeight[i]);
        }
        return max;
    }
}

// 시간복잡도 O(E log(V))
