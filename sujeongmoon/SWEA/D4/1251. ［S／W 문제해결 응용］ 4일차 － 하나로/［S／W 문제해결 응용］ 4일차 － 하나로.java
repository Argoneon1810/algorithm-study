
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Solution {

    /**
     *
     * 문제 설명:
     *
     *
     * 입력:
     * 가장 첫 줄은 전체 테스트 케이스의 수이다.

     각 테스트 케이스의 첫 줄에는 섬의 개수 N이 주어지고 (1≤N≤1,000),

     두 번째 줄에는 각 섬들의 정수인 X좌표, 세 번째 줄에는 각 섬들의 정수인 Y좌표가 주어진다 (0≤X≤1,000,000, 0≤Y≤1,000,000).

     마지막으로, 해저터널 건설의 환경 부담 세율 실수 E가 주어진다 (0≤E≤1).
     *
     * 출력:
     *
     *
     * 풀이방법:
     *
     * static int N; // 섬의 개수
     * static int[N][2] islandArr; // 각 섬들의 좌표, 각각의 인덱스가 정점의 번호
     * class Edge // 정점그래프
     * 	시작정점 x, 도착정점 y, x와 y 사이의 거리 distance
     * edgeList = newEdge[N*N], 근데 동일 정점을 다루는 경우는 distance를 max값으로 설정해주기
     *
     */

    static int N;
    static int[][] isLandArr;


    static int V; // 정점

    static int[] parents;

    static class Edge {
        int from;
        int to;
        long distance;

        public Edge(int from, int to, long distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {

            N = Integer.parseInt(br.readLine());

            isLandArr = new int[N][2];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                isLandArr[i][0] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                isLandArr[i][1] = Integer.parseInt(st.nextToken());
            }

            double E = Double.parseDouble(br.readLine());

            ArrayList<Edge> edges = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    long dx = isLandArr[i][0] - isLandArr[j][0];
                    long dy = isLandArr[i][1] - isLandArr[j][1];

                    long d = dx * dx + dy * dy;
                    edges.add(new Edge(i, j, d)); // 간선 그래프 구성
                }
            }

            parents = new int[N];
            for (int i = 0; i < N; i++) {
                parents[i] = i;
            }

            Collections.sort(edges, (a, b) -> {
                return Long.compare(a.distance, b.distance);
            });

            long answer = 0;
            int count = 0;
            for (Edge edge : edges) {
                if (union(edge.from, edge.to)) {
                    answer += edge.distance;
                    count++;

                    if (count == N - 1) {
                        break;
                    }
                }


            }
            sb.append("#").append(t).append(" ").append(Math.round(answer * E)).append("\n");


        }
        System.out.println(sb);
    }

    static int find(int num) {
        if (parents[num] == num) {
            return num;
        } else {
            return parents[num] = find(parents[num]);
        }
    }

    static boolean union(int a, int b) {
        int rootA =  find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            parents[rootB] = rootA;
            return true; // 합친 경우에 true
        }
        return false; // 안 합쳤다면 false

    }
    

}
