import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    static int ans, N;
    static boolean[][] isAdj;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
        	readInput(br);
            
            solve();

            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

	private static void readInput(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		isAdj = new boolean[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				isAdj[i][j] = Integer.parseInt(st.nextToken()) == 1;
			}
		}
	}
	
	private static void solve() {
		ans = Integer.MAX_VALUE;
		visited = new boolean[N];
		Queue<int[]> queue = new ArrayDeque<>();
		for (int i = 0; i < N; i++) {
			Arrays.fill(visited, false);;
			queue.clear();
			queue.offer(new int[] {i, 0});
			visited[i] = true;
			
			int sum = 0;
			while(!queue.isEmpty()) {
				int[] cur = queue.poll();
				
				sum += cur[1];
				
				for (int next = 0; next < N; next++) {
					if(isAdj[cur[0]][next] && !visited[next]) {
						visited[next] = true;
						queue.offer(new int[] {next, cur[1] + 1});
					}
				}
			}
			ans = Math.min(ans, sum);
		}
	}
}
