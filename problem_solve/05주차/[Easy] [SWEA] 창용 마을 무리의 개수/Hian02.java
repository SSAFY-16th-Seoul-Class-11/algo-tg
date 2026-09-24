import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            ArrayList<Integer>[] graph = new ArrayList[N + 1];

            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                graph[a].add(b);
                graph[b].add(a);
            }

            boolean[] visited = new boolean[N + 1];
            int count = 0;

            for (int i = 1; i <= N; i++) {
                if (!visited[i]) {
                    count++;

                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i);
                    visited[i] = true;

                    while (!queue.isEmpty()) {
                        int current = queue.poll();

                        for (int next : graph[current]) {
                            if (!visited[next]) {
                                visited[next] = true;
                                queue.offer(next);
                            }
                        }
                    }
                }
            }

            System.out.println("#" + tc + " " + count);
        }
    }
}
