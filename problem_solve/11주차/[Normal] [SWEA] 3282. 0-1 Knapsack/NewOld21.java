import java.util.*;
import java.io.*;


class Solution
{
	public static void main(String args[]) throws Exception
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
		int T = Integer.parseInt(br.readLine());


		for(int test_case = 1; test_case <= T; test_case++)
		{
            
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] V = new int[N+1];
            int[] C = new int[N+1];

            for(int i=1; i<N+1; i++){
                st = new StringTokenizer(br.readLine());
                int n = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                V[i] = n;
                C[i] = k;
            }

            int[][] dp = new int[N+1][K+1];

            for(int i=1; i<=N; i++){
                int v = V[i];
                int c = C[i];

                for(int j=0; j<=K; j++){
                    if(j>= v){
                        dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-v] + c);
                    }
                    else{
                        dp[i][j] = dp[i-1][j];
                    }
                }
            }

            System.out.println("#" + test_case + " " + dp[N][K]);
		}
	}
}