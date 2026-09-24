class Solution {

    class Node {

        long allSum;
        long leftMax;
        long rightMax;
        long maxSum;

        public Node(long val) {
            this.allSum = val;
            this.leftMax = val;
            this.rightMax = val;
            this.maxSum = val;
        }

        public Node() {
            this.allSum = 0;
            this.leftMax = 0;
            this.rightMax = 0;
            this.maxSum = 0;
        }
    }

    class SegmentTree {

        int size;
        int[] origin;
        Node[] tree;

        public SegmentTree(int size, int[] origin) {
            this.size = size;
            this.origin = origin;
            this.tree = new Node[size * 4];

            build(1, 0, size - 1);
        }

        private void build(int idx, int start, int end) {
            if (start == end){
                tree[idx] = new Node(origin[start]);
                return;
            }

            int mid = (start + end) / 2;
            build(idx * 2, start, mid);
            build(idx * 2 + 1, mid + 1, end);

            tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
        }

        private Node merge(Node left, Node right) {
            Node parent = new Node();

            parent.allSum = left.allSum + right.allSum;
            parent.leftMax = Math.max(left.leftMax, left.allSum + right.leftMax);
            parent.rightMax = Math.max(right.rightMax, right.allSum + left.rightMax);

            parent.maxSum = Math.max(
                    Math.max(left.maxSum, right.maxSum),
                    left.rightMax + right.leftMax
            );

            return parent;
        }

        public long getMaxSum() {
            return tree[1].maxSum;
        }
    }

    public long solution(int[] sequence) {
        for (int i = 1; i < sequence.length; i+=2) {
            sequence[i] = sequence[i] * -1;
        }

        SegmentTree tree1 = new SegmentTree(sequence.length, sequence);

        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = sequence[i] * -1;
        }

        SegmentTree tree2 = new SegmentTree(sequence.length, sequence);

        return Math.max(tree1.getMaxSum(), tree2.getMaxSum());
    }
}
