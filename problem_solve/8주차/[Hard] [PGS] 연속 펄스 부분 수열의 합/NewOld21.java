import java.util.Deque;

class Solution {
    
    public long solution(int[] sequence) {
        
        long answer = 0;
        long sum1 = 0;
        long sum2 = 0;


        for(int i=0; i<sequence.length; i++){
            long num1;
            long num2;

            if(i%2==0){
                num1 = sequence[i];
                num2 = -sequence[i];
            }
            else{
                num1 = -sequence[i];
                num2 = sequence[i];
            }

            sum1 = Math.max(num1, sum1 + num1);
            sum2 = Math.max(num2, sum2 + num2);

            answer = Math.max(answer, Math.max(sum1, sum2));
        }

        return answer;

    }
}

// 시간복잡도
// O(n)