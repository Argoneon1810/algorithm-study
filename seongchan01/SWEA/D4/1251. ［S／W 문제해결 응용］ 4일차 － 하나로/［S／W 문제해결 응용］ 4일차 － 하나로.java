import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * [문제 해석]
 * 인도네시아 내의 N개의 섬들을 연결하는 교통시스템
 * 인도네시아 내의 모든 섬을 해저 터널로 연결하는 것을 목표
 * 반드시 두 섬은 선분으로 연결하고, 두 해저 터널이 교차된다 해도 물리적으로는 X
 * 해저터널 건설로 인해 파괴되는 자연을 위해 환경 부담금 있음
 * 환경 부담 세율(E)와 해저 터널 길이(L)의 제곱의 곱 : E * L^2
 * 환경 부담금을 최소로 지불하여 N개의 모든 섬을 연결할 수 있는 교통 시스템 설계
 * 
 * [풀이]
 * 환경 부담금 : 간선의 가중치라 생각하고 MST 풀이 -> kruskal
 */

public class Solution { // D4. 하나로

	static int N; // 섬
	static int[][] arr; // 섬 위치
	static long[][] edge;

	static class Edge implements Comparable<Edge> {
		int from;
		int to;
		long cost;

		Edge(int from, int to, long cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return Long.compare(this.cost, o.cost);
		}
	}

	static int[] parent;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			arr = new int[N][2];

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i][0] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i][1] = Integer.parseInt(st.nextToken());
			}

			double E = Double.parseDouble(br.readLine());

			ArrayList<Edge> edges = new ArrayList<>();

			// 환경부담금 = 간선
			for (int i = 0; i < N; i++) {

				for (int j = i + 1; j < N; j++) {
					long dx = arr[i][0] - arr[j][0];
					long dy = arr[i][1] - arr[j][1];

					long cost = dx * dx + dy * dy;

					edges.add(new Edge(i, j, cost));
				}
			}

			parent = new int[N];
			for (int i = 0; i < N; i++) {
				parent[i] = i;
			}

			Collections.sort(edges);

			long answer = 0;
			int count = 0;
			for (Edge edge : edges) {

				// 아직 서로 연결되지 않은 섬이라면
				if (find(edge.from) != find(edge.to)) {

					union(edge.from, edge.to);

					answer += edge.cost;
					count++;

					if (count == N - 1) {
						break;
					}
				}
			}

			sb.append("#").append(tc).append(" ").append(Math.round(answer * E)).append("\n");
		}

		System.out.println(sb);
	}

	static int find(int x) {
		if (parent[x] == x) {
			return x;
		}

		return parent[x] = find(parent[x]);
	}

	static void union(int a, int b) {
		a = find(a);
		b = find(b);

		if (a != b) {
			parent[b] = a;
		}
	}

}
