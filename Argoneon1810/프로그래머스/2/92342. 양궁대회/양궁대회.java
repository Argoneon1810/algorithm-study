/**
 * 화살은 n발 있다.
 * 상대가 몇 점에 몇 발 꽂았는지 info에 주어진다.
 * 나는 n발을 이용해 상대에게 최대 효율로 이겨야 한다.
 * 내가 어떤 점수를 상대보다 많이 맞추면 해당 점수를 가져간다.
 * 
 * 완탐으로 한다면
 * 한 점수에 n발 전부 꽂은 것 부터
 * 모든 점수에 화살을 분배한 것 까지
 * + 각 점수에 몇 발 씩 분배할 것인지
 * 조합과 순열을 같이 쓰게 된다.
 * 화살 개수는 최대 10. 점수 클래스 개수는 0점 포함 총 11.
 * 모든 조합을 보는 문제이므로 ????????
**/

class Solution {
    public int[] solution(int n, int[] info) {
        int[] my = new int[10+1];
        int[] answer = new int[10+1];
        int winScore = comb(my, info, answer, 0, n, 0);
        if (winScore <= 0)
            return new int[] {-1};
        return answer;
    }
    
    int comb(int[] my, int[] info, int[] answer, int winScore, int arrowLeft, int depth) {
        if(arrowLeft == 0 || depth == 10) {
            my[10] = arrowLeft;
            // System.out.printf("evaluating: %s\n", printArray(my));
            int score = evaluate(my, info);
            if(score > 0 && score >= winScore) {
                keepBetter(answer, my, winScore, score);
                winScore = score;
                // System.out.println(winScore);
            }
            my[10] = 0;
            return winScore;
        }
        // debug limit for printing
        // if(depth>4) return -1;
        for(int i=arrowLeft; i>=0; --i) {
            // System.out.printf("depth: %d, arrowLeft: %d\n", depth, arrowLeft);
            // System.out.printf("%d arrows picked\n", i);
            my[depth] = i;
            winScore = Math.max(comb(my, info, answer, winScore, arrowLeft - i, depth+1), winScore);
            my[depth] = 0;
        }
        return winScore;
    }
    
    int evaluate(int[] my, int[] info) {
        int ryan = 0;
        int apeach = 0;
        for(int i=0; i<=10; ++i) {
            int score = 10-i;
            if(my[i] > info[i]) ryan+=score;
            else if(info[i] > 0) apeach+=score;
        }
        int diffScore = ryan - apeach;
        // System.out.println(diffScore);
        return diffScore;
    }
    
    void keepBetter(int[] answer, int[] my, int winScore, int score) {
        if(winScore == score) {
            // tie case
            for(int i=10; i>=0; --i) {
                if(answer[i] == my[i]) continue;
                if(answer[i] > my[i]) return;
                break;
            }
        }
        for(int i=0; i<=10; ++i)
            answer[i] = my[i];
    }
    
    String printArray(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i=0; i<=10; ++i) {
            if(i!=0) sb.append(", ");
            sb.append(arr[i]);
        }
        sb.append(']');
        return sb.toString();
    }
}