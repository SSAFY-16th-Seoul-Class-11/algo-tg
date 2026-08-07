import java.io.*;
import java.util.*;

class Solution {

    static int N, K, maxDist;
    static int[][] map;
    static boolean[][] isVisited;

    static int[] di = {0, 1, 0, -1};
    static int[] dj = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            map = new int[N][N];

            int maxHeight = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    int height = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(maxHeight, height);
                    map[i][j] = height;
                }
            }

            List<int[]> startList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(map[i][j] == maxHeight){
                        startList.add(new int[]{i, j});
                    }
                }
            }

            isVisited = new boolean[N][N];
            maxDist = 0;
            for (int[] start : startList) {
                int i = start[0];
                int j = start[1];
                if(!isVisited[i][j]){
                    isVisited[i][j] = true;
                    dfs(i, j, 1, true);
                    isVisited[i][j] = false;
                }
            }

            sb.append("#").append(tc).append(" ").append(maxDist).append("\n");
        }


        System.out.println(sb);
        br.close();
    }

    private static void dfs(int i, int j, int dist, boolean hasPower) {
        maxDist = Math.max(maxDist, dist);

        for (int dir = 0; dir < 4; dir++) {
            int ni = i + di[dir];
            int nj = j + dj[dir];
            if(isIn(ni, nj) && !isVisited[ni][nj]) {
                if(map[ni][nj] < map[i][j]){
                    isVisited[ni][nj] = true;
                    dfs(ni, nj, dist + 1, hasPower);
                    isVisited[ni][nj] = false;
                }
                else if(hasPower && map[ni][nj] - K < map[i][j]){
                    isVisited[ni][nj] = true;
                    int origin = map[ni][nj];
                    map[ni][nj] = map[i][j] - 1;
                    dfs(ni, nj, dist + 1, false);
                    map[ni][nj] = origin;
                    isVisited[ni][nj] = false;
                }
            }
        }
    }

    private static boolean isIn(int i, int j){
        return i >= 0 && i < N && j >= 0 && j < N;
    }
}
