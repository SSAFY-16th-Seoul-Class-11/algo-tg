import java.util.*;
import java.io.*;

class Solution {
    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        int[] dx = { 0, 0, 1, -1, 1, 1, -1, -1 };
        int[] dy = { 1, -1, 0, 0, 1, -1, 1, -1 };

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());
            List<Node> list = new ArrayList<>();
            int[][] oMok = new int[N][N];
            for (int i = 0; i < N; i++) {
                String str = br.readLine();
                for (int j = 0; j < N; j++) {
                    char c = str.charAt(j);
                    if (c == 'o') {
                        oMok[i][j] = 1;
                        list.add(new Node(i, j));
                    }
                }
            }

            String ans = "NO";

            for (Node node : list) {
                int cnt = 0;
                for (int i = 0; i < 8; i++) {
                    cnt = 1;
                    int nx = node.x;
                    int ny = node.y;
                    for (int j = 0; j < 4; j++) {
                        nx += dx[i];
                        ny += dy[i];

                        if (0 <= nx && nx < N && 0 <= ny && ny < N && oMok[nx][ny] == 1) {
                            cnt++;
                        } else {
                            break;
                        }
                    }

                    if (cnt >= 5) {
                        ans = "YES";
                        break;
                    }
                }
                if (cnt >= 5) {
                    break;
                }
            }
            System.out.println("#" + test_case + " " + ans);
        }
    }
}

class Node {
    int x;
    int y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}