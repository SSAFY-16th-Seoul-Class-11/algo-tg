
class Solution {


    public long solution(int[] sequence) {
        
        long answer = 0;
        Node[] tree1 = new Node[sequence.length*4];
        Node[] tree2 = new Node[sequence.length*4];
        

        build(tree1, false, 1, 0, sequence.length-1, sequence);
        build(tree2, true, 1, 0, sequence.length-1, sequence);
        answer = Math.max(answer, Math.max(tree1[1].maxSum, tree2[1].maxSum));
        

        return answer;

    }


    private static Node build(Node[] tree, boolean opt  ,int idx, int start, int end, int[] seq){
        if(start==end){
            if(start%2==0){
                if(opt)
                    return tree[idx] = new Node(seq[start]);
                return tree[idx] = new Node(-seq[start]);
            }else{
                if(opt)
                    return tree[idx] = new Node(-seq[start]);
                return tree[idx] = new Node(seq[start]);
            }
        }

        int mid = (start + end) /2;

        Node left = build(tree, opt, idx*2, start, mid, seq);
        Node right = build(tree, opt, idx*2+1, mid+1, end, seq);

        long leftMax = Math.max(left.leftMax, left.sum + right.leftMax);
        long rightMax = Math.max(right.rightMax, right.sum + left.rightMax);
        long maxSum = Math.max(Math.max(left.maxSum, right.maxSum), left.rightMax + right.leftMax);
        
        return tree[idx] = new Node(left.sum + right.sum, leftMax, rightMax, maxSum);
    }


}

class Node{
    long sum;
    long leftMax;
    long rightMax;
    long maxSum;

    Node(long val){
        this.sum = val;
        this.leftMax = val;
        this.rightMax = val;
        this.maxSum = val;
    }
    
    Node(long sum, long left, long right, long mx){
        this.sum = sum;
        this.leftMax = left;
        this.rightMax = right;
        this.maxSum = mx;
    } 
}