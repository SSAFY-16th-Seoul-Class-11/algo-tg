import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class NewOld21 {
    
    static Node[] tree;
    static int[] arr;
    static long[] lazy;
    static int N;
    public static void main(String args[]) throws Exception{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        tree = new Node[N*4];
        arr = new int[N+1];
        lazy = new long[N*4];

        builder(1, 1, N);
        for(int m=0; m<M; m++){
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken())+1;
            int r = Integer.parseInt(st.nextToken());
            if(t == 1){
                long v = Integer.parseInt(st.nextToken());
                update(l,r,v);
            }else{
                System.out.println(quary(l,r));
            }
        }

    }

    private static Node builder(int idx, int start, int end){
        if(start==end){
            tree[idx] = new Node(arr[start]);
            return tree[idx];
        }

        int mid = (start + end)/2;
        Node left = builder(idx*2, start, mid);
        Node right = builder(idx*2+1, mid+1, end);

        tree[idx] = new Node(left.sum + right.sum);
        return tree[idx];
    }

    public static long quary(int left, int right){
        return quary(1, 1, N, left, right);
    }

    private static long quary(int idx, int start, int end, int left, int right){
        if(start>right || end<left){
            return 0;
        }
        if(start>=left && end<=right){
            return tree[idx].sum;
        }

        // 자식들 lazy 갱신
        push(idx, start, end);

        int mid = (start+end)/2;
        
        long leftSum = quary(idx*2, start, mid, left, right);
        long rightSum = quary(idx*2+1, mid+1, end, left, right);

        return leftSum + rightSum;
    }

    public static void update(int left, int right, long value){
        update(1, 1, N, left, right, value);
    }

    private static void update(int idx, int start, int end, int left, int right, long value){
        if(start>right || end<left){
            return;
        }
        if(start>=left && end<=right){
            // 부모만 갱신
            tree[idx].sum += value * (end - start + 1);
            // 자식은 나중에
            lazy[idx] += value;
            return;
        }

        // 자식들 lazy 갱신
        push(idx, start, end);

        int mid = (start+end)/2;

        update(idx*2, start, mid, left, right, value);
        update(idx*2+1, mid+1, end, left, right, value);

        tree[idx].sum = tree[idx*2].sum + tree[idx*2+1].sum;

    }

    private static void push(int idx, int start, int end){
        if(lazy[idx] == 0 || start == end){
            return;
        }

        int mid = (start + end) / 2;

        tree[idx*2].sum += lazy[idx] * (mid - start + 1);
        tree[idx*2+1].sum += lazy[idx] * (end - mid);

        lazy[idx*2] += lazy[idx];
        lazy[idx*2+1] += lazy[idx];

        lazy[idx] = 0;
    }


}

class Node{
    long sum;
    Node(long sum){
        this.sum = sum;
    }
}