import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

class Solution
{
    public static void main(String args[]) throws Exception
    {
//		System.setIn(new FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            edge = new ArrayList[N + 1];
            visited = new boolean[N + 1];

            for (int i = 1; i <= N; i++) {
                edge[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int fir = Integer.parseInt(st.nextToken());
                int sec = Integer.parseInt(st.nextToken());
                max = 0;

                edge[fir].add(sec);
                edge[sec].add(fir);
            }

            for (int i = 1; i <= N; i++) {
                longestWay(i, 1);
            }

            sb.append(max).append('\n');
        }

        System.out.println(sb.toString());
    }

    static List<Integer>[] edge;
    static boolean[] visited;
    static int N, max;

    static void longestWay(int n, int len) {
        visited[n] = true;
        max = Math.max(len, max);

        for (int next : edge[n]) {
            if (visited[next]) continue;

            longestWay(next, len + 1);
        }
        visited[n] = false;
    }

}