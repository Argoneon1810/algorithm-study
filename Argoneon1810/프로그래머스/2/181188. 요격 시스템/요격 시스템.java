// 최소 개수 구하기

// 미사일은 x축에 평행한 직선
// 개구간? 을 나타내는 정수 쌍 -> 양 끝이 비포함이란 말인듯
// (s, e) 일 때 s 및 e에서 발사한 미사일은 해당 미사일을 요격할 수 없음
// 발사된 미사일이 날아오는 미사일의 구간을 지나면 요격
// 실수좌표에서 쏠 수 있음

// 미사일은 정수 단위로 날아옴
// 정수단위 칸에서는 절대 격추할 수 없는 미사일을 쏘기 위해 '실수좌표 허용'을 한 듯
// 실제로 쏠 필요는 없는 듯 하다. (실수를 허용했다는건 반드시 격추 가능함을 의미할 뿐이라 보임)

// [s, e)로 취급하고, 히스토그램을 뽑으면?
// 가장 많이 겹친 곳에서 쏘고, 해당 좌표를 지나는 것들을 제거
// 내가 쏜 미사일 좌표보다 뒤에서 쏜 것들은 구간에 겹칠 수 없으므로 검색할 필요 없음
// 내가 x에서 쐈을 때 x inclusive까지 검사하고,
// 이보다 앞이나 같은 칸에서 쏴서 이보다 뒤에서 끝나는 미사일 중 구간이 겹치는 것들 제거

// 안된다. s, e의 값 범위가 0에서 1억.
// 1억개의 int는 무조건 메모리 초과

// 그리디인가?

import java.util.Arrays;

class Solution {
    public int solution(int[][] targets) {
        int N = targets.length;
        Integer[][] my = new Integer[N][2];
        for (int i = 0; i < N; ++i) {
            my[i][0] = targets[i][0];
            my[i][1] = targets[i][1];
        }
        Arrays.sort(my, (l, r) -> {
            int compare = Integer.compare(l[0], r[0]);
            if (compare != 0)
                return compare;
            return Integer.compare(l[1], r[1]);
        });
        // sorting work test...passed
        // for (int i = 0; i < N; ++i) {
        //     System.out.printf("%d %d\n", my[i][0], my[i][1]);
        // }
        
        int answer = 0;
        // 그리디
        for (int i=0; i<N;) {
            int s = my[i][0];
            int e = my[i][1];
            // do {++i;}
            // while(i < N && my[i][0] >= s && my[i][0] < e);
            ++i;
            while(i < N && my[i][0] < e) {
                e = Math.min(e, my[i][1]);
                ++i;
            }
            ++answer;
        }
        return answer;
    }
}