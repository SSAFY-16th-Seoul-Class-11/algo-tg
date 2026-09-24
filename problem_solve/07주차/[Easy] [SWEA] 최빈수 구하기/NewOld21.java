import java.util.*;
import java.io.*;


class Solution
{

    private static ArrayList<Integer>[] list = new ArrayList[1001];
	public static void main(String args[]) throws Exception
	{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;


		for(int test_case = 1; test_case <= T; test_case++)
		{
            int N = Integer.parseInt(br.readLine());
            int[] num = new int[1000];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 1000; i++) {
                num[i] = Integer.parseInt(st.nextToken());
            }

            System.out.println("#" + N + " " + bucketSort(num));
		}
	}

    private static int bucketSort(int[] num){

        for(int i=0; i<1001; i++){
            list[i] = new ArrayList<>();
        }

        for(int n : num){
            list[n].add(n);
        }
        

        int mx = 0;
        int mx_size = 0;
        for(int i=0; i<1001; i++){
            if(mx_size <= list[i].size()){
                mx_size = list[i].size();
                mx = i;
            }
        }

        return mx;
    }

}

// 1001+1000+1001=3002
// O(N+K)