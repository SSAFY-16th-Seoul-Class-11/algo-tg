
import java.io.*;
import java.util.*;

class Solution {
    static int N;
    static int M;
    static int R;
    static int C;
    static int L;
    static int ans;

    static int[][] map;
    static boolean[][] visited;


    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};


    static int[][] tunnel = {
        {},
        {0, 1, 2, 3}, // 1번 : 상하좌우
        {0, 1},       // 2번 : 상하
        {2, 3},       // 3번 : 좌우
        {0, 3},       // 4번 : 상우
        {1, 3},       // 5번 : 하우
        {1, 2},       // 6번 : 하좌
        {0, 2}        // 7번 : 상좌
    };

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            ans = 0;
            map = new int[N][M];
            visited = new boolean[N][M];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            bfs(R, C);

            System.out.println("#" + test_case + " " + ans);
        }
    }

    static void bfs(int x, int y) {

        Queue<Node> q = new ArrayDeque<>();

        q.add(new Node(x, y, 1));
        visited[x][y] = true;
        ans = 1;

        while (!q.isEmpty()) {

            Node node = q.poll();


            if (node.time >= L) {
                continue;
            }

            int type = map[node.x][node.y];


            for (int dir : tunnel[type]) {

                int nx = node.x + dx[dir];
                int ny = node.y + dy[dir];

                if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
                    continue;
                }
        
                if (visited[nx][ny]) {
                    continue;
                }

                if (map[nx][ny] == 0) {
                    continue;
                }

                int reverse;

                if (dir == 0) reverse = 1;
                else if (dir == 1) reverse = 0;
                else if (dir == 2) reverse = 3;
                else reverse = 2;

                boolean connect = false;

                for (int nextDir : tunnel[map[nx][ny]]) {
                    if (nextDir == reverse) {
                        connect = true;
                        break;
                    }
                }

                if (!connect) {
                    continue;
                }

                visited[nx][ny] = true;
                q.add(new Node(nx, ny, node.time + 1));
                ans++;
            }
        }
    }
}

class Node {
    int x;
    int y;
    int time;

    Node(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
