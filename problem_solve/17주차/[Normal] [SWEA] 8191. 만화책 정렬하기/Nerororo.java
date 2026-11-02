
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            int N = Integer.parseInt(br.readLine());

            // 직전 수가 false면 cnt++;
            int cnt = 0;
            boolean[] prevNumExisted = new boolean[N + 1];

            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                int curr = Integer.parseInt(st.nextToken());

                if (!prevNumExisted[curr - 1]) cnt++;
                prevNumExisted[curr] = true;
            }

            sb.append(cnt).append('\n');
        }
        System.out.print(sb.toString());
    }
}

/*
시간 복잡도 O(n) < 입력과 동시에 끝
 */