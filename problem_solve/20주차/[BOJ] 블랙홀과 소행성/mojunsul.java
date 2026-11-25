import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int N, M;
	static int[] b;
	static int[] a, w;

	public static void main(String[] args) throws IOException {
		readInput();
		System.out.println(solve());
	}

	private static void readInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		b = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			b[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(b);

		a = new int[M];
		w = new int[M];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			a[i] = Integer.parseInt(st.nextToken());
			w[i] = Integer.parseInt(st.nextToken());
		}
	}

	private static int solve() {
		int result = 0;
		for (int i = 0; i < M; i++) {
			result = Math.max(result, nearestDistance(a[i]) * w[i]);
		}
		return result;
	}

	private static int nearestDistance(int target) {
		int low = findLargestLessThan(target);
		int high = low + 1;

		int dist = Integer.MAX_VALUE;
		if (low >= 0) dist = Math.min(dist, target - b[low]);
		if (high < N) dist = Math.min(dist, b[high] - target);
		return dist;
	}

	private static int findLargestLessThan(int target) {
		int l = 0, r = N - 1, ans = -1;
		while (l <= r) {
			int m = (l + r) >>> 1;
			if (b[m] < target) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}
}
