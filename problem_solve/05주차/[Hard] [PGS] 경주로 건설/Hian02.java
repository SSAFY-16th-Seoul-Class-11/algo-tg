import java.util.*;

class Solution {

    public int solution(int[][] board) {
        int n = board.length;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // cost[x][y][방향]
        int[][][] cost = new int[n][n][4];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(cost[i][j], Integer.MAX_VALUE);
            }
        }

        // {비용, x, y, 방향}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // 출발점에서는 아직 방향이 없으므로
        // 가능한 첫 이동을 직접 넣음
        for (int dir = 0; dir < 4; dir++) {
            int nx = dx[dir];
            int ny = dy[dir];

            if (nx >= 0 && nx < n && ny >= 0 && ny < n && board[nx][ny] == 0) {
                cost[nx][ny][dir] = 100;
                pq.offer(new int[]{100, nx, ny, dir});
            }
        }

        while (!pq.isEmpty()) {

            int[] cur = pq.poll();

            int currentCost = cur[0];
            int x = cur[1];
            int y = cur[2];
            int dir = cur[3];

            if (currentCost > cost[x][y][dir]) {
                continue;
            }

            for (int nextDir = 0; nextDir < 4; nextDir++) {

                int nx = x + dx[nextDir];
                int ny = y + dy[nextDir];

                if (nx < 0 || nx >= n ||
                    ny < 0 || ny >= n ||
                    board[nx][ny] == 1) {
                    continue;
                }

                int nextCost = currentCost + 100;

                // 방향이 달라지면 코너 비용 추가
                if (dir != nextDir) {
                    nextCost += 500;
                }

                if (nextCost < cost[nx][ny][nextDir]) {
                    cost[nx][ny][nextDir] = nextCost;
                    pq.offer(new int[]{nextCost, nx, ny, nextDir});
                }
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int dir = 0; dir < 4; dir++) {
            answer = Math.min(answer,cost[n - 1][n - 1][dir]);
        }

        return answer;
    }
}
