import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Solution
{
    static int N;
    static int[][] map;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            N = Integer.parseInt(br.readLine());
            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                String str = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = str.charAt(j) - '0';
                }
            }

            sb.append(supply()).append('\n');
        }

        System.out.println(sb);
    }

    public static int supply() {
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];
        int[][] dp = new int[N][N];
        queue.offer(new int[] {0, 0});
        dp[0][0] = map[0][0];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            int y = curr[0];
            int x = curr[1];
            if (y == N - 1 && x == N - 1) continue;

            for (int i = 0; i < 4; i++) {
                int ny = y + dy[i];
                int nx = x + dx[i];

                if (!isIn(ny, nx)) continue;
                if (dp[ny][nx] <= dp[y][x] + map[ny][nx] && visited[ny][nx]) continue;

                queue.offer(new int[] {ny, nx});
                dp[ny][nx] = dp[y][x] + map[ny][nx];
                visited[ny][nx] = true;
            }
        }

        return dp[N - 1][N - 1];
    }

    public static boolean isIn(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}