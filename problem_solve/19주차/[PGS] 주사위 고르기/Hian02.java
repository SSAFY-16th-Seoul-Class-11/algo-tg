/*
문제 정의

N개의 주사위가 있고 A와 B가 각각 N/2개의 주사위를 가져갑니다.
각 주사위에는 6개의 숫자가 적혀 있으며, 각 면이 나올 확률은 모두 같습니다.
A와 B는 자신이 가진 모든 주사위를 한 번씩 굴리고 나온 숫자의 합을 비교합니다.

A의 합 > B의 합 : A 승리
A의 합 = B의 합 : 무승부
A의 합 < B의 합 : A 패배

A가 가져갈 수 있는 모든 N/2개의 주사위 조합 중 A가 승리할 확률이 가장 높은 주사위 번호들을 반환해야 합니다.
모든 주사위 결과의 발생 확률은 동일하므로 실제 확률을 계산하지 않고 A가 승리하는 경우의 수만 비교해도 됩니다.
*/


/*
접근 방법

전체 과정은 다음과 같습니다.

1. N개의 주사위 중 N/2개를 A가 선택하는 모든 조합을 구합니다.
2. A가 선택하지 않은 나머지 주사위는 자동으로 B가 가져갑니다.
3. A가 가진 주사위들의 가능한 모든 점수 합을 구합니다.
4. B가 가진 주사위들의 가능한 모든 점수 합을 구합니다.
5. B의 점수 합 배열을 오름차순으로 정렬합니다.
6. A의 각 점수 a에 대해 B의 점수가 a보다 작은 경우의 개수를 이분탐색으로 구합니다.
7. 모든 A 점수에 대해 승리 횟수를 더합니다.
8. 승리 횟수가 가장 큰 주사위 조합을 정답으로 저장합니다.


예를 들어 A의 점수가 10이라고 하겠습니다.
정렬된 B의 점수가 [4, 7, 7, 10, 12]라면 A가 이기는 경우는 4, 7, 7 총 3개입니다.
즉 B 배열에서 10 이상인 첫 번째 위치를 lower_bound로 찾으면 그 인덱스가 B 점수 < 10인 원소의 개수가 됩니다.
N <= 10이므로 A가 선택할 수 있는 조합의 개수는 최대 C(10, 5) = 252입니다.

한 사람이 가지는 주사위가 최대 5개이므로 가능한 점수 조합의 개수는 6^5 = 7776입니다.
따라서 모든 조합을 확인할 수 있습니다.
*/


/*
문제 풀이
*/

import java.util.*;

class Solution {

    static int[][] dice;

    static int N;
    static int half;

    /*
    A가 선택한 주사위의 인덱스를 저장합니다.
    */
    static int[] selected;

    /*
    최대 승리 횟수
    */
    static long maxWin;

    /*
    최종적으로 A가 선택할 주사위 번호
    */
    static int[] answer;

    /*
    N개의 주사위 중 half개를 A에게 배정하는 조합을 만듭니다.
    */
    static void combination(int start, int depth) {
        /*
        A가 사용할 주사위를 half개 모두 선택했습니다.
        */
        if (depth == half) {
            calculate();
            return;
        }

        /*
        조합 생성
        */
        for (int i = start; i < N; i++) {
            selected[depth] = i;
            combination(i + 1, depth + 1);
        }
    }

    /*
    현재 A의 주사위 선택에 대해 승리 횟수를 계산합니다.
    */
    static void calculate() {
        /*
        A가 선택한 주사위를 표시합니다.
        */
        boolean[] isA = new boolean[N];

        for (int index : selected) {
            isA[index] = true;
        }

        /*
        A와 B가 가진 주사위 인덱스를 각각 저장합니다.
        */
        int[] aDice = new int[half];
        int[] bDice = new int[half];

        int aIndex = 0;
        int bIndex = 0;

        for (int i = 0; i < N; i++) {
            if (isA[i]) {
                aDice[aIndex++] = i;
            } else {
                bDice[bIndex++] = i;
            }
        }

        /*
        한 사람이 만들 수 있는 점수 조합의 개수는 6^half입니다.
        */
        int sumCount = 1;
        for (int i = 0; i < half; i++) {
            sumCount *= 6;
        }

        int[] aSums = new int[sumCount];
        int[] bSums = new int[sumCount];

        /*
        A와 B의 모든 가능한 점수 합을 구합니다.
        */
        makeSums(aDice, 0, 0, aSums, new int[]{0});
        makeSums(bDice, 0, 0, bSums, new int[]{0});

        /*
        B 점수들을 오름차순 정렬합니다.
        이후 A의 각 점수마다 lower_bound를 이용해 A가 이기는 B의 경우의 수를 빠르게 구합니다.
        */
        Arrays.sort(bSums);
        long win = 0;

        /*
        A의 모든 가능한 점수를 확인합니다.
        */
        for (int a : aSums) {
            /*
            B 점수 중 a보다 작은 값의 개수를 구합니다.
            */
            win += lowerBound(bSums, a);
        }

        /*
        더 많은 승리 경우를 가진 조합을 발견하면 정답을 갱신합니다.
        */
        if (win > maxWin) {
            maxWin = win;
            answer = new int[half];
            /*
            문제의 주사위 번호는 1부터 시작하므로 +1합니다.
            selected는 조합으로 오름차순 선택되므로 answer 역시 자동으로 오름차순입니다.
            */
            for (int i = 0; i < half; i++) {
                answer[i] = selected[i] + 1;
            }
        }
    }

