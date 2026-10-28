/*
문제 정의

N x N 지도에서 가장 높은 봉우리 중 하나에서 시작하여 최대한 긴 등산로를 만들어야 합니다.
등산로는 현재 위치보다 높이가 낮은 지형으로만 이동할 수 있습니다.

단, 전체 탐색 중 딱 한 번만 하나의 지형을 최대 K만큼 깎을 수 있습니다.
깎지 않아도 되며, 깎은 뒤의 높이가 현재 위치보다 낮아야 이동할 수 있습니다.

만들 수 있는 가장 긴 등산로의 길이를 구합니다.
*/


/*
접근 방법

DFS + 백트래킹을 사용합니다.

1. 지도에서 가장 높은 높이를 찾습니다.
2. 최고 높이를 가진 모든 위치를 출발점으로 DFS를 수행합니다.
3. 다음 칸의 높이가 현재 칸보다 낮으면 그대로 이동합니다.
4. 다음 칸이 현재 칸보다 높거나 같고 아직 공사를 사용하지 않았다면 공사가 가능한지 확인합니다.
5. nextHeight - K < currentHeight라면 공사를 통해 이동할 수 있습니다.
6. 이때 1부터 K까지 모든 깊이를 시도할 필요가 없습니다.
7. 다음 칸을 currentHeight - 1로 만드는 최소 공사만 수행하면 됩니다.

예를 들어 현재 높이가 5이고 다음 높이가 7이라면 다음 칸을 4로 만들 수 있을 정도의 공사만 하면 됩니다.
필요한 공사량은 nextHeight - currentHeight + 1입니다.
이 값이 K 이하라면 공사가 가능합니다.

DFS가 끝나면 지도의 높이와 visited를 원래 상태로 복구합니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    static int N, K;
    static int[][] map;
    static boolean[][] visited;

    static int answer;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    /*
    r, c       : 현재 위치
    length     : 현재까지 등산로 길이
    usedCut    : 공사를 이미 사용했는지 여부
    */
    static void dfs(int r, int c, int length, boolean usedCut) {
        answer = Math.max(answer, length);


        for (int d = 0; d < 4; d++) {

            int nr = r + dr[d];
            int nc = c + dc[d];

            /*
            지도 범위를 벗어나면 이동할 수 없습니다.
            */
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                continue;
            }

            /*
            현재 경로에서 이미 방문한 곳이면 이동할 수 없습니다.
            */
            if (visited[nr][nc]) {
                continue;
            }

            /*
            다음 위치가 현재 위치보다 낮다면 공사 없이 이동합니다.
            */
            if (map[nr][nc] < map[r][c]) {
                visited[nr][nc] = true;
                dfs(nr,nc,length + 1,usedCut);
                visited[nr][nc] = false;
            }

            /*
            다음 위치가 현재보다 높거나 같고 아직 공사를 사용하지 않았다면 공사를 시도합니다.
            */
            else if (!usedCut) {
                /*
                현재보다 정확히 1 낮게 만들기 위해 필요한 공사량입니다.
                예)현재 = 5 다음 = 7
                7 -> 4로 만들어야 하므로 3만큼 깎아야 합니다.
                7 - 5 + 1 = 3
                */
                int cut = map[nr][nc] - map[r][c] + 1;

                /*
                필요한 공사량이 K 이하이면 이동할 수 있습니다.
                */
                if (cut <= K) {

                    /*
                    DFS가 끝난 뒤 원상복구하기 위해 원래 높이를 저장합니다.
                    */
                    int originalHeight = map[nr][nc];

                    /*
                    가능한 높이 중 가장 높은 값인 현재 높이 - 1로 만듭니다.
                    */
                    map[nr][nc] = map[r][c] - 1;
                    visited[nr][nc] = true;

                    /*
                    공사를 사용했으므로 usedCut = true로 변경합니다.
                    */
                    dfs(nr,nc,length + 1,true);
                    /*
                    백트래킹
                    */
                    visited[nr][nc] = false;
                    map[nr][nc] = originalHeight;
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            int maxHeight = 0;

            /*
            지도를 입력받으면서 최고 높이를 찾습니다.
            */
            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    map[r][c] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max( maxHeight,map[r][c]);
                }
            }
            answer = 0;

            /*
            최고 높이인 모든 위치를 출발점으로 사용합니다.
            */
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (map[r][c] != maxHeight) {
                        continue;
                    }
                    visited = new boolean[N][N];
                    visited[r][c] = true;
                    /*
                    시작 위치도 등산로에 포함되므로 길이는 1부터 시작합니다.
                    */
                    dfs(r,c,1,false);
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
핵심 정리

1. 최고봉에서만 DFS를 시작합니다.
2. 낮은 지형이면 그대로 이동합니다.
3. 높은 지형을 만났고 아직 공사를 사용하지 않았다면 공사를 고려합니다.
4. map[next] - K < map[current]이면 공사를 통해 이동할 수 있습니다.
5. 1부터 K까지 모두 깎아볼 필요가 없습니다.
6. 이동 가능하게 만드는 가장 높은 높이인 map[current] - 1까지만 깎으면 됩니다.
7. 필요한 공사량은 map[next] - map[current] + 1입니다.
8. DFS가 끝난 뒤 높이와 visited를 반드시 복구합니다.
*/
