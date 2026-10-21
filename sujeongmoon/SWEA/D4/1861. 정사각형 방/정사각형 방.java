import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	/**
	 * 
	 * 문제 설명: 
	 * i*j 배열에 1~N^2이하의 전부 다른 수가 적혀있음
	 * 이동하려는 방에 적힌 숫자가 현재 방에 적힌 숫자보다 정확히 1 더 커야 한다
	 * 처음 어떤 수가 적힌 방에 있어야지 가장 많은 개수의 방을 이동할 수 있는지 구하는 프로그램을 작성하라
	 * 
	 * 
	 * 입력:
	 * 첫 번쨰 줄에 테스트 케이스의 수 T
	 * 각 테케의 첫 번째 줄에는 N(1~1000)
	 * 다음 N개의 i번째 줄에는 N개의 정수가 공백 하나로 구분 (전부 다른 수)
	 * 
	 * 
	 * 
	 * 출력:
	 * 처음에 출발해야하는 방 번호와 최대 몇 개의 방을 이동할 수 있는지를 공백으로 구분하여 출력함
	 * 이동할 수 있는 방의 개수가 최대인 방이 여럿이라면 그 중에서 적힌 수가 가장 작은 것을 출력
	 * 
	 * 
	 * 
	 * 풀이방법:
	 * 1. static int[N][N] arr; // 방 배열
	 * 2. static int[N*N] memo; // 전에 나왔던 값 저장하는 배열
	 * 
	 * dfs로 하되 dp개념을 써서... 
	 * 3. for문을 돈다
	 * 	- 
	 * 
	 * 3. dfs(int r, int c, int count) 
	 * 		int now = arr[r][c];
	 * 		memo[now]가 채워져있는지 확인 -> 예전에 경로를 탐색하다 이미 방문했던 경로였을 수도 있기 때문
	 * 		만약 4방탐색 시 다음 값이 now+1이면, 1 + dfs
	 * 	
	*/
	
	static int N;
	static int[][] arr;
	static int[] memo;
	
	static int[] dr = {1, 0, -1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			
			N = Integer.parseInt(br.readLine());			
			arr = new int[N][N];
			memo = new int[N*N+1];
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					dfs(i, j);
				}
			}
			
			int answer = 0;
			int index = 0;
			
			for (int i = 1; i <= N*N; i++) {
				int now = memo[i];
				if (now > answer) {
					index = i;
					answer = now;
				}
			}
			
			sb.append("#").append(t).append(" ").append(index).append(" ").append(answer).append("\n");
			
	        }
	 
	        System.out.println(sb);
	    }
	
	// 리턴하는 값 -> 갈 수 있는 최대 방 수
	static int dfs(int r, int c) {
		
		int now = arr[r][c];
		
		// 이미 구해둔 값이 있으면 그대로 재사용
		if (memo[now] != 0) {
			return memo[now];
		}
		
		// 어디로도 못 가도 갈 수 있는 현재 방 1개
		memo[now] = 1;
		
		for (int i = 0; i < 4; i++) {
			int nextR = r + dr[i];
			int nextC = c + dc[i];
			
			if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N) {
				continue;
			}
			
			int next = arr[nextR][nextC];
			
			if (now+1 == next) {
				memo[now] = 1 + dfs(nextR, nextC);
				break; 
			}
		}
		return memo[now];
	}
	
}
