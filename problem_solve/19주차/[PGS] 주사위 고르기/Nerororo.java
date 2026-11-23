
class Solution {
    static int N;
    static int[][] dices;
    static int[] arr, answer;
    static boolean[] visited;
    static long max;

    public int[] solution(int[][] dice) {
        N = dice.length;
        dices = dice;

        arr = new int[N / 2];
        answer = new int[N / 2];
        visited = new boolean[N];
        max = -1;

        dfs(0, 0);

        return answer;
    }

    static void dfs(int idx, int cnt) {
        if (cnt == N / 2) {
            int[] other = new int[N / 2];
            int len = 0;

            for (int i = 0; i < N; i++) {
                if (!visited[i]) other[len++] = i;
            }

            int[] aCnt = new int[501];
            int[] bCnt = new int[501];

            getSum(arr, 0, 0, aCnt);
            getSum(other, 0, 0, bCnt);

            long win = 0;
            int sum = 0;

            for (int i = 0; i <= 500; i++) {
                win += (long) aCnt[i] * sum;
                sum += bCnt[i];
            }

            if (win > max) {
                max = win;

                for (int i = 0; i < N / 2; i++) {
                    answer[i] = arr[i] + 1;
                }
            }

            return;
        }

        for (int i = idx; i < N; i++) {
            visited[i] = true;
            arr[cnt] = i;

            dfs(i + 1, cnt + 1);

            visited[i] = false;
        }
    }

    static void getSum(int[] arr, int idx, int sum, int[] cnt) {
        if (idx == arr.length) {
            cnt[sum]++;
            return;
        }

        for (int i = 0; i < 6; i++) {
            getSum(arr, idx + 1, sum + dices[arr[idx]][i], cnt);
        }
    }
}
