import java.util.Map;
import java.util.TreeMap;

class Solution {
    private Map<Integer, Integer> map;
    
    public int[] solution(int e, int[] starts) {
        int[] answer = new int[starts.length];
        int cnt=0;
        
        for(int start : starts) {
            map = new TreeMap<>();
            for(int i=1; i<=e; i++)
                for(int j=1; j<=e; j++)
                    map.put(i*j, map.getOrDefault(i*j, 0)+1);
            
            int max = -1;
            int maxNum = -1;
            for(int key:map.keySet()) {
                if(key>=start && key<=e) {
                    if(max<map.get(key)) {
                        max=map.get(key);
                        maxNum=key;
                    }
                }
                
            }
            answer[cnt++] = maxNum;
        }
        
        return answer;
    }
}

/**
 * 시간복잡도: O(start x e**2)
 * 굳이 2차원 배열 만들지 않고 풀어보자 (공간복잡도 고려)
 * arr[i][j] = (i+1)*(j+1);
 */
