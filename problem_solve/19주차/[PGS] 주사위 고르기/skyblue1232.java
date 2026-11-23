import java.util.*;

class Solution {

    public int[] solution(int[][] dice) {
        int n = dice.length;
        int half = n / 2;
        int maxSum = half * 100;

        long maxWin = -1;
        int bestMask = 0;

        // A가 가져갈 주사위 조합 탐색
        for (int mask = 0; mask < (1 << n); mask++) {

            // 정확히 n/2개를 선택한 경우만 확인
            if (Integer.bitCount(mask) != half) {
                continue;
            }

            // A와 B의 점수별 경우의 수
            int[] a = getCounts(dice, mask, true, maxSum);
            int[] b = getCounts(dice, mask, false, maxSum);

            long win = 0;
            long bCount = 0;

            // A 점수 > B 점수인 경우만 계산
            for (int sum = 0; sum <= maxSum; sum++) {
                win += a[sum] * bCount;
                bCount += b[sum];
            }

            // 승리 횟수가 가장 많은 조합 저장
            if (win > maxWin) {
                maxWin = win;
                bestMask = mask;
            }
        }

        // 선택한 주사위 번호 반환
        int[] answer = new int[half];
        int idx = 0;

        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                answer[idx++] = i + 1;
            }
        }

        return answer;
    }

    // 선택한 주사위들의 합계별 경우의 수 계산
    private int[] getCounts(int[][] dice, int mask,
                            boolean isA, int maxSum) {

        int[] dp = new int[maxSum + 1];
        dp[0] = 1;

        int currentMax = 0;

        for (int i = 0; i < dice.length; i++) {

            boolean selected = (mask & (1 << i)) != 0;

            if (selected != isA) {
                continue;
            }

            int[] next = new int[maxSum + 1];

            for (int sum = 0; sum <= currentMax; sum++) {
                if (dp[sum] == 0) continue;

                for (int num : dice[i]) {
                    next[sum + num] += dp[sum];
                }
            }

            dp = next;
            currentMax += 100;
        }

        return dp;
    }
}
