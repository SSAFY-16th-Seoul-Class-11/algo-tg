import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long p = Long.MIN_VALUE; // 최소 P

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 블랙홀의 개수
        int M = Integer.parseInt(st.nextToken()); // 행성의 개수

        long[] b = new long[N];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++)
            b[i] = Long.parseLong(st.nextToken());
        Arrays.sort(b); // 오름차순 정렬

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            long pLocation = Long.parseLong(st.nextToken());
            long pWeight = Long.parseLong(st.nextToken());

            int bLocation = 0; // 블랙홀 위치 인덱스
            int upperBound = N; // 구간 오른쪽 끝
            while(bLocation < upperBound) {

                int mid = (bLocation + upperBound) / 2;

                if(b[mid] < pLocation)
                    bLocation = mid+1;
                else
                    upperBound = mid;

            }

            long dist = Long.MAX_VALUE;
            if(bLocation < N)
                dist = b[bLocation] - pLocation;
            if(bLocation > 0)
                dist = Math.min(dist, pLocation - b[bLocation-1]);

            p = Math.max(p, dist*pWeight);
        }

        System.out.print(p);
    }
}

/**
 * 알고리즘: 이분탐색
 * 시간복잡도: O((N+M) log N)
 */