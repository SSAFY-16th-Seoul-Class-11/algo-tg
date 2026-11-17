import java.util.ArrayList;
import java.util.List;

class Solution {

	static int N;
	static int[][] dice;
	static List<Integer>[] lists = new List[2];
	static int maxWin;
	static int maxMask;

	public int[] solution(int[][] dice) {
		N = dice.length;
		this.dice = dice;
		lists[0] = new ArrayList<>();
		lists[1] = new ArrayList<>();
		maxWin = -1;
		maxMask = 0;
		comb(0, 1, 1); //0번재 거를 포함

		int[] answer = new int[N/2];
		int idx = 0;
		for (int i = 0; i < N; i++) {
			if((maxMask & (1<<i)) != 0) answer[idx++] = i + 1;
		}
		return answer;
	}

	private void comb(int idx, int mask, int cnt) {
		if(cnt == N / 2) {
			evaluate(mask);
			return;
		}

		for (int i = idx + 1; i < N; i++) {
			comb(i, mask | (1<<i), cnt + 1);
		}
	}

	private void evaluate(int mask) {
		lists[0].clear();
		lists[1].clear();
		dfs(0, mask, 0, 0);
		dfs(0, ~mask, 0, 1);

		//compare
		int win = 0;
		int lose = 0;
		for (Integer a : lists[0]) {
			for (Integer b : lists[1]) {
				if(a > b) win++;
				else if(a < b) lose++;
			}
		}

		if(maxWin < win) {
			maxWin = win;
			maxMask = mask;
		}
		if(maxWin < lose) {
			maxWin = lose;
			maxMask = ~mask;
		}
	}

	private void dfs(int idx, int mask, int sum, int group) {
		if(idx == N){
			lists[group].add(sum);
			return;
		}

		if((mask & (1<<idx)) != 0) {
			for (int k = 0; k < 6; k++) {
				dfs(idx + 1, mask, sum + dice[idx][k], group);
			}
		}
		else {
			dfs(idx + 1, mask, sum, group);
		}
	}
}
