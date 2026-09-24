import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T=10;
        
		for(int test_case = 1; test_case <= T; test_case++) {
            int t = sc.nextInt();
            int[] arr = new int[8];
            for(int i=0; i<8; i++)
                arr[i] = sc.nextInt();
			System.out.printf("#%d %s\n", t, makePassword(arr));
		}
	}
    
    private static String makePassword(int[] arr) {
        boolean isDone = false;
        int cnt = 1;
        Deque<Integer> queue = new ArrayDeque<>();
        
        for(int arrBit : arr)
            queue.addLast(arrBit);
        
        while(!isDone) {
            if(cnt>5) cnt=1;
            int current = queue.removeFirst()-cnt++;
            if(current<=0) {
                current = 0;
                isDone = true;
            }
            queue.addLast(current);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int queueBit : queue) {
            sb.append(queueBit).append(" ");
        }
        return sb.toString();
    }
}
