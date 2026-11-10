/*
문제 정의
N개의 식재료가 있고 N은 항상 짝수입니다.
N개의 식재료를 정확히 N/2개씩 나누어 A음식과 B음식을 만들어야 합니다.
두 식재료 i, j를 같은 음식에 사용하면 시너지 S[i][j]가 발생합니다.
하나의 음식에 포함된 모든 식재료 쌍에 대해 시너지를 더한 값이 음식의 맛이 됩니다.
예를 들어 A음식에 0번과 1번 식재료가 있다면 두 식재료의 시너지는S[0][1] + S[1][0]입니다.
두 음식의 맛 차이의 절댓값을 최소로 만드는 것이 목표입니다.
*/


/*
접근 방법
조합 + 백트래킹을 사용합니다.
N개의 식재료 중 N/2개를 A음식으로 선택하면 나머지 식재료는 자동으로 B음식이 됩니다.
selected[i] = true  -> i번 식재료는 A음식
selected[i] = false -> i번 식재료는 B음식
DFS를 이용해서 N/2개의 식재료를 선택합니다.
N/2개를 모두 선택했다면 모든 식재료 쌍 (i, j)을 확인합니다.
i와 j가 모두 A음식에 포함되어 있으면 A의 맛에 S[i][j] + S[j][i]를 더합니다.
i와 j가 모두 B음식에 포함되어 있으면 B의 맛에 S[i][j] + S[j][i]를 더합니다.
마지막에 Math.abs(tasteA - tasteB)를 계산해서 최솟값을 갱신합니다.
추가 최적화
A와 B를 서로 바꾸어도 맛 차이는 같습니다.
따라서 0번 식재료를 항상 A에 포함시키고 나머지 N/2 - 1개만 선택하면 중복되는 경우를 절반으로 줄일 수 있습니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    static int N;
    static int[][] synergy;
    /*
    selected[i] = true이면 i번 식재료를 A음식에 사용합니다.
    false이면 B음식에 사용합니다.
    */
    static boolean[] selected;

    static int answer;

    /*
    start : 다음 식재료 탐색을 시작할 위치
    count : 현재까지 A음식에 선택한 식재료의 개수
    */
    static void dfs(int start, int count) {

        /*
        A음식에 N/2개의 식재료를 모두 선택했다면 두 음식의 맛 차이를 계산합니다.
        */
        if (count == N / 2) {
            calculate();
            return;
        }

        /*
        남은 식재료 중 하나를 A음식에 선택합니다.
        */
        for (int i = start; i < N; i++) {
            selected[i] = true;
            dfs(i + 1, count + 1);
            /*
            다른 조합을 확인하기 위해 선택을 되돌립니다.
            */
            selected[i] = false;
        }
    }

    /*
    현재 selected 상태에서 A음식과 B음식의 맛을 계산합니다.
    */
    static void calculate() {
        int tasteA = 0;
        int tasteB = 0;
        /*
        i < j인 식재료 쌍만 확인합니다.
        같은 음식에 포함된 두 식재료 i, j의 시너지는 synergy[i][j] + synergy[j][i]입니다.
        */
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                /*
                두 식재료가 모두 A음식에 포함된 경우
                */
                if (selected[i] && selected[j]) {
                    tasteA += synergy[i][j];
                    tasteA += synergy[j][i];
                }
                /*
                두 식재료가 모두 B음식에 포함된 경우
                */
                else if (!selected[i] && !selected[j]) {
                    tasteB += synergy[i][j];
                    tasteB += synergy[j][i];
                }
            }
        }

        int difference = Math.abs(tasteA - tasteB);
        answer = Math.min(answer, difference);
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            synergy = new int[N][N];
            /*
            시너지 입력
            */
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    synergy[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            selected = new boolean[N];
            answer = Integer.MAX_VALUE;

            /*
            A와 B를 서로 바꾸는 대칭 경우를 제거하기 위해 0번 식재료를 항상 A음식에 포함시킵니다.
            따라서 이미 1개를 선택한 상태이며, 다음 탐색은 1번 식재료부터 시작합니다.
            */
            selected[0] = true;
            dfs(1, 1);

            sb.append("#").append(tc).append(" ").append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
시간복잡도
N개의 식재료 중 N/2개를 선택해야 합니다.
일반적으로 조합의 개수는 C(N, N/2)입니다.
하지만 0번 식재료를 항상 A음식에 포함시키므로 실제 탐색하는 경우의 수는 C(N - 1, N/2 - 1)입니다.
각 조합마다 모든 식재료 쌍을 확인하므로 O(N^2)이 필요합니다.
따라서 전체 시간복잡도는 O(C(N - 1, N/2 - 1) * N^2)입니다.
N은 최대 16이므로 충분히 빠르게 해결할 수 있습니다.

핵심 정리
1. N개의 식재료 중 N/2개만 A음식으로 선택하면 나머지는 자동으로 B음식이 됩니다.
2. DFS + 백트래킹을 이용해 N/2개의 식재료를 조합으로 선택합니다.
3. 같은 음식에 들어간 두 식재료 i, j의 시너지는 S[i][j] + S[j][i]입니다.
4. A와 B의 맛 차이의 절댓값을 구해서 최솟값을 갱신합니다.
5. A와 B를 서로 바꾼 경우는 동일한 결과이므로 0번 식재료를 A에 고정하면 탐색량을 절반으로 줄일 수 있습니다.
6. 순열이 아니라 조합이므로 DFS에서 start를 사용해서 이전 식재료를 다시 선택하지 않습니다.
*/
