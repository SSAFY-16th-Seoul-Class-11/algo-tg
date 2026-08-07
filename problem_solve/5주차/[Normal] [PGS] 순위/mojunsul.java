import java.util.*;

class Solution {

    static List<List<Integer>> upGraph, downGraph;
    static int[] upCnt, downCnt;
    boolean[] isVisited;

    public int solution(int n, int[][] results) {
        upGraph = new ArrayList<>();
        downGraph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            upGraph.add(new ArrayList<>());
            downGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < results.length; i++) {
            int win = results[i][0];
            int lose = results[i][1];
            upGraph.get(lose).add(win);
            downGraph.get(win).add(lose);
        }

        upCnt = new int[n + 1];
        downCnt = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            isVisited = new boolean[n + 1];
            isVisited[i] = true;
            dfs(i, true, i);
            isVisited = new boolean[n + 1];
            isVisited[i] = true;
            dfs(i, false, i);
        }

        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            if (upCnt[i] + downCnt[i] == n - 1) {
                cnt++;
            }
        }
        return cnt;
    }

    private void dfs(int cur, boolean isUp, int root) {
        List<List<Integer>> graph = isUp ? upGraph : downGraph;
        int[] cnt = isUp ? upCnt : downCnt;

        for (Integer next : graph.get(cur)) {
            if(!isVisited[next]){
                isVisited[next] = true;
                cnt[root]++;
                dfs(next, isUp, root);
            }
        }
    }
}
