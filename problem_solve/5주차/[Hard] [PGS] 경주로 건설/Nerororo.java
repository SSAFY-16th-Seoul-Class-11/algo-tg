
import java.util.PriorityQueue;

class Solution
{
    int N;
    // 아래, 왼쪽, 위, 오른쪽
    int[] di = {1, 0, -1, 0};
    int[] dj = {0, -1, 0, 1};

    StringBuilder sb = new StringBuilder();

    public int solution(int[][] board) {
        N = board.length;
        int answer = Integer.MAX_VALUE;

        // 기본 자료구조 세팅
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[][][] lowValue = new int[N][N][4];

        // 초기값 세팅 (내려가거나 오른쪽으로 가거나)
        init(pq, board, lowValue);

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int i = curr.i;
            int j = curr.j;
            int cost = curr.cost;
            int d = curr.d;

            // 도착 지점일 경우 확인
            if (i == N - 1 && j == N - 1) {
                lowValue[N - 1][N - 1][d] = Math.min(cost, lowValue[N - 1][N - 1][d]);
                answer = Math.min(answer, cost);
                continue;
            }

            int ni = i + di[d];
            int nj = j + dj[d];
            // 직진일 경우 확인
            if (canGo(board, lowValue, ni, nj, d, cost + 100)) {
                pq.offer(new Node(ni, nj, cost + 100, d));
                lowValue[ni][nj][d] = cost + 100;
            }

            // 좌, 우일 경우 확인
            for (int idx = 1; idx <= 3; idx += 2) {
                int nd = (d + idx) % 4;
                ni = i + di[nd];
                nj = j + dj[nd];
                if (canGo(board, lowValue, ni, nj, nd, cost + 600)) {
                    pq.offer(new Node(ni, nj, cost + 600, nd));
                    lowValue[ni][nj][nd] = cost + 600;
                }
            }
        }

        return answer;
    }

    // 초기 세팅
    public void init(PriorityQueue<Node> pq, int[][] board, int[][][] lowValue) {
        if (isIn(board, 0, 1)) {
            pq.offer(new Node(0, 1, 100, 3));
            lowValue[0][1][3] = 100;
        }
        if (isIn(board, 1, 0)) {
            pq.offer(new Node(1, 0, 100, 0));
            lowValue[1][0][0] = 100;
        }
    }

    // lowValue 배열 비교
    public boolean isLowValue(int[][][] lowValue, int i, int j, int d, int cost) {
        return (cost < lowValue[i][j][d]) || lowValue[i][j][d] == 0;
    }

    // 배열 내부 && 벽인지 확인
    public boolean isIn(int[][] board, int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < N  && board[i][j] != 1;
    }

    // 갈 수 있나 확인
    public boolean canGo(int[][] board, int[][][] lowValue, int i, int j, int d, int cost) {
        return isIn(board, i, j) && isLowValue(lowValue, i, j, d, cost);
    }

    // 각 위치별 노드 구현
    class Node implements Comparable<Node> {

        int i, j, cost, d;

        public Node(int i, int j, int cost, int d) {
            this.i = i;
            this.j = j;
            this.cost = cost;
            this.d = d;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}
