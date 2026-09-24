import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        // 숫자가 아닌 스택을 사용해서 풀어보자.
        Scanner sc = new Scanner(System.in);

        for (int T = 1; T <= 10; T++) {
            int N = sc.nextInt();
            String str = sc.next();

            char[] stack = new char[N];
            int top = 0;
            int result = 1;

            for (int i = 0; i < N; i++) {
                char current = str.charAt(i);

                // 여는 괄호는 스택에 저장
                if (current == '(' || current == '[' || current == '{' || current == '<') {
                    stack[top++] = current;
                } else {
                    // 닫는 괄호인데 스택이 비어 있으면 잘못된 문자열
                    if (top == 0) {
                        result = 0;
                        break;
                    }

                    // 스택의 가장 위에 있는 여는 괄호 꺼내기
                    char open = stack[--top];

                    // 괄호의 종류가 맞지 않는 경우
                    if ((current == ')' && open != '(') || (current == ']' && open != '[') || (current == '}' && open != '{') || (current == '>' && open != '<')) {
                        result = 0;
                        break;
                    }
                }
            }

            // 여는 괄호가 스택에 남아 있으면 잘못된 문자열
            if (top != 0) {
                result = 0;
            }

            System.out.println("#" + testCase + " " + result);
        }

        sc.close();
    }
}
