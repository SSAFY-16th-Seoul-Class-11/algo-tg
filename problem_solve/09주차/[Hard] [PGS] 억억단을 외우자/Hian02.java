/*
문제 정의

억억단은 1억 x 1억 크기의 곱셈표입니다.

숫자 n이 억억단에 몇 번 등장하는지는 n을 만들 수 있는 곱셈 조합의 개수와 같습니다.

예를 들어
6 = 1 * 6
  = 2 * 3
  = 3 * 2
  = 6 * 1
이므로 6은 4번 등장합니다.

즉, 숫자 n이 등장하는 횟수는 n의 약수 개수와 같습니다.

각 starts의 값 s에 대해 s 이상 e 이하의 숫자 중
1. 억억단에서 가장 많이 등장하는 숫자
2. 등장 횟수가 같다면 더 작은 숫자 를 찾아야 합니다.
*/


/*
접근 방법

1. 1 ~ e까지 각 숫자의 약수 개수를 구합니다.
count[n] = 숫자 n이 억억단에서 등장하는 횟수
약수 개수는 에라토스테네스의 체와 비슷하게 구할 수 있습니다.

i가 약수라면 i, 2i, 3i, ... 모두 i를 약수로 가집니다.

따라서
for (int i = 1; i <= e; i++) {
    for (int j = i; j <= e; j += i) {
        count[j]++;
    }
}

2. 세그먼트 트리를 만듭니다.

각 노드에는 단순한 최댓값이 아니라 해당 구간에서 정답이 되는 숫자를 저장합니다.

두 숫자 a, b를 비교할 때 
count[a] > count[b] -> a 선택
count[a] < count[b] -> b 선택
count[a] == count[b] -> 더 작은 숫자 선택

3. 각 s에 대해 [s, e] 구간을 세그먼트 트리로 조회합니다.

그러면 해당 범위에서 가장 많이 등장하고, 동점이면 가장 작은 숫자를 O(log e)에 찾을 수 있습니다.
*/


/*
문제 풀이
*/

class Solution {

    int[] count;
    int[] tree;

    public int[] solution(int e, int[] starts) {
        /*
        1. 각 숫자의 등장 횟수 구하기
        count[n]
        = n의 약수 개수
        */
        count = new int[e + 1];
        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                count[j]++;
            }
        }
        /*
        2. 세그먼트 트리 생성
        */
        tree = new int[e * 4];
        build( 1, 1, e);
        /*
        3. starts의 각 구간을 조회
        */
        int[] answer = new int[starts.length];
        for (int i = 0; i < starts.length; i++) {
            answer[i] = query( 1, 1, e, starts[i], e);
        }
        return answer;
    }

    /*
    두 숫자 중 더 좋은 숫자를 반환
    기준
    1. 등장 횟수가 많은 숫자
    2. 등장 횟수가 같으면 작은 숫자
    */
    int better(int a, int b) {
        // 없는 값 처리
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        // 등장 횟수가 다른 경우
        if (count[a] != count[b]) {
            if (count[a] > count[b]) {
                return a;
            }
            return b;
        }
        // 등장 횟수가 같으면 작은 숫자
        return Math.min(a, b);
    }

    /*
    세그먼트 트리 생성
    tree[node] = start ~ end 범위에서 억억단 등장 횟수가 가장 많은 숫자
    등장 횟수가 같다면 가장 작은 숫자
    */
    int build(int node,int start,int end) {
        // 리프 노드
        if (start == end) {
            tree[node] = start;
            return tree[node];
        }
        int mid = (start + end) / 2;
        int left = build(node * 2, start,  mid);
        int right = build( node * 2 + 1, mid + 1, end);
        tree[node] = better(left, right);
        return tree[node];
    }

    /*
    left ~ right 구간의 정답 찾기
    */
    int query( int node, int start, int end, int left, int right) {
        /*
        현재 구간이
        찾는 범위와 전혀 겹치지 않는 경우
        */
        if (right < start || end < left) {
            return 0;
        }

        /*
        현재 구간이
        찾는 범위에 완전히 포함되는 경우
        */
        if (left <= start && end <= right) {
            return tree[node];
        }
        int mid = (start + end) / 2;
        int leftResult = query(node * 2, start, mid,  left,right);
        int rightResult = query(  node * 2 + 1, mid + 1, end, left, right);
        return better( leftResult, rightResult);
    }
}


/*
시간복잡도

e : 최대 숫자
Q : starts의 길이

1. 등장 횟수 계산

for (int i = 1; i <= e; i++) {
    for (int j = i; j <= e; j += i)
}

실행 횟수는 e/1 + e/2 + e/3 + ... + e/e
이는 조화급수의 성질에 의해 O(e log e)

2. 세그먼트 트리 생성

각 숫자를 한 번씩 트리에 넣으므로 O(e)

3. 구간 조회

starts 하나마다 O(log e)가 필요합니다.

starts의 개수를 Q라고 하면 O(Q log e)

따라서 전체 시간복잡도는 O(e log e + Q log e)
*/
