/*
문제 정의

N일 동안의 수익이 주어졌을 때 연속된 날짜 구간 하나를 선택하여 얻을 수 있는 수익의 합 중 최댓값을 구하는 문제입니다.
구간은 반드시 하루 이상 포함해야 합니다.

예를 들어 수익이 -3, 4, 9, -2, -5, 8이라면 2일부터 6일까지 선택했을 때 4 + 9 - 2 - 5 + 8 = 14로 가장 큰 수익을 얻을 수 있습니다.
입력은 여러 테스트 케이스로 이루어져 있으며 N = 0이 입력되면 종료합니다.
*/


/*
접근 방법

카데인 알고리즘을 사용합니다.

current = 현재 날짜를 반드시 포함하는 연속 구간 중 최대 합, answer  = 지금까지 발견한 모든 연속 구간 중 최대 합이라고 정의합니다.
현재 수익이 value라고 할 때 현재 날짜를 포함하는 최적의 구간은 두 가지 중 하나입니다.

1. 이전까지의 연속 구간에 현재 수익을 이어 붙입니다.
current + value

2. 이전 구간을 버리고 현재 날짜부터 새로운 구간을 시작합니다.
value

따라서 current = Math.max(value, current + value)로 갱신합니다.
그리고 지금까지의 전체 최댓값은 answer = Math.max(answer, current)로 갱신합니다.
예를 들어 -3, 4, 9, -2, -5, 8을 순서대로 처리하면
value = -3 -> current = -3, answer = -3
value =  4 -> current =  4, answer =  4
value =  9 -> current = 13, answer = 13
value = -2 -> current = 11, answer = 13
value = -5 -> current =  6, answer = 13
value =  8 -> current = 14, answer = 14

주의할 점은 current와 answer를 0으로 초기화하면 안 된다는 것입니다.

예를 들어 -1000, -19가 주어졌다면 비어 있는 구간은 선택할 수 없으므로 정답은 0이 아니라 -19입니다.
따라서 첫 번째 수익으로 current와 answer를 초기화합니다.

또한 최대 합은 250,000 * 10,000 = 2,500,000,000까지 가능하므로 int의 최댓값을 넘어갈 수 있습니다.
따라서 합을 저장하는 변수는 반드시 long을 사용합니다.
*/

/*
문제 풀이
*/

import java.io.*;

public class Main {

    /*
    입력의 줄 구조에 관계없이 공백 단위로 빠르게 읽기 위한 FastScanner입니다.
    */
    static class FastScanner {

        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len == -1) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');
            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;

            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }
            return value * sign;
        }
    }


    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();
        StringBuilder sb = new StringBuilder();

        /*
        N = 0이 나올 때까지 여러 테스트 케이스를 처리합니다.
        */
        while (true) {
            int N = fs.nextInt();

            /*
            입력 종료
            */
            if (N == 0) {
                break;
            }

            /*
            첫 번째 수익으로 초기화합니다.
            구간이 비어 있으면 안 되므로 0으로 초기화하지 않습니다.
            */
            long first = fs.nextInt();

            long current = first;
            long answer = first;

            /*
            두 번째 날부터 순서대로 확인합니다.
            */
            for (int i = 1; i < N; i++) {
                long value = fs.nextInt();

                /*
                현재 날짜를 반드시 포함하는 최대 연속합입니다.
                이전 구간을 이어갈지, 현재 날짜부터 새로 시작할지 결정합니다.
                */
                current = Math.max(value, current + value);

                /*
                전체 최대 연속합을 갱신합니다.
                */
                answer = Math.max(answer, current);
            }
            sb.append(answer).append('\n');
        }
        System.out.print(sb);
    }
}


/*
시간복잡도

각 테스트 케이스에서 N개의 수익을 정확히 한 번씩 확인합니다.
따라서 시간복잡도는 O(N)입니다.
N <= 250,000이므로 매우 빠르게 해결할 수 있습니다.


공간복잡도

수익 배열 전체를 저장하지 않고 입력받는 즉시 계산합니다.
current, answer 등의 변수만 사용하므로 추가 공간복잡도는 O(1)입니다.

핵심 정리

1. 연속된 구간의 최대 합을 구하는 카데인 알고리즘 문제입니다.
2. current는 현재 날짜를 반드시 포함하는 최대 연속합입니다.
3. current = Math.max(value, current + value)로 갱신합니다.
4. answer = Math.max(answer, current)로 전체 최댓값을 갱신합니다.
5. 구간이 비어 있으면 안 되므로 current와 answer를 0이 아니라 첫 번째 수익으로 초기화해야 합니다.
6. 모든 값이 음수여도 가장 큰 음수 하나가 정답이 됩니다.
7. 최대 합이 int 범위를 넘을 수 있으므로 long을 사용해야 합니다.
8. 배열을 저장할 필요 없이 입력받으면서 바로 처리할 수 있어 공간복잡도는 O(1)입니다.
*/
