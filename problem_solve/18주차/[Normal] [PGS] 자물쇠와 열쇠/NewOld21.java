class Solution {
    static boolean answer;

    public boolean solution(int[][] key, int[][] lock) {

        answer = false;

        int len = lock.length + key.length * 2 - 2;
        int[][] bigLock = new int[len][len];

        for (int i = key.length - 1; i <= len - key.length; i++) {
            for (int j = key.length - 1; j <= len - key.length; j++) {
                bigLock[i][j] = lock[i - key.length + 1][j - key.length + 1];
            }
        }

        for (int i = 0; i < 4; i++) {
            if (isVaild(key, bigLock)) {
                answer = true;
                break;
            } else {
                rotate(key);
            }
        }

        return answer;
    }

    boolean isVaild(int[][] key, int[][] lock) {
        int len = lock.length;
        for (int x = 0; x <= len - key.length; x++) {
            for (int y = 0; y <= len - key.length; y++) {

                for (int i = 0; i < key.length; i++) {
                    for (int j = 0; j < key.length; j++) {
                        lock[x + i][y + j] += key[i][j];
                    }
                }

                boolean check = true;

                for (int i = key.length - 1; i <= len - key.length; i++) {
                    for (int j = key.length - 1; j <= len - key.length; j++) {
                        if (lock[i][j] != 1) {
                            check = false;
                            break;
                        }
                    }
                    if (!check) {
                        break;
                    }
                }

                if (check) {
                    return check;
                }

                for (int i = 0; i < key.length; i++) {
                    for (int j = 0; j < key.length; j++) {
                        lock[x + i][y + j] -= key[i][j];
                    }
                }
            }
        }

        return false;
    }

    void rotate(int[][] key) {
        int[][] temp = new int[key.length][key.length];
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key.length; j++) {
                temp[i][j] = key[j][key.length - i - 1];
            }
        }

        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key.length; j++) {
                key[i][j] = temp[i][j];
            }
        }
    }
}