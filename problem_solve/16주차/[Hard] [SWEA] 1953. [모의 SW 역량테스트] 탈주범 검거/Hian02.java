/*
문제 정의

지하 터널 지도에서 탈주범이 특정 위치 (R, C)에서 시작합니다.
탈주범은 1시간마다 현재 터널과 연결된 인접 터널로 이동할 수 있습니다.

터널 구조물은 1번부터 7번까지 있으며 구조물마다 이동 가능한 방향이 다릅니다.

주어진 시간 L 동안 탈주범이 위치할 수 있는 장소의 개수를 구하는 문제입니다.

주의할 점은 탈주범이 처음 위치한 장소도 1시간째에 포함된다는 것입니다.
따라서 L = 1이면 시작 위치만 방문할 수 있으므로 정답은 1입니다.
*/


/*
접근 방법

BFS를 사용합니다.
각 터널 타입마다 이동할 수 있는 방향을 저장합니다.

방향은 다음과 같이 정의합니다.
0 : 상
1 : 하
2 : 좌
3 : 우
터널별 이동 가능 방향은 다음과 같습니다.

1번 : 상, 하, 좌, 우
2번 : 상, 하
3번 : 좌, 우
4번 : 상, 우
5번 : 하, 우
6번 : 하, 좌
7번 : 상, 좌

하지만 현재 터널에서 어떤 방향으로 이동할 수 있다고 해서 바로 다음 칸으로 이동할 수 있는 것은 아닙니다.
예를 들어 현재 터널이 오른쪽으로 연결되어 있다면 다음 칸의 터널에는 반드시 왼쪽 연결이 존재해야 합니다.
따라서 이동 조건은 다음과 같습니다.

1. 다음 위치가 지도 범위 안이어야 합니다.
2. 다음 위치에 터널이 존재해야 합니다.
3. 아직 방문하지 않은 위치여야 합니다.
4. 현재 터널이 해당 방향으로 연결되어 있어야 합니다.
5. 다음 터널이 반대 방향으로 연결되어 있어야 합니다.
6. 이동 후 시간이 L을 넘으면 안 됩니다.

BFS에서 시작 위치의 시간을 1로 설정하면 다음 위치는 현재 시간 + 1이 됩니다.
시간이 L인 위치에서는 더 이상 다음 위치로 이동하지 않습니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    static int N, M, R, C, L;
    static int[][] map;
    static boolean[][] visited;

    /*
    방향
    0 : 상
    1 : 하
    2 : 좌
    3 : 우
    */
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    /*
    tunnel[type][direction]

    true이면 해당 터널 타입이 해당 방향으로 연결되어 있다는 의미입니다.
    */
    static boolean[][] tunnel = new boolean[8][4];


    /*
    방향의 반대 방향을 반환합니다.

    상 <-> 하
    좌 <-> 우
    */
    static int opposite(int dir) {

        if (dir == 0) return 1;
        if (dir == 1) return 0;
        if (dir == 2) return 3;

        return 2;
    }


    /*
    터널 타입별로 이동 가능한 방향을 설정합니다.
    */
    static void initTunnel() {

        tunnel = new boolean[8][4];

        /*
        1번 : 상, 하, 좌, 우
        */
        tunnel[1][0] = true;
        tunnel[1][1] = true;
        tunnel[1][2] = true;
        tunnel[1][3] = true;

        /*
        2번 : 상, 하
        */
        tunnel[2][0] = true;
        tunnel[2][1] = true;

        /*
        3번 : 좌, 우
        */
        tunnel[3][2] = true;
        tunnel[3][3] = true;

        /*
        4번 : 상, 우
        */
        tunnel[4][0] = true;
        tunnel[4][3] = true;

        /*
        5번 : 하, 우
        */
        tunnel[5][1] = true;
        tunnel[5][3] = true;

        /*
        6번 : 하, 좌
        */
        tunnel[6][1] = true;
        tunnel[6][2] = true;

        /*
        7번 : 상, 좌
        */
        tunnel[7][0] = true;
        tunnel[7][2] = true;
    }

    /*
    BFS에 사용할 위치 정보입니다.

    r    : 행
    c    : 열
    time : 해당 위치에 도착한 시간
    */
    static class Node {

        int r;
        int c;
        int time;

        Node(int r, int c, int time) {
            this.r = r;
            this.c = c;
            this.time = time;
        }
    }

    static int bfs() {

        Queue<Node> queue = new ArrayDeque<>();

        visited = new boolean[N][M];

        /*
        시작 위치는 1시간째에 있는 것으로 처리합니다.
        */
        queue.offer(new Node(R, C, 1));
        visited[R][C] = true;

        /*
        방문 가능한 위치의 개수입니다.
        시작 위치도 포함하므로 1부터 시작합니다.
        */
        int count = 1;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            /*
            현재 시간이 이미 L이라면 더 이상 이동할 수 없습니다.
            */
            if (current.time == L) {
                continue;
            }

            int currentType = map[current.r][current.c];
            /*
            상하좌우를 확인합니다.
            */
            for (int dir = 0; dir < 4; dir++) {
                /*
                현재 터널이 해당 방향으로 연결되어 있지 않으면 이동할 수 없습니다.
                */
                if (!tunnel[currentType][dir]) {
                    continue;
                }

                int nr = current.r + dr[dir];
                int nc = current.c + dc[dir];
                /*
                지도 범위를 벗어나면 이동할 수 없습니다.
                */
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    continue;
                }
                /*
                터널이 없는 위치이면 이동할 수 없습니다.
                */
                if (map[nr][nc] == 0) {
                    continue;
                }
                /*
                이미 방문한 위치라면 다시 방문할 필요가 없습니다.
                */
                if (visited[nr][nc]) {
                    continue;
                }
                int nextType = map[nr][nc];
                /*
                다음 터널이 반대 방향으로 연결되어 있어야 합니다.

                예를 들어 현재 위치에서 오른쪽으로 이동한다면
                다음 터널에는 왼쪽 연결이 있어야 합니다.
                */
                int reverseDir = opposite(dir);

                if (!tunnel[nextType][reverseDir]) {
                    continue;
                }
                /*
                모든 이동 조건을 만족하면 다음 위치를 방문합니다.
                */
                visited[nr][nc] = true;
                queue.offer(new Node(nr,nc,current.time + 1));
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        /*
        터널 타입별 연결 방향을 미리 설정합니다.
        */
        initTunnel();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            map = new int[N][M];

            /*
            지도 입력
            */
            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < M; c++) {
                    map[r][c] = Integer.parseInt(st.nextToken());
                }
            }
            int answer = bfs();
            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
핵심 정리

1. 탈주범의 이동은 시간 순서대로 퍼져나가므로 BFS를 사용합니다.
2. 시작 위치는 1시간째이므로 time = 1부터 시작합니다.
3. L = 1이면 시작 위치만 포함됩니다.
4. 현재 터널에서 이동 가능한 방향인지 먼저 확인합니다.
5. 다음 터널도 반드시 반대 방향으로 연결되어 있어야 합니다.
6. 현재 터널의 연결만 확인하면 안 됩니다.
7. 방문한 위치는 다시 방문할 필요가 없으므로 visited를 사용합니다.
8. 현재 시간이 L이면 해당 위치까지는 포함하지만 더 이상 이동하지 않습니다.
*/
