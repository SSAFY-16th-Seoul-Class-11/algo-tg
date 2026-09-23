
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution
{
    static long[] dp, factorial;
    static final int REMAINDER = 1_000_000_007;

    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        factorial = new long[1001];
        factorial[0] = 1;
        for (int i = 1; i <= 1000; i++) {
            factorial[i] = (factorial[i - 1] * i) % REMAINDER;
        }

        dp = new long[1001];
        for (int i = 1; i <= 1000; i++) {
            dp[i] = (factorial[i - 1] * (i / 2) + i * dp[i - 1]) % REMAINDER;
        }

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            int N = Integer.parseInt(br.readLine());

            sb.append(dp[N]).append('\n');
        }

        System.out.print(sb.toString());
    }
}

/*
시간복잡도
factorial, dp 배열 생성 비용 > N의 최대치인 1000번을 2번 반복
이후 찾는건 O(1)로 가능하다.
O(2n) > O(N)
 */
