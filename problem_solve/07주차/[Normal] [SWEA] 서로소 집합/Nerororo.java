import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    static int[] parent, rank;

    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            init(n);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int calc = Integer.parseInt(st.nextToken());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                unionFind(calc, a, b, sb);
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    public static void init(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public static void unionFind(int calc, int a, int b, StringBuilder sb) {
        if (calc == 0) {
            union(a, b);
        } else {
            sb.append(find(a) == find(b) ? 1 : 0);
        }
    }

    public static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

//        if (rootA != rootB) parent[b] = a;

        if (rootA == rootB) return;

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }

    public static int find(int num) {
        if (parent[num] == num) return num;
        return parent[num] = find(parent[num]);
    }
}

// 상수급의 시간 복잡도를 가짐(매우 빠르다)