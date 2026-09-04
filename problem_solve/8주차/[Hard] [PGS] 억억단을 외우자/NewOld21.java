import java.util.*;

class Solution {
    public int[] solution(int e, int[] starts) {
        int[] answer = new int[starts.length];
        int[] cnt = new int[e+1];
        
        for(int i=1; i<=e; i++){
            for(int j=i; j<=e; j+=i){
                    cnt[j]++;

            }
        }
        
        // starts[i] == idx일 때 최대 약수를 갖은 e저장
        int[] factor = new int[e+1];
        factor[e] = e;

        // 뒤에서 부터 앞으로 
        // starts[i] ~ e
        for(int j=e-1; j>0; j--) {

            if (cnt[j] >= cnt[factor[j+1]]) {
                factor[j] = j;
            }else {
                factor[j] = factor[j+1];
            }
        }
        
        for(int i=0; i<answer.length; i++){
            answer[i] = factor[starts[i]];
        }
        return answer;
    }
}

// 시간복잡도
// O(nlogn)