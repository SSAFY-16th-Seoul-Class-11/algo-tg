import java.io.*;
import java.util.*;
 
class Solution {
    static final int[] dr = {0, -1, 1, 0, 0};
    static final int[] dc = {0, 0, 0, -1, 1};
 
    static class Micro {
        int r, c, cnt, dir;
 
        public Micro(int r, int c, int cnt, int dir) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
            this.dir = dir;
        }
 
        private void touchPoison() {
            cnt /= 2;
            if (dir == 1) dir = 2;
            else if (dir == 2) dir = 1;
            else if (dir == 3) dir = 4;
            else if (dir == 4) dir = 3;
        }
    }
 
    static int N, M, K;
    static List<Micro> micros;
    static int[][][] map; // [r][c][i] i -> 0: 총 개수, 1: 대표 개수: 2: 대표 방향
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
 
        int T = Integer.parseInt(br.readLine().trim());
 
        for (int TC = 1; TC <= T; TC++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
 
            micros = new ArrayList<>();
 
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                micros.add(new Micro(r, c, cnt, dir));
            }
 
            for (int i = 0; i < M; i++) {
                process();
            }
 
            int ans = 0;
            for (Micro m : micros) {
                ans += m.cnt;
            }
            sb.append("#").append(TC).append(" ").append(ans).append("\n");
        }
 
        System.out.print(sb);
        br.close();
    }
 
    private static void process() {
        map = new int[N][N][3];
 
        for (Micro m : micros) {
            if (m.cnt == 0) continue;
 
            int nr = m.r + dr[m.dir];
            int nc = m.c + dc[m.dir];
            m.r = nr;
            m.c = nc;
 
            if (isEdge(nr, nc)) {
                m.touchPoison();
            }
 
            if (m.cnt == 0) continue;
 
            if (map[nr][nc][0] == 0) {
                map[nr][nc][0] = m.cnt;
                map[nr][nc][1] = m.cnt;
                map[nr][nc][2] = m.dir;
            }
            else {
                map[nr][nc][0] += m.cnt;
                if (m.cnt > map[nr][nc][1]) {
                    map[nr][nc][1] = m.cnt;
                    map[nr][nc][2] = m.dir;
                }
            }
        }
 
        List<Micro> nextMicros = new ArrayList<>();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (map[r][c][0] > 0) {
                    nextMicros.add(new Micro(r, c, map[r][c][0], map[r][c][2]));
                }
            }
        }
        micros = nextMicros;
    }
 
    private static boolean isEdge(int r, int c) {
        return r == 0 || r == N - 1 || c == 0 || c == N - 1;
    }
}
