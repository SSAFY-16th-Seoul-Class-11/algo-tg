import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T=Integer.parseInt(sc.nextLine().trim());

		for(int test_case = 1; test_case <= T; test_case++) {
			System.out.printf("#%d %d\n", test_case, getPieces(sc.nextLine().trim()));
		}
	}
    
    private static int getPieces(String pipe) {
        int cnt = 0;
        int many = 0; // 쌓는 파이프의 수
        
        Deque<Integer> dq = new ArrayDeque<>(); // Stack으로 활용
        
        for(int i=0; i<pipe.length(); i++) {
            char c = pipe.charAt(i);
            
            switch(c) {
                case '(': // 파이프 막대기 or 레이저 시작
                    dq.addLast(i);
                    break;
                case ')': // 파이프 막대기 or 레이저 끝
                    int closeIdx = dq.removeLast();
                    
                    if(closeIdx == i-1) // 바로 마지막 전에 '(' 였으면, 레이저 혹은 파이프 막대기 끝으로 판명하고, 그동안 쌓인 막대기 수 세기
                        cnt += dq.size();
                    else // 파이프 막대기 맨 마지막 끝에 하나
                        cnt++;
            }
        }
        return cnt;
    }
}
