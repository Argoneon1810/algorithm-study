
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    /**
     *
     * 문제 설명:
     * 16진수 숫자가 적혀있는 보물상자
     * 보물상자의 뚜껑은 시계방향으로 돌릴 수 있고, 한 번 돌릴 때마다 숫자가 시계방향으로 한 칸씩 회전
     *
     * 각 변에는 동일한 개수의 숫자가 있고, 시계방향으로 높은 순으로 높은 자리 숫자에 해당하며 하나의 수를 나타낸다
     * 보물상자에는 자물쇠가 걸려있는데, 이 자물쇠의 비밀번호는 보물 상자에 적힌 숫자로 만들 수 있는 모든 수 중,
     * K번째로 큰 수를 10진수로 만든 수이다.
     *
     *
     * 입력:
     * 가장 첫 줄 - 테스트 케이스의 개수 T
     * tc의 첫 줄 - 숫자의 개수 N과 크기 순서 K
     * 다음 줄 - 16진수 0~F 숫자가 공백 없이 N개
     *
     * 제약사항:
     * N은 4의 배수, 8~28 정수
     * N개의 숫자는 각각 0이상 F이하 (A~F는 알파벳 대문자)
     * K는 생성 가능한 수의 개수보다 크게 주어지지 않는다.
     *
     * 출력:
     *
     *
     * 풀이방법:
     * static PriorityQueue pq; // 내림차순 정렬로 두기
     * 만들 수 있는 모든 경우의 수는 뭘까...?
     *
     * 1, B, 3,   B, 3, B,   8, 1, F,   7, 5, E (N = 12), 그러면 한 변의 길이는 N/4만큼이 되는 거임
     *
     * 만들 수 있는 경우의 수는 -> 자릿수만큼 로테이션이 돌아간다. = N/4번의 로테이션이 돌아간다.
     * for문을 돌면서 16진수 -> F~A, 9~0 순서로 정렬시켜준다
     *  -> 10진수로 바꾼 뒤 pq에 넣는다
     *
     * 이후 출력 시, 앞이랑 겹치는 경우는 count하지 않고, count == K인 경우 answer이 됨
     *
     */

    static int N;
    static int K;
    static char[] arr;
    static PriorityQueue<Integer> pq;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            arr = new char[N];
            String line = br.readLine();
            for (int i = 0; i < N; i++) {
                arr[i] = line.charAt(i);
            }

            PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

            for (int i = 0; i < N/4; i++) {


                for (int j = 0; j < 4; j++) {
                    StringBuilder sb2 = new StringBuilder();
                    int start = i + (j * N/4);
                    for (int k = 0; k < N/4; k++) {
                        int idx = (start+ k) % N;
                        sb2.append(arr[idx]);
                    }
                    pq.add(Integer.parseInt(sb2.toString(), 16));
                }
            }

            int count = 0;
            int before = -1;
            int answer = -1;

            while (!pq.isEmpty()) {
                int now =  pq.poll();
                if (before == now) {
                    continue;
                }
                before = now;
                count++;

                if (count == K) {
                    answer = now;
                    break;
                }
            }

            sb.append("#").append(t).append(" ").append(answer).append("\n");
        }
        System.out.println(sb);
    }





}
