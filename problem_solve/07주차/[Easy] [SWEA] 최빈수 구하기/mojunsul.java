import java.util.*;
import java.io.*;

class Solution {
	public static void main(String args[]) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int[] input = readInput(br);
			
			int mode = getMode(input);
			
			sb.append("#").append(tc).append(" ").append(mode).append("\n");
		}
		
		System.out.println(sb);
		
		br.close();
	}

	private static int[] readInput(BufferedReader br) throws IOException {
		br.readLine();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int size = 1000;
		int[] input = new int[size];
		
		for (int i = 0; i < size; i++) {
			input[i] = Integer.parseInt(st.nextToken());
		}
		
		return input;
	}
	
	private static int getMode(int[] arr) {
		List<Integer>[] bucket = new List[11];
		for (int i = 0; i <= 10; i++) {
			bucket[i] = new ArrayList<>();
		}
		
		for (int i : arr) {
			bucket[i / 10].add(i % 10);
		}
		
		int modeCnt = 0;
		int mode = 0;
		for (int i = 0; i <= 10; i++) {
			int[] count = new int[10];
			List<Integer> list = bucket[i];
			for (Integer j : list) {
				count[j]++;
				if(modeCnt < count[j] || (modeCnt == count[j] && mode < i*10 + j)) {
					modeCnt = count[j];
					mode = i*10 + j;
				}
			}
		}
		
		return mode;
	}
}
