import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static long[] tree, lazy;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        tree = new long[4 * N];
        lazy = new long[4 * N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                long v = Long.parseLong(st.nextToken());

                update(1, 0, N - 1, l, r - 1, v);
            } else {
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());

                sb.append(query(1, 0, N - 1, l, r - 1)).append('\n');
            }
        }

        System.out.print(sb);
    }

    static void update(int node, int start, int end,
                       int left, int right, long value) {

        if (right < start || end < left) {
            return;
        }

        if (left <= start && end <= right) {
            tree[node] += (end - start + 1L) * value;
            lazy[node] += value;
            return;
        }

        push(node, start, end);

        int mid = (start + end) / 2;

        update(node * 2, start, mid, left, right, value);
        update(node * 2 + 1, mid + 1, end, left, right, value);

        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    static long query(int node, int start, int end,
                      int left, int right) {

        if (right < start || end < left) {
            return 0;
        }

        if (left <= start && end <= right) {
            return tree[node];
        }

        push(node, start, end);

        int mid = (start + end) / 2;

        return query(node * 2, start, mid, left, right)
                + query(node * 2 + 1, mid + 1, end, left, right);
    }

    static void push(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = (start + end) / 2;

        long value = lazy[node];

        tree[node * 2] += (mid - start + 1L) * value;
        tree[node * 2 + 1] += (end - mid) * value;

        lazy[node * 2] += value;
        lazy[node * 2 + 1] += value;

        lazy[node] = 0;
    }
}