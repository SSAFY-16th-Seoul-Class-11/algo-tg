import java.util.*;

class skyblue1232 {

    public boolean solution(String[] phoneBook) {
        Arrays.sort(phoneBook);

        for (int i = 0; i < phoneBook.length - 1; i++) {
            if (phoneBook[i + 1].startsWith(phoneBook[i])) {
                return false;
            }
        }

        return true;
    }
}

// [아이디어]
// 전화번호를 문자열 기준으로 정렬한다.
// 접두어 관계가 있는 두 번호는 정렬 후 서로 인접하게 위치한다.
// 따라서 현재 번호가 바로 다음 번호의 접두어인지 확인한다.
// [시간 복잡도]
// O(N log N × L)
// - 정렬: O(N log N × L)
// - 접두어 확인: O(N × L)
// N: 전화번호 개수
// L: 전화번호 최대 길이
// [공간 복잡도]
// O(N)
// Arrays.sort()에서 사용하는 보조 공간을 고려한다.
