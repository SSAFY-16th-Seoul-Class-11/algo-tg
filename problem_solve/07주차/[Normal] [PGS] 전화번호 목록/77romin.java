import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        return chkDupl(phone_book);
    }
    
    private boolean chkDupl(String[] phones) {
        Map<String, Integer> map = new HashMap<>();
        for(String phone : phones) {
            String[] phoneBits = phone.split("", phone.length());
            String chkQuery = "";
            for(int i=0; i<phoneBits.length; i++) {
                chkQuery += phoneBits[i];
                map.put(chkQuery, map.getOrDefault(chkQuery, 0)+1);
            }
        }
        for(String phone : phones) {
            if(map.get(phone)>1)
                return false;
        }
        return true;
    }
}

/**
 * <Memo>
 * HashMap를 활용하여 문자열의 문자토큰이 들어올때마다 해시맵에 저장하는 식으로 함. 맵에 다 저장하고 나서 각 문자열이  HashMap에서 존재하고(당연히 존재는 함) 그 값이 2번 이상일시  false 반환!
 * 시간복잡도: O(2*N) == O(N)
 */
