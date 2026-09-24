import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int t = 0; t < 10; t++) {
            int T = sc.nextInt();

            int[] numbers = new int[8];

            for (int i = 0; i < 8; i++) {
                numbers[i] = sc.nextInt();
            }

            boolean F = false;

            while (!finished) {
                // 한 사이클: 1, 2, 3, 4, 5 감소
                for (int decrease = 1; decrease <= 5; decrease++) {
                    int nextNumber = numbers[0] - decrease;

                    // 숫자들을 한 칸씩 앞으로 이동
                    for (int i = 0; i < 7; i++) {
                        numbers[i] = numbers[i + 1];
                    }

                    // 감소한 값이 0 이하이면 0을 넣고 종료
                    if (nextNumber <= 0) {
                        numbers[7] = 0;
                        F = true;
                        break;
                    }

                    // 감소한 값을 맨 뒤에 저장
                    numbers[7] = nextNumber;
                }
            }

            System.out.print("#" + T);

            for (int i = 0; i < 8; i++) {
                System.out.print(" " + numbers[i]);
            }

            System.out.println();
        }

        sc.close();
    }
}
