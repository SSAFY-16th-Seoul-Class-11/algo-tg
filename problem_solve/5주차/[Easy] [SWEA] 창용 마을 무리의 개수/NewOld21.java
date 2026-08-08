
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
            int M = Integer.parseInt(st.nextToken());
           
            List<Integer>[] know = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                know[i] = new ArrayList<>();
            }


            for(int i=0; i < M; i++){
            	st = new StringTokenizer(br.readLine());
                int each = Integer.parseInt(st.nextToken()) - 1;
                int other = Integer.parseInt(st.nextToken()) - 1;
                know[each].add(other);
                know[other].add(each);
            }

            System.out.println("#" + test_case + " " + countGroups(know, N));
		}
	}


    private static int countGroups(List<Integer>[] know, int N){
        boolean[] visited = new boolean[N];
        int ans = 0;
        for(int idx=0; idx<N; idx++){
            if(!visited[idx]){
                visited[idx] = true;
                ans++;
                bfs(idx, know, visited); 
            }
        }

        return ans;
    }

    private static void bfs(int n, List<Integer>[] know, boolean[] visited){
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offer(n);

        while(!deque.isEmpty()){
            int each = deque.poll();
            for(int other : know[each]){
                if(!visited[other]){
                    visited[other] = true;
                    deque.offer(other);
                }
            }
        }
                    
    }
}