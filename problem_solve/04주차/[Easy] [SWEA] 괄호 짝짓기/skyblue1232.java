import java.io.BufferedReader;
import java.io.InputStreamReader;

public class skyblue1232 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        StringBuilder answer = new StringBuilder();

        for (int testCase = 1; testCase <= 10; testCase++) {
            int length = Integer.parseInt(br.readLine());
            String brackets = br.readLine();
            char[] stack = new char[length];
            int top = 0;
            int result = 1;

            for (int i = 0; i < length; i++) {
                char current = brackets.charAt(i);

                if (isOpeningBracket(current)) {
                    stack[top++] = current;
                } else {
                    if (top == 0) {
                        result = 0;
                        break;
                    }

                    char opening = stack[--top];

                    if (!isMatchingPair(opening, current)) {
                        result = 0;
                        break;
                    }
                }
            }

            if (top != 0) {
                result = 0;
            }

            answer.append('#')
                  .append(testCase)
                  .append(' ')
                  .append(result)
                  .append('\n');
        }

        System.out.print(answer);
    }

    private static boolean isOpeningBracket(char bracket) {
        return bracket == '('
                || bracket == '['
                || bracket == '{'
                || bracket == '<';
    }

    private static boolean isMatchingPair(char opening, char closing) {
        return (opening == '(' && closing == ')')
                || (opening == '[' && closing == ']')
                || (opening == '{' && closing == '}')
                || (opening == '<' && closing == '>');
    }
}