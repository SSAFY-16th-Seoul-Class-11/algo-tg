import java.util.Arrays;

class Solution {

    public int solution(int[][] info, int n, int m) {

        // dp[i] = A의 흔적이 i일 때 B가 남긴 최소 흔적
        // B의 흔적이 m 이상이면 잡히므로 m을 불가능한 상태로 사용
        int[] dp = new int[n];
        Arrays.fill(dp, m);
        dp[0] = 0;

        for (int i = 0; i < info.length; i++) {

            // 현재 물건을 A 또는 B가 훔친 이후의 상태
            int[] next = new int[n];
            Arrays.fill(next, m);

            for (int j = 0; j < n; j++) {
                if (dp[j] >= m) {
                    continue;
                }

                // A가 현재 물건을 훔치는 경우
                if (j + info[i][0] < n) {
                    next[j + info[i][0]] = Math.min(next[j + info[i][0]], dp[j]);
                }

                // B가 현재 물건을 훔치는 경우
                if (dp[j] + info[i][1] < m) {
                    next[j] = Math.min(next[j], dp[j] + info[i][1]);
                }
            }

            dp = next;
        }

        // 처음 발견되는 가능한 상태가 A 흔적의 최솟값
        for (int i = 0; i < n; i++) {
            if (dp[i] < m) {
                return i;
            }
        }

        return -1;
    }
}

/*
물건의 개수를 N이라고 할 때,
각 물건마다 A의 흔적 n개를 확인한다. > O(N * n)
 */