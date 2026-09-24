import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int test = 1; test <= T; test++) {
            int N = Integer.parseInt(br.readLine());
            // 힙의 인덱스를 1부터 사용
            long[] heap = new long[N + 1];
            int size = 0;

            StringBuilder answer = new StringBuilder();
            answer.append("#").append(test);

            for (int operation = 0; operation < N; operation++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int command = Integer.parseInt(st.nextToken());
                if (command == 1) {
                    // 삽입할 값
                    long value = Long.parseLong(st.nextToken());
                    size++;
                    heap[size] = value;
                    // 새로 삽입된 노드를 위로 올림
                    int current = size;
                    while (current > 1) {
                        int parent = current / 2;
                        // 부모가 더 크거나 같으면 최대 힙 조건 만족
                        if (heap[parent] >= heap[current]) {
                            break;
                        }
                        long temp = heap[parent];
                        heap[parent] = heap[current];
                        heap[current] = temp;
                        current = parent;
                    }
                }
                else {
                    // 삭제할 원소가 없는 경우
                    if (size == 0) {
                        answer.append(" -1");
                    }
                    else {
                        // 루트가 최댓값
                        answer.append(" ").append(heap[1]);
                        // 마지막 원소를 루트로 이동
                        heap[1] = heap[size];
                        size--;
                        // 루트에 옮긴 값을 아래로 내림
                        int current = 1;
                        while (current * 2 <= size) {
                            int left = current * 2;
                            int right = current * 2 + 1;
                            int biggerChild = left;
                            // 오른쪽 자식이 존재하고 더 크면 선택
                            if (right <= size && heap[right] > heap[left]) {
                                biggerChild = right;
                            }
                            // 현재 노드가 자식보다 크거나 같으면 종료
                            if (heap[current] >= heap[biggerChild]) {
                                break;
                            }
                            long temp = heap[current];
                            heap[current] = heap[biggerChild];
                            heap[biggerChild] = temp;
                            current = biggerChild;
                        }
                    }
                }
            }

            System.out.println(answer);
        }
    }
}
