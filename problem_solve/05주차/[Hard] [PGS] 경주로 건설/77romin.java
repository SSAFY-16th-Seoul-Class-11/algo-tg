class Solution {
    private int[][] area;
    private int N;
    private int minFee;
    private final int[] dx = {1, 0, -1, 0};
    private final int[] dy = {0, 1, 0, -1};
    private final int straightFee = 100;
    private final int cornerFee = 500;
    
    private int[][][] fee; // DP 알고리즘 사용: 각 위치까지의 요금 최소값 갱신/저장 (y, x, direction)
    
    public int solution(int[][] board) {
        area = board;
        N = area.length;
        minFee = Integer.MAX_VALUE;
        fee = new int[N][N][4];
        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                for(int k=0; k<4; k++)
                    fee[i][j][k] = Integer.MAX_VALUE;
        
        buildRoad(0, 0, 0, -1); // y, x, sumFee, direction(처음(0,0)에선 -1로 시작)
        return minFee;
    }
    
    // DFS/BackTracking 알고리즘 활용
    private void buildRoad(int y, int x, int curFee, int d) { // y, x, currentSumFee, direction
        if(y==N-1 && x==N-1) { // 목적지에 도달하면 최소요금 
            minFee = minFee>curFee?curFee:minFee;
            return;
        }
        
        for(int i=0; i<4; i++) { // 현 위치에서 사방으로 넘어가는 경우 체크
            int ny = y+dy[i];
            int nx = x+dx[i];
            
            if(!isIn(ny, nx)) // 영역밖으로 벗어나면 넘어가
                continue;
            if(area[ny][nx]==1) // 돌로 막혀있으면 넘어가
                continue;
            
            int nextFee = curFee + straightFee;
            
            if(d != -1 && d != i) // 방향 바뀌면 요금 더 비싸게 받기
                nextFee += cornerFee;
            
            if(fee[ny][nx][i] <= nextFee) // 현 위치의 기존 요금이 더 싸면 넘어가기!
                continue;
            
            fee[ny][nx][i] = nextFee;
            
            buildRoad(ny, nx, nextFee, i);
        }
    }
    
    private boolean isIn(int y, int x) { // 영역 안에 있는지 체크
        return y>=0 && y<N && x>=0 && x<N;
    }
}
