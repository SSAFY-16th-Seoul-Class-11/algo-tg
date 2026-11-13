import java.util.*;
import java.io.*;

class Solution {
    
    private static boolean[][] area;
    private static boolean result;
    
	public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
		int T=Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
            result = false;
            
            int N = Integer.parseInt(br.readLine());
            area = new boolean[N][N];
            
            for(int i=0; i<N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String[] oneLine = st.nextToken().split("");
                for(int j=0; j<N; j++)
                    area[i][j] = oneLine[j].equals("o");
            }
            
            int cnt = 0;
            // 가로
            if(!result) {
                for(int i=0; i<N; i++) {
                    cnt = 0;
                    for(int j=0; j<N; j++) {
                        if(area[i][j]) cnt++;
                        else cnt=0;
                        if(cnt>=5) {
                            result = true;
                            break;
                        }
                    }
                   if(result) break;
                }
            }
            // 세로
            if(!result) {
                for(int i=0; i<N; i++) {
                    cnt = 0;
                    for(int j=0; j<N; j++) {
                        if(area[j][i]) cnt++;
                        else cnt=0;
                        if(cnt>=5) {
                            result = true;
                            break;
                        }
                    }
                    if(result) break;
                }
            }
            
            // 남동쪽 대각선 (아래쪽)
             if(!result) {
                 for(int i=0; i<N; i++) {
                     int r=i;
                     int c=0;
                     cnt = 0;
                     while(c<N && r<N) {
                         if(area[r++][c++]) cnt++;
                         else cnt=0;
                         if(cnt>=5) {
                            result = true;
                            break;
                        }
                     }
                     if(result) break;
                 }
             }
            
            // 남동쪽 대각선 (위쪽)
            if(!result) {
                 for(int i=0; i<N; i++) {
                     int r=0;
                     int c=i;
                     cnt = 0;
                     while(c<N && r<N) {
                         if(area[r++][c++]) cnt++;
                         else cnt=0;
                         if(cnt>=5) {
                            result = true;
                            break;
                        }
                     }
                     if(result) break;
                 }
             }
            
            // 남서쪽 대각선 (위쪽)
            if(!result) {
                 for(int i=N-1; i>=0; i--) {
                     int r=0;
                     int c=i;
                     cnt = 0;
                     while(c>=0 && r<N) {
                         if(area[r++][c--]) cnt++;
                         else cnt=0;
                         if(cnt>=5) {
                            result = true;
                            break;
                        }
                     }
                     
                     if(result) break;
                 }
             }
            
            
            // 남서쪽 대각선 (아래쪽)
            if(!result) {
                 for(int i=0; i<N; i++) {
                     int r=i;
                     int c=N-1;
                     cnt = 0;
                     while(c>=0 && r<N) {
                         if(area[r++][c--]) cnt++;
                         else cnt=0;
                         if(cnt>=5) {
                            result = true;
                            break;
                        }
                     }
                     if(result) break;
                 }
             }
            
            sb.append("#").append(test_case).append(" ").append(result?"YES":"NO").append("\n");
		}
        System.out.print(sb);
	}
}