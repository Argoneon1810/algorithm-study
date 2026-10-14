import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	/**
	 * 
	 * 문제 설명: 
	 * 주어진 일이 모두 성공할 확률의 최댓값
	 * 주어진 일과 직원의 수 모두 N
	 * 
	 * 
	 * 입력:
	 * 첫 번째 줄에 N
	 * 다음 N개의 줄에, 공백으로 구분한 N개의 각각의 확률
	 * 
	 * 
	 * 출력:모든 일을 성공할 확률이 최대화될 때의 확률을 퍼센트 단위로 소수점 아래 7번째 자리에서 반올림하여 6번째까지 출력
	 * 
	 * 
	 * 
	 * 풀이방법:
	 * arr안에 들어오는 값을 100으로 나눈 실제 확률값을 저장
	 * isVisited배열을 만들고 find함수를 만들어서, for문을 돌면서 재귀를 돌림
	 * 최대값 출력
	 * 
	 * find
	 * 	index가 N이되면, sum값이랑 answer이랑 비교해서 sum가 더 큰 경우 answer가 됨
	 * 	i가 0<N까지 돌면서, 백트래킹 활용하고, isVisited 활용해서 방문 체크
	 * 	index가 직원, for 및 isVisited가 현재 일을 나타냄
	 * 
	 * 
	*/
	

	static int N;
	static double[][] arr;
	static boolean[] isVisited;
	static double answer;
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			arr = new double[N][N];
			isVisited = new boolean[N];
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < N; j++) {
					String now = st.nextToken();
					arr[i][j] = Integer.parseInt(now) / 100.0;
				}
				
			}
			
			answer = Integer.MIN_VALUE;
			find(0, 1.0);
			
			sb.append("#").append(t).append(" ").append(String.format("%.6f", answer*100)).append("\n");
			
		}
		System.out.println(sb);
		
	}
	
	static void find(int index, double sum) {
		
		if (sum <= answer) {
			return;
		}
		if (index == N) {
			answer = sum;
		}
		
		for (int i = 0; i < N; i++) {
			if (isVisited[i]) {
				continue;
			}
			isVisited[i] = true;
			find(index+1, sum * arr[index][i]);
			isVisited[i] = false;
		}
	}
	
}
