import java.util.PriorityQueue;

class Solution {

    static PriorityQueue<Node> pq;
    static boolean[][][] visited;
    static int N;

    static int[] dx = { 0, 0, -1, 1 };
    static int[] dy = { 1, -1, 0, 0 };
    static int[] way = { 0, 1, 2, 3 }; 

    public int solution(int[][] board) {
        int answer = 0;
        N = board[0].length;
        visited = new boolean[N][N][4];

        pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.time, o2.time));

        pq.add(new Node(0, 0, 0, 1, 0, 0, 1));
        visited[0][0][0] = true;
        visited[0][1][1] = true;

        while (!pq.isEmpty()) {
            Node node = pq.poll();

            if ((node.x1 == N - 1 && node.y1 == N - 1) ||
                (node.x2 == N - 1 && node.y2 == N - 1)) {
                answer = node.time;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx1 = node.x1 + dx[i];
                int ny1 = node.y1 + dy[i];
                int nx2 = node.x2 + dx[i];
                int ny2 = node.y2 + dy[i];

                if (check(nx1, ny1, board) && check(nx2, ny2, board)) {
                    if (!visited[nx1][ny1][node.way1]) {
                        visited[nx1][ny1][node.way1] = true;
                        visited[nx2][ny2][node.way2] = true;

                        pq.offer(new Node(nx1, ny1, nx2, ny2,
                                          node.time + 1, node.way1, node.way2));
                    }
                }
            }
            bfs(node.x1, node.y1, node.x2, node.y2, node.time, board);
            bfs(node.x2, node.y2, node.x1, node.y1, node.time, board);
        }

        return answer;
    }

    static void bfs(int x, int y, int ox, int oy, int time, int[][] board) {
        for (int i = 0; i < 4; i++) {
            if (x == ox && i / 2 == 0) continue;

            if (y == oy && i / 2 == 1) continue;

            int nx = x + dx[i];
            int ny = y + dy[i];


            int cx = ox + dx[i];
            int cy = oy + dy[i];

            if (check(nx, ny, board) && check(cx, cy, board) && !visited[x][y][i]) {

                visited[x][y][i] = true;
                visited[nx][ny][i ^ 1] = true;

                pq.offer(new Node(x, y, nx, ny, time + 1, i, i ^ 1));
            }
        }
    }

    static boolean check(int x, int y, int[][] board) {
        return 0 <= x && x < N && 0 <= y && y < N && board[x][y] == 0;
    }
}

class Node {
    int x1, x2;
    int y1, y2;
    int time;
    int way1, way2;

    Node(int x1, int y1, int x2, int y2, int time, int way1, int way2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.time = time;
        this.way1 = way1;
        this.way2 = way2;
    }
}
