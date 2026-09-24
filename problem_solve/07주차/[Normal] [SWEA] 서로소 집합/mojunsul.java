import java.util.*;
import java.io.*;

class Solution {
	
	static int N, M;
	static int[] parent;
	
	public static void main(String args[]) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			sb.append("#").append(tc).append(" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			parent = new int[N + 1];
			for (int i = 1; i <= N; i++) {
				parent[i] = i;
			}
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int command = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				switch(command) {
				case 0:
					union(a, b);
					break;
				case 1:
					sb.append(find(a) == find(b) ? 1 : 0);
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
		
		br.close();
	}
	
	private static int find(int x) {
		if(parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}
	
	private static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if(x!=y) {
			parent[y] = x;
		}
	}
}
