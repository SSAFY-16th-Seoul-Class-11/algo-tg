import java.util.Arrays;

class Solution {
    int[][] comp;
    boolean[] visited;
    int cnt;
    int N;
    
    public int solution(int n, int[][] computers) { 
        N = n;
        comp = Arrays.copyOf(computers, n);
        visited = new boolean[n];
        for(int i=0; i<n; i++) {
            if(!visited[i]) {
                isConnected(i);    
                cnt++;
            }
        }
        return cnt;
    }
    
    private void isConnected(int index) {
        int check = 0;
        for(int i=0; i<N; i++) {
            if(index == i) continue; // 자기자신 제외
            if(comp[index][i] == 1) {
                if(visited[i] == true) continue;
                else {
                    visited[i] = true;
                    check++;
                    isConnected(i);
                }
            }
        }
        if(check==0) return;
    }    
}

// BFS로 구현해야지~~
