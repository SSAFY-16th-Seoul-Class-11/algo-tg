/*
문제 정의

N개의 정점과 M개의 간선으로 이루어진 가중치 없는 무방향 그래프가 주어집니다.

경로를 만들 때 같은 정점을 두 번 이상 방문할 수 없습니다.
경로의 길이는 지나간 간선의 개수가 아니라 경로에 포함된 정점의 개수입니다.

예를 들어 1 -> 2 -> 4 -> 5라는 경로가 있다면 경로의 길이는 4입니다.
그래프에서 만들 수 있는 모든 경로 중 가장 긴 경로의 길이를 구하는 문제입니다.
*/


/*
접근 방법

DFS + 백트래킹을 사용합니다.
현재 정점에서 연결된 정점 중 아직 방문하지 않은 정점으로 이동하면서 경로를 계속 확장합니다.

visited[i] = true이면 현재 경로에서 이미 i번 정점을 사용했다는 의미입니다.
다음 정점으로 이동할 때 visited[next] = true로 방문 처리하고 DFS를 수행합니다.
해당 탐색이 끝난 뒤에는 visited[next] = false로 다시 되돌립니다.

중요한 점은 최장 경로가 어느 정점에서 시작하는지 알 수 없다는 것입니다.
따라서 1번 정점에서 시작, 2번 정점에서 시작, ... N번 정점에서 시작하는 모든 경우를 확인해야 합니다.

또한 같은 두 정점 사이에 여러 간선이 존재할 수 있습니다.
하지만 이 문제에서는 간선의 개수가 중요한 것이 아니라 두 정점이 연결되어 있는지만 중요합니다.
따라서 boolean[][] 형태의 인접 행렬을 사용하면 중복 간선을 자연스럽게 하나의 연결로 처리할 수 있습니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    static int N;
    static int answer;

    /*
    graph[a][b] = true이면 a와 b가 연결되어 있다는 의미입니다.
    */
    static boolean[][] graph;

    /*
    현재 경로에서 방문한 정점을 표시합니다.
    */
    static boolean[] visited;

    /*
    current : 현재 위치한 정점
    length  : 현재까지 경로에 포함된 정점의 개수
    */
    static void dfs(int current, int length) {

        /*
        현재까지 만들어진 경로의 길이로 최댓값을 갱신합니다.
        */
        answer = Math.max(answer, length);

        /*
        현재 정점과 연결된 모든 정점을 확인합니다.
        */
        for (int next = 1; next <= N; next++) {

            /*
            연결되어 있지 않으면 이동할 수 없습니다.
            */
            if (!graph[current][next]) {
                continue;
            }

            /*
            현재 경로에서 이미 방문한 정점이면 다시 방문할 수 없습니다.
            */
            if (visited[next]) {
                continue;
            }

            /*
            next 정점을 현재 경로에 포함시킵니다.
            */
            visited[next] = true;
            dfs(next, length + 1);

            /*
            다른 경로에서도 next를 사용할 수 있도록 방문 처리를 되돌립니다.
            */
            visited[next] = false;
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
            int M = Integer.parseInt(st.nextToken());

            /*
            인접 행렬을 생성합니다.
            */
            graph = new boolean[N + 1][N + 1];

            /*
            무방향 그래프이므로 양쪽 방향을 모두 연결합니다.
            x - y가 주어진다면
            graph[x][y] = true
            graph[y][x] = true로 저장합니다.
            */
            for (int i = 0; i < M; i++) {

                st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                graph[x][y] = true;
                graph[y][x] = true;
            }
            answer = 0;
            /*
            최장 경로의 시작점이 어디인지 알 수 없으므로 모든 정점을 시작점으로 DFS를 수행합니다.
            */
            for (int start = 1; start <= N; start++) {

                visited = new boolean[N + 1];

                /*
                시작 정점도 경로에 포함되므로 방문 처리합니다.
                */
                visited[start] = true;

                /*
                시작 정점 하나가 이미 경로에 포함되어 있으므로 length = 1부터 시작합니다.
                */
                dfs(start, 1);
            }

            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
시간복잡도

정점의 개수를 N이라고 하겠습니다.
각 DFS에서는 방문하지 않은 정점을 선택하면서 만들 수 있는 모든 단순 경로를 탐색합니다.
최악의 경우 모든 정점이 서로 연결되어 있다면 가능한 정점 방문 순서를 탐색하므로 시간복잡도는 대략 O(N!)까지 증가할 수 있습니다.


핵심 정리
1. 같은 정점을 두 번 방문할 수 없으므로 visited 배열을 사용합니다.
2. DFS로 방문하지 않은 인접 정점을 계속 선택합니다.
3. 다음 정점을 방문한 뒤 DFS가 끝나면 visited를 false로 되돌리는 백트래킹을 수행합니다.
4. 최장 경로가 어디서 시작하는지 모르므로 1번부터 N번까지 모든 정점을 시작점으로 DFS합니다.
5. 경로의 길이는 간선 개수가 아니라 정점 개수이므로 시작할 때 length = 1입니다.
6. 중복 간선이 존재할 수 있으므로 boolean 인접 행렬을 사용하면 중복을 신경 쓰지 않아도 됩니다.
7. N <= 10이므로 모든 단순 경로를 탐색하는 완전탐색이 가능합니다.
*/
