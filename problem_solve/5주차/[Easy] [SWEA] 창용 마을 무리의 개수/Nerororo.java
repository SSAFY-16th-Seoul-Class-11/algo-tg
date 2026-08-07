import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

class Solution
{
    static int[] parent;

    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/s_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            parent = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                parent[i] = i;
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                // 주어지는 두 입력으로 union
                union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            sb.append(countSet(N)).append('\n');
        }

        System.out.println(sb);
    }

    public static int countSet(int n) {
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            int num = find(i);
            set.add(num);
        }
        return set.size();
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a != b) parent[b] = a;
    }

    public static int find(int x) {
        // 부모 노드가 나 자신이면
        if (parent[x] == x) return x;
        // 경로 압축
        return parent[x] = find(parent[x]);
    }
}
