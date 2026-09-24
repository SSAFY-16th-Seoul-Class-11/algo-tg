import java.util.*;
import java.io.*;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Character> stack;
		
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			stack = new Stack<>();
			int N = Integer.parseInt(br.readLine());
			int ans = 1;
			String list = br.readLine();
			
			 for(int i=0; i<list.length(); i++){
				if(ans == 0)
					break;
				char c = list.charAt(i);
				switch (c) {
					case '(' :
					case '<' :
					case '[' :
					case '{' :
						stack.push(c);
						break;
					case ')' :
						if(stack.isEmpty() || stack.pop()!='(')
							ans = 0;
						break;
					case '>' :
						if(stack.isEmpty() || stack.pop()!='<')
							ans = 0;
						break;
					case ']' :
						if(stack.isEmpty() || stack.pop()!='[')
							ans = 0;
						break;
					case '}' :
						if(stack.isEmpty() || stack.pop()!='{')
							ans = 0;
						break;
					
				}
			}
            if(ans==1 && !stack.isEmpty())
                ans = 0;
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(test_case).append(" ").append(ans);
			System.out.println(sb);

		}
	}
}
