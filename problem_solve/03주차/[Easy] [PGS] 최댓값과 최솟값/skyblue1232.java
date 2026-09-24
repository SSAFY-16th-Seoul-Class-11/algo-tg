class Solution {
    public String solution(String s) {
        String[] numbers = s.split(" ");

        int min = Integer.parseInt(numbers[0]);
        int max = Integer.parseInt(numbers[0]);

        for (int i = 1; i < numbers.length; i++) {
            int num = Integer.parseInt(numbers[i]);

            if (num < min) {
                min = num;
            }

            if (num > max) {
                max = num;
            }
        }

        return min + " " + max;
    }
}