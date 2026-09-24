import java.util.*;

class Solution {
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        Deque<Integer> deque = new ArraysDeque<>();
        
        deque.offer(1);
        
        int [][] roads = new int[N+1][N+1];

        for (int[] road_info : road) {
            if(roads[road_info[0]][road_info[1]]==0 || roads[road_info[0]][road_info[1]] > road_info[2]){
                roads[road_info[0]][road_info[1]] = road_info[2];
                roads[road_info[1]][road_info[0]] = road_info[2];    
            }
            
        }
        
        // 1번부터 n번까지 최소 거리 배열
        int[] distance = new int[N + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[1] = 0;

        while(!deque.isEmpty()){
            int cur = deque.poll();

            for(int i=1; i<N+1; i++){
                if(roads[cur][i] > 0){
                    if((distance[cur]+ roads[cur][i]) < distance[i]){
                        deque.offer(i);
                        distance[i] = distance[cur] + roads[cur][i];
                    }
                } 
            }

        }

        for(int d : distance){
            if(d<=K){
                answer++;
            }
        }

        return answer;
    }
}


// 1번으로부터 최소거리는 distance[]에 담아 distance가 최소값이 될 수 있게 갱신
// O(N^2+E+RN)
// 인접 행렬 생성: O(N²)
// 도로 입력: O(E)
// 큐 처리: O(RN)
// R: 큐에서 정점을 꺼낸 총횟수