class Solution {
    public int solution(int n, int[][] results) {
        boolean[][] canKnow = new boolean[n + 1][n + 1];

        for (int i = 0; i < results.length; i++) {
            int win = results[i][0];
            int lose = results[i][1];

            canKnow[win][lose] = true;
        }

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                if (!canKnow[i][k]) continue;

                for (int j = 1; j <= n; j++) {
                    if (canKnow[i][j]) continue;
                    if (canKnow[i][k] && canKnow[k][j]) canKnow[i][j] = true;
                }
            }
        }

        int answer = 0;
        for (int i = 1; i <= n; i++) {
            boolean check = true;
            for (int j = 1; j <= n; j++) {
                if (i == j) continue;

                if (!canKnow[i][j] && !canKnow[j][i]) {
                    check = false;
                    break;
                }
            }
            if (check) answer++;
        }

        return answer;
    }
}