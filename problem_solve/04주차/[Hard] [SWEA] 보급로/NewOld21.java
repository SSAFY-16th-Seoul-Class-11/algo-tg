import java.util.*;
import java.io.*;


class Solution
	{
	public static void main(String args[]) throws Exception
	{
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Node> minHeap;
		int T = Integer.parseInt(br.readLine());
		int [] dx = {0,0,1,-1};
		int [] dy = {1,-1,0,0};
		
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = Integer.parseInt(br.readLine());
			int [][] road = new int[N][N];
			int ans = 0;
			
			for (int i = 0; i < N; i++) {
			    String line = br.readLine();

			    for (int j = 0; j < N; j++) {
			        road[i][j] = line.charAt(j) - '0';
			    }
			}
			
			minHeap = new PriorityQueue<>((o1, o2) ->{
				if(o1.road_sum == o2.road_sum)
					return Integer.compare(o2.road_cnt, o1.road_cnt);
				return Integer.compare(o1.road_sum, o2.road_sum);
			} );
			
			minHeap.add(new Node(0,0,0,0));
			road[0][0] = -1;
			
			while(!minHeap.isEmpty()) {
				
				Node n = minHeap.poll();
				
				if(n.x == N-1 && n.y == N-1) {
					ans = n.road_sum;	
					break;
				}
					
				for(int i=0; i<4; i++) {
					int nx = dx[i] + n.x;
					int ny = dy[i] + n.y;
					
					if(0<=nx && nx<N && 0<=ny && ny<N && road[nx][ny] >= 0) {
						minHeap.add(new Node(n.road_sum + road[nx][ny], n.road_cnt + 1, nx, ny));
						road[nx][ny] = -1;
					}
				}
				
			}
			
			System.out.println("#" + test_case + " " + ans);
		}
	}
	
	static class Node {
		int road_sum;
		int road_cnt;
		int x;
		int y;
		
		public Node(int road_sum, int road_cnt, int x, int y) {
			super();
			this.road_sum = road_sum;
			this.road_cnt = road_cnt;
			this.x = x;
			this.y = y;
		}
		
	}
}
