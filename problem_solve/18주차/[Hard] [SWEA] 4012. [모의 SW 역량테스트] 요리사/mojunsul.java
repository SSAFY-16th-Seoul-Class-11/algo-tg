import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

	static int N, ans;
	static int[][] S;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			S = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					S[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			ans = Integer.MAX_VALUE;
			solve(0, 0, 0);

			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.print(sb);
	}

	private static void solve(int idx, int cnt, int mask) {
		if (idx > N || ans == 0)
			return;

		if (cnt == N / 2) {
			ans = Math.min(ans, getSynergyDiff(mask));
			return;
		}

		solve(idx + 1, cnt, mask);
		solve(idx + 1, cnt + 1, mask | (1 << idx));
	}

	private static int getSynergyDiff(int mask) {
		int[] sum = {0, 0};

		for (int i = 0; i < N; i++) {
			int group = getGroup(mask, i);
			for (int j = 0; j < N; j++) {
				if(getGroup(mask, j) == group) {
					sum[group] += S[i][j];
				}
			}
		}

		return Math.abs(sum[0] - sum[1]);
	}

	private static int getGroup(int mask, int idx) {
		return (mask & (1 << idx)) == 0 ? 0 : 1;
	}

}
