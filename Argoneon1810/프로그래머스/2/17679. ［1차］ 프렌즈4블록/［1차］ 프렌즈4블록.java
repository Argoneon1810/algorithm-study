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
            if(!anyKill) break;
            int killed = doKill(cboard, toClear, step, m, n);
            answer += killed;
            applyGravity(cboard, m, n);
        } while(anyKill);
        return answer;
    }
    
    boolean scanKillable(char[][] cboard, int[][] toClear, int m, int n, int step) {
        boolean anyKill = false;
        for(int i=0; i<m-1; ++i) {
            for(int j=0; j<n-1; ++j) {
                if (isKillable(cboard, i, j)) {
                    markKillable(toClear, step, i, j);
                    anyKill = true;
                }
            }
        }
        return anyKill;
    }
    
    boolean isKillable(char[][] cboard, int i, int j) {
        char kind = cboard[i][j];
        if(kind == ' ') return false;
        for(int y=0; y<2; ++y) {
            for(int x=0; x<2; ++x) {
                if(cboard[y+i][x+j] != kind) {
                    return false;
                }
            }
        }
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
            while(toGrab >= 0 && cboard[toGrab][x]==' ')
                --toGrab;
            if(toGrab < 0) break;
            swap(cboard, x, toFill, toGrab);
        }
    }
    
    void swap(char[][] cboard, int x, int toFill, int toGrab) {
        cboard[toFill][x] = cboard[toGrab][x];
        cboard[toGrab][x] = ' ';
    }
}