import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean solution(String[] phone_book) {
        Trie trie = new Trie();
        boolean answer = true;

        for (String str : phone_book) {
            if (trie.insert(str)) {
                answer = false;
                break;
            }
        }
        return answer;
    }
}

class Trie {
    static class Node {
        Map<Character, Node> children = new HashMap<>();
        boolean isEnd;
    }

    private final Node root = new Node();

    public boolean insert(String word) {
        Node curr =  root;

        for (char c : word.toCharArray()) {
            if (curr.isEnd) return true;

            if (!curr.children.containsKey(c)) {
                curr.children.put(c, new Node());
            }

            curr = curr.children.get(c);
        }

        if (curr.isEnd || !curr.children.isEmpty()) return true;

        curr.isEnd = true;
        return false;
    }
}