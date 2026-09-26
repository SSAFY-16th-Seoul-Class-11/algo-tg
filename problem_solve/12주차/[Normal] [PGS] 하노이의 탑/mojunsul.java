import java.util.ArrayList;
import java.util.List;

class Solution {

    public int[][] solution(int n) {
        List<int[]> list = new ArrayList<>();
        move(1, 3, n, list);
        int[][] answer = list.toArray(new int[list.size()][]);
        return answer;
    }

    private void move(int from, int to, int cnt, List<int[]> list) {
        if(cnt == 0) return;

        int rest = from ^ to;

        move(from, rest, cnt - 1, list);
        list.add(new int[]{from, to});
        move(rest, to, cnt - 1, list);
    }
}
