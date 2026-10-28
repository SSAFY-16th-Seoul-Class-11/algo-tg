import java.util.*;
import java.io.*;

class Solution {

    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    static Set<Integer>[] pipe;

    static int N, M, sr, sc, L;
    static int[][] map;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        pipe = new Set[8];
        for (int i = 0; i < 8; i++) {
            pipe[i] = new HashSet<>();
        }

        pipe[1].add(0);
        pipe[1].add(1);
        pipe[1].add(2);
        pipe[1].add(3);
        pipe[2].add(0);
        pipe[2].add(2);
        pipe[3].add(1);
        pipe[3].add(3);
        pipe[4].add(0);
        pipe[4].add(1);
        pipe[5].add(1);
        pipe[5].add(2);
        pipe[6].add(2);
        pipe[6].add(3);
        pipe[7].add(3);
        pipe[7].add(0);

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            sr = Integer.parseInt(st.nextToken());
            sc = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            map = new int[N][M];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            sb.append("#").append(tc).append(" ").append(solve()).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    static class Node {
        int r, c, dist;

        public Node(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    static int solve() {
        int cnt = 0;
        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];

        visited[sr][sc] = true;
        queue.offer(new Node(sr, sc, 0));

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (cur.dist >= L) {
                break;
            }

            cnt++;

            for (int d = 0; d < 4; d++) {
                if (pipe[map[cur.r][cur.c]].contains(d)) {
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];
                    if (!isIn(nr, nc)) continue;
                    if (visited[nr][nc]) continue;
                    if (!pipe[map[nr][nc]].contains((d + 2) % 4)) continue;

                    visited[nr][nc] = true;
                    queue.offer(new Node(nr, nc, cur.dist + 1));
                }
            }
        }

        return cnt;
    }

    static boolean isIn(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }
}
