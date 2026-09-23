import java.util.Arrays;

class Solution {

	static int N, M, size;
	static int[][] info;
	static int[][] dp;

	public int solution(int[][] info, int n, int m) {
		this.info = info;
		size = info.length;
		N = n;
		M = m;
		dp = new int[size][M];
		for (int i = 0; i < size; i++) {
			Arrays.fill(dp[i], -1);
		}
		int answer = steal(0, 0);
		return answer < N ? answer : -1;
	}

	private int steal(int idx, int b) {
		if (idx == size) {
			return 0;
		}
		if (dp[idx][b] != -1) {
			return dp[idx][b];
		}

		int min = steal(idx + 1, b) + info[idx][0];

		if (b + info[idx][1] < M) {
			min = Math.min(min, steal(idx + 1, b + info[idx][1]));
		}

		return dp[idx][b] = min;
	}
}
