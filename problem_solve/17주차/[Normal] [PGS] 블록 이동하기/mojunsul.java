import java.util.ArrayDeque;
import java.util.Queue;

class Solution {

	// {dr, dc, dir}
	static final Node[] hd = {
		new Node(0, -1, 0), // left
		new Node(0, 1, 0), // right
		new Node(-1, 0, 0), new Node(0, -1, 1), new Node(0, 0, 1), // up
		new Node(1, 0, 0), new Node(1, -1, 1), new Node(1, 0, 1) // down
	};
	static final Node[] vd = {
		new Node(-1, 0, 1), // up
		new Node(1, 0, 1),  // down
		new Node(0, -1, 1), new Node(-1, 0, 0), new Node(0, 0, 0),  // left
		new Node(0, 1, 1), new Node(-1, 1, 0), new Node(0, 1, 0)// right
	};

	static int N;
	static int[][] map;
	static boolean[][][] visited;
	static Queue<Node> queue;

	static void move(Node cur, Node d) {
		int nr = cur.r + d.r, nc = cur.c + d.c, nd = d.dir;
		if (!visited[nr][nc][nd])
			enQueue(nr, nc, nd, cur.cnt);
	}

	static void enQueue(int nr, int nc, int nd, int cnt) {
		visited[nr][nc][nd] = true;
		queue.offer(new Node(nr, nc, nd, cnt + 1));
	}

	static boolean isIn(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

	public int solution(int[][] board) {
		map = board;
		N = map.length;

		visited = new boolean[N][N][2];
		queue = new ArrayDeque<>();

		visited[0][1][0] = true;
		queue.offer(new Node(0, 1, 0, 0));

		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			if (cur.r == N - 1 && cur.c == N - 1)
				return cur.cnt;

			if (cur.dir == 0) { // 가로
				if (isIn(cur.r, cur.c - 2) && map[cur.r][cur.c - 2] == 0)
					move(cur, hd[0]);
				if (isIn(cur.r, cur.c + 1) && map[cur.r][cur.c + 1] == 0)
					move(cur, hd[1]);
				if (isIn(cur.r - 1, cur.c) && map[cur.r - 1][cur.c] == 0 && map[cur.r - 1][cur.c - 1] == 0)
					for (int i = 2; i < 5; i++)
						move(cur, hd[i]);
				if (isIn(cur.r + 1, cur.c) && map[cur.r + 1][cur.c] == 0 && map[cur.r + 1][cur.c - 1] == 0)
					for (int i = 5; i < 8; i++)
						move(cur, hd[i]);
			}
			else { // 세로
				if (isIn(cur.r - 2, cur.c) && map[cur.r - 2][cur.c] == 0)
					move(cur, vd[0]);
				if (isIn(cur.r + 1, cur.c) && map[cur.r + 1][cur.c] == 0)
					move(cur, vd[1]);
				if (isIn(cur.r, cur.c - 1) && map[cur.r][cur.c - 1] == 0 && map[cur.r - 1][cur.c - 1] == 0)
					for (int i = 2; i < 5; i++)
						move(cur, vd[i]);
				if (isIn(cur.r, cur.c + 1) && map[cur.r][cur.c + 1] == 0 && map[cur.r - 1][cur.c + 1] == 0)
					for (int i = 5; i < 8; i++)
						move(cur, vd[i]);
			}
		}
		return -1;
	}

	static class Node {
		int r, c, dir, cnt;

		public Node(int r, int c, int dir) {
			this.r = r;
			this.c = c;
			this.dir = dir;
		}

		public Node(int r, int c, int dir, int cnt) {
			this(r, c, dir);
			this.cnt = cnt;
		}
	}
}
