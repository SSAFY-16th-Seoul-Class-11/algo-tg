import java.util.*;

class skyblue123 {
    static int[][] delta = {{-1, 0},{0, 1},{1, 0},{0, -1}};

    static class Node implements Comparable<Node> {
        int r;
        int c;
        int dir; 
        int cost;

        Node(int r, int c, int dir, int cost) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public int solution(int[][] board) {
        int N = board.length;
        int INF = Integer.MAX_VALUE;
        int[][] cost = new int[N * N][2];

        for (int i = 0; i < N * N; i++) {
            Arrays.fill(cost[i], INF);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();

        if (board[0][1] == 0) {
            int idx = 1;
            cost[idx][1] = 100;
            pq.offer(new Node(0, 1, 1, 100));
        }

        if (board[1][0] == 0) {
            int idx = N;
            cost[idx][0] = 100;
            pq.offer(new Node(1, 0, 0, 100));
        }

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int curIdx = cur.r * N + cur.c;

            if (cur.cost > cost[curIdx][cur.dir]) {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + delta[d][0];
                int nc = cur.c + delta[d][1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                    continue;
                }
                if (board[nr][nc] == 1) {
                    continue;
                }

                int nextDir = d % 2;
                int nextCost;

                if (cur.dir == nextDir) {
                    nextCost = cur.cost + 100;
                }

                else {
                    nextCost = cur.cost + 600;
                }

                int nextIdx = nr * N + nc;

                if (nextCost < cost[nextIdx][nextDir]) {
                    cost[nextIdx][nextDir] = nextCost;
                    pq.offer(
                        new Node(nr, nc, nextDir, nextCost)
                    );
                }
            }
        }

        int end = N * N - 1;
        return Math.min(
            cost[end][0],
            cost[end][1]
        );
    }
}
