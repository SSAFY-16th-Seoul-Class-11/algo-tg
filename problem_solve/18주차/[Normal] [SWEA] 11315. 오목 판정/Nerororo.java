import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.io.FileInputStream;
import java.util.StringTokenizer;

class Solution
{
    static int N;
    static boolean[][] graph;

    public static void main(String args[]) throws Exception
    {
//		System.setIn(new FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            N = Integer.parseInt(br.readLine());
            graph = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                String str = br.readLine();
                for (int j = 0; j < N; j++) {
                    if (str.charAt(j) == 'o') graph[i][j] = true;
                }
            }

            sb.append(haveFiveStone());
        }

        System.out.println(sb.toString());
    }

    static boolean width(int y, int x) {
        for (int nx = x + 1; nx < x + 5; nx++) {
            if (!graph[y][nx]) return false;
        }
        return true;
    }

    static boolean height(int y, int x) {
        for (int ny = y + 1; ny < y + 5; ny++) {
            if (!graph[ny][x]) return false;
        }
        return true;
    }

    static boolean diag1(int y, int x) {
        for (int i = 1; i < 5; i++) {
            if (!graph[y + i][x + i]) return false;
        }
        return true;
    }

    static boolean diag2(int y, int x) {
        for (int i = 1; i < 5; i++) {
            if (!graph[y + i][x - i]) return false;
        }
        return true;
    }

    static final String no = "NO\n";
    static final String yes = "YES\n";

    static String haveFiveStone() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (graph[i][j]) {
                    if (j < N - 4) {
                        if (width(i, j)) return yes;
                    }
                    if (i < N - 4) {
                        if (height(i, j)) return yes;
                    }
                    if (j < N - 4 && i < N - 4) {
                        if (diag1(i, j)) return yes;
                    }
                    if (j >= 4 && i < N - 4) {
                        if (diag2(i, j)) return yes;
                    }
                }
            }
        }

        return no;
    }

}