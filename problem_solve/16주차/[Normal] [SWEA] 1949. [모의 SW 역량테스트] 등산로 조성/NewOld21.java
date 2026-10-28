import java.io.*;
import java.util.*;

class Solution
{
    static int ans;
    static int N;
    static int K;
    static int[][] moun;
    static List<Node> list;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    static boolean[][] visited;
 
    public static void main(String args[]) throws Exception
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            ans = 0;

            moun = new int[N][N];
            list = new ArrayList<>();
            int max = 0;
            for(int i=0; i<N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<N; j++){
                    moun[i][j] = Integer.parseInt(st.nextToken());
                    if(max<moun[i][j]){
                        list.clear();
                        list.add(new Node(i, j, moun[i][j]));
                        max = moun[i][j];
                    }
                    else if(max==moun[i][j]){
                        list.add(new Node(i, j, moun[i][j]));
                    }
                }
            }

            for(int i=0; i<list.size(); i++){
                Node node = list.get(i);
                visited = new boolean[N][N];
                hiking(node.x, node.y, 1, false, node.h);
            }

            System.out.println("#" + test_case + " " + ans);
        }

    }


    static void hiking(int x, int y, int cnt, boolean cut, int height){
        if(cnt>ans){
            ans = cnt;
        }

        visited[x][y] = true;
        for(int i=0; i<4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(0<=nx && nx < N && 0<=ny && ny < N && !visited[nx][ny]){
                if(height>moun[nx][ny]){
                    hiking(nx, ny, cnt+1, cut, moun[nx][ny]);
                }
                else if(!cut && height > moun[nx][ny] - K){
                    hiking(nx, ny, cnt+1, true, height - 1);
                }
            }
        }
        visited[x][y] = false;

    }
    
}

class Node{
    int x;
    int y;
    int h;

    Node(int x, int y, int h){
        this.x = x;
        this.y = y;
        this.h = h;
    }
}
