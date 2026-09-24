import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int test_case = 1; test_case <= 10; test_case++)
        {
            sb.append("#").append(test_case).append(" ");

            int caseLen = Integer.parseInt(br.readLine());
            String str = br.readLine();

            sb.append(match(caseLen, str)).append("\n");
        }

        System.out.println(sb.toString());
    }

    static int match(int len, String str) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '(':
                case '[':
                case '{':
                case '<':
                    stack.push(c);
                    break;

                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') return 0;
                    break;

                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') return 0;
                    break;

                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') return 0;
                    break;

                case '>':
                    if (stack.isEmpty() || stack.pop() != '<') return 0;
                    break;

            }
        }
        return stack.isEmpty() ? 1 : 0;
    }
}