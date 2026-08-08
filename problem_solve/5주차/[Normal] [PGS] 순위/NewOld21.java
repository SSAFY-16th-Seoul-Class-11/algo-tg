class Solution {
    public int solution(int n, int[][] results) {
        
        int [][] rank = new int[n][n];
        for(int [] result : results){
            int win = result[0] - 1;
            int lose = result[1] - 1;
            rank[win][lose] = 1;
            rank[lose][win] = -1;
            
        }
        countRank(n, rank);
        
        return findFinalRank(n, rank);
    }
    
    public void countRank(int n, int [][] rank){
        
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
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
