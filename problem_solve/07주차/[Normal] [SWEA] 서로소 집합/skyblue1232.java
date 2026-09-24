import java.io.*;
import java.util.*;

public class skyblue1232 {

    private static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            parent = new int[n + 1];
            Arrays.fill(parent, -1);

            sb.append("#").append(tc).append(" ");

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());

                int command = Integer.parseInt(st.nextToken());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if (command == 0) {
                    union(a, b);
                } else {
                    sb.append(find(a) == find(b) ? 1 : 0);
                }
            }

            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int find(int x) {
        if (parent[x] < 0) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    private static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a == b) {
            return;
        }

        if (parent[a] > parent[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        parent[a] += parent[b];
        parent[b] = a;
    }
}


// [아이디어]
// Union-Find를 사용한다.
// parent가 음수이면 루트이며, 절댓값은 해당 집합의 크기를 의미
// find에는 경로 압축, union에는 크기 기반 합치기 적용
// [시간 복잡도]
// O(N + M × α)
// [공간 복잡도]
// O(N)
// parent 배열 하나만 사용
