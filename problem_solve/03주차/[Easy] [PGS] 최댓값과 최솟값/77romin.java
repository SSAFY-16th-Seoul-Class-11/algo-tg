import java.util.*;

class Solution {
    public String solution(String s) {
        
        StringTokenizer st = new StringTokenizer(s);
        int cnt = st.countTokens(); // 얼마나 문자를 받을지 저장: int countTokens() 사용
        int max = Integer.MIN_VALUE; // max를 Integer의 가장 작은 값으로 초기화
        int min = Integer.MAX_VALUE; // min를 Integer의 가장 큰 값으로 초기화
        for(int i=0; i<cnt; i++) {
            int n = Integer.parseInt(st.nextToken());
            if(n>max) max = n;
            if(n<min) min = n;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(min).append(" ").append(max);

        return sb.toString();
    }
}
