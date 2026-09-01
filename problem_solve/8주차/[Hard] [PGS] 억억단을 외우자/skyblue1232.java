class Solution {
    public int[] solution(int e, int[] starts) {
        int[] cnt = new int[e + 1];
        int[] best = new int[e + 1];
        int[] answer = new int[starts.length];

        for (int i = 1; i <= e; i++) 
          for (int j = i; j <= e; j += i) cnt[j]++;
      
        best[e] = e;

        for (int i = e - 1; i >= 1; i--) best[i] = cnt[i] >= cnt[best[i + 1]] ? i : best[i + 1];
        for (int i = 0; i < starts.length; i++) answer[i] = best[starts[i]];

        return answer;
    }
}

/*
[문제 간단 해석]
억억단에서 숫자 n이 등장하는 횟수는 n의 약수 개수와 같다.
각 start마다 [start, e] 범위에서 가장 많이 등장하는 숫자를 찾는다.
등장 횟수가 같다면 더 작은 숫자를 선택한다.

[아이디어]
1. cnt[i]에 i의 약수 개수를 저장한다.
2. 매 start마다 범위를 전부 탐색하면 비효율적이므로,
   뒤에서부터 [i, e] 범위의 정답을 미리 구한다.
3. best[i]에는 i부터 e까지 중 가장 많이 등장하는 숫자를 저장한다.
4. 등장 횟수가 같으면 더 작은 숫자를 선택해야 하므로
   cnt[i] >= cnt[best[i + 1]]일 때 i를 선택한다.
5. 각 start의 정답은 best[start]로 바로 구한다.

[문제 풀이]
- i의 배수 j는 모두 i를 약수로 가지므로 cnt[j]를 증가시킨다.
- e부터 역순으로 탐색하면서 현재 숫자 i와
  기존 정답 best[i + 1]의 등장 횟수를 비교한다.
- 현재 숫자의 등장 횟수가 더 크거나 같으면 i를 저장하고,
  작으면 기존 정답을 그대로 저장한다.
- 마지막으로 starts의 각 값에 대해 best[start]를 answer에 넣는다.
*/

// 시간복잡도: O(e log e)
// 공간복잡도: O(e + starts.length) -> O(e)
