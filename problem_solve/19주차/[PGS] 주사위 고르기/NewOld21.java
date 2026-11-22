
import java.util.*;

class Solution {

    static int N, mx;
    static int[][] dice;
    static boolean[] check;
    static int[] answer;

    public int[] solution(int[][] dice) {
        this.dice = dice;
        N = dice.length;
        mx = -1;

        check = new boolean[N];
        answer = new int[N / 2];

        dfs(0, 0);

        return answer;
    }

    private static void dfs(int start, int depth) {

        if (depth == N / 2) {
            List<Integer> a = new ArrayList<>();
            List<Integer> b = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                if (check[i]) a.add(i);
                else b.add(i);
            }

            List<Integer> sumA = new ArrayList<>();
            List<Integer> sumB = new ArrayList<>();

            roll(a, 0, 0, sumA);
            roll(b, 0, 0, sumB);

            Collections.sort(sumB);

            int win = 0;

            for (int sum : sumA) {
                win += lowerBound(sumB, sum);
            }

            if (win > mx) {
                mx = win;

                for (int i = 0; i < a.size(); i++) {
                    answer[i] = a.get(i) + 1;
                }
            }
            return;
        }

        for (int i = start; i < N; i++) {
            if (!check[i]) {
                check[i] = true;
                dfs(i + 1, depth + 1);
                check[i] = false;
            }
        }
    }

    private static void roll(List<Integer> arr, int depth,
                             int sum, List<Integer> result) {

        if (depth == arr.size()) {
            result.add(sum);
            return;
        }

        int idx = arr.get(depth);

        for (int i = 0; i < 6; i++) {
            roll(arr, depth + 1, sum + dice[idx][i], result);
        }
    }

    private static int lowerBound(List<Integer> arr, int sum) {
        int left = 0;
        int right = arr.size();

        while (left < right) {
            int mid = (left + right) / 2;

            if (arr.get(mid) < sum) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
