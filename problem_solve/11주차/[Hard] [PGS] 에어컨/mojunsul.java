import java.util.Arrays;

class Solution {

	static int out, low, high, activate, wait, N;
	static int[] onboard;
	static int[][] dp;

	static final int MAX_TEMP = 51;
	static final int BIAS = 10;
	static final int INF = 1_000_000;

	public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
		out = temperature + BIAS;
		low = t1 + BIAS;
		high = t2 + BIAS;
		activate = a;
		wait = b;
		this.onboard = onboard;
		N = onboard.length;

		dp = new int[N][MAX_TEMP];
		for (int i = 0; i < N; i++) {
			Arrays.fill(dp[i], -1);
		}

		return dfs(0, out);
	}

	private static int dfs(int time, int temp) {
		if (onboard[time] == 1 && (temp < low || temp > high)) {
			return INF;
		}

		if (time == N - 1) {
			return 0;
		}

		if (dp[time][temp] != -1) {
			return dp[time][temp];
		}

		int min = INF;

		// 에어컨 끔
		int nextTempOff = temp;
		if (temp < out) {
			nextTempOff = temp + 1;
		} else if (temp > out) {
			nextTempOff = temp - 1;
		}
		min = Math.min(min, dfs(time + 1, nextTempOff));

		// 켜서 온도 낮추기 (a)
		if (temp - 1 >= 0) {
			min = Math.min(min, dfs(time + 1, temp - 1) + activate);
		}

		// 켜서 온도 높이기 (a)
		if (temp + 1 < MAX_TEMP) {
			min = Math.min(min, dfs(time + 1, temp + 1) + activate);
		}

		// 켜서 온도 유지하는 경우 (b)
		min = Math.min(min, dfs(time + 1, temp) + wait);

		return dp[time][temp] = min;
	}
}
