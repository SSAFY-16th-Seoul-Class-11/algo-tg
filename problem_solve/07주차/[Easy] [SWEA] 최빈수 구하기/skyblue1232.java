import java.io.*;
import java.util.*;

public class skyblue1232 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            int tc = Integer.parseInt(br.readLine());
            int[] count = new int[101];

            StringTokenizer st = new StringTokenizer(br.readLine());

            int maxCount = 0;
            int answer = 0;

            for (int i = 0; i < 1000; i++) {
                int score = Integer.parseInt(st.nextToken());

                if (++count[score] > maxCount ||
                        (count[score] == maxCount && score > answer)) {
                    maxCount = count[score];
                    answer = score;
                }
            }

            sb.append("#").append(tc)
              .append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }
}

// [아이디어]
// 점수 범위가 0~100이므로 크기 101의 빈도 배열을 사용한다.
// 점수를 입력받으면서 빈도를 증가시키고 최빈값을 동시에 갱신한다.
// 빈도가 같다면 더 큰 점수를 선택한다.
// [시간 복잡도]
// O(N)
// 학생 N명의 점수를 한 번씩 확인한다.
// 전체 테스트 케이스가 T개라면 O(T × N).
// [공간 복잡도]
// O(K)
// K는 점수의 범위인 101이므로 사실상 O(1).
