import java.util.*;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T = Integer.parseInt(sc.nextLine().trim());

		for(int test_case = 1; test_case <= T; test_case++) {
            int n = Integer.parseInt(sc.nextLine().trim());
            int[][] area = new int[n][n];
            for(int i=0; i<n; i++) {
                String[] line = sc.nextLine().trim().split("", n);
                for(int j=0; j<n; j++) {
                    area[i][j] = Integer.parseInt(line[j]);
                }
            }
            System.out.printf("#%d %d\n", test_case, findFastWay(n, area));
		}
	}
    
    // BFS, DP로 구현
    private static int findFastWay(int n, int[][] area) {
        int[][] dp = new int[n][n]; // 각 포인트에서의 효율적인 시간 저장
        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE); // 각 칸을 MAX_VALUE로 초기화
        
        Deque<int[]> queue = new ArrayDeque<>(); // BFS 활용을 위한 queue 사용
        queue.offerLast(new int[]{0, 0}); // input start point in queue
        dp[0][0] = area[0][0]; // start point's Value
        
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        
        while(!queue.isEmpty()) { // queue가 빌 때까지 반복
            int[] curPoint = queue.pollFirst();
            int y=curPoint[0], x=curPoint[1];
            
            for(int i=0; i<4; i++) { // 상하좌우 방향으로 갔을 때의 경로 검사
                int ny = y+dy[i];
                int nx = x+dx[i];

                if(ny<0 || ny>=n || nx<0 || nx>=n) // Out of Boundary Exception 방지
                    continue;
                
                if(dp[ny][nx] > dp[y][x]+area[ny][nx]) { // 최단경로로 갱신하여  dp에 저장하고, queue에 삽입
                    dp[ny][nx] = dp[y][x]+area[ny][nx];
                    queue.offerLast(new int[] {ny, nx});
                }
            }
        }

        return dp[n-1][n-1];
    }
}