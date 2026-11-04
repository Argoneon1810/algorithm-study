/*
 * [문제]
 * - N명의 학생들이 복도를 지나 자기방으로 최단 시간에 돌아가려고 한다.
 * - 이동 거리에 관계없이 한 번 이동할 때 1 단위 시간이 소요된다.
 * - 방은 위아래로 마주보고 있으며, 위는 전부 홀수, 아래는 전부 짝수 방으로 이루어져 있다.
 *   - (1, 2)방, (3, 4)방, ..., (399, 400)방이 같은 복도 구역을 공유한다.
 * - 학생들이 이동하는 복도 구간이 겹치면 동시에 이동할 수 없고, 한 사람은 대기해야 한다.
 * - 현재 방 번호가 돌아갈 방 번호보다 큰 역방향 이동도 가능하다.
 * - 모든 학생들이 방으로 돌아갈 수 있는 최소 단위 시간을 구한다.
 * 
 * [입력]
 * - 돌아가야 할 학생들의 수 N: 1 ~ 200
 * - 각 학생의 현재 방 번호와 돌아가야 할 방 번호 쌍: 1 ~ 400, 중복 없음
 * 
 * [설계]
 * - 방 번호를 복도 번호로 변환
 *   - 마주 보는 두 방이 같은 복도를 쓰므로 복도 배열 크기는 200개만 필요하다.
 *   - 공식: (방 번호 - 1) / 2를 통해 1~400번 방을 0~199번 복도 인덱스로 압축한다.
 * 
 * - 구간 정렬 및 복도 카운팅
 *   - 출발 복도 번호가 도착 복도 번호보다 큰 역방향 이동의 경우, 구간 탐색을 위해 두 값을 교환한다.
 *   - 정렬된 출발 복도부터 도착 복도까지의 구간에 해당하는 배열 칸들을 전부 +1씩 누적한다.
 * 
 * - 최댓값 출력
 *   - 복도 배열 전체를 순회하며 최대 중복 횟수을 찾는다.
 *   - 가장 많이 겹친 구간의 누적 횟수가 곧 전체 학생이 이동하는 데 필요한 최소 시간이 된다.
 */
import java.io.*;
import java.util.*;

public class Solution {
	public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int[] pass = new int[200];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                int fromIdx = (from - 1) / 2;
                int toIdx = (to - 1) / 2;

                if (fromIdx > toIdx) {
                    int temp = fromIdx;
                    fromIdx = toIdx;
                    toIdx = temp;
                }

                for (int j = fromIdx; j <= toIdx; j++) {
                	pass[j]++;
                }
            }

            int max = 0;
            
            for (int i = 0; i < 200; i++) {
                if (pass[i] == 0) continue;
                
                max = Math.max(max, pass[i]);
            }

            sb.append("#").append(tc).append(" ").append(max).append("\n");
        }

        System.out.print(sb);
    }
}
