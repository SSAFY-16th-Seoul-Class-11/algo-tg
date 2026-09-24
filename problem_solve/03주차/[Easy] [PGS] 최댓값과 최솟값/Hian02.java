class Solution {
    public String solution(String str) {
        String[] num_list = str.split(" ");

        int min = Integer.MAX_VALUE; // 최솟값은 초기에 최댓값으로 설정
        int max = Integer.MIN_VALUE; // 최댓값은 초기에 최솟값으로 설정

        for (String num : num_list) {
            int n = Integer.parseInt(num); // 문자열을 숫자로 반환

            if (n < min) {
                min = n;
            }
            if (n > max) {
                max = n;
            }
        }

        return min + " " + max;
    }
}
