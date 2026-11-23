import java.util.*;

class Solution {
    int[][] board;
    int[][] positions = new int[7][2];
    int[] count = new int[7];
    int[][] memo = new int[64][16];

    int fullMask = 0;
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board, int r, int c) {
        this.board = board;

        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // 카드 종류별 위치 저장
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = board[i][j];

                if (num != 0) {
                    positions[num][count[num]++] = i * 4 + j;
                    fullMask |= (1 << (num - 1));
                }
            }
        }

        return dfs(0, r * 4 + c);
    }

    // 카드 제거 순서를 탐색
    int dfs(int mask, int cur) {
        if (mask == fullMask) {
            return 0;
        }

        if (memo[mask][cur] != -1) {
            return memo[mask][cur];
        }

        int result = Integer.MAX_VALUE;

        for (int num = 1; num <= 6; num++) {
            if (count[num] == 0) continue;

            int bit = 1 << (num - 1);

            // 이미 제거한 카드
            if ((mask & bit) != 0) continue;

            int a = positions[num][0];
            int b = positions[num][1];

            int nextMask = mask | bit;

            // a를 먼저 선택하고 b 선택
            int cost1 = bfs(cur, a, mask)
                      + bfs(a, b, mask)
                      + 2
                      + dfs(nextMask, b);

            // b를 먼저 선택하고 a 선택
            int cost2 = bfs(cur, b, mask)
                      + bfs(b, a, mask)
                      + 2
                      + dfs(nextMask, a);

            result = Math.min(result, Math.min(cost1, cost2));
        }

        return memo[mask][cur] = result;
    }

    // 현재 카드 상태에서 최소 이동 횟수 계산
    int bfs(int start, int target, int mask) {
        int[] dist = new int[16];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            if (cur == target) {
                return dist[cur];
            }

            int r = cur / 4;
            int c = cur % 4;

            for (int d = 0; d < 4; d++) {
                // 1. 일반 방향키 이동
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4) {
                    int next = nr * 4 + nc;

                    if (dist[next] == -1) {
                        dist[next] = dist[cur] + 1;
                        queue.offer(next);
                    }
                }

                // 2. Ctrl + 방향키 이동
                nr = r;
                nc = c;

                while (true) {
                    int tr = nr + dr[d];
                    int tc = nc + dc[d];

                    if (tr < 0 || tr >= 4 || tc < 0 || tc >= 4) {
                        break;
                    }

                    nr = tr;
                    nc = tc;

                    int num = board[nr][nc];

                    // 아직 제거되지 않은 카드라면 정지
                    if (num != 0 && (mask & (1 << (num - 1))) == 0) {
                        break;
                    }
                }

                int next = nr * 4 + nc;

                if (dist[next] == -1) {
                    dist[next] = dist[cur] + 1;
                    queue.offer(next);
                }
            }
        }

        return -1;
    }
}
