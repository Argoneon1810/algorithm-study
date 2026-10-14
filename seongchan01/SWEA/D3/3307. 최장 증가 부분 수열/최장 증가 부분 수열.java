import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D3. 최장 증가 부분 수열

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());

			int[] arr = new int[N + 1];

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}

			int[] dp = new int[N + 1];

			for (int i = 1; i <= N; i++) {
				dp[i] = 1;
			}

			for (int i = 1; i <= N; i++) {
				for (int j = 1; j < i; j++) {

					if (arr[i] > arr[j]) {
						dp[i] = Math.max(dp[i], dp[j] + 1);
					}
				}
			}

			int answer = 0;
			for (int i = 1; i <= N; i++) {
				answer = Math.max(answer, dp[i]);
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

}
