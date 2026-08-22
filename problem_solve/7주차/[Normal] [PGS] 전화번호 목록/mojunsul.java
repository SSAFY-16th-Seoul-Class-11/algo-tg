import java.util.*;

class Solution {

    public boolean solution(String[] phone_book) {
        Trie trie = new Trie();
        for (String phone : phone_book) {
            trie.insert(phone);
        }

        for (String phone : phone_book) {
            if (trie.startsWith(phone)) return false;
        }

        return true;
    }

    class TrieNode {

        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    class Trie {

        TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode cur = root;

            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                cur.children.putIfAbsent(ch, new TrieNode());
                cur = cur.children.get(ch);
            }

            cur.isEndOfWord = true;
        }

        public boolean search(String word) {
            TrieNode find = findNode(word);
            return find != null && find.isEndOfWord;
        }

        public boolean startsWith(String prefix) {
            TrieNode find = findNode(prefix);
            return find != null && !find.children.isEmpty();
        }

        private TrieNode findNode(String word) {
            TrieNode cur = root;

            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                TrieNode child = cur.children.get(ch);

                if (child == null) return null;

                cur = child;
            }

            return cur;
        }
    }
}
