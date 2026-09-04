class Solution {
    public long solution(int[] sequence) {
        int len = sequence.length;
        int[] seq1 = parseArr(sequence, len, 1);
        int[] seq2 = parseArr(sequence, len, -1);

        long max1 = 0;
        long max2 = 0;
        long answer = 0;
        for (int i = 0; i < len; i++) {
            max1 = Math.max(seq1[i], max1 + seq1[i]);
            max2 = Math.max(seq2[i], max2 + seq2[i]);

            answer = Math.max(answer, Math.max(max1, max2));
        }

        return answer;
    }

    private int[] parseArr(int[] seq, int len, int per) {
        int[] arr = new int[len];

        for (int i = 0; i < len; i++) {
            arr[i] = seq[i] * per;
            per *= -1;
        }

        return arr;
    }
}

시간 복잡도 O(n), 공간 복잡도 3n