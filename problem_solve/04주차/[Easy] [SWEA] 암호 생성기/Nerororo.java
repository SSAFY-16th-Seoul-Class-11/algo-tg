import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int test_case = 1; test_case <= 10; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            int min = Integer.MAX_VALUE;
            br.readLine();

            int[] pass = new int[8];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 8; i++) {
                pass[i] = Integer.parseInt(st.nextToken());
                min = Math.min(min, pass[i]);
            }

           /*
            8칸의 공간에서 한 사이클이 5개 이동 > 5사이클 돌면 1, 2, 3, 4, 5 골고루 돌아가며 감소한다 >
            가능한 만큼 전체에서 15 * n 만큼 빼고 진행하면 사이클을 덜 돌아도 된다
            */
            int dup = (min - 1) / 15;
            for (int i = 0; i < 8; i++) {
                pass[i] -= dup * 15;
            }
            int start = createPassword(pass);

            for (int i = 0; i < 8; i++) {
                sb.append(pass[(start + i) % 8]).append(' ');
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    // 사이클 돌며 비밀번호 만들기
    public static int createPassword(int[] pass) {
        int idx = 0;
        while (true) {
            for (int i = 1; i <= 5; i++) {
                pass[idx] -= i;
                if (pass[idx] <= 0) {
                    pass[idx] = 0;
                    return (idx + 1) % 8;
                }
                idx = (idx + 1) % 8;
            }
        }
    }
}