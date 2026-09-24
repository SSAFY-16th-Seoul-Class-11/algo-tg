import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    static int ans, size;
    static boolean[] isVisited;
    static int[][] dist;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            size = N + 2;

            Point[] points = new Point[size]; // 0: 출발, 1: 도착, 2 ~ N+1: 나머지

            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int k = 0; k < size; k++) {
                int i = Integer.parseInt(st.nextToken());
                int j = Integer.parseInt(st.nextToken());
                points[k] = new Point(i, j);
            }

            dist = new int[size][size];
            for (int k = 0; k < size; k++) {
                for (int l = 0; l < size; l++) {
                    dist[k][l] = points[k].getDistance(points[l]);
                }
            }

            isVisited = new boolean[size];
            ans = Integer.MAX_VALUE;
            dfs(0, 0, 1);

            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    private void dfs(int cur, int distSum, int cnt) {
        if(cnt == size - 1) { //도착점 빼고 다 순회한 상태
            ans = Math.min(ans, distSum + dist[cur][1]); // 지금까지 순회한 거리 + 현재지점에서 도착점 까지의 거리
            return;
        }
        
        if(distSum > ans) return;

        for (int next = 2; next < size; next++) {
            if(!isVisited[next]){
                isVisited[next] = true;
                dfs(next, distSum + dist[cur][next], cnt + 1);
                isVisited[next] = false;
            }
        }
    }

    class Point {
        int i, j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getDistance(Point comp) {
            return Math.abs(this.i - comp.i) + Math.abs(this.j - comp.j);
        }
    }

    public static void main(String[] args) throws Exception{
        Solution s = new Solution();
        s.solution();
    }
}
