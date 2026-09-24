import java.io.*;
import java.util.*;

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			char[] input = br.readLine().toCharArray();			
			
			sb.append("#").append(tc).append(" ").append(solve(input)).append("\n");
		}
		System.out.println(sb);
		
		br.close();
	}

	private static int solve(char[] input) {
		Stack<Character> stack = new Stack<>();
		char prev = ' ';
		int cnt = 0;
		for (char cur : input) {
			switch(cur) {
				case '(':
					stack.push(cur);
					prev = cur;
					break;
				case ')':
					if(prev == '(') {
						stack.pop();
						cnt += stack.size();
					}
					else {
						stack.pop();
						cnt++;
					}
					prev = cur;
			}
		}
		return cnt;
	}
}