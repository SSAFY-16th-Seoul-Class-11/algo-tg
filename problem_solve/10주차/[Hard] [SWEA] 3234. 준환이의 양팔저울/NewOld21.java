import java.util.*;
import java.io.*;

class Solution
{
    static int N;
    static int[] chu;
    static int ans;
    static int sum;
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
		

		for(int test_case = 1; test_case <= T; test_case++)
		{
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            chu = new int[N];
            sum = 0;

            for(int i=0; i<N; i++){
                chu[i] = Integer.parseInt(st.nextToken());
                sum+= chu[i];

            }
            ans = 0;
            boolean[] used = new boolean[N];
            dfs(0, 0, 0, used);
            
			System.out.println("#" + test_case + " " + ans);
		}
	}

    private static void dfs(int cnt, int left, int right, boolean[] used){
        if(left<right){
            return;
        }
        if(cnt==N){
            ans++;
            return;
        }
        // 남은 모든 값을 오른쪽에 올려도 left가 클 경우 가지치기
        if(left >=  sum - left){
            int result = 1;
            for (int i = 2; i <= N-cnt; i++) {
                result *= i;
            }
            ans +=  result * (int)Math.pow(2,N-cnt) ;
            return;
        }
        for(int i=0; i<N; i++){
            if(!used[i]){
                used[i] = true;
                dfs(cnt+1, left+chu[i], right, used);
                dfs(cnt+1, left, right+chu[i], used);
                used[i] = false;
            }
        }
    }
}