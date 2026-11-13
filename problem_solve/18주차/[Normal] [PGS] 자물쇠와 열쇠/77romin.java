class Solution {

    class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public boolean solution(int[][] key, int[][] lock) {
        int M = key.length;
        int N = lock.length;

        Point lock_sp = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point lock_ep = new Point(-1, -1);

        // lock에서 열쇠 맞춰볼 구역 구획화 (직사각형)
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (lock[r][c] == 0) {
                    lock_sp.r = Math.min(lock_sp.r, r);
                    lock_sp.c = Math.min(lock_sp.c, c);
                    lock_ep.r = Math.max(lock_ep.r, r);
                    lock_ep.c = Math.max(lock_ep.c, c);
                }
            }
        }

        // 자물쇠에 구멍이 없는 경우
        if (lock_ep.r == -1) return true;

        // 구획화한 구역 저장 / 구멍 개수 저장
        int hole_cnt = 0;
        int lock_r_size = lock_ep.r - lock_sp.r + 1;
        int lock_c_size = lock_ep.c - lock_sp.c + 1;

        int[][] lock_area = new int[lock_r_size][lock_c_size];

        for (int r = 0; r < lock_r_size; r++) {
            for (int c = 0; c < lock_c_size; c++) {
                lock_area[r][c] = lock[lock_sp.r + r][lock_sp.c + c];

                if (lock_area[r][c] == 0) hole_cnt++;
            }
        }

        /*
         * 구멍의 개수: hole_cnt
         * 열쇠 맞춰볼 구역 크기: lock_r_size x lock_c_size
         * 열쇠 맞춰볼 구역: lock_area
         */

        /* ----- 이제 키를 들여다보자 ----- */

        int[][] currentKey = key;

        // 0도, 90도, 180도, 270도 회전
        for (int direction = 0; direction < 4; direction++) {

            // 열쇠에서 lock_area 크기만큼 슬라이딩 윈도우
            for (int r = 0; r <= M - lock_r_size; r++) {
                for (int c = 0; c <= M - lock_c_size; c++) {

                    // 1. 해당 구역의 돌기 개수 확인
                    int cnt = 0;

                    for (int i = 0; i < lock_r_size; i++) {
                        for (int j = 0; j < lock_c_size; j++) {
                            if (currentKey[r + i][c + j] == 1) cnt++;
                        }
                    }

                    if (cnt != hole_cnt) continue;

                    // 2. 구멍과 돌기가 정확히 맞는지 확인
                    boolean isMatch = true;

                    for (int i = 0; i < lock_r_size && isMatch; i++) {
                        for (int j = 0; j < lock_c_size; j++) {

                            if (currentKey[r + i][c + j]
                                    + lock_area[i][j] != 1) {
                                isMatch = false;
                                break;
                            }
                        }
                    }

                    if (!isMatch) continue;

                    // 3. 직사각형 외부까지 포함해 돌기 충돌 확인
                    for (int i = 0; i < M && isMatch; i++) {
                        for (int j = 0; j < M; j++) {

                            // 열쇠에 돌기가 없다면 검사 불필요
                            if (currentKey[i][j] == 0) continue;

                            // 열쇠 좌표를 자물쇠 좌표로 변환
                            int lockR = lock_sp.r + (i - r);
                            int lockC = lock_sp.c + (j - c);

                            // 자물쇠 범위 밖이라면 충돌하지 않음
                            if (lockR < 0 || lockR >= N
                                    || lockC < 0 || lockC >= N) {
                                continue;
                            }

                            // 자물쇠 돌기와 열쇠 돌기가 충돌
                            if (lock[lockR][lockC] == 1) {
                                isMatch = false;
                                break;
                            }
                        }
                    }

                    // 모든 조건 충족
                    if (isMatch) return true;
                }
            }

            // 열쇠를 시계방향으로 90도 회전
            currentKey = rotate(currentKey);
        }

        return false;
    }

    // 시계방향 90도 회전
    private int[][] rotate(int[][] key) {
        int M = key.length;
        int[][] rotated = new int[M][M];

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < M; c++) {
                rotated[c][M - 1 - r] = key[r][c];
            }
        }

        return rotated;
    }
}
