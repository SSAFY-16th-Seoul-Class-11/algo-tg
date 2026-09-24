class Solution {
    static long[] arr;
    static Node[] tree;

    static class Node {
        long sum;
        long left;
        long right;
        long max;

        Node(long value) {
            this.sum = value;
            this.left = value;
            this.right = value;
            this.max = value;
        }

        Node(long sum, long left, long right, long max) {
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.max = max;
        }
    }

    public long solution(int[] sequence) {
        arr = new long[sequence.length];

        // 1, -1, 1, -1 ... 펄스 
        for (int i = 0; i < sequence.length; i++) {
            if (i % 2 == 0) {
                arr[i] = sequence[i];
            } else {
                arr[i] = -sequence[i];
            }
        }

        long answer1 = getMax();

        // 반대 펄스는 부호만 뒤집기
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= -1;
        }

        long answer2 = getMax();

        return Math.max(answer1, answer2);
    }

    static long getMax() {
        tree = new Node[arr.length * 4];

        build(1, 0, arr.length - 1);

        return tree[1].max;
    }

    private static void build(int node, int start, int end) {

        // 자식 노드가 없을 경우 현재 값 저장
        if (start == end) {
            tree[node] = new Node(arr[start]);
            return;
        }

        // 부모 노드일 경우 좌우 노드를 합쳐서 저장
        int mid = (start + end) / 2;

        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        tree[node] = merge(
                tree[node * 2],
                tree[node * 2 + 1]
        );
    }

    private static Node merge(Node n1, Node n2) {
        // 구간 전체 합
        long sum = n1.sum + n2.sum;

        // 왼쪽부터 이어지는 최대합
        long left = Math.max(
                n1.left,
                n1.sum + n2.left
        );

        // 오른쪽까지 이어지는 최대합
        long right = Math.max(
                n2.right,
                n2.sum + n1.right
        );

        // 구간 안에서 가장 큰 연속합
        long max = Math.max(
                Math.max(n1.max, n2.max),
                n1.right + n2.left
        );

        return new Node(sum, left, right, max);
    }
}

// 동준님 코드 많이 참고했어요
