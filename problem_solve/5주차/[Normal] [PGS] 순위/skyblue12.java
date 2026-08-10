class skyblue12 {
    public int solution(int n, int[][] results) {
        int answer = 0;
        boolean[][] win = new boolean[n + 1][n + 1];

        for (int[] result : results) {
            int winner = result[0];
            int loser = result[1];
            win[winner][loser] = true;
        }

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (win[i][k] && win[k][j]) win[i][j] = true;
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            int count = 0;
            for (int j = 1; j <= n; j++) {
                if (i == j) continue;
                if (win[i][j] || win[j][i]) count++;
            }
            if (count == n - 1) answer++;
        }
        return answer;
    }
}
