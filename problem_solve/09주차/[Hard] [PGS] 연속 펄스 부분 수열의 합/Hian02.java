/*
문제 정의

sequence의 연속 부분 수열에
[1, -1, 1, -1, ...] 또는 [-1, 1, -1, 1, ...]
형태의 펄스 수열을 곱합니다.

그렇게 만들어지는 모든 연속 펄스 부분 수열 중 합의 최댓값을 구하는 문제입니다.
*/


/*
접근 방법

먼저 원래 sequence 전체에 [1, -1, 1, -1, ...]을 곱한 새로운 배열 pulse를 만듭니다.

예를 들어
sequence = [2, 3, -6, 1, 3, -1, 2, 4]이라면
pulse = [2, -3, -6, -1, 3, 1, 2, -4]가 됩니다.

이 배열에서 연속 부분 수열의 최대 합을 구하면
[1, -1, 1, -1, ...] 펄스를 사용한 경우의 답을 구할 수 있습니다.

그런데 반대 펄스 [-1, 1, -1, 1, ...]를 곱한 배열은
pulse 배열의 모든 값에 -1을 곱한 것과 같습니다.

따라서 첫 번째 pulse 배열의 최대 부분합
그리고 -pulse 배열의 최대 부분합
두 값을 구해서 더 큰 값을 선택하면 됩니다.

세그먼트 트리에서는 각 노드에 4개의 값을 저장합니다.

sum = 해당 구간 전체 합
prefix = 해당 구간의 왼쪽부터 시작하는 최대 부분합
suffix = 해당 구간의 오른쪽까지 끝나는 최대 부분합
best = 해당 구간 안에서 만들 수 있는 최대 연속 부분합

왼쪽 자식을 L, 오른쪽 자식을 R이라고 하면
sum = L.sum + R.sum
prefix = max(L.prefix, L.sum + R.prefix)
suffix = max(R.suffix, R.sum + L.suffix)
best = max( L.best, R.best, L.suffix + R.prefix)
로 부모 노드를 만들 수 있습니다.

첫 번째 펄스 배열로 트리를 만든 뒤 best를 저장하고,
배열의 모든 값에 -1을 곱한 뒤 세그먼트 트리를 다시 만들어 두 번째 펄스의 best를 구합니다.
*/


/*
문제 풀이
*/

class Solution {
    long[] sum, prefix, suffix, best;
    long[] pulse;
    public long solution(int[] sequence) {
        int n = sequence.length;
        pulse = new long[n];
        for (int i = 0; i < n; i++){
          pulse[i] = (long) sequence[i] * (i % 2 == 0 ? 1 : -1);
        }
        sum = new long[n * 4];
        prefix = new long[n * 4];
        suffix = new long[n * 4];
        best = new long[n * 4];

        build(1, 0, n - 1);
        long answer = best[1];

        for (int i = 0; i < n; i++){
          pulse[i] *= -1;
        }

        build(1, 0, n - 1);
        answer = Math.max(answer, best[1]);

        return answer;
    }

    void build(int node, int start, int end) {
        if (start == end) {
            sum[node] = pulse[start];
            prefix[node] = pulse[start];
            suffix[node] = pulse[start];
            best[node] = pulse[start];
            return;
        }

        int mid = (start + end) / 2;
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        int left = node * 2;
        int right = node * 2 + 1;

        sum[node] = sum[left] + sum[right];
        prefix[node] = Math.max(prefix[left], sum[left] + prefix[right]);
        suffix[node] = Math.max(suffix[right], sum[right] + suffix[left]);
        best[node] = Math.max(Math.max(best[left], best[right]), suffix[left] + prefix[right]);
    }
}


/*
시간복잡도

N : sequence의 길이
1. 펄스 배열 생성 O(N)
2. 첫 번째 세그먼트 트리 생성 O(N)
3. 펄스 부호 반전 O(N)
4. 두 번째 세그먼트 트리 생성 O(N)

따라서 전체 시간복잡도는 O(N)


*/
