/*
문제 정의

길이가 n인 배열이 있고 처음에는 모든 값이 0입니다.
두 종류의 연산을 처리해야 합니다.

1 l r v
l부터 r - 1까지의 모든 원소에 v를 더합니다.

2 l r
l부터 r - 1까지의 구간 합을 구합니다.

n, m <= 100000이므로
매번 l부터 r까지 직접 값을 변경하거나 직접 합을 구하면 너무 느립니다.

따라서 Lazy Propagation을 사용하는 세그먼트 트리로 해결합니다.
*/

/*
접근 방법

일반 세그먼트 트리에서는 tree[node] = 해당 구간의 합을 저장합니다.
그런데 이번 문제에서는 구간 전체에 v를 더하는 연산이 존재합니다.
예를 들어 [0, 0, 0, 0, 0]에서
0 ~ 2 구간에 3을 더하면
[3, 3, 3, 0, 0]이 됩니다.

이때 세그먼트 트리의 리프 노드까지 모두 내려가면서 값을 변경하면 구간 길이만큼 시간이 걸릴 수 있습니다.
그래서 lazy 배열을 사용합니다. 

lazy[node]
= 이 노드가 담당하는 구간의 자식들에게
  나중에 전달해야 할 값

만약 어떤 노드가 담당하는 구간 전체에 v를 더해야 한다면
tree[node] += 구간 길이 * v를 바로 계산하고,
자식들에게는 당장 내려가지 않고
lazy[node] += v로 저장해둡니다.

이후 그 자식 노드를 실제로 사용할 때 lazy 값을 아래로 전달합니다.
이것을 Lazy Propagation이라고 합니다.
*/

/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Main {
    static long[] tree;
    static long[] lazy;
    /*
    lazy 값 전달
    */
    static void push(int node, int start, int end) {
        if (lazy[node] == 0) return;
        tree[node] += (end - start + 1) * lazy[node];
        if (start != end) {
            lazy[node * 2] += lazy[node];
            lazy[node * 2 + 1] += lazy[node];
        }
        lazy[node] = 0;
    }

    /*
    left ~ right 구간에 value 더하기
    */
    static void update(int node, int start, int end, int left, int right, long value) {
        push(node, start, end);
        // 범위를 벗어난 경우
        if (right < start || end < left) return;

        // 현재 구간이 완전히 포함되는 경우
        if (left <= start && end <= right) {
            lazy[node] += value;
            push(node, start, end);
            return;
        }

        int mid = (start + end) / 2;

        update(node * 2, start, mid, left, right, value);
        update(node * 2 + 1, mid + 1, end, left, right, value);

        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    /*
    left ~ right 구간 합 구하기
    */
    static long query(int node, int start, int end, int left, int right) {
        push(node, start, end);
        // 범위를 벗어난 경우
        if (right < start || end < left) return 0;

        // 현재 구간이 완전히 포함되는 경우
        if (left <= start && end <= right) return tree[node];

        int mid = (start + end) / 2;

        long leftSum = query(node * 2, start, mid, left, right);
        long rightSum = query(node * 2 + 1, mid + 1, end, left, right);

        return leftSum + rightSum;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        tree = new long[n * 4];
        lazy = new long[n * 4];


        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            /*
            입력은 l ~ r - 1 구간을 의미하므로
            세그먼트 트리에서는  l ~ r - 1 그대로 사용
            */
            if (type == 1) {
                long v = Long.parseLong(st.nextToken());
                update(1, 0, n - 1, l, r - 1, v);
            } 
            else {
                System.out.println(query(1, 0, n - 1, l, r - 1));
            }
        }
    }
}


/*
시간복잡도

n : 배열 크기
m : 연산 개수

구간 업데이트 Lazy Propagation을 사용하므로 O(log n)
구간 합 조회 O(log n)

총 m개의 연산을 수행하므로 O(m log n)
*/
