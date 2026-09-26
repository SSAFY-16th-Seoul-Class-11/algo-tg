import java.util.HashSet;
import java.util.Set;

class Solution {

    static final int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    static Set<String> visitPoint = new HashSet<>(); // key: "r_c"
    static Set<String> visitEdge = new HashSet<>(); // key: "r1_c1_r2_c2" r1 < r2

    public int solution(int[] arrows) {
        int answer = 0;
        int r = 0;
        int c = 0;
        String key = r + "_" + c;
        visitPoint.add(key);
        for (int i = 0; i < arrows.length; i++) {
            int dir = arrows[i];
            for (int j = 0; j < 2; j++) {
                int nr = r + dr[dir];
                int nc = c + dc[dir];

                int r1, c1, r2, c2;
                if (r < nr || r == nr && c < nc) {
                    r1 = r;
                    c1 = c;
                    r2 = nr;
                    c2 = nc;
                }
                else {
                    r1 = nr;
                    c1 = nc;
                    r2 = r;
                    c2 = c;
                }
                key = r1 + "_" + c1 + "_" + r2 + "_" + c2;
                if (!visitEdge.contains(key)) {
                    visitEdge.add(key);
                    key = nr + "_" + nc;
                    if (visitPoint.contains(key)) answer++;
                    else visitPoint.add(key);
                }
                r = nr;
                c = nc;
            }
        }
        return answer;
    }
}
