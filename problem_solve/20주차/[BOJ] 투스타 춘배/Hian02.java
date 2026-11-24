/*
문제 정의

N개의 산이 트리 형태로 연결되어 있고 P번 산에 N명의 병사가 있습니다.
각 산 i의 현재 높이는 A[i], 목표 높이는 B[i]입니다.

병사는 다음 작업을 할 수 있습니다.
1. 산을 X만큼 깎으면 흙 X를 얻습니다.
2. 가지고 있는 흙 X를 사용하여 산을 X만큼 높입니다.
3. 흙 X를 구매하면 X원의 비용이 듭니다.
4. 연결된 산으로 이동할 수 있지만 자신이 이미 지나온 길은 다시 지나갈 수 없습니다.

모든 산의 높이를 목표 높이로 만들기 위해 필요한 최소 비용을 구하는 문제입니다.

P번 산을 루트로 생각하면 병사들은 부모 -> 자식 방향으로 이동하게 됩니다.
자식 방향으로 내려간 병사는 다시 부모로 올라올 수 없으므로 자식 서브트리에서 남은 흙을 부모에게 다시 가져올 수 없습니다.
반대로 현재 노드에서 얻은 흙은 아직 자식으로 이동하기 전이므로 여러 병사가 나누어 여러 자식 서브트리로 가져갈 수 있습니다.
*/


/*
접근 방법

트리 DP를 사용합니다.

P번 산을 루트로 만들고 need[u] = u의 서브트리를 모두 목표 높이로 만들기 위해 u의 부모 쪽에서 가져와야 하는 최소 흙의 양

먼저 현재 산 u만 생각해보겠습니다.
B[u] > A[u]이면 B[u] - A[u]만큼 흙이 필요합니다.
B[u] < A[u]이면 A[u] - B[u]만큼 산을 깎아서 흙을 얻을 수 있습니다.

따라서 현재 산의 흙 부족량을 B[u] - A[u]라고 표현할 수 있습니다.

그리고 각 자식 서브트리에서 필요한 흙 need[child]를 모두 더합니다.
value = B[u] - A[u] + sum(need[child])
value > 0이라면 현재 산에서 얻을 수 있는 흙을 사용해도 u의 전체 서브트리에서 value만큼 흙이 부족하다는 의미입니다.
따라서 부모에게서 value만큼 가져와야 합니다.

need[u] = value, value <= 0이라면
현재 산을 깎아서 얻은 흙만으로 자식들의 부족분까지 모두 해결할 수 있다는 뜻입니다.
남는 흙이 존재할 수도 있지만 그 흙을 부모 방향으로 가져갈 수는 없습니다.
병사는 부모 -> u로 이동할 때 이미 그 길을 지나왔기 때문입니다.
따라서 need[u] = 0입니다.

결론적으로 점화식은 need[u] = max(0, B[u] - A[u] + sum(need[child]))입니다.

마지막으로 P는 루트이므로 부모가 없습니다.
따라서 need[P]만큼의 흙은 외부에서 실제로 구매해야 합니다.
흙 X를 구매하면 X원이 필요하므로 정답 = need[P]입니다.

N <= 100,000이므로 Java에서 재귀 DFS를 사용하면 StackOverflowError가 발생할 수 있습니다.

따라서 반복 DFS로 부모 관계와 방문 순서를 만든 뒤,
방문 순서를 역순으로 확인하면서 자식부터 DP를 계산합니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Main {
    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];

        private int ptr = 0;
        private int len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) {
                    return -1;
                }
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }
            return value * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        /*
        N : 산의 개수
        P : 병사들이 처음 있는 산
        */
        int N = fs.nextInt();
        int P = fs.nextInt();

        int[] A = new int[N + 1];
        int[] B = new int[N + 1];

        /*
        현재 높이
        */
        for (int i = 1; i <= N; i++) {
            A[i] = fs.nextInt();
        }

        /*
        목표 높이
        */
        for (int i = 1; i <= N; i++) {
            B[i] = fs.nextInt();
        }

        /*
        트리 생성
        */
        ArrayList<Integer>[] graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }

        /*
        P를 루트로 만들기 위한 부모 배열
        */
        int[] parent = new int[N + 1];

        /*
        DFS 방문 순서
        나중에 역순으로 읽으면 자식 -> 부모 순서가 됩니다.
        */
        int[] order = new int[N];
        int orderSize = 0;

        /*
        반복 DFS
        */
        int[] stack = new int[N];
        int top = 0;
        stack[top++] = P;
        parent[P] = -1;

        while (top > 0) {
            int current = stack[--top];
            order[orderSize++] = current;
            for (int next : graph[current]) {
                /*
                부모로 다시 이동하지 않습니다.
                */
                if (next == parent[current]) {
                    continue;
                }
                parent[next] = current;
                stack[top++] = next;
            }
        }

        /*
        need[u] = u의 서브트리를 완성하기 위해
                  부모에서 가져와야 하는 최소 흙의 양
        */
        long[] need = new long[N + 1];

        /*
        방문 순서를 역순으로 확인하면 자식부터 계산할 수 있습니다.
        */
        for (int i = orderSize - 1; i >= 0; i--) {
            int current = order[i];

            /*
            현재 산에서 필요한 흙의 양입니다.
            양수 : 흙이 필요함
            음수 : 산을 깎아서 흙을 얻을 수 있음
            */
            long value = (long) B[current] - A[current];

            /*
            자식 서브트리가 부모에게 요구하는 흙을 모두 더합니다.
            */
            for (int next : graph[current]) {
                if (parent[next] == current) {
                    value += need[next];
                }
            }

            /*
            value > 0이면 부모에서 그만큼 가져와야 합니다.
            value <= 0이면 현재 서브트리 내부의 흙으로 모두 해결할 수 있습니다.
            남는 흙은 부모 방향으로 다시 가져갈 수 없으므로 0으로 처리합니다.
            */
            need[current] = Math.max(0L, value);
        }

        /*
        루트 P에 필요한 흙은 외부에서 구매해야 합니다.
        흙 1당 1원이므로 need[P]가 최소 비용입니다.
        */
        System.out.println(need[P]);
    }
}


