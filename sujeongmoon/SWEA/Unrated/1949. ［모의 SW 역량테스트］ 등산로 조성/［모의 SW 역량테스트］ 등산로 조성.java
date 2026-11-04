import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

    /**
     *
     * 문제 설명:
     * 등산로 조성
     * 등산로를 만들기 위한 부지는 N * N, 최대한 긴 등산로 만들기
     * 숫자가 표시된 지도로 주어지며, 각  숫자는 지형의 높이를 나타냄
     *
     * 1. 가장 높은 봉우리(최대값)에서 시작
     * 2. 등산로는 산으로 올라갈 수 있도록, 반드시 높은 지형에서 낮은 지형으로, 가로 또는 세로
     *  -> 높이가 같은 곳 혹은 낮은 지형이나, 대각선 방향 불가능
     * 3. 긴 등산로를 만들기 위해 딱 한 곳을 정해서 최대 K 깊이만큼 지형을 깎는 공사 가능
     *
     * 최대 공사 가능 깊이 K가 주어짐, 이떄 만들 수 있는 가장 긴 등산로를 찾아 그 길이를 출력해라
     *
     * 입력:
     * 0: T
     * 첫 줄: 지도의 한 변의 길이 N(3~8), 최대 공사 가능 깊이 K(1~5)
     * 다음 N개의 줄: N * N 크기의 지도 정보
     *
     *
     * 출력:
     * 만들 수 있는 가장 긴 등산로의 길이
     *
     * 제약사항:
     * 가장 높은 봉우리는 최대 5개
     * 높이를 1보다 작게 만드는 것도 가능하다
     *
     *
     * 풀이방법:
     * => 깎는 경우, 안 깎는 경우 모두 고려해서 완전탐색 -> 얼마나의 K를 깎을 것인지도 고려해야 함(다음에 갈 거보다 조금 더 크게)
     *  - 깎는 거가 현재 나 + K - 1만 돼도 가능(현재 있는 곳보다 -1만 깎아야지 가장 유리함)
     *
     *
     *  1. N과 K를 받는다
     *  2. int[N][N] arr
     *  3. ArrayList에다가 최대값을 담은 좌표들을 구한다.
     *  4. find(좌표)로 해당 최대값에서 출발한 최대경로들을 찾는다.
     *
     *
     */

    static int N;
    static int K;
    static int[][] arr;
    static boolean[][] isVisited;
    static int answer;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            int max = Integer.MIN_VALUE;
            answer = Integer.MIN_VALUE;
            arr = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    if (arr[i][j] > max) max = arr[i][j];
                }
            }

            List<int[]> maxList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (arr[i][j] == max) {
                        maxList.add(new int[] {i, j});
                    }
                }
            }

            for (int i = 0; i < maxList.size(); i++) {
                isVisited = new boolean[N][N];
                isVisited[maxList.get(i)[0]][maxList.get(i)[1]] = true;
                find(maxList.get(i)[0], maxList.get(i)[1], false, arr[maxList.get(i)[0]][maxList.get(i)[1]], 1);
            }
            sb.append("#").append(t).append(" ").append(answer).append("\n");

        }


        System.out.println(sb);
    }

    static void find(int r, int c, boolean isK, int nowDepth, int nowLength) {

        boolean isEnd = true; // 종료조건 판별

        for (int i = 0; i < 4; i++) {
            int nextR = r + dr[i];
            int nextC = c + dc[i];

            if(nextR < 0 || nextR >= N || nextC < 0 || nextC >= N) continue;
            if (isVisited[nextR][nextC]) continue;

            int nextDepth = arr[nextR][nextC];

            //K가 이미 사용된 경우(이미 깎은 경우)
            if (isK) {
                if (nextDepth >= nowDepth) {
                    continue;
                }
                isVisited[nextR][nextC] = true;
                find(nextR, nextC, true, nextDepth, nowLength + 1);
                isVisited[nextR][nextC] = false;
                isEnd = false;
            } else {
                // 깎는 경우
                for (int j = 1; j <= K; j++) {
                    if (nextDepth - j < nowDepth) {
                        isVisited[nextR][nextC] = true;
                        find(nextR, nextC, true, nextDepth-j, nowLength + 1);
                        isVisited[nextR][nextC] = false;
                        isEnd = false;
                    }
                }
                // 안 깎는 경우
                if (nextDepth < nowDepth) {
                    isVisited[nextR][nextC] = true;
                    find(nextR, nextC, false, nextDepth, nowLength + 1);
                    isVisited[nextR][nextC] = false;
                    isEnd = false;
                }
            }
        }

        if (isEnd) {
            answer = Math.max(answer, nowLength);
        }
    }



}
