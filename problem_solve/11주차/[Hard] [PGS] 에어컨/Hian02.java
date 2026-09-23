/*
문제 정의

현재 실내온도는 실외온도 temperature와 같습니다.
에어컨을 이용해 실내온도를 조절할 수 있으며, 승객이 탑승 중일 때는 실내온도를 반드시 t1 <= 실내온도 <= t2 범위로 유지해야 합니다.

에어컨의 동작은 다음과 같습니다.
1. 에어컨 OFF
실내온도가 실외온도와 같아지는 방향으로 1분에 1도 변화합니다.
실외온도와 같다면 변하지 않습니다.
소비전력은 0입니다.

2. 에어컨 ON - 온도 변화
희망온도를 현재 실내온도와 다르게 설정하면 희망온도 방향으로 1분에 1도 변화합니다.
소비전력은 a입니다.

3. 에어컨 ON - 온도 유지
희망온도를 현재 실내온도와 같게 설정하면 실내온도가 변하지 않습니다.
소비전력은 b입니다.

onboard[i] == 1이라면 i분의 실내온도가 반드시 t1 ~ t2 범위여야 합니다.

모든 승객의 탑승 조건을 만족하면서 소비전력의 총합을 최소화하는 문제입니다.
*/


/*
접근 방법

DP를 사용합니다.
1. DP 상태 정의
dp[temp] = 현재 시간에 실내온도가 temp일 때 지금까지 사용한 최소 소비전력

단, 온도의 범위는 -10 ~ 40이므로 배열의 인덱스로 사용하기 위해 10을 더합니다.
실제 온도 -10 -> 인덱스 0
실제 온도   0 -> 인덱스 10
실제 온도  40 -> 인덱스 50
따라서 배열 크기는 51이면 충분합니다.

2. 초기 상태
0분의 실내온도는 실외온도와 같습니다.
따라서 dp[temperature + 10] = 0으로 설정합니다.
다른 온도는 아직 도달할 수 없으므로 INF로 초기화합니다.

3. 상태 전이
현재 실내온도가 temp라고 가정합니다.
다음 1분 동안 가능한 행동은 다음과 같습니다.

(1) 에어컨 OFF
실외온도보다 실내온도가 높다면 temp -> temp - 1
실외온도보다 실내온도가 낮다면 temp -> temp + 1
실외온도와 같다면temp -> temp
소비전력은 0입니다.

(2) 에어컨 ON - 온도 상승
temp -> temp + 1
소비전력은 a입니다.


(3) 에어컨 ON - 온도 하강
temp -> temp - 1
소비전력은 a입니다.

(4) 에어컨 ON - 온도 유지
temp -> temp
소비전력은 b입니다.

희망온도는 에어컨이 켜져 있는 동안 자유롭게 변경할 수 있으므로
현재 온도를 기준으로 상승, 하강, 유지 중 하나를 선택하면 됩니다.

4. 승객 탑승 조건 확인
현재 시간이 time이라면 위 행동을 수행한 후의 시간은 time + 1입니다.
따라서 onboard[time + 1] == 1인 경우 다음 실내온도가 t1 <= 다음 온도 <= t2를 만족하는 상태만 저장합니다.

5. 최종 정답
마지막 시간까지 모든 상태를 계산한 후 가능한 실내온도 중 최소 소비전력을 반환합니다.
*/


/*
문제 풀이
*/

import java.util.Arrays;

class Solution {
    static final int MIN_TEMP = -10;
    static final int MAX_TEMP = 40;
    static final int OFFSET = 10;
    static final int SIZE = 51;
    static final int INF = 1_000_000_000;

