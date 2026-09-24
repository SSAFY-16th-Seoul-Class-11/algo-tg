import java.util.*;
import java.io.*;


class Solution
{
	public static void main(String args[]) throws Exception
	{
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PriorityQueue<Integer> pq;
		StringBuilder sb;
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
	
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = Integer.parseInt(br.readLine());
			pq = new PriorityQueue<>();
			sb = new StringBuilder();
			sb.append("#" + test_case + " ");
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				int key = Integer.parseInt(st.nextToken());
				if(key==1) {
					pq.add(-Integer.parseInt(st.nextToken()));
				}else if(key==2) {
					if(pq.size()>0)
						sb.append(-pq.remove() + " ");
					else 
						sb.append(-1 + " ");
				}
			}
			System.out.println(sb);
		}
	}
}