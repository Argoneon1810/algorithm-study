import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {

	static int N, M, X;
	static ArrayList<int[]>[] graph;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());

			graph = new ArrayList[N + 1];
			for (int i = 1; i <= N; i++) {
				graph[i] = new ArrayList<>();
			}

			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());

				graph[x].add(new int[] { y, c });
			}

			int[] go = new int[N + 1];
			int[] back = new int[N + 1];

			for (int i = 1; i <= N; i++) {
				go[i] = dijkstra(i, X, graph); // i to X
			}

			for (int i = 1; i <= N; i++) {
				back[i] = dijkstra(X, i, graph); // X to i
			}

			int[] answer = new int[N + 1];
			for (int i = 1; i <= N; i++) {
				answer[i] = go[i] + back[i];
			}

			Arrays.sort(answer);

			sb.append("#").append(tc).append(" ").append(answer[answer.length - 1]).append("\n");
		}

		System.out.println(sb);
	}

	static int dijkstra(int start, int finish, ArrayList<int[]>[] graph) {

		int[] dist = new int[N + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

		dist[start] = 0;
		pq.offer(new int[] { start, 0 });

		while (!pq.isEmpty()) {
			int[] now = pq.poll();

			int nowHome = now[0];
			int value = now[1];

			if (dist[nowHome] < value) {
				continue;
			}

			if (nowHome == finish) {
				return value;
			}

			for (int[] next : graph[nowHome]) {
				int nextHome = next[0];
				int nextValue = next[1] + value;

				if (dist[nextHome] > nextValue) {
					dist[nextHome] = nextValue;
					pq.offer(new int[] { nextHome, nextValue });
				}
			}
		}

		return 0;
	}
}
