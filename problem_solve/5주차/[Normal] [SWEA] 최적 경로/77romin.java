import java.util.*;
import java.io.*;

class Solution {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static StringBuilder sb = new StringBuilder();
    
    // backtracking을 위한 전역변수로 선언
    private static int[][] arr;
    private static boolean[] visited;
    private static int minDistSum;
    
	public static void main(String args[]) throws Exception {
        int T=Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++) {
            int n = Integer.parseInt(br.readLine().trim());
            
            sb.append("#").append(test_case).append(" ")
                .append(getMinSumDist(n))
                .append("\n");
		}
        System.out.print(sb);
	}
    
    private static int getMinSumDist(int n) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        arr = new int[n+2][2];
        visited = new boolean[n+2];
        minDistSum = Integer.MAX_VALUE;
        
        // [0]: 처음 방문 좌표(회사), Departure / [1]: 마지막 방문 좌표(집), Arrival / [2]~[n+2]: 중간 방문 좌표(고객), Stopovers
        for(int i=0; i<n+2; i++)
            for(int j=0; j<2; j++)
            	arr[i][j] = Integer.parseInt(st.nextToken());
        
        backTracking(0, 0, 0);
        
		return minDistSum;
    }
    
    private static void backTracking(int vCnt, int cIndex, int distSum) { // 고객 방문한 횟수, 현재의 인덱스, 지금까지의 거리
        if(distSum>minDistSum) return; // 현재의 거리합이 현시점 최소 거리합보다 크면 back!
        
        if(vCnt==arr.length-2) { // 모두 돌면 집까지의 거리를 구하기
            distSum += getDist(arr[cIndex], arr[1]);
            minDistSum = minDistSum>distSum ? distSum : minDistSum;
            return;
        }
        
        for(int i=2; i<arr.length; i++) { // [1]은 도착 값이므로, 건너뛰고 재귀
            if(visited[i])
                continue;
            
            visited[i] = true;
            vCnt++;;
            backTracking(vCnt, i, distSum+ getDist(arr[cIndex], arr[i])); // 재귀
            visited[i] = false;
            vCnt--;
        }    
    }
    
    private static int getDist(int[] cp, int[] np) { // (current point, next point)
        return Math.abs(cp[0]-np[0])+Math.abs(cp[1]-np[1]);
    }
}

/**
 * # MEMO
 * 효율적으로 찾는 것이 해답이 아니라, 짧은 경로를 찾기만 하면 된다! 그래서 고객수도 최대 10으로 준 듯하다.
 * 거리 공식은 맨하탄 거리를 사용하여 절대값(Math.abs)만 사용해도 되서 좋다.
 * 백트래킹 알고리즘
 */
