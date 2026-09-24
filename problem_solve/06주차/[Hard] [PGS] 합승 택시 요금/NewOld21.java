import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {

    static int answer;
    static List<List<Node>> maps;


    public int solution(int N, int s, int a, int b, int[][] fares) {
        answer = Integer.MAX_VALUE;
        maps = new ArrayList<>();

        for(int i=0; i<N+1; i++){
            maps.add(new ArrayList<>());
        }

        for(int[] far : fares ){
            maps.get(far[0]).add(new Node(far[1], far[2]));
            maps.get(far[1]).add(new Node(far[0], far[2]));
        }

        int[] sharing = takingTaxi(s, N);
            
        int[] alone_A = takingTaxi(a, N);
        int[] alone_B = takingTaxi(b, N);


        for(int i=1; i<N+1; i++){
            answer = Math.min(answer, sharing[i] + alone_A[i] + alone_B[i]);
        }

        return answer;
    }


    private static int[] takingTaxi(int x,  int N){
        int [] visited = new int[N+1];
        Arrays.fill(visited, Integer.MAX_VALUE);
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.charge, o2.charge));

        pq.add(new Node(x, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();

  

            if(visited[node.cur]<node.charge){
                continue;
            }

            visited[node.cur] = node.charge;

            for(Node n : maps.get(node.cur)){
                if(visited[n.cur] > node.charge + n.charge){
                    pq.offer(new Node(n.cur, node.charge + n.charge));
                }
            }
        }
        return visited;
    }
}

class Node{
    int cur;
    int charge;

    Node(int cur, int charge){
        this.cur = cur;
        this.charge = charge;
    }
}


// takingTaxi(s) → O(M log N)
// takingTaxi(a) → O(M log N)
// takingTaxi(b) → O(M log N)
// 시간복잡도
// O(3*MlogN) => O(M log N)
