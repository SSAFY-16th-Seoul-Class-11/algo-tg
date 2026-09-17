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
			
			volume = new int[N + 1];
			score = new int[N + 1];

			for (int i = 1; i <= N; i++) {
				st = new StringTokenizer(br.readLine());
				volume[i] = Integer.parseInt(st.nextToken());
				score[i] = Integer.parseInt(st.nextToken());
			}

			dp = new int[N + 1][K + 1];

			for (int i = 1; i <= N; i++) {
				for (int w = 0; w <= K; w++) {
					int max = dp[i - 1][w];
					if (volume[i] <= w) {
						max = Math.max(max, dp[i - 1][w - volume[i]] + score[i]);
					}
					dp[i][w] = max;
				}
			}

			ans = dp[N][K];
			sb.append("#")
				.append(tc)
				.append(" ")
				.append(ans)
				.append("\n");
		}

		System.out.println(sb);

		br.close();
	}
}
