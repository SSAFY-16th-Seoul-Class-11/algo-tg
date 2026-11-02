import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[] pos = new int[N + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int i = 1; i <= N; i++) {
                int num = Integer.parseInt(st.nextToken());
                pos[num] = i;
            }

            int answer = 1;

            for (int i = 2; i <= N; i++) {
                if (pos[i] < pos[i - 1]) {
                    answer++;
                }
            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }
}
