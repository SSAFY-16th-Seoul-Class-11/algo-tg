import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int N, P;
	static int[] diff;
	static List<List<Integer>> graph;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		init(br);
		System.out.println(dfs(P));
		br.close();
	}

	private static void init(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());

		diff = new int[N + 1];
		st = new StringTokenizer(br.readLine());
		StringTokenizer st2 = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			int height = Integer.parseInt(st.nextToken());
			int goal = Integer.parseInt(st2.nextToken());
			diff[i] = goal - height;
		}

		graph = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());

			graph.get(u).add(v);
			graph.get(v).add(u);
		}
		visited = new boolean[N + 1];
	}

	private static int dfs(int cur) {
		visited[cur] = true;

		int cost = diff[cur];
		for (Integer next : graph.get(cur)) {
			if(visited[next]) continue;
			cost += dfs(next);
		}

		return Math.max(cost, 0);
	}
}
