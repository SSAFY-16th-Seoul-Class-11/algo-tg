import java.io.*;
import java.util.*;

public class Solution {
	
	static int N, M;
	static List<List<Integer>> graph;
	static boolean[] isVisited;
	static int groupCount;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
        	
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	N = Integer.parseInt(st.nextToken());
        	M = Integer.parseInt(st.nextToken());
        	graph = new ArrayList<>();
        	for (int i = 0; i <= N; i++) {
				graph.add(new ArrayList<>());
			}
        	
        	for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				graph.get(a).add(b);
				graph.get(b).add(a);
			}
        	
        	isVisited = new boolean[N+1];
        	groupCount = 0;
        	for (int i = 1; i <= N; i++) {
				if(!isVisited[i]) {
					groupCount++;
					dfs(i);
				}
			}
            
            sb.append("#").append(tc).append(" ").append(groupCount).append("\n");
        }
        System.out.println(sb);
        br.close();
    }
    
    private void dfs(int cur) {
    	isVisited[cur] = true;
    	for (Integer next : graph.get(cur)) {
			if(!isVisited[next]) {
				dfs(next);
			}
		}
    }
    
    public static void main(String[] args) throws Exception {
		Solution s = new Solution();
		s.solution();
	}
}
