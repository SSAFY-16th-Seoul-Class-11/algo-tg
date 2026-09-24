import java.io.*;

public class skyblue12 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String input = br.readLine();

            int stickCount = 0;
            int result = 0;

            for (int i = 0; i < input.length(); i++) {
                char current = input.charAt(i);

                if (current == '(') {
                    stickCount++;
                } else {
                    stickCount--;

                    if (input.charAt(i - 1) == '(') {
                        result += stickCount;
                    } else {
                        result++;
                    }
                }
            }

            answer.append('#')
                  .append(tc)
                  .append(' ')
                  .append(result)
                  .append('\n');
        }

        System.out.print(answer);
    }
}