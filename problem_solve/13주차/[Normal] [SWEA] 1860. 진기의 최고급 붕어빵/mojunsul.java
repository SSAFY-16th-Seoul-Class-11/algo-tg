import java.util.*;
import java.io.*;

class Solution {
	
	static final String POSSIBLE = "Possible";
	static final String IMPOSSIBLE = "Impossible";
	
	static int N, M, K;
	static int[] coming;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			readInput(br);

			sb.append("#").append(tc).append(" ").append(solve() ? POSSIBLE : IMPOSSIBLE).append("\n");
		}

		System.out.println(sb);

		br.close();
	}

	private static void readInput(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		coming = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			coming[i] = Integer.parseInt(st.nextToken());
		}
	}

	private static boolean solve() {
		Arrays.sort(coming);
		
		int time;
		int make;
		int use = 0;
		for (int i = 0; i < N; i++) {
			time = coming[i];
			make = (time / M) * K;
			use++;
			if(make < use) {
				return false;
			}
		}
		return true;
	}
}
