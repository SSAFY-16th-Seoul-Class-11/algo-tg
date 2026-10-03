import java.util.HashSet;
import java.util.Set;

class Solution {
	public String solution(String play_time, String adv_time, String[] logs) {
    	int size = toSecond(play_time);
    	int adv = toSecond(adv_time);
    	
    	int[] viewDiff = new int[size+1];
    	
    	for (String log : logs) {
			String[] time = seperateTime(log);
			int start = toSecond(time[0]);
			int end = toSecond(time[1]);
			viewDiff[start]++;
			viewDiff[end]--;
		}
    	
    	// 1차 누적합 : 특정 초에 재생 중인 시청자 수
    	int [] view = new int[size+1];
    	view[0] = viewDiff[0];
    	for (int i = 1; i <= size; i++) {
    		view[i] = view[i-1] + viewDiff[i];
		}
    	
    	// 2차 누적합 : 0초부터 해당 초까지의 누적 시청 시간
    	long[] viewSum = new long[size+1];
    	viewSum[0] = view[0];
    	for (int i = 1; i <= size; i++) {
    		viewSum[i] = viewSum[i-1] + view[i];
		}
    	
    	long maxSecond = viewSum[adv-1];
    	int maxIdx = 0;
    	for(int i=0; i+adv <= size; i++) {
    		if(maxSecond < viewSum[i+adv]-viewSum[i]) {
    			maxSecond = viewSum[i+adv]-viewSum[i];
    			maxIdx = i + 1;
    		}
    	}
    	
    	return toTime(maxIdx);
    }

	private int toSecond(String time) {
		String[] split = time.split(":");
		int hour = Integer.parseInt(split[0]);
		int minute = Integer.parseInt(split[1]);
		int second = Integer.parseInt(split[2]);

		return hour * 3600 + minute * 60 + second;
	}
	
	private String toTime(int second) {
		StringBuilder sb = new StringBuilder();
		
		int h = second;
		int s = h % 60;
		h /= 60;
		int m = h % 60;
		h /= 60;
		sb.append(String.format("%02d", h)).append(":").append(String.format("%02d", m)).append(":").append(String.format("%02d", s));
		return sb.toString();
	}

	private String[] seperateTime(String intervalTime) {
    	String[] split = intervalTime.split("-");
    	return new String[] {split[0], split[1]};
	}
}
