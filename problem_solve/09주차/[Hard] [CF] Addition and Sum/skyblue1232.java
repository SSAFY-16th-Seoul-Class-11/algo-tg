import java.io.*;
import java.util.*;

public class skyblue1232 {

    static long[] tree;
    static long[] lazy;

    // 구간 [left, right)에 value 더하기
    static void update(int node, int start, int end,
                       int left, int right, long value) {

        // 범위가 겹치지 않음
        if (right <= start || end <= left) {
            return;
        }

        // 현재 구간이 완전히 포함됨
        if (left <= start && end <= right) {
            tree[node] += (end - start) * value;
            lazy[node] += value;
            return;
        }

        push(node, start, end);

        int mid = (start + end) / 2;

        update(node * 2, start, mid, left, right, value);
        update(node * 2 + 1, mid, end, left, right, value);

        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    // 구간 [left, right)의 합 구하기
    static long query(int node, int start, int end,
                      int left, int right) {

        // 범위가 겹치지 않음
        if (right <= start || end <= left) {
            return 0;
        }

        // 현재 구간이 완전히 포함됨
        if (left <= start && end <= right) {
            return tree[node];
        }

        push(node, start, end);

        int mid = (start + end) / 2;

        long leftSum =
                query(node * 2, start, mid, left, right);

        long rightSum =
                query(node * 2 + 1, mid, end, left, right);

        return leftSum + rightSum;
    }

    // lazy 값을 자식에게 전달
    static void push(int node, int start, int end) {

        if (lazy[node] == 0 || end - start == 1) {
            return;
        }

        int mid = (start + end) / 2;
        long value = lazy[node];

        // 왼쪽 자식
        tree[node * 2] += (mid - start) * value;
        lazy[node * 2] += value;

        // 오른쪽 자식
        tree[node * 2 + 1] += (end - mid) * value;
        lazy[node * 2 + 1] += value;

        lazy[node] = 0;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st =
                new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        tree = new long[4 * n];
        lazy = new long[4 * n];

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {

            st = new StringTokenizer(br.readLine());

            int type = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            if (type == 1) {

                long value = Long.parseLong(st.nextToken());

                update(1, 0, n, l, r, value);

            } else {

                sb.append(query(1, 0, n, l, r))
                  .append('\n');
            }
        }

        System.out.print(sb);
    }
}
