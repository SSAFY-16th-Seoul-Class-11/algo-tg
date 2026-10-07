import java.util.ArrayDeque;
import java.util.StringTokenizer;

class Solution {

	static int size, cur;
	boolean[] isRemoved;
	int[] next, prev;
	static ArrayDeque<Integer> stack = new ArrayDeque<>();

	public String solution(int n, int k, String[] cmd) {
		stack.clear();
		isRemoved = new boolean[n];
		next = new int[n];
		prev = new int[n];

		for (int i = 0; i < n; i++) {
			next[i] = i + 1;
			prev[i] = i - 1;
		}

		cur = k;
		size = n;

		for (String c : cmd) {
			StringTokenizer st = new StringTokenizer(c);

			char op = st.nextToken().charAt(0);

			int l;
			switch (op) {
			case 'U':
				l = Integer.parseInt(st.nextToken());
				move(l, -1);
				break;
			case 'D':
				l = Integer.parseInt(st.nextToken());
				move(l, 1);
				break;
			case 'C':
				remove();
				break;
			default:
				undo();
			}
			int a = 0;
		}

		StringBuilder sb = new StringBuilder();
		for (boolean b : isRemoved) {
			sb.append(b ? "X" : "O");
		}
		return sb.toString();
	}

	private void move(int len, int dir) {
		for (int i = 0; i < len; i++) {
			if (dir == 1) {
				cur = next[cur];
			} else {
				cur = prev[cur];
			}
		}
	}

	private void remove() {
		int n = next[cur];
		int p = prev[cur];

		isRemoved[cur] = true;
		stack.push(cur);
		
		if(isIn(p)) next[p] = n;
		if(isIn(n)) prev[n] = p;
		
	    cur = isIn(n) ? n : p;
	}

	private void undo() {
	    int restore = stack.pop();
	    isRemoved[restore] = false;

	    int p = prev[restore];
	    int n = next[restore];

	    if (isIn(p)) next[p] = restore;
	    if (isIn(n)) prev[n] = restore;
	}

	private boolean isIn(int i) {
		return i >= 0 && i < size;
	}

	public static void main(String[] args) {
		int n = 8;
		int k = 2;
		String[] cmd = { "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C" };
		Solution s = new Solution();
		System.out.println(s.solution(n, k, cmd));
	}
}
