/*
문제 정의

N명의 직원과 N개의 일이 있습니다.
각 직원은 정확히 하나의 일을 맡고, 각 일도 정확히 한 명의 직원에게 배정해야 합니다.

i번 직원이 j번 일을 성공할 확률 P[i][j]가 주어질 때 모든 직원이 맡은 일을 성공할 확률의 최댓값을 구해야 합니다.

예를 들어 세 직원이 각각 성공 확률 0.13, 0.70, 1.00인 일을 맡았다면 모든 일이 성공할 확률은 0.13 * 0.70 * 1.00입니다.

입력은 퍼센트 단위이므로 계산할 때 100으로 나누고, 마지막 출력할 때 다시 100을 곱합니다.
*/


/*
접근 방법

비트마스킹 DP를 사용합니다.

mask는 현재까지 어떤 일이 배정되었는지를 나타냅니다.
예를 들어 N = 4일 때 mask = 0101이라면 0번 일과 2번 일이 이미 배정된 상태입니다.
현재까지 배정된 일의 개수는 Integer.bitCount(mask)로 구할 수 있습니다.
직원은 0번 직원부터 순서대로 배정하므로 employee = Integer.bitCount(mask)가 현재 일을 배정할 직원 번호가 됩니다.

dp[mask] = mask에 표시된 일들을 앞의 직원들에게 배정했을 때 얻을 수 있는 최대 성공 확률로 정의합니다.
초기 상태에서는 아무 일도 배정하지 않았으므로 dp[0] = 1.0입니다.

현재 직원 employee에게 아직 선택되지 않은 job을 배정하면 nextMask = mask | (1 << job)이 되고 새로운 성공 확률은 dp[mask] * probability[employee][job]입니다.
따라서 dp[nextMask] = max(dp[nextMask], dp[mask] * probability[employee][job])으로 갱신합니다.
모든 일이 배정된 mask = (1 << N) - 1의 dp 값이 최대 성공 확률입니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());


        for (int tc = 1; tc <= T; tc++) {

            int N = Integer.parseInt(br.readLine());
            /*
            probability[i][j] = i번 직원이 j번 일을 성공할 확률
            입력은 0 ~ 100의 퍼센트 값이므로 계산하기 편하도록 0.0 ~ 1.0으로 변환합니다.
            */
            double[][] probability = new double[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    probability[i][j] = Integer.parseInt(st.nextToken()) / 100.0;
                }
            }

            /*
            N개의 일을 비트마스크로 표현하므로 전체 상태의 개수는 2^N개입니다.
            */
            int size = 1 << N;

            /*
            dp[mask] = mask에 포함된 일을 배정했을 때 얻을 수 있는 최대 성공 확률
            */
            double[] dp = new double[size];

            /*
            아직 아무 일도 맡지 않은 상태의 성공 확률은 1입니다.
            */
            dp[0] = 1.0;

            /*
            모든 상태를 확인합니다.
            */
            for (int mask = 0; mask < size; mask++) {
                /*
                현재까지 배정한 일의 개수가 현재 배정할 직원 번호가 됩니다.
                예)
                3개의 일이 이미 배정됨
                -> 0, 1, 2번 직원까지 배정 완료
                -> 현재 3번 직원에게 일을 배정
                */
                int employee = Integer.bitCount(mask);

                /*
                모든 직원을 이미 배정했다면 더 이상 진행하지 않습니다.
                */
                if (employee == N) {
                    continue;
                }

                /*
                현재 상태에 도달하지 못했다면 확인할 필요가 없습니다.
                */
                if (dp[mask] == 0.0) {
                    continue;
                }

                /*
                현재 직원에게 아직 배정되지 않은 일을 하나 선택합니다.
                */
                for (int job = 0; job < N; job++) {

                    /*
                    job번 일이 이미 선택된 경우입니다.
                    */
                    if ((mask & (1 << job)) != 0) {
                        continue;
                    }

                    /*
                    현재 job을 추가한 새로운 상태입니다.
                    */
                    int nextMask = mask | (1 << job);

                    /*
                    현재 직원이 job번 일을 맡았을 때 모든 일이 성공할 확률입니다.
                    */
                    double nextProbability = dp[mask] * probability[employee][job];

                    /*
                    더 높은 성공 확률을 발견하면 갱신합니다.
                    */
                    dp[nextMask] = Math.max(dp[nextMask],nextProbability);
                }
            }

            /*
            모든 일이 선택된 상태입니다.
            예)
            N = 4
            1 << 4 = 10000
            (1 << 4) - 1 = 01111
            따라서 모든 비트가 1인 상태가 됩니다.
            */
            double answer = dp[size - 1] * 100.0;

            /*
            퍼센트 단위로 소수점 아래 6자리까지 출력합니다.
            printf의 %.6f와 동일하게 7번째 자리에서 반올림됩니다.
            */
            sb.append("#").append(tc).append(" ").append(String.format(Locale.US, "%.6f", answer)).append('\n');
        }
        System.out.print(sb);
    }
}


/*
시간복잡도

비트마스크의 상태 개수는 2^N개입니다.
각 상태마다 최대 N개의 일을 확인합니다.
따라서 시간복잡도는 O(N * 2^N)입니다.


공간복잡도

dp 배열의 크기는 2^N이므로 O(2^N)입니다.
성공 확률을 저장하는 probability 배열은 O(N^2)입니다.
따라서 전체 공간복잡도는 O(2^N + N^2)입니다.


핵심 정리

1. 한 직원에게 하나의 일, 한 일에는 한 직원만 배정해야 합니다.
2. 직원을 0번부터 순서대로 배정하면 현재 직원 번호는 Integer.bitCount(mask)로 알 수 있습니다.
3. mask는 이미 배정된 일의 집합을 나타냅니다.
4. dp[mask]는 현재까지의 최대 성공 확률입니다.
5. 아직 선택되지 않은 일을 현재 직원에게 하나씩 배정합니다.
6. 성공 확률은 더하는 것이 아니라 곱해야 합니다.
7. 입력 확률을 100으로 나누어 계산하고 마지막 결과에 100을 곱합니다.
8. 최종 상태는 모든 비트가 1인 (1 << N) - 1입니다.
*/
