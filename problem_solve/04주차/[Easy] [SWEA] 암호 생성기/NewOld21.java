import java.util.*;
import java.io.*;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    StringTokenizer st;
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			int N = Integer.parseInt(br.readLine());
	        String s = br.readLine();
	            
			Queue<Integer> q = new LinkedList<>();
	        st = new StringTokenizer(s);
				
	        while(st.hasMoreTokens()) {
	            q.add(Integer.parseInt(st.nextToken()));
	        }
	            
	        int pre_num = 100;
	        int cnt = 1;
	        while(pre_num > 0) {
	        	pre_num = q.poll();
	            	
	            if(pre_num - cnt <= 0) {
	            	pre_num = 0;
	            	cnt = 0;
	            }
	            q.add(pre_num-cnt);
                cnt = cnt%5 + 1;
	       }
	            
	      StringBuilder sb = new StringBuilder();
           sb.append("#").append(N);

            while (!q.isEmpty()) {
                sb.append(" ").append(q.poll());
            }

            System.out.println(sb);
		}
	}
}