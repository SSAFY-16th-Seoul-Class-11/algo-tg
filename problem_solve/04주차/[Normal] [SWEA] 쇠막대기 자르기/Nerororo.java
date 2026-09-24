import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

// 연속이면 1개 증가 아닐 시 먼저 pop한 뒤 size만큼 증가
class Solution
{
    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            char[] c = br.readLine().toCharArray();
            Deque<Character> dq = new ArrayDeque<>();
            int num = 0;

            // 직전에 )였는지
            boolean b = false;
            for (char curr : c) {
                if (curr == '(') {
                    dq.offer('(');
                    b = false;
                }
                else {
                    dq.pollLast();
                    if (b) {
                        num++;
                        continue;
                    }
                    b = true;
                    num += dq.size();
                }
            }

            sb.append(num).append('\n');
        }

        System.out.println(sb.toString());
    }
}