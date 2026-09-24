import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static class SegmentTree {
        int n;
        long[] tree;
        long[] lazy;

        public SegmentTree(int n) {
            this.n = n;
            tree = new long[n * 4];
            lazy = new long[n * 4];
        }

        private void updateLazy(int idx, int start, int end) {
            if (lazy[idx] == 0) return;

            tree[idx] += (long) (end - start + 1) * lazy[idx];

            if (start != end) {
                lazy[idx * 2] += lazy[idx];
                lazy[idx * 2 + 1] += lazy[idx];
            }

            lazy[idx] = 0;
        }

        public long query(int l, int r) {
            return query(1, 0, n - 1, l, r);
        }

        private long query(int idx, int start, int end, int l, int r) {
            updateLazy(idx, start, end);

            if (r < start || end < l) return 0;

            if (l <= start && end <= r) {
                return tree[idx];
            }

            int mid = (start + end) / 2;
            return query(idx * 2, start, mid, l, r) + query(idx * 2 + 1, mid + 1, end, l, r);
        }

        public void update(int l, int r, long diff) {
            update(1, 0, n - 1, l, r, diff);
        }

        private void update(int idx, int start, int end, int l, int r, long diff) {
            updateLazy(idx, start, end);

            if (r < start || end < l) return;

            if (l <= start && end <= r) {
                tree[idx] += (long) (end - start + 1) * diff;

                if (start != end) {
                    lazy[idx * 2] += diff;
                    lazy[idx * 2 + 1] += diff;
                }
                return;
            }

            int mid = (start + end) / 2;
            update(idx * 2, start, mid, l, r, diff);
            update(idx * 2 + 1, mid + 1, end, l, r, diff);

            tree[idx] = tree[idx * 2] + tree[idx * 2 + 1];
        }
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        SegmentTree seg = new SegmentTree(n);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            if (command == 1) {
                long v = Long.parseLong(st.nextToken());
                seg.update(l, r - 1, v);
            } else {
                sb.append(seg.query(l, r - 1)).append("\n");
            }
        }

        System.out.print(sb);
        br.close();
    }
}
// 시간 복잡도 O(M log(N)))
