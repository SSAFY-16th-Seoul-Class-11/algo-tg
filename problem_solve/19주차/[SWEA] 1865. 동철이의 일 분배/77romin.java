import java.util.*;
import java.io.*;

class Solution {
    
    private static double maxP;
    private static int n;
    private static int[][] p;
    private static boolean[] visited;
    
	public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++) {
            
            maxP = 0;
            n = Integer.parseInt(br.readLine());
            p = new int[n][n];
            visited = new boolean[n];
            
            for(int i=0; i<n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j=0; j<n; j++)
                    p[i][j] = Integer.parseInt(st.nextToken());
            }
            
            choice(0, 1); // 누가, 확률곱
            
            sb.append("#").append(test_case).append(" ").append(String.format("%.6f", maxP*100)).append("\n");
		}
        
        System.out.print(sb);
	}
    
    private static void choice(int r, double multP) {
        if(multP <= maxP) return; // 현재의 확률 곱이 최대 확률 곱보다 작으면 가지치기
        
        if(r==n) {
            if(multP>maxP) maxP = multP;
            return;
        }
        
        for(int i=0; i<n; i++) {
            if(p[r][i]==0) continue; // 확률이 0인 경우 넘어가기
            if(visited[i]) continue;  // 이미 완료한 일의 경우 넘어가기
            visited[i] = true;
            choice(r+1, multP*p[r][i]*0.01);
            visited[i] = false;
        }
    }
    
}
