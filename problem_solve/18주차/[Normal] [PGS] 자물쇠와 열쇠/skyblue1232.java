import java.util.*;

class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        int n = lock.length;
        int m = key.length;
        int boardSize = n + 2 * (m - 1);
        int[][] board = new int[boardSize][boardSize];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[m - 1 + i][m - 1 + j] = lock[i][j];
            }
        }
        
        int[][] currentKey = key;
        for (int r = 0; r < 4; r++) {
            currentKey = rotate(currentKey);
            
            for (int x = 0; x <= boardSize - m; x++) {
                for (int y = 0; y <= boardSize - m; y++) {
                    
                    if (check(board, currentKey, x, y, m, n)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private int[][] rotate(int[][] key) {
        int m = key.length;
        int[][] rotated = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                rotated[j][m - 1 - i] = key[i][j];
            }
        }
        return rotated;
    }
    
    private boolean check(int[][] board, int[][] key, int startX, int startY, int m, int n) {
        int[][] tempBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            tempBoard[i] = board[i].clone();
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                tempBoard[startX + i][startY + j] += key[i][j];
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tempBoard[m - 1 + i][m - 1 + j] != 1) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
