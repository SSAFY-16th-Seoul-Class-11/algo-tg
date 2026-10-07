class Solution {
	
	static int ans, len;
	static int[] lis;
	
    public int lengthOfLIS(int[] nums) {
    	len = 0;
    	lis = new int[nums.length];
    	
    	for (int n : nums) {
			if(len == 0 || lis[len - 1] < n) {
				lis[len] = n;
				len++;
			}
			else {
				int idx = getBiggerIdx(n);
				lis[idx] = n;
			}
		}
    	
        return len;
    }
    
    // n보다 크거나 같은 수의 인덱스 binary search로
    private int getBiggerIdx(int n) { 
    	int left = 0;
    	int right = len - 1;
    	
    	while (left <= right) {
            int mid = (right + left) / 2;

            if (lis[mid] == n) return mid;
            else if (lis[mid] < n) left = mid + 1;
            else right = mid - 1;
        }
    	
    	return left;
    }
}
