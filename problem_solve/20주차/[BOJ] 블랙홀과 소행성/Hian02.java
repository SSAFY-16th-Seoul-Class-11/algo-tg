/*
문제 정의

수직선 위에 N개의 블랙홀과 M개의 소행성이 있습니다.
모든 블랙홀의 끌어당기는 힘은 동일하게 P입니다.
블랙홀의 위치를 b, 소행성의 위치를 a, 소행성의 질량을 w라고 할 때 |b - a| <= P / w를 만족하면 해당 블랙홀이 소행성을 빨아들일 수 있습니다.

식을 정리하면 P >= |b - a| * w가 됩니다.
따라서 하나의 소행성을 빨아들이기 위해 필요한 최소 P는
가장 가까운 블랙홀까지의 거리 * 소행성의 질량입니다.

모든 소행성을 빨아들여야 하므로 각 소행성이 필요로 하는 최소 P 중 가장 큰 값이 전체 정답입니다.
*/


/*
접근 방법

블랙홀의 위치를 오름차순으로 정렬합니다.
각 소행성의 위치 a에 대해 가장 가까운 블랙홀을 찾아야 합니다.

블랙홀 위치 배열에서 a가 들어갈 위치를 이분 탐색으로 찾으면 가장 가까운 블랙홀 후보는 최대 2개뿐입니다.
1. a 이상인 첫 번째 블랙홀
2. 그 바로 왼쪽 블랙홀

예를 들어 블랙홀 위치가 [1, 5, 10, 20]이고 소행성 위치가 7이라면 7이 들어갈 위치는 5와 10 사이입니다.
따라서 가장 가까운 블랙홀은 5 또는 10중 하나만 확인하면 됩니다.
거리의 최솟값을 distance라고 하면 해당 소행성에 필요한 최소 힘은 distance * weight입니다.

모든 소행성에 대해 이 값을 구하고 최댓값을 정답으로 저장합니다.
여러 블랙홀이 하나의 소행성을 빨아들일 수 있을 때 가장 왼쪽 블랙홀이 빨아들인다는 조건은 정답 계산에는 영향을 주지 않습니다.
우리가 필요한 것은 "어떤 블랙홀이 빨아들이는가"가 아니라 "적어도 하나의 블랙홀이 빨아들일 수 있는 최소 P"이기 때문입니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Main {

    /*
    정렬된 블랙홀 배열에서 target 이상인 첫 번째 위치를 찾습니다.
    반환값이 N이면 target 이상인 블랙홀이 없다는 의미입니다.
    */
    static int lowerBound(int[] blackHoles, int target) {

        int left = 0;
        int right = blackHoles.length;

        while (left < right) {
            int mid = (left + right) / 2;
            if (blackHoles[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        /*
        N : 블랙홀 개수
        M : 소행성 개수
        */
        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        /*
        블랙홀 위치 입력
        */
        int[] blackHoles = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            blackHoles[i] = Integer.parseInt(st.nextToken());
        }

        /*
        가장 가까운 블랙홀을 이분 탐색으로 찾기 위해 정렬합니다.
        */
        Arrays.sort(blackHoles);

        /*
        모든 소행성을 빨아들이기 위해 필요한 P의 최솟값입니다.
        */
        long answer = 0;

        /*
        각 소행성을 하나씩 확인합니다.
        */
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int asteroid = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            /*
            asteroid 이상인 첫 번째 블랙홀 위치를 찾습니다.
            */
            int index = lowerBound(blackHoles, asteroid);

            /*
            가장 가까운 블랙홀까지의 거리입니다.
            */
            long minDistance = Long.MAX_VALUE;

            /*
            asteroid 오른쪽에 있는 가장 가까운 블랙홀 후보
            */
            if (index < N) {
                minDistance = Math.min(minDistance,Math.abs((long) blackHoles[index] - asteroid));
            }

            /*
            asteroid 왼쪽에 있는 가장 가까운 블랙홀 후보
            */
            if (index > 0) {
                minDistance = Math.min(minDistance,Math.abs((long) blackHoles[index - 1] - asteroid));
            }

            /*
            해당 소행성을 빨아들이기 위해 필요한 최소 P는 가장 가까운 거리 * 질량입니다.
            */
            long requiredP = minDistance * weight;

            /*
            모든 소행성을 빨아들여야 하므로 필요한 P 중 최댓값을 저장합니다.
            */
            answer = Math.max(answer, requiredP);
        }
        System.out.println(answer);
    }
}


/*
시간복잡도

블랙홀의 개수를 N, 소행성의 개수를 M이라고 하겠습니다.
블랙홀 위치를 정렬하는 데 O(N log N)이 필요합니다.

각 소행성마다 가장 가까운 블랙홀을 찾기 위해 이분 탐색을 수행합니다.
소행성 하나당 O(log N)이므로 M개의 소행성에 대해 O(M log N)이 필요합니다.

따라서 전체 시간복잡도는 O(N log N + M log N)입니다.

공간복잡도

블랙홀 위치를 저장하는 배열에 O(N)의 공간을 사용합니다.
소행성은 입력받으면서 바로 처리하므로 별도로 저장할 필요가 없습니다.
따라서 공간복잡도는 O(N)입니다.

핵심 정리

1. 조건 |b - a| <= P / w를 P >= |b - a| * w로 바꿔 생각합니다.
2. 하나의 소행성에 필요한 최소 P는 가장 가까운 블랙홀까지의 거리 * 질량입니다.
3. 블랙홀을 정렬하면 가장 가까운 블랙홀을 이분 탐색으로 찾을 수 있습니다.
4. lowerBound로 찾은 위치와 그 바로 왼쪽 위치만 확인하면 됩니다.
5. 모든 소행성을 빨아들여야 하므로 소행성별 requiredP의 최댓값이 정답입니다.
6. 여러 블랙홀이 동시에 끌어당길 수 있을 때 왼쪽 블랙홀이 선택된다는 규칙은 최소 P 계산에는 영향을 주지 않습니다.
*/
