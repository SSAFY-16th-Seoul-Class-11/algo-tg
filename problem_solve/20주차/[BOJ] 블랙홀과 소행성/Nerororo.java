import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[] blackhole;
    static Node[] asteroid;

    public static class Node implements Comparable<Node> {
        int a, w;

        public Node(int a, int w) {
            this.a = a;
            this.w = w;
        }

        @Override
        public int compareTo(Node o) {
            return this.a - o.a;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 소행성 위치
        asteroid = new Node[M];
        // 블랙홀 위치
        blackhole = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            blackhole[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            asteroid[i] = new Node(a, w);
        }

        Arrays.sort(blackhole);
        Arrays.sort(asteroid);

        System.out.print(solution());
    }

    // (거리의 절댓값) x Wj의 최댓값을 구하여라
    private static int solution() {
        int p = 0;
        int idx = 0;

        for (int i = -1_000_000; i < blackhole[0]; i++) {
            if (idx < M && i == asteroid[idx].a) {
                p = Math.max(p, (blackhole[0] - asteroid[idx].a) * asteroid[idx].w);
                idx++;
            }
        }

        int prev = 0;
        int next = 1;
        for (int i = blackhole[0] + 1; i < blackhole[N - 1]; i++) {
            if (i == blackhole[next]) {
                prev++;
                next++;
                continue;
            }

            if (idx < M && i == asteroid[idx].a) {
                p = Math.max(p, Math.min(asteroid[idx].a - blackhole[prev], blackhole[next] - asteroid[idx].a) * asteroid[idx].w);
                idx++;
            }
        }

        for (int i = blackhole[N - 1] + 1; i <= 1_000_000; i++) {
            if (idx < M && i == asteroid[idx].a) {
                p = Math.max(p, (asteroid[idx].a - blackhole[N - 1]) * asteroid[idx].w);
                idx++;
            }
        }

        return p;
    }
}