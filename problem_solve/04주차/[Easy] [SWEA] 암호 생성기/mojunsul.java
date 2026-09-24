import java.io.*;
import java.util.*;

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		for (int tc = 1; tc <= 10; tc++) {
			br.readLine();
			Queue<Integer> queue = new LinkedList<>();
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			while(st.hasMoreTokens()) {
				queue.offer(Integer.parseInt(st.nextToken()));
			}
			
			int cnt = 1;
			while(true) {
				Integer cur = queue.poll();
				cur -= cnt;
				cnt = cnt % 5 + 1;
				if(cur <= 0) {
					queue.offer(0);
					break;
				}
				queue.offer(cur);
			}
			
			sb.append("#").append(tc).append(" ");
			while(!queue.isEmpty()) {
				sb.append(queue.poll()).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
		
		br.close();
	}
}