import java.io.*;
import java.util.*;

class Solution {
    static List<Node>[] adjList;
    
    class Node {
        int v, weight;
        public Node(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }
    
    public int solution(int n, int[][] costs) {
        adjList = new List[n+1];
        
        for (int i = 0; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] cost : costs) {
            int a = cost[0];
            int b = cost[1];
            int c = cost[2];
            
            adjList[a].add(new Node(b, c));
            adjList[b].add(new Node(a, c));
        }
        
        int answer = dijkstra(n);
        return answer;
    }
    
    class State {
        int v, dist;
        public State(int v, int dist) {
            this.v = v;
            this.dist = dist;
        }
    }
    
    private int dijkstra(int n) {
        PriorityQueue<State> pq = new PriorityQueue<>((a,b)->Integer.compare(a.dist, b.dist)); 
        boolean[] fixed = new boolean[n+1];
        
        pq.offer(new State(0, 0));
        
        int cnt = 0;
        int ans = 0;
        
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int v = cur.v;
            
            if (fixed[v]) continue;
            
            fixed[v] = true;
            ans += cur.dist;
            cnt++;
            
            if (cnt == n) {
                break;
            }
            
            for (Node to: adjList[v]) {
                if (!fixed[to.v]) {
                    pq.offer(new State(to.v, to.weight));
                }
            }
        }
        
        return ans;
        
    }
}