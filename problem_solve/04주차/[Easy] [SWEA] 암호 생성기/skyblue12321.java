import java.io.*;
import java.util.*;

public class skyblue12321 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();

        for (int tc = 0; tc < 10; tc++) {
            int testCase = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] numbers = new int[8];

            for (int i = 0; i < 8; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            int index = 0;
            int decrease = 1;

            while (true) {
                numbers[index] -= decrease;

                if (numbers[index] <= 0) {
                    numbers[index] = 0;
                    index = (index + 1) % 8;
                    break;
                }

                index = (index + 1) % 8;
                decrease = decrease % 5 + 1;
            }

            answer.append('#').append(testCase);

            for (int i = 0; i < 8; i++) {
                answer.append(' ').append(numbers[(index + i) % 8]);
            }

            answer.append('\n');
        }

        System.out.print(answer);
    }
}