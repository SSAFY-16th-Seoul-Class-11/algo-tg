import java.io.*;
import java.util.*;

public class skyblue1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int[][] deltasCross = {{-1,0},{0,1},{1,0},{0,-1}};
        
        int T = Integer.parseInt(br.readLine());
        
        for(int i=1; i<=T; i++) {
        	int N = Integer.parseInt(br.readLine());
        	int[][] map = new int[N][N];
        	int[][] minCost = new int[N][N];
        	
        	for (int  r=0; r<N; r++) {
        		String l = br.readLine();
        		for(int c=0;c<N; c++) {
        			map[r][c] = l.charAt(c) - '0';
        			minCost[r][c] = Integer.MAX_VALUE;
        		}
        	}
        	
        	PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(q->q[2]));
        	minCost[0][0] = 0;
        	// pq.add(new int[] {0, 0, 0}); 
        	pq.offer(new int[] {0, 0, 0}); // 우선순위 큐에 원소를 추가. 값 추가  실패 시 false를 반환
        	while(!pq.isEmpty()) {
          		// int[] current = pq.remove(); 
        		int[] current = pq.poll(); // 우선순위 큐에서 첫 번쨰 값을 반환하고 제거, 비어있으면 null 반환
        		
        		int r = current[0];
        		int c = current[1];
        		int cost = current[2];
        		
        		if(cost > minCost[r][c]) continue;
        		// 도착치
        		if(r==N-1&&c==N-1) break;
        		
        		for(int d=0; d<deltasCross.length; d++) {
        			int nr = r + deltasCross[d][0];
        			int nc = c + deltasCross[d][1];
        			if(!isIn(nr, nc, N)) continue;
        			int nCost = cost + map[nr][nc];
        			
        			if(nCost < minCost[nr][nc])
        				minCost[nr][nc] = nCost;
        				// pq.add(new int[] {nr, nc, nCost});
        				pq.offer(new int[] {nr, nc, nCost});
        		}
        	}
        	answer.append('#').append(i).append(' ').append(minCost[N-1][N-1]).append('\n');
        }
        System.out.println(answer);
    }
    public static boolean isIn(int r, int c, int N) {
    	return r>=0 && r<N && c>=0 && c<N;
    }
}
