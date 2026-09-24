import java.util.*;
class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        
		for(int test_case = 1; test_case <= T; test_case++) {
            int tc = sc.nextInt();
            sb.append("#").append(tc).append(" ");
            
            int[] bucket = new int[101]; // 0<= score <= 100
            
            for(int i=0; i<1000; i++) {
                int score = sc.nextInt();
                bucket[score]++;
            }
            
            int maxCount = 0;
            int maxScore = 0;
            
            for(int score=0; score<=100; score++) {
                if(bucket[score] >= maxCount) {
                    maxCount = bucket[score];
                    maxScore = score;
                }
            }
            
            sb.append(maxScore).append("\n");
		}
        System.out.println(sb);
	}
}

/**
 * <Memo>
 * 시간복잡도: O(N)
 * Bucket Sort: 미리 바구니에 번호표를 붙여두고 분류해서 담는 방법
 * 즉, 분류 후 집계하는 것이다.
 * Map의 key-value 매핑으로, 점수별 등장 횟수를 묶어서 세는 방식과 매우 유사하다.
 */

