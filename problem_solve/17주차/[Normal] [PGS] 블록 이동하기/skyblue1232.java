import java.util.*;

class Solution {
    static class State {
        int r, c, dir;

        State(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }
    }

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    boolean[][][] visited;
    Queue<State> queue;

    void add(int r, int c, int dir) {
        if (visited[r][c][dir]) return;

        visited[r][c][dir] = true;
        queue.offer(new State(r, c, dir));
    }

    public int solution(int[][] board) {
        int n = board.length;
        int[][] map = new int[n + 2][n + 2];

        for (int[] row : map) Arrays.fill(row, 1);
        
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, map[i + 1], 1, n);
        }

        visited = new boolean[n + 2][n + 2][2];
        queue = new ArrayDeque<>();

        add(1, 1, 0);
        int time = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                State cur = queue.poll();
                int r = cur.r;
                int c = cur.c;
                int dir = cur.dir;
                int nr2 = dir;
                int nc2 = dir ^ 1;

                if (r + nr2 == n && c + nc2 == n) return time;

                // 상하좌우 이동
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (map[nr][nc] != 0 ||
                        map[nr + nr2][nc + nc2] != 0)
                        continue;

                    add(nr, nc, dir);
                }
                
                for (int s = -1; s <= 1; s += 2) {

                    if (dir == 0) {
                        // 가로 -> 세로
                        if (map[r + s][c] != 0 ||
                            map[r + s][c + 1] != 0)
                            continue;

                        int nextR = Math.min(r, r + s);

                        add(nextR, c, 1);
                        add(nextR, c + 1, 1);

                    } else {
                        // 세로 -> 가로
                        if (map[r][c + s] != 0 ||
                            map[r + 1][c + s] != 0)
                            continue;

                        int nextC = Math.min(c, c + s);

                        add(r, nextC, 0);
                        add(r + 1, nextC, 0);
                    }
                }
            }

            time++;
        }

        return -1;
    }
}
