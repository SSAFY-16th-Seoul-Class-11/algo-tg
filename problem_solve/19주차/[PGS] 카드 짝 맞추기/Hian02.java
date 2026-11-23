/*
문제 정의

4 x 4 보드에 같은 그림의 카드가 2장씩 존재합니다.
커서는 다음 두 가지 방식으로 이동할 수 있습니다.

1. 방향키
   상하좌우로 1칸 이동하며 조작 횟수는 1입니다.

2. Ctrl + 방향키
   해당 방향에서 가장 가까운 카드가 있는 곳까지 한 번에 이동합니다.
   카드가 없다면 해당 방향의 가장 끝 칸까지 이동합니다.
   조작 횟수는 1입니다.

카드를 선택하려면 Enter를 눌러야 하며 Enter 역시 조작 횟수 1입니다.
같은 그림의 카드 2장을 차례로 선택하면 두 카드가 제거됩니다.
모든 카드를 제거하기 위한 최소 키 조작 횟수를 구해야 합니다.
*/

/*
접근 방법

이 문제는 DFS + BFS를 사용합니다.

1. DFS : 카드 제거 순서 탐색
카드 종류는 최대 6개입니다.
예를 들어 카드 1, 2, 3이 남아 있다면 1번 카드부터 제거 -> 2번 카드부터 제거 -> 3번 카드부터 제거하는 모든 경우를 확인합니다.
또한 같은 카드가 A, B 두 위치에 있다면 현재 위치 -> A -> B와 현재 위치 -> B -> A
두 가지 순서도 모두 확인해야 합니다.

따라서 한 카드 종류를 선택할 때마다 2가지 선택 순서를 확인합니다.

2. BFS : 두 위치 사이 최소 이동 횟수 계산
현재 위치에서 목표 위치까지 이동할 때 사용할 수 있는 동작은 총 8개입니다.

일반 이동
상 / 하 / 좌 / 우

Ctrl 이동
Ctrl+상 / Ctrl+하 / Ctrl+좌 / Ctrl+우

각 동작의 비용은 모두 1입니다.
따라서 각 칸을 정점으로 생각하면 가중치가 모두 1인 최단거리 문제이므로 BFS를 사용하면 됩니다.


3. 카드가 제거되면 Ctrl 이동 결과가 달라진다
이 문제에서 중요한 부분입니다.
카드를 제거하면 해당 위치가 빈칸이 되기 때문에 이후 Ctrl 이동 시 해당 카드를 건너뛰게 됩니다.

따라서 카드 제거 후 board[r1][c1] = 0, board[r2][c2] = 0으로 실제 보드를 변경한 뒤 다음 DFS를 수행해야 합니다.
DFS가 끝나면 다른 경우를 탐색하기 위해 원래 카드 번호로 복구합니다.


4. Enter 횟수
카드 한 쌍을 제거할 때 반드시 카드 2장을 선택해야 하므로 Enter를 정확히 2번 누릅니다.
따라서 카드 한 쌍을 제거하는 비용은 현재 위치 -> 첫 번째 카드 이동 + 첫 번째 카드 -> 두 번째 카드 이동 + Enter 2번입니다.
*/


/*
문제 풀이
*/

import java.util.*;

class Solution {
    static int[][] board;
    /*
    cardPos[type]에는 해당 카드의 두 위치를 저장합니다.

    예)
    cardPos[3][0] = 첫 번째 3번 카드의 행
    cardPos[3][1] = 첫 번째 3번 카드의 열
    cardPos[3][2] = 두 번째 3번 카드의 행
    cardPos[3][3] = 두 번째 3번 카드의 열
    */
    static int[][] cardPos = new int[7][4];
    static int[] cardCount = new int[7];

    /*
    상, 하, 좌, 우
    */
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    /*
    현재 커서 위치 (r, c)에서 남아 있는 모든 카드를 제거하는 최소 조작 횟수를 반환합니다.
    */
    static int dfs(int r, int c) {
        /*
        남아 있는 카드가 없다면 더 이상 필요한 조작이 없습니다.
        */
        boolean empty = true;
        for (int type = 1; type <= 6; type++) {
            if (cardCount[type] == 0) {
                continue;
            }

            int r1 = cardPos[type][0];
            int c1 = cardPos[type][1];

            /*
            처음에는 존재했던 카드라도 현재 board에서 0이라면 이미 제거된 카드입니다.
            */
            if (board[r1][c1] != 0) {
                empty = false;
                break;
            }
        }
        if (empty) {
            return 0;
        }
        int answer = Integer.MAX_VALUE;

        /*
        현재 남아 있는 카드 종류 중 하나를 선택합니다.
        */
        for (int type = 1; type <= 6; type++) {
            if (cardCount[type] == 0) {
                continue;
            }

            int r1 = cardPos[type][0];
            int c1 = cardPos[type][1];

            int r2 = cardPos[type][2];
            int c2 = cardPos[type][3];

            /*
            이미 제거된 카드라면 건너뜁니다.
            */
            if (board[r1][c1] == 0) {
                continue;
            }

            /*
            첫 번째 순서
            현재 위치 -> 첫 번째 카드 -> 두 번째 카드
            */
            int move1 = bfs(r, c, r1, c1) + bfs(r1, c1, r2, c2) + 2;

            /*
            두 번째 순서
            현재 위치 -> 두 번째 카드 -> 첫 번째 카드
            */
            int move2 =  bfs(r, c, r2, c2) + bfs(r2, c2, r1, c1) + 2;

            /*
            현재 카드 한 쌍을 제거합니다.
            이후 Ctrl 이동에서 이 두 위치는 빈칸으로 처리되어야 합니다.
            */
            board[r1][c1] = 0;
            board[r2][c2] = 0;

            /*
            첫 번째 카드 -> 두 번째 카드 순서로 제거했다면 커서는 두 번째 카드 위치에 남습니다.
            */
            answer = Math.min( answer, move1 + dfs(r2, c2));

            /*
            두 번째 카드 -> 첫 번째 카드 순서로 제거했다면 커서는 첫 번째 카드 위치에 남습니다.
            */
            answer = Math.min( answer, move2 + dfs(r1, c1) );

            /*
            다른 제거 순서를 탐색하기 위해 카드를 다시 복구합니다.
            */
            board[r1][c1] = type;
            board[r2][c2] = type;
        }

        return answer;
    }

