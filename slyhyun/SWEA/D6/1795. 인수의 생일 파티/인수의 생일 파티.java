/*
 * [문제]
 * - N개의 정점이 있는 가중치 방향 그래프가 있다.
 * - 모든 정점들 간에 이동이 가능하고, 정점에 1 ~ N 까지 번호를 붙인다.
 * - 모든 정점에서 X번 정점으로 최단 가중치로 모였다가 되돌아간다.
 * - X번 정점으로 오고 가는데 드는 가중치가 제일 큰 정점의 가중치를 구한다.
 *
 * [입력]
 * - 정점 개수 N: 1 ~ 1,000
 * - 목표 정점 X: 1 ~ 1,000
 *   - X <= N
 * - 간선 개수 M: 1 ~ 10,000
 * - 간선 정보 x, y, c: x번 정점에서 y번 정점으로 가는데 c의 가중치를 가짐
 *   - x, y: 1 ~ N-1
 *   - c: 1 ~ 100
 *
 * [설계]
 * - 최단 경로: 다익스트라
 *   - 우선 순위 큐를 이용해서 목적지까지의 최단 경로를 찾는다.
 *   - 모든 정점에서 다익스트라를 돌리면 시간 초과
 *   - X에서만 돌리면 돌아가는 경우는 알 수 있지만, X로 가는 경우는 찾을 수 없다.
 * - 역방향 그래프를 만들어 X에서 다익스트라 수행
 *   - 기존 그래프와 역방향 그래프에서의 최단 경로를 더한 뒤 최댓값을 찾는다.
 */
import java.io.*;
import java.util.*;

public class Solution {
    static int N, M, X;

    static class Node {
        int id, weight;

        public Node(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
             st = new StringTokenizer(br.readLine());
             N = Integer.parseInt(st.nextToken());
             M = Integer.parseInt(st.nextToken());
             X = Integer.parseInt(st.nextToken());

             ArrayList<Node>[] graph = new ArrayList[N + 1];
             ArrayList<Node>[] reverse = new ArrayList[N + 1];

             for (int i = 0; i < N + 1; i++) {
                 graph[i] = new ArrayList<>();
                 reverse[i] = new ArrayList<>();
             }

             for (int i = 0; i < M; i++) {
                 st = new StringTokenizer(br.readLine());
                 int x = Integer.parseInt(st.nextToken());
                 int y = Integer.parseInt(st.nextToken());
                 int c = Integer.parseInt(st.nextToken());

                 graph[x].add(new Node(y, c));
                 reverse[y].add(new Node(x, c));
             }

             int[] from = dijkstra(graph, X);
             int[] to = dijkstra(reverse, X);

             int max = 0;

             for (int i = 0; i < N + 1; i++) {
                 if (from[i] == Integer.MAX_VALUE || to[i] == Integer.MAX_VALUE) continue;

                 max = Math.max(max, from[i] + to[i]);
             }

             sb.append("#").append(tc).append(" ").append(max).append("\n");
        }

        System.out.print(sb);
    }

    static int[] dijkstra(ArrayList<Node>[] graph, int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));

        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            int id = curr.id;
            int weight = curr.weight;

            if (dist[id] < weight) continue;

            for (Node next : graph[id]) {
                int nd = weight + next.weight;

                if (nd >= dist[next.id]) continue;

                dist[next.id] = nd;
                pq.offer(new Node(next.id, nd));
            }
        }

        return dist;
    }
}
