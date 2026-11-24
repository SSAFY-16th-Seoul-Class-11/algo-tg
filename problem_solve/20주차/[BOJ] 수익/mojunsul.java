import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static int N;
	static long max;
	static long[] sum;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		while ((N = Integer.parseInt(br.readLine())) != 0) {
			init(br);
			solve();
			sb.append(max).append("\n");
		}
		System.out.println(sb);
		br.close();
	}

	private static void init(BufferedReader br) throws IOException {
		sum = new long[N];

		sum[0] = Integer.parseInt(br.readLine());
		max = sum[0];
		for (int i = 1; i < N; i++) {
			int cur = Integer.parseInt(br.readLine());
			sum[i] = sum[i - 1] + cur;
			max = Math.max(max, cur);
		}
	}

	private static void solve() {
		long min = 0;

		for (int i = 0; i < N; i++) {
			long cur = sum[i];
			min = Math.min(min, cur);
			if (cur > min) {
				max = Math.max(max, cur - min);
			}
		}
	}
}
