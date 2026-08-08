import java.util.*;

class Solution {
    private static List<Integer> [] list; // 배열 리스트
    private static boolean[] visited;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
        int T=sc.nextInt();
		
        StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++) {
            sb.append("#").append(test_case).append(" ");
            
            int n = sc.nextInt(); // n: 주민 수
            int m = sc.nextInt(); // m: 관계 수
            
            list = new ArrayList[n+1]; // 리스트 배열 객체 생성
            for(int i=0; i<n+1; i++) // 리스트 배열 초기화
                list[i] = new ArrayList<>();

            for(int i=0; i<m; i++) { // 리스트 배열에 서로의 관계 추가
                int p1 = sc.nextInt();
                int p2 = sc.nextInt();
                list[p1].add(p2);
                list[p2].add(p1);
            }

            int cnt = getNumOfGroups(list, n, m); // 그룹의 수 구하기
			sb.append(cnt).append("\n");
		}
        sc.close();
        System.out.print(sb);
	}

    private static int getNumOfGroups(List<Integer>[] list, int n, int m) { // 해당 케이스의 네트워크 그룹 수 반환
        int cnt = 0;
        visited = new boolean[n+1];

        for(int i=1; i<=n; i++) { // 오름차순으로 dfs 순회 (이미 방문했으면 건너뛰기)
            if(!visited[i]) {
                dfs(i);
                cnt++;
            } 
        }

        return cnt;
    }

    private static void dfs(int i) { // dfs 진입하면 visited로 방문체크해주고, 해당 인덱스와 연결된 곳으로 넘어가서 dfs 진입하기(이미 방문했으면 넘어가기)
        visited[i] = true;
        for(int nextI : list[i]) {
            if(!visited[nextI])
                dfs(nextI);
        }
    }
}

// 일종의 네트워크 문제 -> DFS로 풀기
// 1 <= N <= 100, 0 <= M <= 4950
