class Solution {
    public int solution(int n, int[][] computers) {
        int[] IsConnected = new int[n];
        int answer = 0;

        for (int i = 0; i < n; i++) {
            if (IsConnected[i] == 0) {
                answer++;

                int[] stack = new int[n];
                int top = 0;

                stack[top++] = i;
                IsConnected[i] = 1;

                while (top > 0) {
                    int current = stack[--top];

                    for (int next = 0; next < n; next++) {
                        if (computers[current][next] == 1 && IsConnected[next] == 0) {
                            IsConnected[next] = 1;
                            stack[top++] = next;
                        }
                    }
                }
            }
        }

        return answer;
    }
}
