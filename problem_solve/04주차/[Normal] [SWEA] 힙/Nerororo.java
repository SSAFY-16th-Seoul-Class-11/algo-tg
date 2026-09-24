import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

            int num = Integer.parseInt(br.readLine());
            for (int i = 0; i < num; i++) {
                st = new StringTokenizer(br.readLine());

                int calcNum = Integer.parseInt(st.nextToken());
                if (calcNum == 1) {
                    pq.offer(Integer.parseInt(st.nextToken()));
                } else {
                    if (pq.isEmpty()) {
                        sb.append(-1).append(' ');
                        continue;
                    }
                    sb.append(pq.poll()).append(' ');
                }
            }

            sb.append('\n');
        }

        System.out.println(sb);
    }
}