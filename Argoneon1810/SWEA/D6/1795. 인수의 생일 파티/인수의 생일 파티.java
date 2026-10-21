import java.io.*;
import java.util.*;

/**
 * [문제정보]
 * N개의 집
 * 각 집에 한 사람 거주
 * 어떤 집에서 다른 집으로 향하는 **단방향** 간선
 * 가는 거리 **가중치 있는**
 * 도로는 반드시 모든 집을 갈 수 있게 되어 있음
 * 인수의 집은 X번 집
 * 각 사람들은 인수의 집으로 올 때와 집으로 돌아갈 때 서로 다른 경로 사용 (단방향이라서)
 * 
 * X까지 오고 가는데 가장 오래 걸리는 사람은 누구
 * 
 * N M X // 정점의 수 간선의 수 인수의 집
 * N = [1, 1000]
 * M = [1, 10000]
 * 
 * (자연스럽게 X는 1-based)
 * 
 * 각 M 별로 3개의 정수 x, y, c
 * x = from
 * y = to
 * c = distance. [1, 100]
 * 
 * [문제분석]
 * 각 정점으로부터 X까지의 거리를 구하면 시간복잡도상 불가
 * X로부터 각 정점까지의 거리를 bfs로 한꺼번에 구하고,
 * 각 정점이 어떤 값을 가지고 있는지를 보면 됨
 * 
 * [접근]
 * 들어가는 쪽 경로와 나가는 쪽 경로를 분리하고
 * 각각 X로부터 다익스트라
 * 각 정점에 기록된 '올 때의 최단 거리'와 '갈 때의 최단거리'를 합하고
 * 가장 큰 값을 출력
 */

class Solution {
	static final int INDEX_MASK = 0b11_1111_1111;

	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}

	void solve() throws IOException {
		AutoStringTokenizer st = new AutoStringTokenizer(
			new BufferedReader(
				new InputStreamReader(System.in)
			)
		);
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
					.append(' ').append(solveInner(st))
					.append('\n');
		System.out.print(sb);
	}

	int solveInner(AutoStringTokenizer st) throws IOException {
		int N, M, X;
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken())-1;
		NestList out = new NestList(N);
		NestList in = new NestList(N);
		for(int i=0;i<M;++i) {
			int x, y, c;
			x = Integer.parseInt(st.nextToken())-1;
			y = Integer.parseInt(st.nextToken())-1;
			c = Integer.parseInt(st.nextToken());
			out.add(x, bake(c, y)); // X로부터 나가는 쪽
			in.add(y, bake(c, x)); // X로 들어오는 쪽
		}
		int travel[][] = new int[2][N];
		Arrays.fill(travel[0], Integer.MAX_VALUE);
		Arrays.fill(travel[1], Integer.MAX_VALUE);
		traverse(out, travel[0], N, X);
		traverse(in, travel[1], N, X);
		int maxDistAccum = 0;
		for(int i=0; i<N; ++i) {
			if (i==X) continue;
			maxDistAccum = Integer.max(
				maxDistAccum,
				travel[0][i] + travel[1][i]
			);
		}
		return maxDistAccum;
	}

	void traverse(NestList E, int travel[], int N, int X) {
		boolean visited[] = new boolean[N+1];
		PriorityQueue<Integer> q = new PriorityQueue<>();
		q.offer(bake(0, X));
		while(!q.isEmpty()) {
			int baked = q.poll();
			int currIdx = getIndex(baked);
			int distance = getDistance(baked);
			if(visited[currIdx]) continue;
			visited[currIdx] = true;
			for(Integer toBaked : E.get(currIdx)) {
				int toIdx = getIndex(toBaked);
				int toDistance = getDistance(toBaked);
				if(visited[toIdx]) continue;
				int tempDist = distance + toDistance;
				if(travel[toIdx] <= tempDist) continue;
				travel[toIdx] = tempDist;
				q.offer(bake(tempDist, toIdx));
			}
		}
	}

	static int bake(int distance, int index) {
		return (distance << 10) | index;
	}

	static int getDistance(int baked) {
		return baked >> 10;
	}

	static int getIndex(int baked) {
		return baked & INDEX_MASK;
	}

	static class NestList {
		List<List<Integer>> d;
		NestList() {
			d = new ArrayList<>();
		}
		// 간선 없는 정점도 빈 리스트 보장
		NestList(int n) {
			d = new ArrayList<>();
			for(int i=0; i<n; ++i)
				d.add(new ArrayList<Integer>());
		}
		void add(int idx0, int val) {
			int diff = d.size() - idx0 - 1;
			if(diff <= 0) {
				int absdiff = Math.abs(diff);
				for(int i=0; i<absdiff; ++i)
					d.add(new ArrayList<Integer>());
			}
			d.get(idx0).add(val);
		}
		void add(int idx0, int idx1, int val) {
			int diff = d.size() - idx0 - 1;
			if(diff <= 0) {
				int absdiff = Math.abs(diff);
				for(int i=0; i<absdiff; ++i)
					d.add(new ArrayList<Integer>());
			}
			List<Integer> d1 = d.get(idx0);
			diff = d1.size() - idx1 - 1;
			if(diff <= 0) {
				int absdiff = Math.abs(diff);
				for(int i=0; i<absdiff; ++i)
					d1.add(0);
			}
			d.get(idx0).set(idx1, val);
		}
		List<Integer> get(int idx0) {
			return d.get(idx0);
		}
		Integer get(int idx0, int idx1) {
			return d.get(idx0).get(idx1);
		}
	}
	
	static class AutoStringTokenizer {
		BufferedReader br;
		StringTokenizer st;
		AutoStringTokenizer(BufferedReader br) throws IOException {
			this.br = br;
			st = new StringTokenizer(br.readLine());
		}
		String nextToken() throws IOException {
			if(!st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
	}
}
