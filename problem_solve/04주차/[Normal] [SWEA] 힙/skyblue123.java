import java.io.*;
import java.util.*;

public class skyblue123 {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        StringBuilder answer = new StringBuilder();

        // 테스트 케이스 개수
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {

            // 연산 개수
            int N = Integer.parseInt(br.readLine());

            // 큰 숫자가 먼저 나오는 최대 힙
            PriorityQueue<Integer> heap =
                    new PriorityQueue<>(Collections.reverseOrder());

            answer.append('#').append(tc);

            for (int i = 0; i < N; i++) {
                StringTokenizer st =
                        new StringTokenizer(br.readLine());

                int command = Integer.parseInt(st.nextToken());

                if (command == 1) {
                    // 1 x : x를 힙에 삽입
                    int number = Integer.parseInt(st.nextToken());
                    heap.offer(number);

                } else {
                    // 2 : 현재 가장 큰 값 삭제 후 출력
                    if (heap.isEmpty()) {
                        answer.append(" -1");
                    } else {
                        answer.append(' ').append(heap.poll());
                    }
                }
            }

            answer.append('\n');
        }

        System.out.print(answer);
    }
}