/*
문제 정의

n개의 지점이 있고 각 지점은 양방향 택시 경로로 연결되어 있습니다.
A와 B는 출발지 s에서 함께 출발하고,
A는 a 지점, B는 b 지점으로 이동해야 합니다.

중간까지 택시를 합승한 뒤 각자의 목적지로 이동할 수도 있고,
처음부터 합승하지 않고 따로 이동할 수도 있습니다.

두 사람이 목적지까지 이동하는 데 필요한 최소 택시 요금은?
*/


/*
접근 방법

두 사람이 어떤 지점까지 같이 이동한 후
그 지점에서 헤어진다고 생각합니다.

예를 들어 k번 지점에서 헤어진다면 총 비용은
s → k : 두 사람이 합승하는 비용
k → a : A가 혼자 이동하는 비용
k → b : B가 혼자 이동하는 비용

따라서 총 비용은
dist[s][k] + dist[k][a] + dist[k][b]

문제는 k가 어디인지 모르기 때문에
1번부터 n번까지 모든 지점을 확인해야 합니다.
-> 먼저 모든 지점 사이의 최단 거리를 구하자!
-> 플로이드-워셜 알고리즘을 사용하자

1. dist[i][j]에 i번 지점에서 j번 지점까지의 최소 비용을 저장합니다.
2. fares의 도로 정보를 dist에 저장합니다.
3. 플로이드-워셜로 모든 지점 사이의 최단 거리를 구합니다.
4. 모든 지점 k를 합승 종료 지점으로 생각하여 최솟값을 구합니다.
*/


/*
문제 풀이
*/

import java.util.*;
class Solution {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int INF = 100000000;
        int[][] dist = new int[n + 1][n + 1];

        // 모든 거리를 큰 값으로 초기화
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], INF);

            // 자기 자신까지의 거리는 0
            dist[i][i] = 0;
        }

        // 택시 요금 정보 저장
        // 양방향이므로 양쪽 모두 저장
        for (int[] fare : fares) {
            int start = fare[0];
            int end = fare[1];
            int cost = fare[2];
            dist[start][end] = cost;
            dist[end][start] = cost;
        }

        // 플로이드-워셜
        // k : 중간에 거쳐가는 지점
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dist[i][j] = Math.min(dist[i][j],dist[i][k] + dist[k][j]);
                }
            }
        }
        int answer = INF;
        // k번 지점까지 합승한다고 가정
        for (int k = 1; k <= n; k++) {
            int cost = dist[s][k]+ dist[k][a]+ dist[k][b];
            answer = Math.min(answer, cost);
        }
        return answer;
    }
}


/*
시간복잡도
n : 지점의 개수
플로이드-워셜에서 3중 반복문을 사용합니다.
따라서 시간복잡도는 O(n^3)
*/
