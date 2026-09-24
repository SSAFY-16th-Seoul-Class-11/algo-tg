class Solution {
    static int[] count;
    static int[] tree;
  
    public int[] solution(int e, int[] starts) {
        // 각 숫자의 등장 횟수 -> 약수의 개수
        count = new int[e + 1];

        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                count[j]++;
            }
        }

        // tree[node]에는 해당 구간에서 등장 횟수가 가장 많은 "숫자"를 저장
        tree = new int[e * 4];

        build(1, 1, e);

        // starts[i] ~ e 구간 탐색
        int[] answer = new int[starts.length];

        for (int i = 0; i < starts.length; i++) {
            answer[i] = query(1, 1, e, starts[i], e);
        }

        return answer;
    }

    // 세그먼트 트리 생성
    private static void build(int node, int start, int end) {

        // 리프 노드
        if (start == end) {
            tree[node] = start;
            return;
        }

        int mid = (start + end) / 2;

        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        // 왼쪽 / 오른쪽 중 더 적절한 숫자 저장
        tree[node] = getBest(
                tree[node * 2],
                tree[node * 2 + 1]
        );
    }

    // 구간 [left, right]의 최빈값 탐색
    private static int query(
            int node,
            int start,
            int end,
            int left,
            int right
    ) {

        // 탐색 범위를 완전히 벗어남
        if (right < start || end < left) {
            return 0;
        }

        // 현재 구간이 탐색 범위에 완전히 포함
        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;

        int leftResult = query(
                node * 2,
                start,
                mid,
                left,
                right
        );

        int rightResult = query(
                node * 2 + 1,
                mid + 1,
                end,
                left,
                right
        );

        return getBest(leftResult, rightResult);
    }

    // 두 숫자 중 조건에 맞는 숫자 반환
    private static int getBest(int a, int b) {
        // 범위 밖 query 결과 처리
        if (a == 0) return b;
        if (b == 0) return a;

        // 등장 횟수가 많은 거
        if (count[a] > count[b]) {
            return a;
        }

        if (count[a] < count[b]) {
            return b;
        }
        // 등장 횟수가 같으면 더 작은 숫자
        return Math.min(a, b);
    }
}
