/*
 * [문제]
 * - 초기에 {1}, {2}, ... {n} 이 각각 n개의 집합을 이루고 있다.
 * - 여기에 합집합 연산과, 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산을 수행하려고 한다.
 * 
 * [입력]
 * - 초기 집합의 개수 n: 1 ~ 1,000,000
 * - 연산의 개수 m: 1 ~ 100,000
 * - 합집합 연산은 0 a b의 형태로 입력이 주어진다.
 *   - a가 포함되어 있는 집합과, b가 포함되어 있는 집합을 합치는 연산이다.
 * - 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산은 1 a b의 형태로 입력이 주어진다.
 *   - a와 b가 같은 집합에 포함되어 있는지를 확인하는 연산이다.
 * - a와 b는 n이하의 자연수이며 같을 수도 있다.
 * 
 * [설계]
 * - 유니온 파인드를 구현한다.
 */
import java.io.*;
import java.util.*;

public class Solution {
	static int[] parent;

	static int find(int a) {
		if (parent[a] == a)
			return a;

		return parent[a] = find(parent[a]);
	}

	static boolean union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);

		if (rootA == rootB)
			return false;

		parent[rootB] = rootA;

		return true;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			parent = new int[N + 1];

			for (int i = 1; i <= N; i++) {
				parent[i] = i;
			}

			sb.append("#").append(tc).append(" ");

			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());

				int n = Integer.parseInt(st.nextToken());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				if (n == 0) {
					union(a, b);
				}
				else {
					if (find(a) == find(b)) {
						sb.append(1);
					}
					else {
						sb.append(0);
					}
				}
			}
			
			sb.append("\n");
		}
		
		System.out.print(sb);
	}
}
