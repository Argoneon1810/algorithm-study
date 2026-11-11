/**
 * 
**/
class Solution {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        char[][] cboard = new char[board.length][];
        int[][] toClear = new int[board.length][board[0].length()];
        int t = 0;
        for(String s : board)
            cboard[t++] = s.toCharArray();
        boolean anyKill = false;
        int step = 0;
        int testFor = 2;
        do {
            anyKill = scanKillable(cboard, toClear, m, n, ++step);
            // System.out.println(anyKill);
            if(!anyKill) break;
            int killed = doKill(cboard, toClear, step, m, n);
            answer += killed;
            // System.out.println(killed);
            // System.out.println(answer);
            applyGravity(cboard, m, n);
            // if(testFor == step)
            //     anyKill = false;
            // // debugPrint
            // printBoard(cboard, m, n);
        } while(anyKill);
        return answer;
    }
    
    boolean scanKillable(char[][] cboard, int[][] toClear, int m, int n, int step) {
        boolean anyKill = false;
        for(int i=0; i<m-1; ++i) {
            for(int j=0; j<n-1; ++j) {
                // all loop test ... ok
                // if(j==0) System.out.println();
                // System.out.printf("%d %d ", i, j);
                if (isKillable(cboard, i, j)) {
                    markKillable(toClear, step, i, j);
                    anyKill = true;
                }
            }
        }
        return anyKill;
    }
    
    /**
     * 2x2 커널 구간이 같은 종류인지 확인
     * 외부 루프 조건에 의해 반드시 2x2 순회 가능
     * @param cboard 보드
     * @param i 보드 왼쪽 위 좌표의 행 성분
     * @param j 보드 왼쪽 위 좌표의 열 성분
     * @return 같은 종류인지 여부
    **/
    boolean isKillable(char[][] cboard, int i, int j) {
        char kind = cboard[i][j];
        // printBoardSegment(cboard, j, j+1, i, i+1);
        if(kind == ' ') return false;
        for(int y=0; y<2; ++y) {
            for(int x=0; x<2; ++x) {
                if(cboard[y+i][x+j] != kind) {
                    // System.out.println(false);
                    return false;
                }
            }
        }
        // System.out.println(true);
        return true;
    }
    
    void markKillable(int[][] toClear, int step, int i, int j) {
        for(int y=0; y<2; ++y)
            for(int x=0; x<2; ++x)
                toClear[y+i][x+j] = step;
    }
    
    int doKill(char[][] cboard, int[][] toClear, int step, int m, int n) {
        int killed = 0;
        for(int y=0; y<m; ++y) {
            for(int x=0; x<n; ++x) {
                if(toClear[y][x]==step) {
                    ++killed;
                    cboard[y][x] = ' ';
                }
            }
        }
        return killed;
    }
    
    void applyGravity(char[][] cboard, int m, int n) {
        for(int x=0; x<n; ++x)
            applyGravityColumn(cboard, m, n, x);
    }
    
    void applyGravityColumn(char[][] cboard, int m, int n, int x) {
        int toGrab = m-2;
        int toFill = m-1;
        while(toGrab > 0) {
            char c = cboard[toFill][x];
            if(c!=' ') {
                --toFill;
                if(toFill <= 0) break;
                toGrab = Math.min(toGrab, toFill-1);
                continue;
            }
            // no short-circuit?
            // while(toGrab >= 0 || cboard[toGrab][x]==' ')
            //     --toGrab;
            while(true) {
                if(toGrab < 0) break;
                if(cboard[toGrab][x]!=' ') break;
                --toGrab;
            }
            if(toGrab < 0) break;
            // printBoardWithMarkers(cboard, m, n, x, toGrab, x, toFill);
            swap(cboard, x, toFill, toGrab);
        }
    }
    
    /**
     * 문제 특성상 임시변수 제거
    **/ 
    void swap(char[][] cboard, int x, int toFill, int toGrab) {
        cboard[toFill][x] = cboard[toGrab][x];
        cboard[toGrab][x] = ' ';
    }
    
    void printBoard(char[][] cboard, int m, int n) {
        StringBuilder sb = new StringBuilder();
        for(int y=0; y<m; ++y) {
            if(y!=0) sb.append('\n');
            for(int x=0; x<n; ++x) {
                if(x!=0) sb.append(' ');
                sb.append(cboard[y][x]);
            }
        }
        System.out.println(sb.toString());
    }
    
    void printBoardSegment(char[][] cboard, int fromX, int toXInc, int fromY, int toYInc) {
        StringBuilder sb = new StringBuilder();
        for(int y=fromY; y<=toYInc; ++y) {
            if(y!=fromY) sb.append('\n');
            for(int x=fromX; x<=toXInc; ++x) {
                if(x!=fromX) sb.append(' ');
                sb.append(cboard[y][x]);
            }
        }
        System.out.println(sb.toString());
    }
    
    void printBoardWithMarkers(char[][] cboard, int m, int n, int mAX, int mAY, int mBX, int mBY) {
        StringBuilder sb = new StringBuilder();
        for(int y=0; y<m; ++y) {
            if(y!=0) sb.append('\n');
            for(int x=0; x<n; ++x) {
                if(x!=0) sb.append(' ');
                if (y == mAY && x == mAX)
                    sb.append('V');
                else if (y == mBY && x == mBX)
                    sb.append('X');
                else
                    sb.append(cboard[y][x]);
            }
        }
        System.out.println(sb.toString());
    }
}