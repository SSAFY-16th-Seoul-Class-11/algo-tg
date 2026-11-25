import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = -1;

        while(true) {
            n = Integer.parseInt(br.readLine());
            if(n==0) break;

            long[] benefit = new long[n];
            for(int i=0; i<n; i++)
                benefit[i] = Integer.parseInt(br.readLine());

            long[] prefix = new long[n+1];
            prefix[0] = 0;

            for(int i=1; i<=n; i++)
                prefix[i] = prefix[i-1] + benefit[i-1];

            long result = Long.MIN_VALUE;
            int minIndex = 0;
            for(int i=1; i<=n; i++) {
                result = Math.max(result, prefix[i] - prefix[minIndex]);

                if(prefix[i] <= prefix[minIndex])
                    minIndex = i;
            }

            sb.append(result).append("\n");
        }
        System.out.print(sb);
    }
}

/**
 * 알고리즘: 누적합
 * 시간복잡도: O(T * N)
 */