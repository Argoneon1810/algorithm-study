import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
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

	String solveInner(AutoStringTokenizer st) throws IOException {
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		boolean[][] winChain = new boolean[N][N];
		for(int i=0; i<M; ++i) {
			int win = Integer.parseInt(st.nextToken());
			int lose = Integer.parseInt(st.nextToken());
			winChain[win-1][lose-1] = true;
		}
		for(int i=0; i<N; ++i) {
			// 모순이 없다는 조건 하에:
			// i에게 진 노드보다 아래인 모든 노드에게 i에게 진 패배 이력 추가
			Queue<Integer> q = new ArrayDeque<>();
			for(int j=0; j<N; ++j)
				if(winChain[i][j])
					q.offer(j);
			while(!q.isEmpty()) {
				int j = q.poll();
				for(int k=0; k<N; ++k) {
					if(winChain[j][k] && !winChain[i][k]) {
						winChain[i][k] = true;
						q.offer(k);
					}
				}
			}
		}
		// 한 줄이 가득 찬 행을 모두 셈
		int count = 0;
		for(int r=0; r<N; ++r) {
			int localCount = 0;
			for(int c=0; c<N; ++c)
				if(winChain[r][c] || winChain[c][r])
					++localCount;
			if(localCount==N-1)
				++count;
		}
		return Integer.toString(count);
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
