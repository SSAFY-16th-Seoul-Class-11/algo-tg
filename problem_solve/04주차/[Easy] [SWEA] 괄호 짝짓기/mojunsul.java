import java.io.*;
import java.util.*;

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		for (int tc = 1; tc <= 10; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			char[] input = br.readLine().toCharArray();			
			
			sb.append("#").append(tc).append(" ").append(solve(input)).append("\n");
		}
		System.out.println(sb);
		
		br.close();
	}

	private static int solve(char[] input) {
		Stack<Character> stack = new Stack<>();
		Character top;
		for (char c : input) {
			switch(c) {
				case '(': case '[': case '{': case '<':
					stack.push(c);
					break;
				case ')':
					top = stack.peek();
					if(top != '(') {
						return 0;
					}
					stack.pop();
					break;
				case ']':
					top = stack.peek();
					if(top != '[') {
						return 0;
					}
					stack.pop();
					break;
				case '}':
					top = stack.peek();
					if(top != '{') {
						return 0;
					}
					stack.pop();
					break;
				case '>':
					top = stack.peek();
					if(top != '<') {
						return 0;
					}
					stack.pop();
			}
		}
		
		if(!stack.isEmpty()) return 0;
		
		return 1;
	}
}