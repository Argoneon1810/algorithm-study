import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

    /**
     *
     * 문제 설명:
     * 최단시간에 모든 학생이 자신의 방으로 돌아가야 함
     * 숙소는 긴 복도를 따라 총 400개의 방이 다음과 같이 배열돼 있음
     * 두 학생이 자기방으로 돌아가면서 지나는 복도의 구간이 겹치면, 동시에 돌아갈 수 없음
     *
     * 모든 학생들은 현재 위치에서 자신의 방으로 돌아가려고 함
     * 두 학생이 자기방으로 돌아가면서 지나는 복도의 구간이 겹치면, 두 학생은 동시에 돌아갈 수 없음
     * 복도 구간이 겹치는 경우, 한 사람은 기다렸다가 다음 차례에 이동해야 함
     * 이동하는 데에는 거리에 관계없이 단위시간이 걸림
     *
     * 각 학생들의 현재 방 위치와 돌아가야 할 방의 위치의 목록이 주어질 때, 최소 몇 단위시간 안에 모든 학생들이 이동할 수 있는지 구해라
     *
     *
     * 입력:
     * 0: T
     * 첫 줄: 돌아가야 할 학생들의 수 N(~400)
     * 다음 줄: 각 학생의 현재 방 번호와 돌아가야 할 방의 번호(중복 x) (~400)
     *
     *
     * 출력:
     * 각 테스트 케이스마다 필요한 시간을 한 줄에 하나씩 출력
     *
     *
     * 풀이방법:
     * => 모든 학생들이 지나가는 복도 구간 중, 가장 많이 겹치는 구간의 중복 횟수
     *
     */

    static int N;
    static int[] countArr;
    static int[][] arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            countArr = new int[200]; // 짝수 홀수 고려
            arr = new int[N][2];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int now1 = (Integer.parseInt(st.nextToken())-1)/2;
                int now2 = (Integer.parseInt(st.nextToken())-1)/2;

                arr[i][0] = Math.min(now1, now2);
                arr[i][1] = Math.max(now1, now2);

            }

            for (int i = 0; i < N; i++) {
                int[] now = arr[i];
                for (int j = now[0]; j <= now[1]; j++) {
                    countArr[j]++;
                }
            }

            int answer = -1;
            for (int i = 0; i < 200; i++) {
                answer = Math.max(answer, countArr[i]);
            }

            sb.append("#").append(t).append(" ").append(answer).append("\n");

        }

        System.out.println(sb);
    }



}
