import java.io.BufferedReader;
import java.io.InputStreamReader;

class Solution {

	static int N;
	static boolean[][] board;

	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			board = new boolean[N][N];

			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					board[i][j] = line.charAt(j) == 'o';
				}
			}

			sb.append("#")
				.append(tc)
				.append(" ")
				.append(solve() ? "YES" : "NO")
				.append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	static boolean solve() {
		for (int i = 0; i < N; i++) {
			if (checkRow(i))
				return true;
			if (checkCol(i))
				return true;
		}
		for (int i = 0; i <= N - 5; i++) {
			for (int j = 0; j <= N - 5; j++) {
				if (checkDiag(i, j))
					return true;
			}
			for (int j = 4; j < N; j++) {
				if (checkDiagRev(i, j))
					return true;
			}
		}
		return false;
	}

	private static boolean checkRow(int i) {
		for (int j = 0; j <= N - 5; j++) {
			for (int k = 0; k < 5; k++) {
				if (!board[i][j + k])
					break;
				if (k == 4)
					return true;
			}
		}
		return false;
	}

	private static boolean checkCol(int j) {
		for (int i = 0; i <= N - 5; i++) {
			for (int k = 0; k < 5; k++) {
				if (!board[i + k][j])
					break;
				if (k == 4)
					return true;
			}
		}
		return false;
	}

	private static boolean checkDiag(int i, int j) {
		for (int k = 0; k < 5; k++) {
			if (!board[i + k][j + k])
				break;
			if (k == 4)
				return true;
		}
		return false;
	}

	private static boolean checkDiagRev(int i, int j) {
		for (int k = 0; k < 5; k++) {
			if (!board[i + k][j - k])
				break;
			if (k == 4)
				return true;
		}
		return false;
	}
}
