class Solution {

	static int K, L;

	public boolean solution(int[][] key, int[][] lock) {
		K = key.length;
		L = lock.length;

		int[][] rk = key;
		for (int r = 0; r < 4; r++) {
			if (check(lock, rk)) return true;
			rk = rotate(rk);
		}
		return false;
	}

	private boolean check(int[][] lock, int[][] key) {
		for (int si = 1 - L; si < K; si++) {
			for (int sj = 1 - L; sj < K; sj++) {
				if (fit(lock, key, si, sj)) return true;
			}
		}
		return false;
	}

	private boolean fit(int[][] lock, int[][] key, int si, int sj) {
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {
				if (lock[i][j] == getK(key, i + si, j + sj))
					return false;
			}
		}
		return true;
	}

	private int getK(int[][] key, int i, int j) {
		if (i < 0 || i >= K || j < 0 || j >= K) return 0;
		return key[i][j];
	}

	private int[][] rotate(int[][] key) {
		int[][] rk = new int[K][K];
		for (int i = 0; i < K; i++)
			for (int j = 0; j < K; j++)
				rk[j][K - 1 - i] = key[i][j];
		return rk;
	}
}
