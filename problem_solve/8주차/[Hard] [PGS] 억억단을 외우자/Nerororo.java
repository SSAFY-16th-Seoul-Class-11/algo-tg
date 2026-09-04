class Solution {
    public int[] solution(int e, int[] starts) {
        // 몇번 등장했는지 체크
        int[] count = new int[e + 1];
        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                count[j]++;
            }
        }

        /*
        구간 별 가장 많이 등장하는 숫자 저장
        끝값(e)가 고정이다. 때문에 매번 바뀌는
        starts[i] ~~~ e 까지의 범위 중 최빈수값을 구하면 된다.
        */
        int max = 0;
        // 빈도수 저장 배열
        int[] maxFrequency = new int[e + 1];
        for (int i = e; i > 0; i--) {
            if (count[i] >= count[max]) {
                max = i;
            }
            maxFrequency[i] = max;
        }

        // 답안 작성
        int[] answer = new int[starts.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = maxFrequency[starts[i]];
        }

        return answer;
    }
}

/*
세그먼트 트리의 경우 트리 생성에 O(n),
탐색과 갱신에 O(log n)의 시간 복잡도가 소요된다.
또한 트리 생성에 4n의 공간 복잡도가 필요하다.
다만, 이 문제의 경우 끝값이 고정이고 갱신이 일어나지 않음으로
좀 더 간단한 구현으로 해결 가능하다.
공간 복잡도 2n, 시간 복잡도 O(nlogn) > 몇번 등장했는지 체크가 이만큼 걸려서
*/

// ___________________________ 세그먼트 트리 풀이 ___________________________

class Solution {

    static int[] arr;
    static Node[] tree;

    static class Node {
        int num, freq;

        Node() {}

        public Node(int num, int freq) {
            this.num = num;
            this.freq = freq;
        }

        static public Node max(Node n1, Node n2) {
            if (n1.freq > n2.freq) {
                return n1;
            } else if (n1.freq < n2.freq) {
                return n2;
            } else {
                if (n1.num < n2.num) {
                    return n1;
                } else {
                    return n2;
                }
            }
        }
    }

    public int[] solution(int e, int[] starts) {
        arr = new int[e + 1];
        tree = new Node[4 * e];

        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                arr[j]++;
            }
        }

        build(1, 1, e);

        int[] answer = new int[starts.length];
        for (int i = 0; i < starts.length; i++) {
            answer[i] = query(1, 1, e, starts[i], e).num;
        }

        return answer;
    }

    private static void build(int node, int start, int end) {
        // 자식 노드가 없을 시 현재 위치에 숫자 저장
        if (start == end) {
            tree[node] = new Node(start, arr[start]);
            return;
        }

        // 부모 노드일 경우 좌 우 노드 합쳐서 저장
        int mid = (start + end) / 2;
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        tree[node] = Node.max(tree[node * 2], tree[node * 2 + 1]);
    }

    private static Node query(int node, int start, int end, int left, int right) {
        // 범위 밖일 시 더 이상 들어가지 않는다
        if (right < start || left > end) {
            return new Node();
        }

        // 완벽히 범위 내부일 경우 현재 노드의 값 반환
        if (left <= start && end <= right) {
            return tree[node];
        }

        // 범위에 걸쳐 있을 경우 좌 우 노드 모두 탐색
        int mid = (start + end) / 2;

        return Node.max(query(node * 2, start, mid, left, right),
                query(node * 2 + 1, mid + 1, end, left, right));
    }
}

/*
너
무

공간 복잡도(4n) > 단, Node 사용으로 좀 더 높음

어
려
워

시간 복잡도(nlogn) > 위와 동일하게 몇번 걸렸는지 체크가 이만큼 걸림

이
거

세그먼트 트리 이해는 했다. 근데 ChatGPT와 동균GPT가 없으면 못 풀 것 같다

아
니
야
*/