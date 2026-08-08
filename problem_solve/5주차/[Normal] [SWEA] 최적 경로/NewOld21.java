
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
            int ans;

            int N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            int cmp_x = Integer.parseInt(st.nextToken());
            int cmp_y = Integer.parseInt(st.nextToken());

            int home_x = Integer.parseInt(st.nextToken());
            int home_y = Integer.parseInt(st.nextToken());

            boolean[] visited = new boolean[N];
            int [] cust_x = new int[N];
            int [] cust_y = new int[N];

            for(int i=0; i < N; i++){
                cust_x[i] =  Integer.parseInt(st.nextToken());
                cust_y[i] =  Integer.parseInt(st.nextToken());
            }

            ans = dfs(visited, cust_x, cust_y, cmp_x, cmp_y, home_x, home_y, N, 0);

            System.out.println("#" + test_case + " " + ans);
		}
	}

    private static int dfs(boolean[] visited, int[] cust_x, int[] cust_y, int cur_x, int cur_y, int home_x, int home_y, int N, int cnt){
        int mn = Integer.MAX_VALUE;
        
        if(cnt==N){
            return getDistance(cur_x, cur_y, home_x, home_y);
        }

        for(int i=0; i < N; i++){
                if(!visited[i]){
                    visited[i] = true;
                     mn = Math.min(mn, dfs(visited, cust_x, cust_y,cust_x[i], cust_y[i], home_x, home_y, N, cnt+1) + getDistance(cur_x, cur_y, cust_x[i], cust_y[i]));
                    visited[i]= false;
                }
            }
        return mn;
    }


    private static int getDistance(int cur_x, int cur_y, int next_x, int next_y){
        return Math.abs(cur_x - next_x) + Math.abs(cur_y - next_y);
    }

}