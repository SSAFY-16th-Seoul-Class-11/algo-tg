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

		dp = new int[N + 1][MAX_TEMP];
		for (int i = 0; i <= N; i++) {
			Arrays.fill(dp[i], INF);
		}
		dp[0][out] = 0;

		
		for (int time = 0; time < N; time++) {
			for (int temp = 0; temp < MAX_TEMP; temp++) {
				if (onboard[time] == 1 && (temp < low || temp > high)) {
					continue;
				}
				else {
					// 온도 높이기
					if(temp + 1 < MAX_TEMP) {
						if(temp < out) dp[time + 1][temp + 1] = Math.min(dp[time + 1][temp + 1], dp[time][temp]);
						else dp[time + 1][temp + 1] = Math.min(dp[time + 1][temp + 1], dp[time][temp] + activate);
					}
					
					// 온도 낮추기
					if(temp - 1 >= 0) {
						if(temp > out) dp[time + 1][temp - 1] = Math.min(dp[time + 1][temp - 1], dp[time][temp]);
						else dp[time + 1][temp - 1] = Math.min(dp[time + 1][temp - 1], dp[time][temp] + activate);
					}
					
					// 온도 유지
					if(temp == out) dp[time + 1][temp] = Math.min(dp[time + 1][temp], dp[time][temp]);
					else dp[time + 1][temp] = Math.min(dp[time + 1][temp], dp[time][temp] + wait);
				}
			}
		}
		
		int ans = INF;
		
		for (int temp = 0; temp < MAX_TEMP; temp++) {
			ans = Math.min(ans, dp[N][temp]);
		}
		
		return ans;
	}
}
