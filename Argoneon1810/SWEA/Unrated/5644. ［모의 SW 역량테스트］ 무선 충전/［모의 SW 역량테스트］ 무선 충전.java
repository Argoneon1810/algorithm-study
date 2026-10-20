import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
 
class Solution {
    static final int R = 0;
    static final int C = 1;
    static final int COVERAGE = 2;
    static final int POWER = 3;
    static final int IN_USE = 4;
 
    static final int[][] DT = {
        {0, 0}, // 이동 없음
        {-1, 0}, // 상
        {0, 1}, // 우
        {1, 0}, // 하
        {0, -1}, // 좌
    };
 
    public static void main(String args[]) throws Exception {
        new Solution().solve();
    }
 
    void solve() throws IOException {
        AutoStringTokenizer st = new AutoStringTokenizer(
            new BufferedReader(
                new InputStreamReader(System.in)
            )
        );
        int T = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        for (int test_case = 1; test_case <= T; test_case++) {
            sb.append('#').append(test_case)
                    .append(' ').append(solveInner(st))
                    .append('\n');
        }
        System.out.print(sb);
    }
 
    int solveInner(AutoStringTokenizer st) throws IOException {
        int M = Integer.parseInt(st.nextToken()); // 사람 이동 횟수
        int A = Integer.parseInt(st.nextToken()); // 충전기의 수
        int moveA[] = new int[M]; // 사람 A의 행동기호
        int moveB[] = new int[M]; // 사람 B의 행동기호
        for(int i=0;i<M;++i)
            moveA[i] = Integer.parseInt(st.nextToken());
        for(int i=0;i<M;++i)
            moveB[i] = Integer.parseInt(st.nextToken());
        int charger[][] = new int[A][4];
        for(int i=0;i<A;++i) {
            charger[i][C] = Integer.parseInt(st.nextToken())-1;
            charger[i][R] = Integer.parseInt(st.nextToken())-1;
            charger[i][COVERAGE] = Integer.parseInt(st.nextToken());
            charger[i][POWER] = Integer.parseInt(st.nextToken());
        }
        int posA[] = new int[] {0, 0};
        int posB[] = new int[] {9, 9};
        int accumPower = 0;
        for(int t=0; t<=M; ++t) { // 시작 t=0부터 마지막 t=20까지
            int maxSum = 0; // 이번 t에 나올 수 있는 최대 합
            for(int i=0; i<A; ++i) {  // 이중루프 조합
                for(int j=0; j<A; ++j) {
                    int[] cA = charger[i];
                    int[] cB = charger[j];
                    int distP1 = l1(cA[R], cA[C], posA[R], posA[C]);
                    int distP2 = l1(cB[R], cB[C], posB[R], posB[C]);
                    // 둘이 같은 충전기를 잡았을 때
                    if(i==j) {
                        if(distP1 <= cA[COVERAGE] && distP2 <= cB[COVERAGE]) {
                            int halfPower = cA[POWER];
                            maxSum = Integer.max(maxSum, halfPower);
                        }
                        // 둘 다 충전기에 닿지 않는 경우
                        else if(distP1 > cA[COVERAGE] && distP2 > cB[COVERAGE]);
                        // 한쪽만 충전기에 닿는 경우
                        else {
                            maxSum = Integer.max(
                                maxSum,
                                distP1 <= cA[COVERAGE]
                                        ? cA[POWER]
                                        : cB[POWER]
                            );
                        }
                    }
                    // 둘이 다른 충전기를 잡았을 때
                    else {
                        int tempSum = (distP1 <= cA[COVERAGE] ? cA[POWER] : 0)
                                    + (distP2 <= cB[COVERAGE] ? cB[POWER] : 0);
                        maxSum = Integer.max(
                            maxSum, tempSum
                        );
                    }
                }
            }
            // 이번 t에서의 최적 파워를 누적에 갱신
            accumPower += maxSum;
            // 유저를 다음 위치로 갱신
            if(t<M) {
                move(posA, DT[moveA[t]]);
                move(posB, DT[moveB[t]]);
            }
        }
        // 최적 파워 반환
        return accumPower;
    }
 
    static int l1(int tr, int tc, int cr, int cc) {
        return Math.abs(tr-cr) + Math.abs(tc-cc);
    }
 
    static void move(int[] pos, int[] dt) {
        pos[R] += dt[R];
        pos[C] += dt[C];
    }
     
    static class AutoStringTokenizer {
        BufferedReader br;
        StringTokenizer st;
        AutoStringTokenizer(BufferedReader br) throws IOException {
            this.br = br;
            st = new StringTokenizer(br.readLine());
        }
        String nextToken() throws IOException {
            if(!st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
    }
}