import java.util.*;

class UserSolution {

    static class Edge implements Comparable<Edge> {
        int u, v, limit;

        Edge(int u, int v, int limit) {
            this.u = u;
            this.v = v;
            this.limit = limit;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(o.limit, this.limit); //용량 내림차순
        }
    }

    int N;
    List<Edge> edges;
    int[] parent;

    public void init(int N, int K, int sCity[], int eCity[], int mLimit[]) {
        this.N = N;
        this.edges = new ArrayList<>();
        this.parent = new int[N];

        for (int i = 0; i < K; i++) {
            edges.add(new Edge(sCity[i], eCity[i], mLimit[i]));
        }
    }

    public void add(int sCity, int eCity, int mLimit) {
        edges.add(new Edge(sCity, eCity, mLimit));
    }

    public int calculate(int sCity, int eCity, int M, int mStopover[]) {
        Collections.sort(edges);

        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }

        // 연결되어야 하는 노드 목록
        int[] targetNodes = new int[M + 2];
        for (int i = 0; i < M; i++) {
            targetNodes[i] = mStopover[i];
        }
        targetNodes[M] = sCity;
        targetNodes[M + 1] = eCity;


        for (Edge edge : edges) {
            union(edge.u, edge.v);

            // 타켓 노드가 다 연결된 순간의 간선 용량이 병목 최대값 (edges 정렬해놨기 때문에)
            if (isAllConnected(targetNodes)) {
                return edge.limit;
            }
        }

        return -1;
    }

    private boolean isAllConnected(int[] nodes) {
        int root = find(nodes[0]);
        for (int i = 1; i < nodes.length; i++) {
            if (find(nodes[i]) != root) {
                return false;
            }
        }
        return true;
    }

    private int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    private void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x != y) {
            parent[y] = x;
        }
    }
}
// 소요 시간 3:38:44
