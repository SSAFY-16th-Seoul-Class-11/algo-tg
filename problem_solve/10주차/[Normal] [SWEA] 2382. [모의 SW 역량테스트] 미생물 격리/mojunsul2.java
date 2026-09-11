import java.io.*;
import java.util.*;

class Solution {
    static final int[] dr = {0, -1, 1, 0, 0};
    static final int[] dc = {0, 0, 0, -1, 1};

    static class Micro implements Comparable<Micro> {
        int r, c, cnt, dir;

        public Micro(int r, int c, int cnt, int dir) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
            this.dir = dir;
        }

        public boolean isOnPoison() {
            return r == 0 || r == N - 1 || c == 0 || c == N - 1;
        }

        public void touchPoison() {
            cnt /= 2;
            if(dir % 2 == 1) dir++;
            else dir--;
        }

        // 정렬 기준: 1. 행 오름차순, 2. 열 오름차순, 3. 미생물 수 내림차순
        @Override
        public int compareTo(Micro o) {
            if (this.r != o.r) return Integer.compare(this.r, o.r);
            if (this.c != o.c) return Integer.compare(this.c, o.c);
            return Integer.compare(o.cnt, this.cnt);
        }
    }

    static int N, M, K;
    static List<Micro> micros;

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
        for (Micro m : micros) {
            m.r += dr[m.dir];
            m.c += dc[m.dir];

            if (m.isOnPoison()) {
                m.touchPoison();
            }
        }

        Collections.sort(micros);

        List<Micro> nextMicros = new ArrayList<>();
        int size = micros.size();

        for (int i = 0; i < size; i++) {
            Micro cur = micros.get(i);
            if (cur.cnt == 0) continue;

            int sumCnt = cur.cnt;
            int j = i + 1;

            // 정렬로 인한 최적화
            while (j < size && micros.get(j).r == cur.r && micros.get(j).c == cur.c) {
                sumCnt += micros.get(j).cnt;
                j++;
            }

            cur.cnt = sumCnt;
            nextMicros.add(cur);

            i = j - 1;
        }

        micros = nextMicros;
    }
}