/*
시간복잡도

트리는 N개의 정점과 N - 1개의 간선을 가집니다.
P를 루트로 만드는 DFS에서 모든 정점과 간선을 한 번씩 확인하므로 O(N)입니다.
역순 DP에서도 모든 정점과 간선을 한 번씩 확인하므로 O(N)입니다.
따라서 전체 시간복잡도는 O(N)입니다.

공간복잡도

인접 리스트 : O(N)
parent 배열 : O(N)
order 배열 : O(N)
stack 배열 : O(N)
need 배열 : O(N)
따라서 전체 공간복잡도는 O(N)입니다.


핵심 정리

1. 병사들이 시작하는 P번 산을 루트로 잡습니다.
2. 병사는 이미 지나온 길을 다시 갈 수 없으므로 흙의 이동 방향은 부모 -> 자식입니다.
3. B[u] - A[u]가 양수이면 현재 산에 필요한 흙이고 음수이면 현재 산을 깎아 얻을 수 있는 흙입니다.
4. 각 자식 서브트리가 필요로 하는 need[child]를 현재 노드에 모두 더합니다.
5. need[u] = max(0, B[u] - A[u] + sum(need[child]))입니다.
6. 현재 노드에서 남는 흙은 여러 병사가 나누어 자식들에게 가져갈 수 있습니다.
7. 하지만 자식 서브트리에서 남은 흙은 이미 지나온 길을 거슬러 부모에게 가져올 수 없습니다.
8. 따라서 각 노드에서 음수가 되면 0으로 잘라야 합니다.
9. 최종 need[P]가 외부에서 구매해야 하는 최소 흙이며 곧 최소 비용입니다.
*/
