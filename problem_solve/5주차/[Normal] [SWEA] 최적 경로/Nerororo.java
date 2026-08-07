import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    static int min, N;
    static int[][] arr;
    static boolean[] visited;

    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            N = Integer.parseInt(br.readLine());
            min = Integer.MAX_VALUE;
            // 회사, 집, 각 고객의 위치(y, x)
            arr = new int[N + 2][2];
            visited = new boolean[N + 2];
            // 입력
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N + 2; i++) {
                // tc는 xy순으로 제공
                arr[i][1] = Integer.parseInt(st.nextToken());
                arr[i][0] = Integer.parseInt(st.nextToken());
            }

            shortWay(0, 0, 0);

            sb.append(min).append('\n');
        }

        System.out.println(sb);
    }

    // 이번 루트의 걸어온 거리, 거쳐간 횟수, 내 현재 위치 정보
    public static void shortWay(int len, int cnt, int curr) {
        // 집 도착
        if (cnt == N + 1) {
            min = Math.min(min, len);
        }

        // 가지치기
        if (len >= min) return;

        // 집으로 가야한다면
        if (cnt == N) {
            shortWay(len + abs(curr, 1), cnt + 1, 1);
        }

        // 회사에서 출발한다면
        if (cnt == 0) {
            for (int i = 2; i < N + 2; i++) {
                visited[i] = true;
                shortWay(abs(0, i), 1, i);
                visited[i] = false;
            }
        }

        for (int i = 2; i < N + 2; i++) {
            if (visited[i]) continue;

            visited[i] = true;
            shortWay(len + abs(curr, i), cnt + 1, i);
            visited[i] = false;
        }
    }

    // 거리계산
    public static int abs(int curr, int next) {
        return Math.abs(arr[curr][0] - arr[next][0]) + Math.abs(arr[curr][1] - arr[next][1]);
    }
}