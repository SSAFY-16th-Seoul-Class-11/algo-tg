import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );
    }
}

class Solution {
	public int solution(int n, int[][] computers) {
		boolean[] visited = new boolean[n];
		int answer = 0;
		
		for(int i=0; i<n;i++) {
			if(visited[i]) {
				continue;
			}
			
			answer++;		
			
			int[] stack = new int[n];
			int top=0;
			
			stack[top] = i;
			top++;
			visited[i] = true;
			
			while(top>0) {
				top--;
				int current = stack[top];
				
				for(int next=0; next<n;next++) {
					if(computers[current][next]==1 && !visited[next]) {
						visited[next] = true;
						
						stack[top] = next;
						top++;
					}
				}
			}
		}
		
		return answer;
	}
}

