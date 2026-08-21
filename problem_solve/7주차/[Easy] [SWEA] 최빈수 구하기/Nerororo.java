
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            br.readLine();

            int[] num = init(br);
            int answer = bucketSort(num);

            sb.append('#')
                    .append(test_case)
                    .append(' ')
                    .append(answer)
                    .append('\n');
        }

        System.out.println(sb.toString());
    }

    public static int[] init(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] numArr = new int[st.countTokens()];

        int i = 0;
        while (st.hasMoreTokens()) {
            numArr[i++] = Integer.parseInt(st.nextToken());
        }
        return numArr;
    }

    public static int bucketSort(int[] num) {
        int n = num.length;
        if (n == 0) return 0;

        /*
        Bucket 정렬은 내부적으로 삽입 정렬이 일어나기 때문에
        ArrayList대신 빈번한 삽입, 이동에 좋은 LinkedList를 사용한다.
        */
        LinkedList<Integer>[] bucket = new LinkedList[101];
        for (int i = 0; i <= 100; i++) {
            bucket[i] = new LinkedList<>();
        }

        for (int i = 0; i < n; i++) {
            int idx = num[i];
            bucket[idx].add(num[i]);
        }

//        정렬이 필요하다면 각각 LinkedList에 대해 정렬한다.
//        for (int i = 0; i < len; i++) {
//            Collections.sort(bucket[i]);
//        }

        int answer = 0;
        int maxLen = 0;
        for (int i = 0; i <= 100; i++) {
            int len = bucket[i].size();
            if (len >= maxLen) {
                answer = i;
                maxLen = len;
            }
        }
        return answer;
    }
}

/*
Bucket 정렬은 데이터가 특정 범위 안에 균등하게 분포되어 있다는 가정 하에 매우 유용하게 사용 가능하다.

버킷이 b개이고 원소가 균등하게 분포한다고 하면, 각 버킷의 평균 원소 수는 'n / b' 이다.
삽입 정렬 비용은 각 버킷마다 'O((n/b)²)' 이고, 버킷이 b개니까 b × O((n/b)²) 이므로 'O(n²/b)' 가 된다.
여기에 버킷에 원소를 분배하는 과정 O(n)을 더하면, O(n + n²/b) 가 된다.
따라서 버킷의 개수가 충분히 많고 원소가 균등하게 분포한다면 O(n)에 가까워질 수 있다.
>> b, 즉 버킷 수가 n과 동일하다면 완벽히 O(n)이 된다.
 */