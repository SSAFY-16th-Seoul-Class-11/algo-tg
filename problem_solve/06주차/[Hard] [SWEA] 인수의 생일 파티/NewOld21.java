import java.util.*;
import java.io.*;


class Solution
{

    static List<List<Node>> graph;
    static int N,M,X;
    static int [] ans;
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        
		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            
            ans = new int[N+1];
            graph = new ArrayList<>();

            for(int i=0; i<N+1; i++){
                graph.add(new ArrayList<>());
            }


            for(int i=0; i<M; i++){
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
        
                graph.get(x).add(new Node(y, c));
            }

            for(int i=1; i<N+1; i++){
                if(i!=X){
                    ans[i] += shortestPath(i);
                } else{
                    shortestPathToX(); 
                } 
            }
            System.out.println("#" + test_case + " " + Arrays.stream(ans).max().getAsInt());
		}
	}


    private static int shortestPath(int cur){
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));
        boolean[] visited = new boolean[N+1];

        pq.offer(new Node(cur, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();

            if(visited[node.home]){
                    continue;
            }

            visited[node.home] = true;

            if(node.home == X){
                return node.distance;
            }

            for(Node n : graph.get(node.home) ){
                if(!visited[n.home]){
                    pq.offer(new Node(n.home, n.distance + node.distance));
                }
            }
        }
        return 0;
    }

    private static void shortestPathToX(){
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.distance, o2.distance));
        boolean[] visited = new boolean[N+1];

        pq.offer(new Node(X, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();

             if(visited[node.home]){
                continue;
            }

            ans[node.home] += node.distance;
            visited[node.home] = true;

            for(Node n : graph.get(node.home) ){
                if(!visited[n.home]){
                    pq.offer(new Node(n.home, n.distance + node.distance));
                }
            }
        }
    }
}

class Node {
    int home;
    int distance;

    Node(int home, int distance){
        this.home = home;
        this.distance = distance;
    }
}

// 시간복잡도
// shortestPath(i) : O(M log N)
// shortestPath(i)를 약 N번 실행
// shortestPathToX(` : O(M log N)

// 전체 시간복잡도
// O(NM log N)