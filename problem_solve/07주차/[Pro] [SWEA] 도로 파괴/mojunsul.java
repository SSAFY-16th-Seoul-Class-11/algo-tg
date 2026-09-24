import java.util.*;

class UserSolution {

    static class Edge {
        int id;
        int to;
        int weight;
        boolean isRemoved;

        Edge(int id, int to, int weight) {
            this.id = id;
            this.to = to;
            this.weight = weight;
            this.isRemoved = false;
        }
    }

    static class Node implements Comparable<Node> {
        int vertex;
        int dist;

        Node(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    private int N;
    private List<List<Edge>> adj;
    private Map<Integer, Edge> edgeMap;
    private static final int INF = 1_000_000_000;

    int[] prevVertex;
    Edge[] prevEdge;

    public void init(int N, int K, int mId[], int sCity[], int eCity[], int mTime[]) {
        this.N = N;
        this.adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
        this.edgeMap = new HashMap<>();

        for (int i = 0; i < K; i++) {
            add(mId[i], sCity[i], eCity[i], mTime[i]);
        }
    }

    public void add(int mId, int sCity, int eCity, int mTime) {
        Edge edge = new Edge(mId, eCity, mTime);
        adj.get(sCity).add(edge);
        edgeMap.put(mId, edge);
    }

    public void remove(int mId) {
        Edge edge = edgeMap.get(mId);
        if (edge != null) {
            edge.isRemoved = true;
        }
    }

    public int calculate(int sCity, int eCity) {
        prevVertex = new int[N];
        prevEdge = new Edge[N];
        Arrays.fill(prevVertex, -1);

        int originDist = dijkstra(sCity, eCity);

        if (originDist == INF) {
            return -1;
        }

        List<Edge> pathEdges = new ArrayList<>();
        int cur = eCity;
        while (cur != sCity) {
            Edge e = prevEdge[cur];
            pathEdges.add(e);
            cur = prevVertex[cur];
        }

        int maxDelay = 0;

        for (Edge edge : pathEdges) {
            edge.isRemoved = true;

            int newDist = dijkstra(sCity, eCity);
            edge.isRemoved = false;

            if (newDist == INF) {
                return -1;
            }

            int delay = newDist - originDist;
            maxDelay = Math.max(maxDelay, delay);
        }

        return maxDelay;
    }

    private int dijkstra(int start, int end) {
        int[] dist = new int[N];
        Arrays.fill(dist, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();

        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.dist > dist[cur.vertex]) continue;
            if (cur.vertex == end) return cur.dist;

            for (Edge edge : adj.get(cur.vertex)) {
                if (edge.isRemoved) continue;
                int nextDist = cur.dist + edge.weight;
                if (nextDist < dist[edge.to]) {
                    dist[edge.to] = nextDist;
                    prevVertex[edge.to] = cur.vertex;
                    prevEdge[edge.to] = edge;
                    pq.offer(new Node(edge.to, nextDist));
                }
            }
        }

        return dist[end];
    }
}
// 소요 시간 : 2:59:09
