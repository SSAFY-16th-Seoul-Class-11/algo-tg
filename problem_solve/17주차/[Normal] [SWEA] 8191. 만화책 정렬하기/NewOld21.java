import java.io.*;
import java.util.*;


class Solution
{
	public static void main(String args[]) throws Exception
	{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;


		for(int test_case = 1; test_case <= T; test_case++)
		{
		
            int N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            boolean[] check = new boolean[N+1];

            int ans = 0;
            for(int i=0; i<N;i++){
                int num = Integer.parseInt(st.nextToken());
                if(!check[num-1]){
                    ans++;
                }
                check[num] = true;
                
            }

            System.out.println("#" + test_case + " " + ans);
		}
	}
}