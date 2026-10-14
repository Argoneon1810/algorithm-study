import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	/**
	 * 
	 * 문제 설명: 
	 * N명의 점원들이 자기 키를 이용해 탑을 쌓아서, 선반 위에 닿아야 함
	 * 점원들이 쌓는 탑은 점원 1명 이상으로 이루어져 있다
	 * 탑의 높이는 탑을 만든 모든 점원의 키와 같다
	 * 높이가 B 이상인 탑 중에서 높이가 가장 낮은 탑을 알아내야 함
	 * 
	 * 입력:
	 * 첫 번째 줄에 테스트 케이스의 수 T
	 * 각 tc의 첫 번째 줄에는 점원들의 수 N, 높이 기준 B
	 * 그  뒷줄에는 N개씩, 점원들의 키를 나타냄
	 * 
	 * 출력: 만들 수 있는 높이가 B 이상인 탑 중에서 가장 작은 것 출력
	 * 
	 * 풀이방법:
	 * 재귀와 index를 돌면서, 해당 점원의 키를 넣을지 안넣을지를 세어주면 됨
	 * 만약 기존 answer보다 값이 커지는 순간이 나오면 가지치기
	 * 
	 * void find(index, sum) {
	 * 	if index == N : B>sum이면 return, sum과 answer 비교 후 return; 
	 *  if sum > answer: return;
	 * 
	 * }
	 * 
	*/
	
	static int N;
	static int B;
	static int[] arr;
	static int answer;
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			answer = Integer.MAX_VALUE;
			find(0, 0);
			
			sb.append("#").append(t).append(" ").append(answer - B).append("\n");
			
		}
		System.out.println(sb);
		
	}
	
	static void find(int index, int sum) {
		
		if (answer < sum) {
			return;
		}
		
		if (index == N) {
			if (sum < B) {
				return;
			}
			answer = sum;
			return;
		}
		
		find(index+1, sum + arr[index]);
		find(index+1, sum);
	}

}
