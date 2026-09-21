import java.util.*;
import java.io.*;

class Solution {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine().trim());
        
		for(int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken()); // 물건 개수
            int K = Integer.parseInt(st.nextToken()); // 가방 담을 수 있는 최대 부피
            
            int[] dp = new int[K+1];
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int v = Integer.parseInt(st.nextToken()); 
                int c = Integer.parseInt(st.nextToken());
                
                for(int j=K; j>=v; j--) 
                    dp[j] = Math.max(dp[j], dp[j-v]+c);
            }
			
            sb.append("#").append(test_case).append(" ").append(dp[K]).append("\n");
		}
        System.out.print(sb);
    }
}
