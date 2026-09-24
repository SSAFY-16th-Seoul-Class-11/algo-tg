import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        Trie trie = new Trie();
        int size = phone_book.length;

        for(int i = 0; i < size; i++){
            trie.insert(phone_book[i]);
        }

        for(int i = 0; i < size; i++){
            if(trie.search(phone_book[i])){
                answer = false;
                break;
            }
        }
        return answer;
    }
}

class Node{
    Map<Character, Node> child = new HashMap<Character, Node>();
    boolean end;
}

class Trie{
    Node root = new Node();

    void insert(String s){
        Node node = this.root;
        int size = s.length();
        for(int i = 0; i < size; i++){
            char key = s.charAt(i);

            // 현재 문자에 해당하는 자식 노드가 있는지 확인
            if (!node.child.containsKey(key)) {
                // 없으면 새로운 Node를 추가
                node.child.put(key, new Node());
            }

            // 해당 자식 노드로 이동
            node = node.child.get(key);
        }

        // 전화번호 끝 노드에 end를 true로 변경
        node.end = true;
    }

    boolean search(String s){
        Node node = this.root;

        int size = s.length();
        for(int i = 0; i < size; i++){
            char key = s.charAt(i);
            
            //해당 노드에 자식이 있는지 확인
            if (node.child.containsKey(key)) {
                node = node.child.get(key);
            } 
            
            // end가 true인데 전화번호가 마지막 숫자가 아닌 경우 접두어로 판단
            if (node.end && i < s.length() - 1) {
                return true;
            }
        }

        return false;
    }
}

// 시간복잡도 O(N × L) (N = 전화번호 개수, L = 전화번호 길이)
// Tire 삽입 (https://velog.velcdn.com/images/shk0221/post/8a6797ad-9e63-440a-8c3f-ef49082b49a6/image.webp)
// Tire 삭제 (https://velog.velcdn.com/images/shk0221/post/94301f1d-a42f-4e6d-a25c-a4c286a92e5a/image.webp)
// 장점: 문자열을 탐색하는 데 매우 효율적이며, 검색 시간은 문자열의 길이에 비례하므로 데이터가 많을수록 장점이 된다.
// 단점: 메모리 소비가 크고 불필요한 노드가 생길 수 있다. 특히 긴 문자열을 많이 저장하면 메모리 사용량이 급증할 수 있다.