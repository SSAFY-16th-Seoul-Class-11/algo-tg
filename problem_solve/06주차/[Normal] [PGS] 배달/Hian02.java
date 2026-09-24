/* 
문제 정의
N개의 마을로 이루어진 나라가 있습니다.
각 마을은 양방향으로 통행할 수 있는 도로로 연결되어 있습니다.
N개의 마을 중에서 K 시간 이하로 배달이 가능한 마을에서만 주문을 받으려고 합니다.

마을의 개수 N, 각 마을을 연결하는 도로의 정보 road, 음식 배달이 가능한 시간 K가 매개변수로 주어질 때, 
1번 마을에서 음식 주문을 받을 수 있는 마을의 개수를 return

풀이 과정
1번 마을에서 i번 마을까지 가는 최소 시간을 각각 구해서 저장해보자.

다익스트라 알고리즘 쓰자
1. 각 마을의 도로 정보를 인접 리스트에 저장합니다.
   - 도로는 양방향이므로 양쪽 모두 저장합니다.
2. dist[i]에 1번 마을에서 i번 마을까지의 최소 시간을 저장합니다.
3. PriorityQueue를 사용하여
   현재까지 이동 시간이 가장 짧은 마을부터 탐색합니다.
4. 현재 마을을 거쳐서 다음 마을로 가는 시간이
   기존에 저장된 시간보다 짧다면 dist 값을 갱신합니다.
5. 다익스트라가 끝난 후
   dist[i] <= K인 마을의 개수를 세어 반환합니다.
*/

/* 
문제 풀이
*/

import java.util.*;

class Solution {
    public int solution(int N, int[][] road, int K) {
        // graph[i] : i번 마을과 연결된 도로 정보
        // int[] = {도착 마을, 이동 시간}
        ArrayList<int[]>[] graph = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        // 도로 정보 저장
        // 도로는 양방향이므로 양쪽 모두 저장
        for (int[] r : road) {
            int start = r[0];
            int end = r[1];
            int time = r[2];
            graph[start].add(new int[]{end, time});
            graph[end].add(new int[]{start, time});
        }
        // dist[i] = 1번 마을에서 i번 마을까지의 최소 시간
        int[] dist = new int[N + 1];
        // 처음에는 모든 마을까지의 거리를 매우 큰 값으로 설정
        Arrays.fill(dist, Integer.MAX_VALUE);

        // PriorityQueue
        // int[] = {마을 번호, 현재까지 걸린 시간}
        // 시간이 짧은 것부터 꺼냄
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        // 1번 마을에서 시작
        dist[1] = 0;
        pq.offer(new int[]{1, 0});
        // 다익스트라
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int now = current[0];
            int cost = current[1];
            // 이미 더 짧은 경로가 있다면 탐색하지 않음
            if (cost > dist[now]) {
                continue;
            }

            // 현재 마을과 연결된 모든 도로 확인
            for (int[] next : graph[now]) {
                int nextNode = next[0];
                int nextCost = cost + next[1];
                // 현재 마을을 거쳐가는 것이 더 빠른 경우
                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
                }
            }
        }
        // K 시간 이하로 배달 가능한 마을 개수
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            if (dist[i] <= K) {
                answer++;
            }
        }

        return answer;
    }
}


/* 
시간복잡도
N : 마을의 개수
M : 도로의 개수 (road.length)
인접 리스트에 모든 도로를 저장하므로 O(N + M)
최단 거리를 저장하는 dist 배열이 O(N)
*/
