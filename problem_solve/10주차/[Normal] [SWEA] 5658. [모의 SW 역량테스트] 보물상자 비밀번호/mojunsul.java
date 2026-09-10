import java.io.*;
import java.util.*;

class Solution {

    static int N, K, C;
    static char[] input;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int TC = 1; TC <= T; TC++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            C = N / 4;
            input = br.readLine().toCharArray();

            Set<Integer> set = new HashSet<>();

            for (int i = 0; i < C; i++) {
                for (int j = 0; j < 4; j++) {
                    set.add(hexToDec(i + j * C));
                }
            }

            List<Integer> list = new ArrayList<>(set);
            list.sort(Collections.reverseOrder());

            sb.append("#").append(TC).append(" ").append(list.get(K - 1)).append("\n");
        }

        System.out.print(sb);
        br.close();
    }

    private static int hexToDec(int start) {
        StringBuilder hex = new StringBuilder();

        int idx = start;
        for (int i = 0; i < C; i++) {
            hex.append(input[idx]);
            idx = (idx + 1) % N;
        }

        return Integer.parseInt(hex.toString(), 16);
    }
}

// 시간 복잡도 O(N^2) 

/* 참고
이거 쓰면 아주 조금 더 빨라짐
    private static int hexToDec(int start) {
        int val = 0;
        int idx = start;

        for (int i = 0; i < C; i++) {
            char ch = input[idx];
            int num;
            if (ch <= '9') {
                num = ch - '0';
            } else {
                num = ch - 'A' + 10;
            }

            val = (val << 4) | num;
            idx = (idx + 1) % N;
        }

        return val;
    }
*/
