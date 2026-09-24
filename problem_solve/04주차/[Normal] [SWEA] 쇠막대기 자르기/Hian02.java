import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for (int T = 1; T <= T;T++) {
            String str = sc.next();

            int count = 0;
            int result = 0;

            for (int i = 0; i < str.length(); i++) {
                char current = str.charAt(i);

                if (current == '(') {
                    count++;
                } else {
                    count--;

                    if (str.charAt(i - 1) == '(') {
                        // 레이저: 현재 열린 쇠막대기들을 모두 자름
                        result += count;
                    } else {
                        // 쇠막대기의 끝: 마지막 조각 하나 추가
                        result++;
                    }
                }
            }

            System.out.println("#" + T + " " + result);
        }

        sc.close();
    }
}
