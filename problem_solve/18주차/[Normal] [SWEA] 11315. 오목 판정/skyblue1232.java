import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    static int[] dr = {0, 1, 1, 1};
    static int[] dc = {1, 0, 1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine().trim());
            char[][] board = new char[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine().trim();
                for (int j = 0; j < N; j++) {
                    board[i][j] = line.charAt(j);
                }
            }

            boolean isOmok = false;

            for (int r = 0; r < N && !isOmok; r++) {
                for (int c = 0; c < N && !isOmok; c++) {
                    if (board[r][c] == 'o') {
                        for (int d = 0; d < 4; d++) {
                            int count = 0;
                            for (int step = 0; step < 5; step++) {
                                int nr = r + dr[d] * step;
                                int nc = c + dc[d] * step;

                                if (nr >= 0 && nr < N && nc >= 0 && nc < N && board[nr][nc] == 'o') {
                                    count++;
                                } else {
                                    break;
                                }
                            }
                            if (count >= 5) {
                                isOmok = true;
                                break; 
                            }
                        }
                    }
                }
            }

            if (isOmok) {
                System.out.println("#" + tc + " YES");
            } else {
                System.out.println("#" + tc + " NO");
            }
        }
    }
}