    /*
    시작 위치에서 목표 위치까지 필요한 최소 이동 횟수를 BFS로 구합니다.
    Enter는 포함하지 않고 이동 횟수만 계산합니다.
    */
    static int bfs(int startR, int startC, int targetR, int targetC) {
        /*
        이미 같은 위치라면 이동할 필요가 없습니다.
        */
        if (startR == targetR && startC == targetC) {
            return 0;
        }

        int[][] dist = new int[4][4];

        for (int i = 0; i < 4; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startR, startC});
        dist[startR][startC] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            /*
            4개의 방향을 확인합니다.
            */
            for (int d = 0; d < 4; d++) {

                /*
                1. 일반 방향키로 1칸 이동
                */
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    /*
                    목표 위치에 도착했다면 바로 반환할 수 있습니다.
                    */
                    if (nr == targetR && nc == targetC) {
                        return dist[nr][nc];
                    }
                    queue.offer(new int[]{nr, nc});
                }
                /*
                2. Ctrl + 방향키 이동
                */
                int[] ctrl = ctrlMove(r, c, d);
                nr = ctrl[0];
                nc = ctrl[1];

                if (dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    if (nr == targetR && nc == targetC) {
                        return dist[nr][nc];
                    }
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;
    }
    /*
    현재 위치에서 Ctrl + 방향키를 눌렀을 때 이동하게 되는 위치를 반환합니다.
    */
    static int[] ctrlMove(int r, int c, int dir) {
        int nr = r;
        int nc = c;

        while (true) {
            int nextR = nr + dr[dir];
            int nextC = nc + dc[dir];
            /*
            다음 칸이 보드 밖이라면 현재 위치가 해당 방향의 마지막 칸입니다.
            */
            if (nextR < 0 || nextR >= 4 || nextC < 0 || nextC >= 4) {
                return new int[]{nr, nc};
            }
            /*
            한 칸 이동합니다.
            */
            nr = nextR;
            nc = nextC;
            /*
            가장 가까운 카드를 발견했다면 해당 카드 위치에서 멈춥니다.
            */
            if (board[nr][nc] != 0) {
                return new int[]{nr, nc};
            }
        }
    }

    public int solution(int[][] inputBoard, int r, int c) {
        board = new int[4][4];
        cardPos = new int[7][4];
        cardCount = new int[7];
        /*
        입력 보드를 복사하면서 각 카드의 위치를 저장합니다.
        */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = inputBoard[i][j];
                int type = board[i][j];
                if (type == 0) {
                    continue;
                }

                /*
                같은 카드의 첫 번째 또는 두 번째 위치에 저장합니다.
                */
                if (cardCount[type] == 0) {
                    cardPos[type][0] = i;
                    cardPos[type][1] = j;
                }
                else {
                    cardPos[type][2] = i;
                    cardPos[type][3] = j;
                }
                cardCount[type]++;
            }
        }
        return dfs(r, c);
    }
}


/*
시간복잡도

남아 있는 카드 종류의 개수를 K라고 하겠습니다.
K는 최대 6입니다.

DFS
카드 제거 순서를 정하는 경우는 최대 K!개입니다.
각 카드 쌍은 첫 번째 -> 두 번째, 두 번째 -> 첫 번째
두 가지 순서가 있으므로 최대 탐색 경우는 대략 K! * 2^K입니다.

K = 6일 때 6! * 2^6 = 720 * 64 = 46,080 정도로 충분히 작습니다.


BFS
보드의 칸은 4 x 4 = 16개뿐입니다.
각 칸에서 일반 이동 4개와 Ctrl 이동 4개를 확인하므로 BFS 하나는 사실상 상수 시간입니다.
따라서 전체적으로 충분히 빠르게 해결할 수 있습니다.


공간복잡도
board는 항상 4 x 4입니다.
BFS의 dist 역시 4 x 4입니다.
DFS의 최대 깊이는 카드 종류의 개수인 6입니다.

따라서 입력 제한에서는 매우 작은 공간만 사용합니다.


핵심 정리

1. 카드 종류를 어떤 순서로 제거할지는 DFS로 탐색합니다.
2. 같은 카드 두 장도 A -> B, B -> A 두 가지 순서를 모두 확인합니다.
3. 두 위치 사이의 최소 이동 횟수는 BFS로 구합니다.
4. BFS에서는 일반 방향키 4개와 Ctrl + 방향키 4개를 모두 이동 방법으로 사용합니다.
5. Ctrl 이동은 해당 방향의 가장 가까운 카드에서 멈추고, 카드가 없다면 가장 끝 칸에서 멈춥니다.
6. 카드 한 쌍을 선택하는 Enter 입력 2회도 반드시 비용에 더해야 합니다.
7. 카드를 제거하면 이후 Ctrl 이동 결과가 달라지므로 board에서 실제로 0으로 변경한 뒤 다음 DFS를 수행합니다.
8. 백트래킹이 끝나면 제거한 카드를 다시 복구합니다.
*/
