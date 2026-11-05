import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {

	static int N, leafCnt, fulfilled;
	static List<List<Integer>> graph = new ArrayList<>();
	static int[] ptr, cnt, target;
	static List<Integer> order = new ArrayList<>();

	public int[] solution(int[][] edges, int[] target) {
		init(edges, target);
		return solve();
	}

	private static void init(int[][] edges, int[] target) {
		Solution.target = target;
		N = target.length;

		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] e : edges) {
			int parent = e[0] - 1;
			int child = e[1] - 1;
			graph.get(parent).add(child);
		}
		for (List<Integer> c : graph) {
			Collections.sort(c);
		}

		ptr = new int[N];
		cnt = new int[N];

		leafCnt = 0;
		fulfilled = 0;
		for (int i = 0; i < N; i++) {
			if (graph.get(i).isEmpty()) {
				leafCnt++;
				if (target[i] == 0)
					fulfilled++;
			}
		}
	}

	private static int[] solve(){
		while (fulfilled < leafCnt) {
			int cur = 0;
			while (!graph.get(cur).isEmpty()) {
				List<Integer> children = graph.get(cur);
				int next = children.get(ptr[cur]);
				ptr[cur] = (ptr[cur] + 1) % children.size();
				cur = next;
			}
			order.add(cur);
			cnt[cur]++;

			// 상한 초과
			if (cnt[cur] > target[cur]) {
				return new int[] {-1};
			}

			// 하한 처음 만족
			if (cnt[cur] * 3 >= target[cur] && (cnt[cur] - 1) * 3 < target[cur])
				fulfilled++;
		}

		int[] answer = new int[order.size()];
		for (int i = 0; i < order.size(); i++) {
			int leaf = order.get(i);
			/**
			 * 1,2,3 중 몇 떨어 뜨릴지 greedy 하게 체크
			 * 남은 cnt-1번을 전부 3으로 채운다고 가정, 그러고도 모자란 만큼 채우기
 			 */
			int num = Math.max(1, target[leaf] - 3 * (cnt[leaf] - 1));
			answer[i] = num;
			target[leaf] -= num;
			cnt[leaf]--;
		}

		return answer;
	}
}