    /*
    선택한 주사위들로 만들 수 있는 모든 점수 합을 구합니다.

    diceIndex : 현재 사용할 주사위 위치
    sum       : 현재까지 점수 합
    sums      : 완성된 점수 합들을 저장할 배열
    index     : sums 배열에서 값을 저장할 위치
    */
    static void makeSums( int[] selectedDice, int diceIndex, int sum, int[] sums, int[] index) {
        /*
        모든 주사위를 굴렸다면 현재 합을 저장합니다.
        */
        if (diceIndex == selectedDice.length) {
            sums[index[0]++] = sum;
            return;
        }

        /*
        현재 주사위의 6개 면을 모두 시도합니다.
        */
        int currentDice = selectedDice[diceIndex];

        for (int face = 0; face < 6; face++) {
            makeSums(selectedDice, diceIndex + 1, sum + dice[currentDice][face], sums, index);
        }
    }

    /*
    정렬된 배열에서 target 이상인 첫 번째 위치를 찾습니다.
    따라서 반환값은 target보다 작은 원소의 개수가 됩니다.
    */
    static int lowerBound(int[] arr, int target) {

        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = (left + right) / 2;
            /*
            arr[mid]가 target보다 작으면
            더 오른쪽을 확인합니다.
            */
            if (arr[mid] < target) {
                left = mid + 1;
            }
            /*
            target 이상이라면
            현재 위치 또는 더 왼쪽에 첫 위치가 존재합니다.
            */
            else {
                right = mid;
            }
        }
        return left;
    }

    public int[] solution(int[][] inputDice) {
        dice = inputDice;

        N = dice.length;
        half = N / 2;

        selected = new int[half];

        maxWin = -1;

        /*
        A가 가져갈 N/2개의 주사위를 모든 조합으로 확인합니다.
        */
        combination(0, 0);
        return answer;
    }
}


/*
시간복잡도

N개의 주사위 중 N/2개를 선택하는 조합의 개수는 C(N, N/2)입니다.
한 사람이 가진 주사위의 개수를 K = N/2라고 하면 가능한 주사위 결과 조합은 6^K개입니다.

각 A 주사위 조합마다
A 점수 합 생성 : O(6^K)
B 점수 합 생성 : O(6^K)
B 점수 정렬 : O(6^K * log(6^K))
A의 모든 점수에 대해 B 배열에서 이분탐색 : O(6^K * log(6^K))이 필요합니다.
따라서 전체 시간복잡도는 대략 O( C(N, N/2)* 6^(N/2)* log(6^(N/2)))입니다.


공간복잡도

A와 B의 점수 합 배열을 각각 저장합니다.
각 배열의 크기는 최대 6^(N/2)이므로O(6^(N/2))입니다.

핵심 정리

1. A가 가져갈 N/2개의 주사위를 조합으로 모두 선택합니다.
2. 선택하지 않은 나머지 주사위는 자동으로 B의 주사위가 됩니다.
3. 각 사람이 가진 주사위로 만들 수 있는 모든 점수 합을 DFS로 구합니다.
4. B의 점수 합을 오름차순으로 정렬합니다.
5. A의 점수 a마다 lower_bound를 사용해서 B 점수 중 a보다 작은 값의 개수를 구합니다.
6. 그 개수가 해당 a에서 A가 승리하는 경우의 수입니다.
7. 모든 A 점수의 승리 횟수를 더해 해당 주사위 조합의 전체 승리 횟수를 구합니다.
8. 모든 결과의 발생 확률은 같으므로 승리 확률 대신 승리 횟수만 비교하면 됩니다.
9. 가장 많은 승리 횟수를 만드는 A의 주사위 조합을 반환합니다.
*/
