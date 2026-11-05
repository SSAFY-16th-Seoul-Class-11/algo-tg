import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {

    static class Robot implements Comparable<Robot> {
        int x;
        int y;
        int dir;
        int time;

        public Robot(int x, int y, int dir, int time) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.time = time;
        }

        @Override
        public int compareTo(Robot o) {
            return Integer.compare(this.time, o.time);
        }
    }

    static int n;
    static int[][] map;
    // 가로(0) 세로(1) 기록
    static int[][][] dist;
    static PriorityQueue<Robot> pq;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public int solution(int[][] board) {
        map = board;
        n = map.length;

        dist = new int[n][n][2];
        pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
            }
        }

        dist[0][0][0] = 0;
        pq.offer(new Robot(0, 0, 0, 0));

        while (!pq.isEmpty()) {
            Robot cur = pq.poll();

            int x = cur.x;
            int y = cur.y;
            int dir = cur.dir;
            int time = cur.time;

            if (dist[x][y][dir] < time) continue;

            // 목적지 도착
            if ((dir == 0 && x == n - 1 && y == n - 2) || (dir == 1 && x == n - 2 && y == n - 1)) {
                return time;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                move(nx, ny, dir, time + 1);
            }

            // 회전
            for (int d = -1; d <= 1; d += 2) {

                if (dir == 0) {
                    // 가로 > 세로
                    if (!check(x + d, y) || !check(x + d, y + 1)) continue;

                    int nx = d == -1 ? x - 1 : x;

                    move(nx, y, 1, time + 1);
                    move(nx, y + 1, 1, time + 1);
                } else {
                    // 세로 > 가로
                    if (!check(x, y + d) || !check(x + 1, y + d)) continue;

                    int ny = d == -1 ? y - 1 : y;

                    move(x, ny, 0, time + 1);
                    move(x + 1, ny, 0, time + 1);
                }
            }
        }

        return -1;
    }

    static boolean check(int x, int y) {
        if (x < 0 || y < 0 || x >= n || y >= n) {
            return false;
        }
        return map[x][y] == 0;
    }

    static void move(int x, int y, int dir, int time) {
        int nx = x + (dir == 1 ? 1 : 0);
        int ny = y + (dir == 0 ? 1 : 0);

        if (!check(x, y) || !check(nx, ny)) return;
        if (dist[x][y][dir] <= time) return;

        dist[x][y][dir] = time;
        pq.offer(new Robot(x, y, dir, time));
    }
}
