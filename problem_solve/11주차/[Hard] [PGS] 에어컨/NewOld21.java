
class Solution {

    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int answer = Integer.MAX_VALUE;
        t1 += 10;
        t2 += 10;
        temperature += 10;
        int len = onboard.length;

        int[][] dp = new int[len][51];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 51; j++) {
                dp[i][j] = Integer.MAX_VALUE - 100001;
            }
        }

        dp[0][temperature] = 0;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 51; j++) {
                if (onboard[i] == 1 && (t1 > j || t2 < j)) {
                    continue;
                }

                if (i == len - 1) {
                    answer = Math.min(answer, dp[i][j]);
                } else {
                    // 에어컨 유지
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + b);

                    // 에어컨 O
                    if (j > 0) {
                        dp[i + 1][j - 1] = Math.min(dp[i + 1][j - 1], dp[i][j] + a);
                    }
                    if (j < 50) {
                        dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + a);
                    }

                    // 에어컨 X
                    if (temperature < j) {
                        dp[i + 1][j - 1] = Math.min(dp[i + 1][j - 1], dp[i][j]);
                    }
                    if (temperature > j) {
                        dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j]);
                    }
                    if (temperature == j) {
                        dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j]);
                    }
                }

            }
        }

        return answer;
    }
}

// 희망온도와 같아지는 방향으로 1도 상승 또는 하강
// 에어컨의 전원을 끄면 실내온도가 실외온도와 같아지는 방향으로 매 분 1도 상승 또는 하강
// 차내에 승객이 탑승 중일 때 항상 쾌적한 실내온도(t1 ~ t2)를 유지
