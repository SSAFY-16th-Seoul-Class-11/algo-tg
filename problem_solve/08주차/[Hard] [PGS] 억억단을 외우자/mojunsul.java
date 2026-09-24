class Solution {

	static class Node {
		int val, max;
		
		public Node() {}

		public Node(int val, int max) {
			this.val = val;
			this.max = max;
		}

		static public Node max(Node n1, Node n2) {
			if (n1.max >= n2.max) {
				return n1;
			}
			return n2;
		}
	}

	class SegmentTree {
		int size;
		int[] origin;
		Node[] tree;

		public SegmentTree(int size, int[] origin) {
			this.size = size;
			this.origin = origin;
			tree = new Node[size * 4];
			build(1, 0, size - 1);
		}

		private void build(int idx, int start, int end) {
			if (start == end) {
				tree[idx] = new Node(start, origin[start]);
				return;
			}

			int mid = (start + end) / 2;
			build(idx * 2, start, mid);
			build(idx * 2 + 1, mid + 1, end);

			tree[idx] = Node.max(tree[idx * 2], tree[idx * 2 + 1]);
		}

		public int query(int l, int r) {
			return query(1, 0, size - 1, l, r).val;
		}

		private Node query(int idx, int start, int end, int l, int r) {
			if(r < start || end < l) {
				return new Node();
			}
			
			if(l<=start && end <= r) {
				return tree[idx];
			}
			
			int mid = (start + end) / 2;
			Node leftQuery = query(idx * 2, start, mid, l, r);
			Node rightQuery = query(idx * 2 + 1, mid + 1, end, l, r);
			
			return Node.max(leftQuery, rightQuery);
		}
	}

	public int[] solution(int e, int[] starts) {

		int[] cnt = new int[e + 1];

		for (int i = 1; i <= e; i++) {
			int k = i;
			while (k <= e) {
				cnt[k]++;
				k += i;
			}
		}

		SegmentTree tree = new SegmentTree(e + 1, cnt);

		int[] answer = new int[starts.length];
		for (int i = 0; i < starts.length; i++) {
			int s = starts[i];
			answer[i] = tree.query(s, e);
		}

		return answer;
	}
}
