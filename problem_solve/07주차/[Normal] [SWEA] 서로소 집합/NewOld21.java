import java.util.*;
import java.io.*;

class Solution
{
    static int [] arr;
	public static void main(String args[]) throws Exception
	{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

        StringTokenizer st;

		for(int test_case = 1; test_case <= T; test_case++)
		{
		
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            StringBuilder sb = new StringBuilder();
            sb.append("#" + test_case + " ");

            arr = new int[N+1];
            for(int i=0; i<N+1; i++){
                arr[i] = i;
            }

            for(int i=0; i<M; i++){
                st = new StringTokenizer(br.readLine());
                int n = Integer.parseInt(st.nextToken());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if(n==0){
                    union(a,b);
                }
                else{
                    int findA = find(a);
                    int findB = find(b);

                    if(findA!=findB)
                        sb.append("0");
                    else 
                        sb.append("1");
                }
            }
            System.out.println(sb);
		}
	}

    private static void union(int a, int b){
        int findA = find(a);
        int findB = find(b);

        if(findA!=findB)
            arr[findA] = findB;
            
    }

    private static int find(int n){
        if(arr[n]== n)
            return n;
        else
            return arr[n] = find(arr[n]);
    }
}

// 시간 복잡도 O(N+M)