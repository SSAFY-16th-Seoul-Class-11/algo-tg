import java.util.*;
import java.io.*;


class Solution
{
    static int N;
    static int M;
    static List<List<Integer>> graph;
    static boolean[] visited;
    static int ans;
    public static void main(String args[]) throws Exception
	{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;

		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            graph = new ArrayList<>();
            for(int i=0; i<=N; i++){
                graph.add(new ArrayList<>());
            }

            for(int i=0; i<M; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                graph.get(a).add(b);
                graph.get(b).add(a);
            }

            visited = new boolean[N+1];
            ans = 1;

            for(int i=1; i<=N; i++){
                if(graph.get(i).isEmpty())
                    continue;
                dfs(i, 1);
            }

            System.out.println("#" + test_case + " " + ans);
		}
	}

    static void dfs(int idx, int cnt){
        if(cnt>ans){
            ans = cnt;
        }
        visited[idx] = true;
        for(int num : graph.get(idx)){
            if(visited[num])
                continue;
            dfs(num, cnt+1);
        }
        visited[idx] = false;
    }
}