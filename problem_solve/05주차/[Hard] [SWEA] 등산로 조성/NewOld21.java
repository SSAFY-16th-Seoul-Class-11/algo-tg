import java.util.*;
import java.io.*;


class Solution
{
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;

		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[][] mount = new int[N][N];
            Deque<int[]> deque = new ArrayDeque<>();
            int mx = 0;

            for(int i=0; i<N; i++){
                 st = new StringTokenizer(br.readLine());
                 for(int j=0; j<N; j++){
                    int n = Integer.parseInt(st.nextToken());
                    mount[i][j] = n;
                    if(n>mx){
                        mx = n;
                        deque.clear();
                        deque.offer(new int[]{i,j});
                    }
                    else if(n==mx){
                        deque.offer(new int[]{i,j});
                    }
                 }
            }

            System.out.println("#" + test_case + " " + createTrail(deque, N, K, mount));
		}
	}


    private static int createTrail(Deque<int []> deque, int N, int K, int[][] mount){

        int mx = 0;
        boolean [][] visited = new boolean[N][N];
        while(!deque.isEmpty()){
            int[] cur = deque.poll();
            visited[cur[0]][cur[1]] = true;
            mx = Math.max(mx, dfs(cur[0], cur[1], K, N, mount, visited));
            visited[cur[0]][cur[1]] = false;
        }
        return mx;
    }

    private static int dfs(int x, int y, int K, int N, int[][] mount, boolean [][] visited){
        int [] dx = {0,0,1,-1};
        int [] dy = {1,-1,0,0};
        int cnt = 1;
        
        for(int i=0; i<4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(0<= nx && nx < N && 0<= ny && ny < N && mount[x][y] > mount[nx][ny] && !visited[nx][ny]){
                visited[nx][ny] =true;
                cnt = Math.max(cnt, dfs(nx, ny, K, N, mount, visited) + 1);
                visited[nx][ny] = false;
            }
            // 지형 cut
            else if(K>0 && 0<= nx && nx < N && 0<= ny && ny < N && !visited[nx][ny]){
                int cut = mount[nx][ny] - mount[x][y] + 1;

                if (cut <= K) {
                    int original = mount[nx][ny];

                    mount[nx][ny] -= cut;
                    visited[nx][ny] = true;
                    cnt = Math.max(cnt, dfs(nx, ny, 0, N, mount, visited) + 1);
                    visited[nx][ny] = false;
                    mount[nx][ny] = original;
                }
            }
        }
        return cnt;
    }

    
}