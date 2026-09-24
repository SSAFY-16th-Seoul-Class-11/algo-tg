class Solution {
    public int solution(int n, int[][] results) {
        
        int [][] rank = new int[n][n];
        for(int [] result : results){
            
            int win = result[0] - 1;
            int lose = result[1] - 1;
            // 이긴 사람 1
            // 진 사람 -1
            rank[win][lose] = 1;
            rank[lose][win] = -1;
            
        }
        countRank(n, rank);
        
        return findFinalRank(n, rank);
    }
    

//  i가 k를 이기고, k가 j를 이겼다면
//  i도 j를 이긴 것으로 판단할 수 있다.
    public void countRank(int n, int [][] rank){
        
        for(int k=0;k<n;k++){
            // k한테 이긴 i 찾기
            for(int i=0;i<n;i++){
                // k한테 진 j 찾기
                for(int j=0;j<n;j++){
                    // i -> k
                    // k -> j
                    // => i -> j
                    if(rank[i][k]==1 && rank[k][j]==1){
                        rank[i][j]=1;
                        rank[j][i]=-1;
                    }

                }
            }
        }
    }

    public int findFinalRank(int n, int [][] rank){
        int ans = 0;
        for(int i=0; i<n; i++){
            boolean finalRank = true;
            for(int j=0; j<n; j++){
                // 자신의 배열 ranl[i]에 0이 있다면 순위를 확정 지울 수 없음
                if(i!=j & rank[i][j] == 0 ){
                    finalRank =false;
                    break;
                }
            }

            if(finalRank){
                ans++;
            }
                
        }
        return ans;
    }
}


