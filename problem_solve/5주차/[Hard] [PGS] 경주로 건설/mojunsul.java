import java.util.*;

class Solution {

    class Node implements Comparable<Node> {

        int i, j, dir, cost;

        public Node(int i, int j, int dir, int cost) {
            this.i = i;
            this.j = j;
            this.dir = dir; // -1 이면 시작점
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public int solution(int[][] board) {
        int[] di = {0, 1, 0, -1};
        int[] dj = {1, 0, -1, 0};
        int N = board.length;

        // minCost[i][j][dir] : (i, j)에 dir 방향으로 도달했을 때의 최소 비용
        int[][][] minCost = new int[N][N][4];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(minCost[i][j], Integer.MAX_VALUE);
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0, -1, 0));
        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.i == N - 1 && cur.j == N - 1) {
                return cur.cost;
            }

            for (int d = 0; d < 4; d++) {
                int ni = cur.i + di[d];
                int nj = cur.j + dj[d];

                if (isIn(ni, nj, N) && board[ni][nj] == 0) {
                    int addCost = 100;
                    if (cur.dir != -1 && d != cur.dir) addCost += 500;
                    int nextCost = cur.cost + addCost;

                    if (nextCost <= minCost[ni][nj][d]) {
                        minCost[ni][nj][d] = nextCost;
                        pq.offer(new Node(ni, nj, d, nextCost));
                    }
                }
            }
        }

        return -1;
    }

    private boolean isIn(int i, int j, int N) {
        return i >= 0 && i < N && j >= 0 && j < N;
    }
}