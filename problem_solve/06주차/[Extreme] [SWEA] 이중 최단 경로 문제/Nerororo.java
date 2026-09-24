import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Solution
{
    static int N, min;
    static boolean[] visited;
    static List<int[]>[] list;

    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            min = Integer.MAX_VALUE;
            visited = new boolean[N + 1];

            list = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++) {
                list[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int xWeight = Integer.parseInt(st.nextToken());
                int yWeight = Integer.parseInt(st.nextToken());

                list[from].add(new int[] {to, xWeight, yWeight});
                list[to].add(new int[] {from, xWeight, yWeight});
            }

            calcMinWeight(0, 1, 0, 0);

            sb.append('#')
                    .append(test_case)
                    .append(' ')
                    .append(min != Integer.MAX_VALUE ? min : -1)
                    .append('\n');
        }
        System.out.println(sb.toString());
    }

    public static void calcMinWeight(int len, int index, int x, int y) {
        if (x * y > min) return;
        if (index == 2) min = Math.min(min, x * y);
        if (len == N - 1) return;

        for (int[] arr : list[index]) {
            if (visited[arr[0]]) continue;

            visited[arr[0]] = true;
            calcMinWeight(len + 1, arr[0], x + arr[1], y + arr[2]);
            visited[arr[0]] = false;
        }
    }
}

/*
다익스트라가 어려워서.. 다 잘 못풀었는데.. dfs로 접근 가능한 문제라 쉽게 푼 듯 하다
위 코드의 시간 복잡도는 O(N!)으로 예상 된다.
다익스트라로 접근 하기엔 최소 경로를 매번 x의 합과 y의 합으로 구해야해서 복잡하길래...
N의 개수도 적겠다 완탐으로 풀자 > dfs 백트래킹!! 의 방식으로 접근했다.
 */