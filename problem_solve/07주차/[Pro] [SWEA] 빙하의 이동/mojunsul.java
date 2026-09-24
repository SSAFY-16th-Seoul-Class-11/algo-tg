import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class UserSolution {

    private static final int MAX_N = 100;
    static final int MAX_ICEBLOCK = 2005;

    static final int[] di = {-1, 0, 1, 0};
    static final int[] dj = {0, 1, 0, -1};

    class RESULT {
        public int[][] heights;

        public RESULT() {
            this.heights = new int[MAX_N][MAX_N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    this.heights[i][j] = height[i][j];
                }
            }
        }
    }

    static class IceBlock {
        int dir;    // 0: 상, 1: 우, 2: 하, 3: 좌
        int volume;
        int area;
        int minY;
        int minX;
    }

    int N;
    int[][] height;
    int[][] gid;
    int[][] nextHeight;
    int[][] nextGid;
    boolean[][] willMelt;
    boolean[][] visited;

    IceBlock[] iceBlocks = new IceBlock[MAX_ICEBLOCK];
    int iceBlockCnt;

    Queue<int[]> queue = new ArrayDeque<>();
    List<int[]> list = new ArrayList<>();

    public void init(int N, int M, int[][] mIceBlock, int[][] mIceGroup) {
        height = new int[N][N];
        gid = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < MAX_ICEBLOCK; i++) {
            iceBlocks[i] = new IceBlock();
        }

        this.N = N;
        this.iceBlockCnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                height[i][j] = mIceBlock[i][j];
            }
        }

        for (int g = 0; g < M; g++) {
            int j = mIceGroup[g][0];
            int i = mIceGroup[g][1];
            int dir = mIceGroup[g][2];

            if (visited[i][j] || height[i][j] == 0) continue;

            int gid = ++iceBlockCnt;
            iceBlocks[gid].dir = dir;


            queue.offer(new int[]{i, j});
            visited[i][j] = true;
            this.gid[i][j] = gid;

            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                i = cur[0];
                j = cur[1];

                for (int d = 0; d < 4; d++) {
                    int ni = getIdx(i + di[d]);
                    int nj = getIdx(j + dj[d]);

                    if (visited[ni][nj] || height[ni][nj] == 0) continue;

                    visited[ni][nj] = true;
                    this.gid[ni][nj] = gid;
                    queue.offer(new int[] {ni, nj});
                }
            }
        }
    }

    private int getIdx(int x) {
        return (x + N) % N;
    }

    private boolean isHigherPriority(int gidA, int gidB) {
        if (gidB == 0) return true;
        if (gidA == 0) return false;

        IceBlock a = iceBlocks[gidA];
        IceBlock b = iceBlocks[gidB];

        if (a.volume != b.volume) return a.volume > b.volume;
        if (a.area != b.area) return a.area < b.area;
        if (a.minY != b.minY) return a.minY < b.minY;
        return a.minX < b.minX;
    }

    public RESULT oneYearLater() {
        melt();

        move();

        merge();

        return new RESULT();
    }

    private void melt() {
        // 융해 확인
        willMelt = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (height[i][j] > 0) {
                    for (int d = 0; d < 4; d++) {
                        int ni = getIdx(i + di[d]);
                        int nj = getIdx(j + dj[d]);

                        if (height[ni][nj] == 0) {
                            willMelt[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }

        // 융해
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (height[i][j] > 0 && willMelt[i][j]) {
                    height[i][j]--;
                    if (height[i][j] == 0) {
                        gid[i][j] = 0;
                    }
                }
            }
        }

        // 빙하 분리
        visited = new boolean[N][N];
        int splitCnt = 0;
        IceBlock[] splits = new IceBlock[MAX_ICEBLOCK];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (height[i][j] > 0 && !visited[i][j]) {
                    int sGid = ++splitCnt;
                    splits[sGid] = new IceBlock();

                    splits[sGid].dir = iceBlocks[gid[i][j]].dir;
                    splits[sGid].volume = 0;
                    splits[sGid].area = 0;
                    splits[sGid].minY = 9999;
                    splits[sGid].minX = 9999;

                    queue.clear();
                    list.clear();
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;

                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();
                        list.add(cur);
                        int ci = cur[0];
                        int cj = cur[1];

                        splits[sGid].volume += height[ci][cj];
                        splits[sGid].area++;
                        if (ci < splits[sGid].minY) splits[sGid].minY = ci;
                        if (cj < splits[sGid].minX) splits[sGid].minX = cj;

                        for (int d = 0; d < 4; d++) {
                            int ni = getIdx(ci + di[d]);
                            int nj = getIdx(cj + dj[d]);

                            if (!visited[ni][nj] && height[ni][nj] > 0) {
                                visited[ni][nj] = true;
                                queue.offer(new int[]{ni, nj});
                            }
                        }
                    }

                    for (int[] cur : list) {
                        gid[cur[0]][cur[1]] = sGid;
                    }
                }
            }
        }

        this.iceBlockCnt = splitCnt;
        for (int i = 1; i <= splitCnt; i++) {
            iceBlocks[i] = splits[i];
        }
    }

    private void move() {
        // 이동
        nextHeight = new int[N][N];
        nextGid = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (height[i][j] > 0) {
                    int gid = this.gid[i][j];
                    int dir = iceBlocks[gid].dir;

                    int ni = getIdx(i + di[dir]);
                    int nj = getIdx(j + dj[dir]);

                    if (height[i][j] > nextHeight[ni][nj]) {
                        nextHeight[ni][nj] = height[i][j];
                        nextGid[ni][nj] = gid;
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                height[i][j] = nextHeight[i][j];
                gid[i][j] = nextGid[i][j];
            }
        }
    }

    private void merge() {
        // 병합
        visited = new boolean[N][N];
        int mergeCnt = 0;
        IceBlock[] merges = new IceBlock[MAX_ICEBLOCK];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (height[i][j] > 0 && !visited[i][j]) {
                    int newGid = ++mergeCnt;
                    merges[newGid] = new IceBlock();

                    int bestPrevGid = 0;

                    queue.clear();
                    list.clear();
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;

                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();
                        list.add(cur);
                        int ci = cur[0];
                        int cj = cur[1];

                        int prevGid = gid[ci][cj];
                        if (isHigherPriority(prevGid, bestPrevGid)) {
                            bestPrevGid = prevGid;
                        }

                        for (int d = 0; d < 4; d++) {
                            int ni = getIdx(ci + di[d]);
                            int nj = getIdx(cj + dj[d]);

                            if (!visited[ni][nj] && height[ni][nj] > 0) {
                                visited[ni][nj] = true;
                                queue.offer(new int[]{ni, nj});
                            }
                        }
                    }

                    merges[newGid].dir = iceBlocks[bestPrevGid].dir;
                    for (int[] cur : list) {
                        gid[cur[0]][cur[1]] = newGid;
                    }
                }
            }
        }

        // 최종 병합된 빙하 정보 배열 갱신
        this.iceBlockCnt = mergeCnt;
        for (int i = 1; i <= mergeCnt; i++) {
            iceBlocks[i] = merges[i];
        }
    }
}
// 소요 시간: 5:15:59
