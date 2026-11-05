import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class Solution {

	static int N, ans;
	static Book[] books;

	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			books = new Book[N];

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				books[i] = new Book(i, Integer.parseInt(st.nextToken()));
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
		ans = 1;

		Arrays.sort(books, Comparator.comparingInt(o -> o.num));

		for (int i = 1; i < N; i++) {
			int prev = books[i - 1].idx;
			int cur = books[i].idx;
			if (prev > cur)
				ans++;
		}
	}

	static class Book {
		int idx, num;

		public Book(int idx, int num) {
			this.idx = idx;
			this.num = num;
		}
	}
}
