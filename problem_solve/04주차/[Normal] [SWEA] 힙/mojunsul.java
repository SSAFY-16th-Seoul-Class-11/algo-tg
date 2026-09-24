import java.io.*;
import java.util.*;

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
			int N = Integer.parseInt(br.readLine());
			
			sb.append("#").append(tc).append(" ");
			for (int i = 0; i < N; i++) {
				int res = 0;
				StringTokenizer st = new StringTokenizer(br.readLine());
				int op = Integer.parseInt(st.nextToken());
				if(op == 1) {
					int src = Integer.parseInt(st.nextToken());
					pq.offer(src);
				}
				else {
					if(pq.isEmpty()) {
						res = -1;
					}
					else {
						res = pq.poll();
					}
					sb.append(res).append(" ");
				}
			}
			sb.append("\n");
			
		}
		System.out.println(sb);
		
		br.close();
	}
}