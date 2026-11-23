import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class Solution {

	private static final int[] dr = {0, 0, -1, 1};
	private static final int[] dc = {-1, 1, 0, 0};
	private static final int EMPTY = -1;

	private int N, full;
	private int[][] map;
	private int[][][] dist;
	private Queue<Node> queue;

	public int solution(int[][] board, int r, int c) {
		init(board);

		visit(r, c, 0, 0);

		while (!queue.isEmpty()) {
			Node cur = queue.poll();

			if (cur.mask == full) {
				return cur.cnt;
			}

			for (int d = 0; d < 4; d++) {
				moveOne(cur, d);
				moveCtrl(cur, d);
			}
			flip(cur);
		}
		return -1;
	}

	private void init(int[][] board) {
		N = board.length;
		map = new int[N][N];
		dist = new int[N][N][1 << 12];

		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Arrays.fill(dist[i][j], Integer.MAX_VALUE);

				int value = board[i][j];
				if (value == 0) {
					map[i][j] = EMPTY;
					continue;
				}

				if (!set.contains(value)) {
					set.add(value);
					map[i][j] = value * 2 - 2;  // 첫 번째 카드
				} else {
					map[i][j] = value * 2 - 1;  // 두 번째 카드
				}
				full |= 1 << map[i][j];
			}
		}
		queue = new ArrayDeque<>();
	}

	private void moveOne(Node cur, int d) {
		int nr = cur.r + dr[d];
		int nc = cur.c + dc[d];

		if (isIn(nr, nc)) {
			visit(nr, nc, cur.mask, cur.cnt + 1);
		}
	}

	private void moveCtrl(Node cur, int d) {
		int nr = cur.r + dr[d];
		int nc = cur.c + dc[d];

		while (isIn(nr, nc) && isBlank(nr, nc, cur.mask)) {
			nr += dr[d];
			nc += dc[d];
		}
		if (!isIn(nr, nc)) {
			nr -= dr[d];
			nc -= dc[d];
		}
		visit(nr, nc, cur.mask, cur.cnt + 1);
	}

	private void flip(Node cur) {
		int card = map[cur.r][cur.c];
		if (card == EMPTY)
			return;

		boolean isFirstFlip = Integer.bitCount(cur.mask) % 2 == 0;
		boolean isPairFlipped = (cur.mask & (1 << (card ^ 1))) != 0;

		if (isFirstFlip || isPairFlipped) {
			visit(cur.r, cur.c, cur.mask | (1 << card), cur.cnt + 1);
		}
	}

	// 더 짧은 경로로 처음 도달하는 상태만 큐에 추가
	private void visit(int r, int c, int mask, int cnt) {
		if (dist[r][c][mask] <= cnt)
			return;
		dist[r][c][mask] = cnt;
		queue.offer(new Node(r, c, mask, cnt));
	}

	private boolean isBlank(int r, int c, int mask) {
		return map[r][c] == EMPTY || (mask & (1 << map[r][c])) != 0;
	}

	private boolean isIn(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

	private static class Node {
		final int r, c, mask, cnt;

		Node(int r, int c, int mask, int cnt) {
			this.r = r;
			this.c = c;
			this.mask = mask;
			this.cnt = cnt;
		}
	}
}
