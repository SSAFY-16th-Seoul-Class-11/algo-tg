class Solution {
    public int solution(int n, int[][] results) {
        boolean[][] win = new boolean[n + 1][n + 1];

        // 직접 경기 결과 저장
        for (int[] result : results) {
            int winner = result[0];
            int loser = result[1];

            win[winner][loser] = true;
        }

        // 간접적인 승패 관계까지 계산
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (win[i][k] && win[k][j]) {
                        win[i][j] = true;
                    }
                }
            }
        }

        int answer = 0;

        // 각 선수의 순위를 확정할 수 있는지 확인
        for (int i = 1; i <= n; i++) {
            int count = 0;

            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    continue;
                }

                // i가 j를 이겼거나, j가 i를 이겼다면
                // 두 선수의 순서가 확실함
                if (win[i][j] || win[j][i]) {
                    count++;
                }
            }

            // 나머지 모든 선수와의 관계를 알고 있다면 순위 확정
            if (count == n - 1) {
                answer++;
            }
        }

        return answer;
    }
}
