import java.util.*;
import java.io.*;

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());
        
		for(int test_case = 1; test_case <= T; test_case++) {
            StringBuilder sb = new StringBuilder();
            sb.append("#").append(test_case).append(" ");

            int n = Integer.parseInt(br.readLine()); // the number of inputs
            
            PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder()); // 우선순위가 높은 숫자가 먼저 나오도록 설정(reverseOrder)

            for(int i=0; i<n; i++) {
                String[] input = br.readLine().split(" ");
                
                if(input.length==2) { // insert: 1
                    pq.add(Integer.parseInt(input[1]));
                } else { // remove: 2
                    if(pq.isEmpty())
                        sb.append("-1 ");
                    else
                        sb.append(pq.poll()).append(" ");
                }
            }
            System.out.println(sb);
		}
	}
}
