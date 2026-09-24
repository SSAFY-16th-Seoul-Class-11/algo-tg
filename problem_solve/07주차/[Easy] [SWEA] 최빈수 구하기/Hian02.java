/*
문제 정의

1000명의 학생들의 수학 점수가 주어집니다.
각 점수는 0점 이상 100점 이하입니다.

학생들의 점수 중에서
가장 많이 등장한 점수인 최빈수를 구해야 합니다.

만약 가장 많이 등장한 점수가 여러 개라면
그 중 가장 큰 점수를 출력해야 합니다.
*/


/*
접근 방법

점수의 범위가 0점부터 100점까지로 정해져 있습니다.

따라서 크기가 101인 count 배열을 만들어서
각 점수가 몇 번 등장했는지 저장하면 됩니다.

예를 들어 점수가
10 8 7 2 2 8 8이라면

count[2] = 2
count[7] = 1
count[8] = 3
count[10] = 1
처럼 저장됩니다.

그 후 0점부터 100점까지 확인하면서
가장 많이 등장한 점수를 찾습니다.

최빈수가 여러 개일 경우 가장 큰 점수를 출력해야 하므로

count[i]가 현재 최대 등장 횟수보다
크거나 같은 경우 answer를 i로 갱신합니다.

점수를 작은 값부터 큰 값 순서로 확인하기 때문에
등장 횟수가 같다면 더 큰 점수가 최종적으로 저장됩니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            // 테스트 케이스 번호
            int testNumber = Integer.parseInt(br.readLine());

            // count[i] = i점의 등장 횟수
            int[] count = new int[101];

            StringTokenizer st =  new StringTokenizer(br.readLine());

            // 학생 1000명의 점수 입력
            for (int i = 0; i < 1000; i++) {
                int score = Integer.parseInt(st.nextToken());
                count[score]++;
            }
            int maxCount = 0;
            int answer = 0;

            // 0점부터 100점까지 확인
            for (int i = 0; i <= 100; i++) {
                // 등장 횟수가 같아도 갱신
                // -> 더 큰 점수를 선택하기 위해
                if (count[i] >= maxCount) {
                    maxCount = count[i];
                    answer = i;
                }
            }
            System.out.println("#" + testNumber + " " + answer);
        }
    }
}


/*
시간복잡도

학생의 수를 N이라고 하면
학생들의 점수를 한 번 확인하면서
등장 횟수를 저장하므로 O(N)

그 후 0점부터 100점까지 확인하므로 O(101)
따라서 전체 시간복잡도는 O(N + 101)
이고 점수의 범위가 고정되어 있으므로 O(N)

*/
