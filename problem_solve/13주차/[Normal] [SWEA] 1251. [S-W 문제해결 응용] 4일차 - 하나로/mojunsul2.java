import java.util.*;
import java.io.*;

class Solution {

	static class Edge implements Comparable<Edge> {
		int to;
		long distSq;

		public Edge(int to, long distSq) {
			this.to = to;
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
	static List<List<Edge>> graph = new ArrayList<>();

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
		graph.clear();
		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<>());
		}
		for (int i = 0; i < N - 1; i++) {
			int x = X[i];
			int y = Y[i];
			for (int j = i + 1; j < N; j++) {
				int cx = X[j];
				int cy = Y[j];
				long distSq = getDistSq(x, y, cx, cy);
				graph.get(i).add(new Edge(j, distSq));
				graph.get(j).add(new Edge(i, distSq));
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
		int cnt = 0;
		
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[N];
		
		pq.offer(new Edge(0, 0));
		
		while(!pq.isEmpty()) {
			Edge cur = pq.poll();
			
            if (visited[cur.to]) {
                continue;
            }
            

			visited[cur.to] = true;
			sumDistSq += cur.distSq;
			cnt++;
			
			if(cnt == N) break;
			
			for (Edge edge : graph.get(cur.to)) {
				if(visited[edge.to]) continue;
				pq.offer(edge);
			}
		}		
		
		ans = Math.round(sumDistSq * E);
	}
}
