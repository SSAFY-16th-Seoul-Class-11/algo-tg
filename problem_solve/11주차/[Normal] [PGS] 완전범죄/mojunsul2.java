import java.util.Arrays;

class Solution {

	public int solution(int[][] info, int n, int m) {
		int size = info.length;
		
		int[][] dp = new int[size + 1][m];
		
		int INF = 100000;
		for (int i = 0; i <= size; i++) {
			Arrays.fill(dp[i], INF);
		}
		
		dp[0][0] = 0;

		for (int i = 1; i <= size; i++) {
			int aTrace = info[i - 1][0];
			int bTrace = info[i - 1][1];

			for (int b = 0; b < m; b++) {
				if (dp[i - 1][b] == INF) continue;

				dp[i][b] = Math.min(dp[i][b], dp[i - 1][b] + aTrace);

				if (b + bTrace < m) {
					dp[i][b + bTrace] = Math.min(dp[i][b + bTrace], dp[i - 1][b]);
				}
			}
		}

		int answer = INF;
		for (int b = 0; b < m; b++) {
			answer = Math.min(answer, dp[size][b]);
		}

		return answer < n ? answer : -1;
	}
}
