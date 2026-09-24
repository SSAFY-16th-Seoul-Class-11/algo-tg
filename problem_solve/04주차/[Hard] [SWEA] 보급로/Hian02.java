import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int T = 1; T <= T; T++) {
            int N = Integer.parseInt(br.readLine());

            int[][] map = new int[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();

                for (int j = 0; j < N; j++) {
                    map[i][j] = line.charAt(j) - '0';
                }
            }

            int[][] distance = new int[N][N];

            for (int i = 0; i < N; i++) {
                Arrays.fill(distance[i], Integer.MAX_VALUE);
            }

            // {현재까지의 복구 시간, 행, 열}
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

            distance[0][0] = 0;
            pq.offer(new int[]{0, 0, 0});

            while (!pq.isEmpty()) {
                int[] current = pq.poll();

                int currentCost = current[0];
                int row = current[1];
                int col = current[2];

                // 이미 더 짧은 경로가 존재하는 경우
                if (currentCost > distance[row][col]) {
                    continue;
                }

                // 목적지에 도착
                if (row == N - 1 && col == N - 1) {
                    break;
                }

                for (int direction = 0; direction < 4; direction++) {
                    int nextRow = row + dr[direction];
                    int nextCol = col + dc[direction];

                    if (nextRow < 0 || nextRow >= N
                            || nextCol < 0 || nextCol >= N) {
                        continue;
                    }

                    int nextCost = currentCost + map[nextRow][nextCol];

                    if (nextCost < distance[nextRow][nextCol]) {
                        distance[nextRow][nextCol] = nextCost;

                        pq.offer(new int[]{
                                nextCost,
                                nextRow,
                                nextCol
                        });
                    }
                }
            }

            System.out.println(
                    "#" + T + " " + distance[N - 1][N - 1]
            );
        }
    }
}
