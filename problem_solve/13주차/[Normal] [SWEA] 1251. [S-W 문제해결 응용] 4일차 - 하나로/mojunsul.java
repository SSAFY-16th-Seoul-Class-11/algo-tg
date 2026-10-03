import java.util.*;
import java.io.*;

class Solution {

	static class Edge implements Comparable<Edge> {
		int u, v;
		long distSq;

		public Edge(int u, int v, long distSq) {
			this.u = u;
			this.v = v;
			this.distSq = distSq;
		}

		@Override
		public int compareTo(Edge o) {
			return Long.compare(this.distSq, o.distSq);
		}
	}

	static int N;
	static double E;
	static long ans;
	static int[] X, Y;
	static int[] parent = new int[1001];
	static List<Edge> edges = new ArrayList<>();

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			readInput(br);

			makeEdges();

			solve();

			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}

		System.out.println(sb);

		br.close();
	}

	private static void readInput(BufferedReader br) throws IOException {
		N = Integer.parseInt(br.readLine());
		X = new int[N];
		Y = new int[N];

		StringTokenizer st1 = new StringTokenizer(br.readLine());
		StringTokenizer st2 = new StringTokenizer(br.readLine());

		for (int i = 0; i < N; i++) {
			X[i] = Integer.parseInt(st1.nextToken());
			Y[i] = Integer.parseInt(st2.nextToken());
		}

		E = Double.parseDouble(br.readLine());
	}

	private static void makeEdges() {
		edges.clear();
		for (int i = 0; i < N - 1; i++) {
			int x = X[i];
			int y = Y[i];
			for (int j = i + 1; j < N; j++) {
				int cx = X[j];
				int cy = Y[j];
				long distSq = getDistSq(x, y, cx, cy);
				edges.add(new Edge(i, j, distSq));
			}
		}
	}

	private static long getDistSq(int x1, int y1, int x2, int y2) {
		long dx = x1 - x2;
		long dy = y1 - y2;
		return dx * dx + dy * dy;
	}

	private static void solve() {
		long sumDistSq = 0;
		for (int i = 0; i < N; i++) {
			parent[i] = i;
		}
		Collections.sort(edges);
		
		int cnt = 0;
		for (Edge edge : edges) {
			if (find(edge.u) != find(edge.v)) {
				union(edge.u, edge.v);
				sumDistSq += edge.distSq;
				cnt++;
				if (cnt == N - 1) break;
			}
		}
		
		ans = Math.round(sumDistSq * E);
	}

	private static int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		return parent[x] = find(parent[x]);
	}

	private static void union(int x, int y) {
		x = find(x);
		y = find(y);

		if (x != y) {
			parent[y] = x;
		}
	}
}