    /*
    다음 시간의 상태를 갱신하는 함수입니다.
    next       : 다음 시간의 DP 배열
    nextTemp   : 다음 시간의 실내온도
    nextCost   : 해당 온도에 도달하기 위한 소비전력
    onboardNext: 다음 시간의 승객 탑승 여부
    */
    static void update(int[] next,int nextTemp,int nextCost,int onboardNext,int t1,int t2) {
        /*
        계산 가능한 온도 범위를 벗어난다면 해당 상태는 사용하지 않습니다.
        */
        if (nextTemp < MIN_TEMP || nextTemp > MAX_TEMP) {
            return;
        }

        /*
        다음 시간에 승객이 탑승 중이라면
        반드시 쾌적한 온도 범위를 유지해야 합니다.
        */
        if (onboardNext == 1) {
            if (nextTemp < t1 || nextTemp > t2) {
                return;
            }
        }

        /*
        다음 시간에 같은 온도에 도달하는
        여러 방법 중 소비전력이 가장 작은 값을 선택합니다.
        */
        int index = nextTemp + OFFSET;
        next[index] = Math.min(next[index],nextCost);
    }

    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        /*
        dp[temp + OFFSET]
        현재 시간에 실내온도가 temp일 때 필요한 최소 소비전력입니다.
        */
        int[] dp = new int[SIZE];
        /*
        처음에는 모든 온도에 도달할 수 없다고 가정합니다.
        */
        Arrays.fill(dp, INF);
        /*
        0분의 실내온도는 실외온도와 같고, 아직 전력을 사용하지 않았습니다.
        */
        dp[temperature + OFFSET] = 0;
        /*
        마지막 시간까지 DP를 진행합니다.
        onboard.length가 7이라면
        0분 -> 1분
        1분 -> 2분
        ...
        5분 -> 6분
        총 6번 상태를 전이합니다.

        마지막 시간의 실내온도만 확인하면 되므로 마지막 시간 이후에는 전력을 사용할 필요가 없습니다.
        */
        for (int time = 0; time < onboard.length - 1; time++) {
            /*
            다음 시간의 실내온도별 최소 소비전력을 저장합니다.
            */
            int[] next = new int[SIZE];
            Arrays.fill(next, INF);

            /*
            현재 시간에 가능한 모든 실내온도를 확인합니다.
            */
            for (int temp = MIN_TEMP; temp <= MAX_TEMP; temp++) {
                int index = temp + OFFSET;
                /*
                현재 시간에 해당 온도로 도달할 수 없다면 건너뜁니다.
                */
                if (dp[index] == INF) {
                    continue;
                }

                int currentCost = dp[index];
                /*
                다음 시간에 승객이 탑승 중인지 확인합니다.
                */
                int onboardNext = onboard[time + 1];
                /*
                1. 에어컨 OFF
                실외온도와 같아지는 방향으로 실내온도가 1도 변화합니다.
                소비전력은 0입니다.
                */
                int offTemp = temp;
                if (temp < temperature) {
                    offTemp++;
                } 
                else if (temp > temperature) {
                    offTemp--;
                }
                update(next,offTemp,currentCost,onboardNext,t1,t2);

                /*
                2. 에어컨 ON - 온도 상승
                희망온도를 현재 실내온도보다 높게 설정하는 경우입니다.
                실내온도가 1도 상승하고 전력을 a만큼 소비합니다.
                */
                update( next,temp + 1,currentCost + a,onboardNext,t1,t2);

                /*
                3. 에어컨 ON - 온도 하강
                희망온도를 현재 실내온도보다 낮게 설정하는 경우입니다.
                실내온도가 1도 하강하고 전력을 a만큼 소비합니다.
                */
                update(next,temp - 1,currentCost + a,onboardNext,t1,t2);

                /*
                4. 에어컨 ON - 온도 유지
                희망온도를 현재 실내온도와 같게 설정하는 경우입니다.
                실내온도는 변하지 않고 전력을 b만큼 소비합니다.
                */
                update(next,temp,currentCost + b,onboardNext,t1,t2);
            }

            /*
            현재 시간에서 다음 시간으로 이동합니다.
            */
            dp = next;
        }
        /*
        마지막 시간에 도달할 수 있는 모든 실내온도 중 소비전력이 가장 작은 값을 찾습니다.
        */
        int answer = INF;
        for (int temp = MIN_TEMP; temp <= MAX_TEMP; temp++) {
            answer = Math.min(answer,dp[temp + OFFSET]);
        }
        return answer;
    }
}


/*
시간복잡도

onboard의 길이를 L이라고 하겠습니다.
각 시간마다 가능한 실내온도 -10 ~ 40을 모두 확인합니다.
온도는 총 51가지입니다.

각 온도에서는
1. 에어컨 OFF
2. 에어컨 ON - 상승
3. 에어컨 ON - 하강
4. 에어컨 ON - 유지
총 4가지 상태를 확인합니다.

따라서 시간복잡도는 O(L * 51 * 4)
온도 범위와 행동 개수는 고정되어 있으므로 실질적으로 O(L)
*/
