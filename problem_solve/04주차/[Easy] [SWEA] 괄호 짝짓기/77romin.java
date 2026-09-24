import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
	public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = 10;

		for(int test_case = 1; test_case <= T; test_case++) {
            int n = Integer.parseInt(br.readLine().trim());
            String[] bits = br.readLine().split("", n);
            System.out.printf("#%d %d\n", test_case, isPair(bits, n));
		}
	}
    
    private static int isPair(String[] bits, int n) {
        Deque<String> dq = new ArrayDeque<>(); // ArrayDeque를 Stack으로 활용
        
        for(String bit : bits) {
            if(bit.equals("[") || bit.equals("{") || bit.equals("(") || bit.equals("<")) // 열린 괄호의 경우 스택에 추가
                dq.offerLast(bit);
            else {
                if(dq.isEmpty()) { // 스택이 비어있는데 닫힌 괄호가 들어오면 스택에 추가하고 break (추가하는 이유: 짝이 안 맞음을 알려주기 위해서)
                    dq.pollLast();
                    break;
                }
                if(bit.equals("]") && dq.peekLast().equals("["))
                    dq.pollLast();
                else if(bit.equals("}") && dq.peekLast().equals("{"))
                    dq.pollLast();
                else if(bit.equals(")") && dq.peekLast().equals("("))
                    dq.pollLast();
                else if(bit.equals(">") && dq.peekLast().equals("<"))
                    dq.pollLast();
                else // 아무것도 일치하지 않으면 짝이 안 맞는 것으로 간주하고 break
                    break;
            }
        }
        // 반복문 이후 스택에 값이 비어있으면 짝이 맞는 것으로 1 반환, 비어있지 않으면 짝이 맞지 않는 것으로 0반환
        return dq.isEmpty()?1:0; // isValidtoPair: 1, isNotValidtoPair: 0
    }
}
