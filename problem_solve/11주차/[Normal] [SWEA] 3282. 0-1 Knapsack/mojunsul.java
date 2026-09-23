import java.util.*;
import java.io.*;

class Solution {

	static int ans, N, K;
	static int[] volume, score;
	static int[][] dp;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			volume = new int[N];
			score = new int[N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				volume[i] = Integer.parseInt(st.nextToken());
				score[i] = Integer.parseInt(st.nextToken());
			}

			dp = new int[N][K+1];
			for (int i = 0; i < N; i++) {
				Arrays.fill(dp[i], -1);
			}

			ans = knapsack(0, 0);
			sb.append("#")
				.append(tc)
				.append(" ")
				.append(ans)
				.append("\n");
		}

		System.out.println(sb);

		br.close();
	}

	private static int knapsack(int idx, int vol) {
		if (idx == N)
			return 0;
		if (dp[idx][vol] != -1)
			return dp[idx][vol];

		int max = knapsack(idx + 1, vol);

		if (vol + volume[idx] <= K) {
			max = Math.max(max, knapsack(idx + 1, vol + volume[idx]) + score[idx]);
		}

		return dp[idx][vol] = max;
	}
}
