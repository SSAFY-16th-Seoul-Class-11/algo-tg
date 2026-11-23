import java.util.*;
import java.io.*;

class Solution {

    static int N;
    static double[][] job;
    static boolean[] check;
    static double mx;

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for (int test_case = 1; test_case <= T; test_case++) {

            N = Integer.parseInt(br.readLine());
            job = new double[N][N];
            check = new boolean[N];
            mx = 0.0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    job[i][j] = Double.parseDouble(st.nextToken()) * 0.01;
                }
            }

            dfs(0, 1);

            System.out.printf("#%d %.6f%n", test_case, mx * 100);

        }
    }

    private static void dfs(int n, double odds) {
        if (mx >= odds) {
            return;
        }
        if (n == N) {
            mx = odds;
            return;
        }
        for (int i = 0; i < N; i++) {
            if (!check[i]) {
                check[i] = true;
                dfs(n + 1, odds * job[n][i]);
                check[i] = false;
            }
        }
    }
}