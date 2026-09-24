class skyblue1232 {
    public long solution(int[] sequence) {
        // int 범위 넘을 것 같아서 long
        long answer = Long.MIN_VALUE;

        long sum1 = 0;
        long sum2 = 0;

        for (int i = 0; i < sequence.length; i++) {
            long num1;
            long num2;

            if (i % 2 == 0) {
                num1 = sequence[i];
                num2 = -sequence[i];
            } else {
                num1 = -sequence[i];
                num2 = sequence[i];
            }

            sum1 = Math.max(num1, sum1 + num1);
            sum2 = Math.max(num2, sum2 + num2);

            answer = Math.max(answer, Math.max(sum1, sum2));
        }

        return answer;
    }
}

// [아이디어]
// 펄스 수열은 [1, -1, 1, -1, ...]과 [-1, 1, -1, 1, ...] 두 가지. 
// 두 경우를 각각 적용하면서 연속된 구간의 합이 가장 큰 경우를 찾으면 된다고 생각
// [문제 간단]
// 수열에서 연속된 부분을 고르고 1과 -1이 번갈아 나오는 값을 곱했을 때 나올 수 있는 가장 큰 합 구하기
// [풀이]
// 각 숫자에 두 종류의 펄스를 적용한 값을 구하고, 
// 이전까지 구한 합에 현재 값을 이어서 더할지, 현재 값부터 다시 시작할지 비교하고, 
// 이전 합이 오히려 현재 합을 작게 만들면 버리고 현재 값부터 새로 시작! 
// 이 과정을 두 펄스에 대입 반복하면서 가장 큰 값 저장

// 수열을 한 번만 확인 -> 시.복 O(N). 
// 별도 배열 없이 변수만 -> 공.복 O(1).
