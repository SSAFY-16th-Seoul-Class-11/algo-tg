import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {

    public int solution(String numbers) {
        int[] arr = new int[numbers.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = numbers.charAt(i) - '0';
        }

        Arrays.sort(arr);

        Set<Integer> set = new HashSet<>();

        do {
            int num = 0;
            for (int i = 0; i < arr.length; i++) {
                num = num * 10 + arr[i];
                set.add(num);
            }
        } while (np(arr));

        int ans = 0;
        for (int num : set) {
            if (isPrime(num)) {
                ans++;
            }
        }

        return ans;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private boolean np(int[] arr) {
        int N = arr.length;

        int i = N - 1;
        while (i > 0 && arr[i - 1] >= arr[i]) {
            i--;
        }

        if (i == 0) return false;

        int j = N - 1;
        while (arr[i - 1] >= arr[j]) {
            j--;
        }
        swap(arr, i - 1, j);

        int k = N - 1;
        while (i < k) {
            swap(arr, i++, k--);
        }

        return true;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
