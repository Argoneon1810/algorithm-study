/*
 * [문제]
 * - L분짜리 광고가 있다.
 * - 광고 효과가 뛰어난 N개의 피크 시간이 있고, i번째 피크 시간은 s(i)분부터 e(i)분 까지다.
 * - 광고가 시작되는 시간 t를 정해 t ~ t+L까지 지속되는 광고를 올릴 것이다.
 * - 이 광고가 지속되는 시간과 피크 시간이 가장 많이 겹치도록 했을 때 그 겹치는 시간을 구한다.
 * 
 * [입력]
 * 광고 시간 L: 1 ~ 100,000,000
 * 피크 시간 개수 N: 1 ~ 100,000
 * 피크 시간 시작과 끝 s(i), e(i): 0 ~ 100,000,000
 * e(i) < s(i+1)
 * 
 * [설계]
* - 투 포인터
 *    - 광고를 피크 시간이 아닌 중간에 시작하는 것보다, 특정 피크 시간의 시작점에 딱 맞춰 시작하는 것이 항상 유리하다.
 *    - 광고 시작 지점이 뒤로 밀릴수록, 광고가 끝나는 지점도 무조건 뒤로만 이동한다.
 *    - 따라서 탐색 범위를 앞에서부터 뒤로 같이 밀고 나가는 투 포인터 방식으로 접근한다.
 * 
 * - 탐색 및 피크 시간 누적
 *    - 특정 피크 시간의 시작점을 기준으로 광고를 시작하고, 광고 종료 시간을 계산한다.
 *    - 광고 종료 시간이 다음 피크 시간들의 시작점보다 뒤에 있다면, 그 피크 시간들은 광고 범위에 완전히 포함되는 것으로 간주하여 전체 길이를 누적해 나간다.
 *    - 기준점이 다음 칸으로 넘어가면 기존 기준점의 피크 시간 길이를 누적 값에서 제외하며 범위를 유지한다.
 * 
 * - 경계선 예외 처리 및 최댓값 갱신
 *    - 광고 종료 시간이 어떤 피크 시간의 중간에 걸치는 경우가 발생한다.
 *    - 이 경우 광고 종료 시간에서 해당 피크 시간의 시작점을 뺀 만큼만 부분적으로 더해 겹치는 시간을 계산한다.
 *    - 각 피크 시간의 시작점마다 계산된 총 겹치는 시간 중 가장 큰 값으로 계속 정답을 업데이트한다.
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
            int L = Integer.parseInt(br.readLine());
            int N = Integer.parseInt(br.readLine());

            int[][] peaks = new int[N][2];            
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                peaks[i][0] = Integer.parseInt(st.nextToken());
                peaks[i][1] = Integer.parseInt(st.nextToken());
            }

            int max = 0;
            int cur = 0;
            int right = 0;

            for (int left = 0; left < N; left++) {
                int start = peaks[left][0];
                int end = start + L;

                while (right < N && peaks[right][1] <= end) {
                    cur += (peaks[right][1] - peaks[right][0]);
                    right++;
                }

                int sum = cur;
                if (right < N && peaks[right][0] < end) {
                    sum += (end - peaks[right][0]);
                }

                max = Math.max(max, sum);

                cur -= (peaks[left][1] - peaks[left][0]);
            }

            sb.append("#").append(tc).append(" ").append(max).append("\n");
        }
        
        System.out.print(sb);
    }
}
