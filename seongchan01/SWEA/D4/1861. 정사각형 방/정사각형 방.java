import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 해석]
 * N^2개의 방이 NxN 형태
 * (i, j) 위치의 방에는 N^2 이하의 수 A(i,j)가 적혀 있고, 이 숫자는 모든 방에서 다름
 * 내가 어떤 방에 위치해 있으면, 상하좌우에 있는 다른 방으로 이동 가능
 * 이동하려는 방이 존재해야만 하고, 이동하려는 방에 적힌 숫자가 현재 방에 적힌 숫자보다 정확히 1 더 커야 한다.
 * 어떤 수가 적힌 방에 있어야 가장 많은 방을 이동할 수 있는지 구해라.
 *
 */

public class Solution { // D4. 정사각형 방

	static int N;
	static int[][] arr;
	static boolean[][] visit;

	static int[] dx = { 0, 0, -1, 1 };
	static int[] dy = { 1, -1, 0, 0 };

	static int room;
	static int answer;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			arr = new int[N][N];

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < N; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			room = N;
			answer = 0;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					visit = new boolean[N][N];
					dfs(i, j, 1, arr[i][j]);
				}
			}

			sb.append("#").append(tc).append(" ").append(room).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static void dfs(int x, int y, int depth, int start) {
		visit[x][y] = true;

		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if (nx < 0 || ny < 0 || nx >= N || ny >= N)
				continue;

			if (visit[nx][ny])
				continue;

			if (arr[nx][ny] != arr[x][y] + 1)
				continue;

			dfs(nx, ny, depth + 1, start);
		}

		if (depth > answer) {
			answer = depth;
			room = start;
		} else if (depth == answer) {
			room = Math.min(room, start);
		}

	}
}
