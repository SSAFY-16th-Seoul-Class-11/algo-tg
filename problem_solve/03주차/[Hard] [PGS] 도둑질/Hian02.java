class Solution {
    public int solution(int[] money) {
        int answer = 0;
        int n = money.length;
        if (n==1){
            return money[0];
        }
        // 집을 털때마다의 최댓값을 저장하는 dp
        int [] dp1 = new int[n];
        int [] dp2 = new int[n];
        
        //1번째 집부터 시작하자.
        //마지막 집을 못 감.
        dp1[0] = money[0];
        dp1[1] = money[0];
        for(int i = 2; i < n - 1; i++){                             //마지막 집을 제외
            dp1[i] = Math.max(dp1[i - 1], dp1[i - 2] + money[i]);
        }
        //2번째 집부터 시작하자.
        //마지막 집을 갈 수 있음.
        dp2[0] = 0;
        dp2[1] = money[1];
        for(int i = 2; i < n; i++){                                 //마지막 집을 포함
            dp2[i] = Math.max(dp2[i - 1], dp2[i - 2] + money[i]);
        }
        answer = Math.max(dp1[n-2], dp2[n-1]);
        return answer;
    }
}
