import java.io.*;
import java.util.*;

public class Solution {

    static int N, ans;
    static List<Integer>[] adj;
    static int[] dist;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            readInput(br);
            solve();
            sb.append('#').append(tc).append(' ').append(ans).append('\n');
        }
        System.out.print(sb);
        br.close();
    }

    private static void readInput(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        adj = new List[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (st.nextToken().charAt(0) == '1') adj[i].add(j);
            }
        }

        dist = new int[N];
        visited = new boolean[N];
    }

    private static void solve() {
        ans = Integer.MAX_VALUE;
        Queue<Integer> queue = new ArrayDeque<>();

        for (int s = 0; s < N; s++) {
            Arrays.fill(visited, false);
            queue.clear();
            queue.offer(s);
            visited[s] = true;
            dist[s] = 0;

            int sum = 0;
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                int d = dist[cur] + 1;

                for (int next : adj[cur]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        dist[next] = d;
                        sum += d;
                        queue.offer(next);
                    }
                }
                if (sum >= ans) break; // 가지치기
            }
            ans = Math.min(ans, sum);
        }
    }
}
