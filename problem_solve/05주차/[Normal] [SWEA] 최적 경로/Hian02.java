import java.io.*;
import java.util.*;

public class Solution {

    static int N;
    static int[][] customer;
    static int[] company;
    static int[] home;
    static boolean[] visited;
    static int min;

    static void dfs(int count, int x, int y, int distance) {
        // 모든 고객을 방문했다면 집으로 이동
        if (count == N) {
            distance += Math.abs(x - home[0]) + Math.abs(y - home[1]);
            min = Math.min(min, distance);
            return;
        }
        // 현재까지의 거리가 이미 최소값 이상이면 더 볼 필요 없음
        if (distance >= min) {
            return;
        }

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                int nextDistance =
                        Math.abs(x - customer[i][0])+ Math.abs(y - customer[i][1]);
                dfs(count + 1, customer[i][0], customer[i][1], distance + nextDistance);
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            company = new int[]{Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};

            home = new int[]{Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};

            customer = new int[N][2];

            for (int i = 0; i < N; i++) {
                customer[i][0] = Integer.parseInt(st.nextToken());
                customer[i][1] = Integer.parseInt(st.nextToken());
            }

            visited = new boolean[N];
            min = Integer.MAX_VALUE;
            dfs(0, company[0], company[1], 0);
            System.out.println("#" + tc + " " + min);
        }
    }
}
