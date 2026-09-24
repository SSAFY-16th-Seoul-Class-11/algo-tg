import java.util.*;

class Solution {
    static int[] cnt;
    static Node[] tree;

    public int[] solution(int e, int[] starts) {
        int[] answer = new int[starts.length];
        cnt = new int[e+1];
        
        for(int i=1; i<=e; i++){
            for(int j=i; j<=e; j+=i){
                    cnt[j]++;

            }
        }
        
        tree= new Node[e*4];
        build(1,1,e);
        
        for(int i=0; i<starts.length; i++){
            answer[i] = query(1, 1, e, starts[i], e).val;
        }

        return answer;
    }
    
    
    private Node build(int idx,int start, int end){
        if(start == end){
            return tree[idx] = new Node(start, cnt[start]);
        }

        int mid = (start + end)/2;

        Node left = build(idx*2, start, mid);
        Node right = build(idx*2+1, mid+1, end);

        tree[idx] = compareNode(left, right);
        return tree[idx];
    }

    private Node compareNode(Node a, Node b) {
        if(a==null)
            return b;
        if(b==null)
            return a;

        if(a.max == b.max){
            return a.val < b.val ? a : b;
        }

        if(a.max > b.max){
            return a;
        }
        else{
            return b;
        }

    }

    private Node query(int idx, int start, int end, int left, int right){
        if(left > end || right < start)
            return null;

        if(left <= start && right >= end)
            return tree[idx];

        int mid = (start+end)/2;
        Node leftNode = query(idx*2, start, mid, left, right);
        Node rightNode = query(idx*2+1, mid+1, end, left, right);

        return compareNode(leftNode, rightNode);
    }
}


class Node {
    int val, max;
		
    public Node() {}

    public Node(int val, int max) {
        this.val = val;
        this.max = max;
    }
}