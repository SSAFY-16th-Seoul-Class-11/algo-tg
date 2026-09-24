
import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    static int N, K, maxLen;
    static int[][] map;
    static boolean[][] visited;

    static final int[] dy = {-1, 1, 0, 0};
    static final int[] dx = {0, 0, -1, 1};

    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            maxLen = 0;
            int maxHeight = 0;

            // 입력
            map = new int[N][N];
            visited = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(maxHeight, map[i][j]);
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == maxHeight) dfs(i, j, 1, false);
                }
            }

            sb.append(maxLen).append('\n');
        }

        System.out.println(sb);
    }

    public static void dfs(int y, int x, int len, boolean useK) {
        visited[y][x] = true;
        maxLen = Math.max(maxLen, len);

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (!isIn(ny, nx) || visited[ny][nx]) continue;

            if (map[y][x] > map[ny][nx]) {
                dfs(ny, nx, len + 1, useK);
            } else {
                if (!useK && ( map[ny][nx] < map[y][x] + K )) {
                    int nMap = map[ny][nx];

                    map[ny][nx] = map[y][x] - 1;
                    dfs(ny, nx, len + 1, true);
                    map[ny][nx] = nMap;
                }
            }
        }

        visited[y][x] = false;
    }

    public static boolean isIn(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}