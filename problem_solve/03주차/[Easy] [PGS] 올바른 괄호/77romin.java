import java.util.ArrayDeque;

class Solution {
    boolean solution(String s) {
        String[] queryBits = s.trim().split("");
        return chkRight(queryBits);
    }
    
    boolean chkRight(String[] queryBits) {
        ArrayDeque<String> stack = new ArrayDeque<>();
        
        for(String queryBit : queryBits) {
            if(stack.size()==0 && queryBit.equals(")")) // 스택이 비어있는데, ")" 입력 받으면 false 반환
                return false;
            
            if(queryBit.equals("(")) { // "(" 입력 받으면 stack에 저장
                stack.add("(");
            } else if(queryBit.equals(")")) { // ")" 입력 받으면 stack 맨 위에 있는 것"(" 삭제
                stack.pop();
            }
        }
        if(stack.size()!=0) return false; // 다 돌았는데도 스택에 "("가 남아있으면 false 반환
        else return true; // 다 돌고, 스택이 완전히 비어있으면 true 반환
    }
}
