/*
 * [문제]
 * - N*N의 방이 있는 격자가 있다.
 * - i행 j열의 방에는 1 ~ N^2의 수 A(i, j)가 적혀있고, 모든 방의 숫자는 다르다.
 * - 상하좌우에 방이 있고, 그 방에 적힌 숫자가 현재 있는 방의 숫자보다 1 크면 이동할 수 있다.
 * - 처음에 어떤 수가 적힌 방에서 있어야 가장 많은 개수의 방을 이동할 수 있는지 구한다.
 *
 * [입력]
 * - N: 1 ~ 1,000
 * - 방에 적힌 수 A(i, j): 1 ~ 1,000,000
 *
 * [설계]
 * - 크기가 N*N + 1인 boolean 배열을 생성한다.
 *   - v[num]은 num 방에서 num+1 방으로 이동 가능 여부
 * - grid의 모든 칸을 순회하며 상하좌우를 체크한다.
 *   - 만약 주변 칸의 숫자가 현재 칸의 숫자보다 정확히 1 크다면 v[현재 숫자] = true로 설정한다.
 * - v 배열의 1번 인덱스부터 N*N까지 반복문을 돌며 true가 가장 길게 연속되는 구간을 찾는다.
 *   - 연속된 true의 개수 + 1 이 최대 이동 가능한 방의 개수가 된다.
 *   - 구간을 탐색할 때마다 최댓값을 갱신하고, 이때의 시작 방 번호도 기록한다.
 */
import java.io.*;
import java.util.*;

public class Solution {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine().trim());
            int[][] grid = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            boolean[] v = new boolean[N * N + 1];

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    int current = grid[r][c];

                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];

                        if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;

                        if (grid[nr][nc] == current + 1) {
                            v[current] = true;
                            break;
                        }
                    }
                }
            }

            int maxLen = 0;
            int startRoom = 1;

            for (int i = 1; i < N * N; i++) {
                if (!v[i]) continue;

                int start = i;
                int currentLen = 0;

                while (i < N * N && v[i]) {
                    currentLen++;
                    i++;
                }

                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    startRoom = start;
                }
            }

            sb.append("#").append(tc).append(" ").append(startRoom).append(" ").append(maxLen + 1).append("\n");
        }

        System.out.print(sb);
    }
}
