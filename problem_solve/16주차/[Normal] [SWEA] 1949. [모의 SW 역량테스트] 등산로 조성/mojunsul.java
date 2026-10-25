import java.io.*;
import java.util.*;

class Solution {

	static final int[] dr = {0, 0, 1, -1};
	static final int[] dc = {1, -1, 0, 0};

	static int N, K, maxHeight, ans;
	static int[][] map;
	static boolean[][] visited;

	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new int[N][N];
			maxHeight = 0;

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					maxHeight = Math.max(maxHeight, map[i][j]);
				}
			}

			solve();

			sb.append("#")
				.append(tc)
				.append(" ")
				.append(ans)
				.append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	static void solve() {
		ans = 0;
		visited = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(map[i][j] == maxHeight) {
					ans = Math.max(ans, dfs(i, j, true));
				}
			}
		}
	}

	static int dfs(int r, int c, boolean hasPower) {
		visited[r][c] = true;
		int cnt = 1;

		for (int d = 0; d < 4; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];

			if(!isIn(nr, nc) || visited[nr][nc]) continue;

			if(map[nr][nc] < map[r][c]) {
				cnt = Math.max(cnt, dfs(nr, nc, hasPower) + 1);
			}
			else if(hasPower && map[nr][nc] - K < map[r][c]) {
				int origin = map[nr][nc];
				map[nr][nc] = map[r][c] - 1;
				cnt = Math.max(cnt, dfs(nr, nc, false) + 1);
				map[nr][nc] = origin;
			}
		}

		visited[r][c] = false;
		return cnt;
	}

	static boolean isIn(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
}
