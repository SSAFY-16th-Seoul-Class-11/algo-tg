import java.io.*;
import java.util.*;

public class skyblue1232 {
    static ArrayList<Integer>[] graph;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
            graph = new ArrayList[N + 1];
            visited = new boolean[N + 1];
            for (int i = 1; i <= N; i++) graph[i] = new ArrayList<>();

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
                graph[a].add(b); graph[b].add(a);
            }

            int count = 0;
            for (int i = 1; i <= N; i++)
                if (!visited[i]) {
                    dfs(i);
                    count++;
                }
            System.out.println("#" + tc + " " + count);
        }
    }

    static void dfs(int cur) {
        visited[cur] = true;
        for (int next : graph[cur]) if (!visited[next]) dfs(next);
    }
}
