import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D3. 0/1 Knapsack

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			StringTokenizer st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			int[] V = new int[N + 1];
			int[] C = new int[N + 1];

			for (int i = 1; i <= N; i++) {
				st = new StringTokenizer(br.readLine());

				V[i] = Integer.parseInt(st.nextToken()); // 부피
				C[i] = Integer.parseInt(st.nextToken()); // 가치
			}

			int dp[][] = new int[N + 1][K + 1];

			for (int i = 1; i <= N; i++) {
				for (int w = 0; w <= K; w++) {

					// 못 담을때
					if (V[i] > w) {
						dp[i][w] = dp[i - 1][w];
					}
					// 가능
					else {
						dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - V[i]] + C[i]);
					}
				}
			}

			sb.append("#").append(tc).append(" ").append(dp[N][K]).append("\n");
		}

		System.out.println(sb);
	}

}
