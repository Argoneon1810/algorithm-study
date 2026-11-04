import java.io.*;
import java.util.*;

public class Solution {
	static int M, BC_CNT;
    static int[] moveA, moveB;
    static int[][] bcInfo;
    static int[] dr = {0, -1, 0, 1, 0};
    static int[] dc = {0, 0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            BC_CNT = Integer.parseInt(st.nextToken());

            moveA = new int[M];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                moveA[i] = Integer.parseInt(st.nextToken());
            }

            moveB = new int[M];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                moveB[i] = Integer.parseInt(st.nextToken());
            }

            bcInfo = new int[BC_CNT][4];
            for (int i = 0; i < BC_CNT; i++) {
                st = new StringTokenizer(br.readLine());
                bcInfo[i][0] = Integer.parseInt(st.nextToken());
                bcInfo[i][1] = Integer.parseInt(st.nextToken());
                bcInfo[i][2] = Integer.parseInt(st.nextToken());
                bcInfo[i][3] = Integer.parseInt(st.nextToken());
            }

            int rA = 1, cA = 1;
            int rB = 10, cB = 10;
            int totalCharge = getOptimalCharge(rA, cA, rB, cB);

            for (int i = 0; i < M; i++) {
                rA += dr[moveA[i]];
                cA += dc[moveA[i]];
                rB += dr[moveB[i]];
                cB += dc[moveB[i]];

                totalCharge += getOptimalCharge(rA, cA, rB, cB);
            }

            sb.append("#").append(tc).append(" ").append(totalCharge).append("\n");
        }

        System.out.print(sb);
    }

    static int getOptimalCharge(int rA, int cA, int rB, int cB) {
        int maxCharge = 0;

        boolean[] canAccessA = new boolean[BC_CNT];
        boolean[] canAccessB = new boolean[BC_CNT];

        for (int i = 0; i < BC_CNT; i++) {
            int distA = Math.abs(bcInfo[i][0] - cA) + Math.abs(bcInfo[i][1] - rA);
            if (distA <= bcInfo[i][2]) {
                canAccessA[i] = true;
            }

            int distB = Math.abs(bcInfo[i][0] - cB) + Math.abs(bcInfo[i][1] - rB);
            if (distB <= bcInfo[i][2]) {
                canAccessB[i] = true;
            }
        }

        for (int i = -1; i < BC_CNT; i++) {
            for (int j = -1; j < BC_CNT; j++) {
                if (i != -1 && !canAccessA[i]) continue;
                if (j != -1 && !canAccessB[j]) continue;

                int currentSum = 0;

                if (i != -1 && j != -1) {
                    if (i == j) {
                        currentSum = bcInfo[i][3];
                    } else {
                        currentSum = bcInfo[i][3] + bcInfo[j][3];
                    }
                } else if (i != -1) {
                    currentSum = bcInfo[i][3];
                } else if (j != -1) {
                    currentSum = bcInfo[j][3];
                }

                maxCharge = Math.max(maxCharge, currentSum);
            }
        }

        return maxCharge;
    }
}
