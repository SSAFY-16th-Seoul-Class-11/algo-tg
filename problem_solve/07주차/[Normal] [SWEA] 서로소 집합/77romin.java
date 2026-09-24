import java.util.*;
import java.io.*;

class Solution {
    private static int[] parent; // 부모노드 저장 배열
    
	public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

		for(int test_case = 1; test_case <= T; test_case++) {
            sb.append("#").append(test_case).append(" ");
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            parent = new int[n+1];
            for(int i=1; i<=n; i++) // 최초 집합현황: {1}, {2}, ..., {n}
                parent[i] = i;
            
            for(int i=0; i<m; i++) {
                st = new StringTokenizer(br.readLine());
                boolean isUnion =st.nextToken().equals("0") ? true : false;
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                
                if(isUnion) // '0'이면 합치기
                    union(a, b);
                else // '1'이면 같은 집합인지 여부 출력
                    sb.append(find(a)==find(b)?"1":"0");
                    
            }
            sb.append("\n");
		}
        
        System.out.println(sb);
	}
    
    static int find(int n) { // 부모노드 찾기
        if(n == parent[n]) return n;
        return parent[n] = find(parent[n]); // 경로압축
    }
    
    static void union(int a, int b) { // (결국 부모노드가 같도록) 합치기
        int rootA = find(a);
        int rootB = find(b);
        if(rootA==rootB) return;
        parent[rootA] = rootB;
    }
}

/**
 * <Memo>
 * 시간복잡도: O(N+M)
 * Union Find를 활용함. --> 합집합 문제는 유니온파인드로 풀면 될 듯
 * '경로압축': find 함수에서 root 부모를 찾음과 동시에 parent[n]의 값을 바로 갱신해주어야 트리의 높이가 1로 압축되어 연산속도가 O(1)에 가까워진다고 함.
 * 경로압축 안 쓰면 시간복잡도가 O(NxM)에 달한다. (편향트리-일자형태로 형성되기 때문이다)
 */
