/*
문제 정의

초기에는 1번부터 n번까지의 원소가
각각 서로 다른 집합에 속해 있습니다.

두 가지 연산을 수행해야 합니다.

0 a b
: a가 속한 집합과 b가 속한 집합을 하나로 합칩니다.
1 a b
: a와 b가 같은 집합에 속해 있는지 확인합니다.

같은 집합이라면 1, 다른 집합이라면 0을 출력하는 문제입니다.
*/


/*
접근 방법

집합을 합치고,
두 원소가 같은 집합에 속하는지 빠르게 확인해야 합니다.

따라서 Union-Find(Disjoint Set) 자료구조를 사용합니다.

1. parent[i]
  i번 원소의 부모를 저장합니다.
  처음에는 모든 원소가 서로 다른 집합이므로
  parent[i] = i로 초기화합니다.

2. find(x)
   x가 속한 집합의 대표 원소(root)를 찾습니다.
   경로 압축(Path Compression)을 사용해서
   한번 찾은 원소가 바로 대표 원소를 가리키도록 합니다.

3. union(a, b)
   a와 b의 대표 원소를 찾은 뒤,
   서로 다른 집합이라면 하나로 합칩니다.
   이때 size 배열을 이용하여
   작은 집합을 큰 집합 아래에 연결합니다.

4. 1 a b 연산에서는
   find(a) == find(b)인지 확인합니다.

   같다면 같은 집합이므로 1, 다르면 0을 출력
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    static int[] parent;
    static int[] size;

    // x가 속한 집합의 대표 원소 찾기
    static int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        // 경로 압축
        return parent[x] = find(parent[x]);
    }

    // 두 집합 합치기
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        // 이미 같은 집합
        if (rootA == rootB) {
            return;
        }

        // 작은 집합을 큰 집합에 연결
        if (size[rootA] < size[rootB]) {
            parent[rootA] = rootB;
            size[rootB] += size[rootA];
        } else {
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
        }
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
      
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            parent = new int[n + 1];
            size = new int[n + 1];

            // 처음에는 모든 원소가 서로 다른 집합
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }

            StringBuilder answer = new StringBuilder();
            answer.append("#").append(tc).append(" ");

            // m개의 연산 수행
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int command = Integer.parseInt(st.nextToken());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                // 합집합 연산
                if (command == 0) {
                    union(a, b);
                }
                // 같은 집합인지 확인
                else {
                    if (find(a) == find(b)) {
                        answer.append("1");
                    } else {
                        answer.append("0");
                    }
                }
            }
            System.out.println(answer);
        }
    }
}


/*
시간복잡도
n : 원소의 개수
m : 연산의 개수

Union-Find에서 find 연산은 경로 압축, union 연산은 size를 이용한 합치기를 사용합니다.
이 경우 한 번의 연산은 거의 O(1)에 가깝고, 정확하게는 O(alpha(n)) 정도입니다.
alpha(n)은 역 아커만 함수로, 실제 입력 범위에서는 거의 상수라고 생각할 수 있습니다.

m개의 연산을 수행하므로 전체 시간복잡도는 O(m * alpha(n)), 거의 O(m)입니다.

초기 parent, size 배열을 만드는 데 O(n)이 필요하므로 전체적으로는 O(n + m * alpha(n))으로 볼 수 있습니다.
*/
