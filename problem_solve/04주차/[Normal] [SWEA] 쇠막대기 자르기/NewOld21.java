import java.util.*;
import java.io.*;


class Solution
{
	public static void main(String args[]) throws Exception
	{
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		Stack<Character> stack = new Stack<>();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			String list = br.readLine();

			int bar = 0; // 현재 막대기
			int cnt = 0; // 총 막대기 개수
			int ans = 0;
			for(int i=0; i<list.length(); i++) {
				char c = list.charAt(i);
				if(c=='(') {
					if(list.charAt(i+1) == ')') { // 레이저 
						ans += bar;
						i++;
						continue;
					}else {
						stack.push('(');
						bar += 1;
					}
					
				}
				else if(c==')') {
					stack.pop();
					bar--;
					cnt += 1;
				}
				
			}
			ans += cnt;
			System.out.println("#" + test_case + " " + ans);
		}
	}
}