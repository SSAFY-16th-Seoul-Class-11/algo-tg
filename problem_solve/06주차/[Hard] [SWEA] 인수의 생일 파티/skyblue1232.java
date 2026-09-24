import java.io.*;
import java.util.*;

public class Solution {
    private static int n, m, x;
    private static List<int[]>[] graph;
    private static List<int[]>[] reverseGraph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            readGraph(br);
            int[] fromParty = dijkstra(graph);
            int[] toParty = dijkstra(reverseGraph);
            int maxTime = getMaxRoundTripTime(fromParty, toParty);

            answer.append("#").append(tc).append(" ").append(maxTime).append("\n");
        }

        System.out.print(answer);
    }

    private static void readGraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        graph = new ArrayList[n + 1];
        reverseGraph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            graph[from].add(new int[]{to, time});
            reverseGraph[to].add(new int[]{from, time});
        }
    }

    private static int[] dijkstra(List<int[]>[] targetGraph) {
        int[] distance = new int[n + 1];
        Arrays.fill(distance, -1);

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // 누적 시간, 현재 정점
        pq.offer(new int[]{0, x});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();

            int currentTime = current[0];
            int currentNode = current[1];

            // 먼저 꺼낸 경로가 가장 짧으니까 이미 방문한 정점은 건너뛰기
            if (distance[currentNode] != -1) continue;

            distance[currentNode] = currentTime;

            for (int[] next : targetGraph[currentNode]) {
                int nextNode = next[0];
                int nextTime = currentTime + next[1];

                pq.offer(new int[]{nextTime, nextNode});
            }
        }

        return distance;
    }

    private static int getMaxRoundTripTime(int[] fromParty, int[] toParty) {
        int maxTime = 0;

        for (int i = 1; i <= n; i++) {
            int roundTripTime = fromParty[i] + toParty[i];
            maxTime = Math.max(maxTime, roundTripTime);
        }

        return maxTime;
    }

    /*
     * [메모]
     * 왕복 거리라서 X -> 집(graph), 집 -> X (reverseGraph) 최단거리 -> 다익스트라 2개 쓰자.
     * 집마다 X까지 다익스트라를 돌리면 반복이 많아서 원래 그래프와 방향을 뒤집은 그래프를 따로 만들어서 X에서 각각 한 번씩 돌림
     * PriorityQueue에는 {누적 시간, 정점}을 넣어서 짧은 경로부터 확인하고,
     * 같은 정점이 여러 번 들어갈 수 있으니까 처음 꺼낸 거리만 최단거리로 확정하고
     * 마지막에는 두 거리의 합이 가장 큰 값
     */
}
