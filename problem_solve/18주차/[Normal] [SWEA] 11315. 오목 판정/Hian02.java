/*
문제 정의

N x N 크기의 오목판이 주어집니다.
'o'는 돌이 놓인 위치이고 '.'는 빈칸입니다.
가로, 세로, 대각선 중 어느 한 방향으로 돌이 5개 이상 연속해서 놓여 있다면 "YES", 그렇지 않다면 "NO"를 출력하는 문제입니다.
5개 이상 연속되어 있어도 오목으로 인정됩니다.
*/


/*
접근 방법

모든 칸을 하나씩 확인하면서 현재 칸이 'o'라면 4개의 방향으로 돌이 5개 연속되어 있는지 확인합니다.

확인할 방향은 다음 4개만 있으면 됩니다.
1. 오른쪽       : (0, 1)
2. 아래쪽       : (1, 0)
3. 오른쪽 아래  : (1, 1)
4. 오른쪽 위    : (-1, 1)

반대 방향까지 확인할 필요는 없습니다.
예를 들어 왼쪽 방향의 5개 돌은 가장 왼쪽 돌에서 오른쪽 방향으로 확인하면 동일한 경우를 검사할 수 있습니다.

현재 위치 (r, c)에서 한 방향으로 5개의 칸을 확인하면서
1. 지도 범위를 벗어나거나
2. 'o'가 아닌 칸을 만나면
해당 방향은 오목이 아닙니다.

5개의 칸이 모두 'o'라면 즉시 YES를 반환합니다.
모든 위치와 방향을 확인했는데도 5개의 연속된 돌이 없다면 NO를 반환합니다.
*/


/*
문제 풀이
*/

import java.io.*;

public class Solution {
    static int N;
    static char[][] board;
    /*
    확인할 4개의 방향입니다.
    0 : 오른쪽
    1 : 아래쪽
    2 : 오른쪽 아래 대각선
    3 : 오른쪽 위 대각선
    */
    static int[] dr = {0, 1, 1, -1};
    static int[] dc = {1, 0, 1, 1};

    /*
    현재 오목판에 5개 이상의 연속된 돌이 존재하는지 확인합니다.
    */
    static boolean check() {

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {

                /*
                현재 칸에 돌이 없다면 시작점이 될 수 없습니다.
                */
                if (board[r][c] != 'o') {
                    continue;
                }

                /*
                4개의 방향을 확인합니다.
                */
                for (int d = 0; d < 4; d++) {

                    boolean five = true;

                    /*
                    현재 위치를 포함하여 총 5개의 칸을 확인합니다.
                    */
                    for (int k = 0; k < 5; k++) {

                        int nr = r + dr[d] * k;
                        int nc = c + dc[d] * k;

                        /*
                        범위를 벗어나면 오목이 될 수 없습니다.
                        */
                        if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                            five = false;
                            break;
                        }

                        /*
                        돌이 없는 칸을 만나면 연속된 5개의 돌이 아닙니다.
                        */
                        if (board[nr][nc] != 'o') {
                            five = false;
                            break;
                        }
                    }

                    /*
                    5개의 돌이 연속되어 있다면 오목이 존재합니다.
                    */
                    if (five) {
                        return true;
                    }
                }
            }
        }

        /*
        모든 위치를 확인했지만 오목이 없습니다.
        */
        return false;
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            board = new char[N][N];

            /*
            오목판 입력
            */
            for (int r = 0; r < N; r++) {
                board[r] = br.readLine().toCharArray();
            }


            /*
            오목이 존재하면 YES, 존재하지 않으면 NO를 출력합니다.
            */
            String answer = check() ? "YES" : "NO";


            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
시간복잡도

오목판의 크기를 N이라고 하겠습니다.
N * N개의 모든 칸을 확인합니다.
각 돌에서 최대 4개의 방향을 확인하고 각 방향마다 최대 5개의 칸을 확인합니다.
따라서 시간복잡도는 O(N^2 * 4 * 5) 이고 4와 5는 상수이므로 최종적으로 O(N^2)입니다.


핵심 정리

1. 모든 'o'를 시작점으로 확인합니다.
2. 오른쪽, 아래쪽, 오른쪽 아래 대각선, 오른쪽 위 대각선의 4방향만 확인하면 됩니다.
3. 각 방향으로 현재 위치를 포함하여 5개의 칸이 모두 'o'인지 확인합니다.
4. 5개가 모두 'o'라면 즉시 YES를 반환합니다.
5. 6개 이상의 돌이 이어진 경우에도 그 안에 5개 연속 구간이 존재하므로 자동으로 YES가 됩니다.
6. 모든 칸을 확인한 뒤에도 찾지 못하면 NO입니다.
*/
