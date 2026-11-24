import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0) break;

            int[] arr = new int[n + 1];
            int max = -10001;
            int idx = 0;
            for (int i = 1; i <= n; i++) {
                arr[i] = arr[i - 1] + Integer.parseInt(br.readLine());
                if (arr[i] > max) {
                    max = Math.max(max, arr[i]);
                    idx = i;
                }
            }
           sb.append(calc(arr, idx)).append('\n');
        }

        System.out.print(sb.toString());
    }

    public static int calc(int[] arr, int idx) {
        int finalMax = Integer.MIN_VALUE;

        for (int i = 0; i < idx; i++) {
            finalMax = Math.max(finalMax, arr[idx] - arr[i]);
        }

        return finalMax;
    }
}