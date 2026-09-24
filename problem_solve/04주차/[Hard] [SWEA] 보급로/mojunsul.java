import java.io.*;
import java.util.*;

class Solution {

    public static void main(String[] args) throws IOException {

        int[] di = {0, 1, 0, -1};
        int[] dj = {1, 0, -1, 0};

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N][N];

            for (int i = 0; i < N; i++) {
                char[] line = br.readLine().toCharArray();
                for (int j = 0; j < N; j++) {
                    map[i][j] = line[j] - '0';
                }
            }

            int ans = 0;
            boolean[][] isVisited;
            isVisited = new boolean[N][N];
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.offer(new Node(0, 0, 0));
            isVisited[0][0] = true;
            while(!pq.isEmpty()) {
                Node cur = pq.poll();

                if (cur.i == N - 1 && cur.j == N - 1) {
                    ans = cur.cost;
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int ni = cur.i + di[d];
                    int nj = cur.j + dj[d];
                    if (isIn(ni, nj, N) && !isVisited[ni][nj]) {
                        pq.offer(new Node(ni, nj, cur.cost + map[ni][nj]));
                        isVisited[ni][nj] = true;
                    }
                }
            }


            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }

        System.out.println(sb);
        br.close();
    }

    static class Node implements Comparable<Node> {

        int i, j, cost;

        public Node(int i, int j, int cost) {
            this.i = i;
            this.j = j;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static boolean isIn(int i, int j, int N) {
        return i >= 0 && i < N && j >= 0 && j < N;
    }
}