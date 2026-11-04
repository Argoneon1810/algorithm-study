import java.util.*;
import java.io.*;

public class Solution {
	static int count;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			int M = Integer.parseInt(br.readLine());
			
			List<Integer>[] big = new ArrayList[N + 1];
			List<Integer>[] small = new ArrayList[N + 1];
			
			for (int i = 1; i <= N; i++) {
		        big[i] = new ArrayList<>();
		        small[i] = new ArrayList<>();
		    }
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				big[a].add(b);
				small[b].add(a);
			}
			
			int answer = 0;
			
			for (int i = 1; i <= N; i++) {
				count = 0;
				
				boolean[] visitedBig = new boolean[N + 1];
				boolean[] visitedSmall = new boolean[N + 1];
				
				dfs(i, visitedBig, big);
				dfs(i, visitedSmall, small);
				  
				if (count == N - 1) {
					answer++;
				}
				
			}
			
			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}
		
		System.out.println(sb);
	}
	
	static void dfs(int current, boolean[] visited, List<Integer>[] graph) {
		visited[current] = true;
		
		for (int next : graph[current]) {
			if (!visited[next]) {
				count++;
				
				dfs(next, visited, graph);
			}
		}
	}
}
