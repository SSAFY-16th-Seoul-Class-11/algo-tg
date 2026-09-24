/*
접근 방법

Trie 자료구조를 사용합니다.
Trie는 문자열을 한 글자씩 트리 형태로 저장하는 자료구조입니다.

전화번호는 숫자 0~9로만 이루어져 있으므로
각 노드는 최대 10개의 자식 노드를 가질 수 있습니다.

각 노드에는
1. 다음 숫자로 연결되는 child[10]
2. 이 위치에서 전화번호가 끝나는지 나타내는 isEnd

전화번호를 Trie에 하나씩 삽입하면서 접두어 관계를 확인합니다.
삽입 중 다음 두 경우가 발생하면 접두어 관계가 존재합니다.
1. 문자열을 삽입하는 도중
   현재 노드의 isEnd가 true인 경우
   예:
   기존에 119가 저장되어 있고
   119552를 삽입하는 경우
   ->
   119까지 내려왔을 때 이미 전화번호가 끝났으므로
   119는 119552의 접두어입니다.


2. 새로운 전화번호를 모두 삽입했는데
   현재 노드 아래에 자식이 존재하는 경우
   예:
   기존에 119552가 저장되어 있고
   119를 나중에 삽입하는 경우
   ->
   119 위치 아래에 이미 다른 숫자가 연결되어 있으므로
   새로 넣은 119가 기존 전화번호의 접두어입니다.

따라서 전화번호를 하나씩 Trie에 삽입하면서
위 두 경우를 확인하면 됩니다.
*/


/*
문제 풀이
*/

class Solution {
    static class Node {
        // 숫자 0~9
        Node[] child = new Node[10];
        // 이 위치에서 하나의 전화번호가 끝나는지
        boolean isEnd;
    }
    public boolean solution(String[] phone_book) {
        Node root = new Node();

        for (String phone : phone_book) {
            Node current = root;

            // 전화번호를 한 글자씩 Trie에 삽입
            for (int i = 0; i < phone.length(); i++) {
                // 기존 전화번호가 여기서 이미 끝났다면
                // 기존 번호가 현재 번호의 접두어
                if (current.isEnd) {
                    return false;
                }

                int number = phone.charAt(i) - '0';
                // 해당 숫자의 노드가 없다면 새로 생성
                if (current.child[number] == null) {
                    current.child[number] = new Node();
                }
                current = current.child[number];
            }
            // 현재 전화번호 입력이 끝남
            current.isEnd = true;

            // 현재 노드 아래에 자식이 있다면
            // 현재 전화번호가 기존 전화번호의 접두어
            for (int i = 0; i < 10; i++) {
                if (current.child[i] != null) {
                    return false;
                }
            }
        }
       return true;
    }
}


/*
시간복잡도
N : 전화번호의 개수
L : 전화번호 하나의 최대 길이

각 전화번호의 모든 문자를 한 번씩 Trie에 삽입합니다.
따라서 시간복잡도는 O(N * L)
*/
