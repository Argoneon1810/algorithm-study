import java.util.Queue;
import java.util.ArrayDeque;

class Solution {
    public static final int GROUND = 0;
    public static final int RED_START = 1;
    public static final int BLUE_START = 2;
    public static final int RED_GOAL = 3;
    public static final int BLUE_GOAL = 4;
    public static final int WALL = 5;
    
    public static final int[][] DT = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1},
    };
    public static final int Y = 0;
    public static final int X = 1;
    
    public int solution(int[][] maze) {
        int n = maze.length;
        int m = maze[0].length;
        int red_start_x = -1; int red_start_y = -1;
        int red_goal_x = -1; int red_goal_y = -1;
        int blue_start_x = -1; int blue_start_y = -1;
        int blue_goal_x = -1; int blue_goal_y = -1;
        for(int y=0; y<n; ++y) {
            for(int x=0; x<m; ++x) {
                int current = maze[y][x];
                switch(current) {
                    case RED_START:
                        red_start_x = x;
                        red_start_y = y;
                        break;
                    case BLUE_START:
                        blue_start_x = x;
                        blue_start_y = y;
                        break;
                    case RED_GOAL:
                        red_goal_x = x;
                        red_goal_y = y;
                        break;
                    case BLUE_GOAL:
                        blue_goal_x = x;
                        blue_goal_y = y;
                        break;
                }
            }
        }
        JointState goal = new JointState(red_goal_x, red_goal_y, blue_goal_x, blue_goal_y, 0, 0);
        Queue<JointState> q = new ArrayDeque<>();
        
        int initAVisited = 1 << (red_start_y * m + red_start_x);
        int initBVisited = 1 << (blue_start_y * m + blue_start_x);
        q.offer(new JointState(red_start_x, red_start_y, blue_start_x, blue_start_y, initAVisited, initBVisited));
        
        int steps = 0;
        boolean everReachedGoal = false;
        bfs: while(!q.isEmpty()) {
            // System.out.printf("======%d======\n", steps);
            int qSize = q.size();
            for(int i=0; i<qSize; ++i) {
                JointState current = q.poll();
                // debugPrint(maze, n, m, current, goal);
                // System.out.println();
                if(
                    isOnGoal(red_goal_x, red_goal_y, current.ax, current.ay) &&
                    isOnGoal(blue_goal_x, blue_goal_y, current.bx, current.by)
                ) {
                    everReachedGoal = true;
                    break bfs;
                }

                boolean aOnGoal = isOnGoal(red_goal_x, red_goal_y, current.ax, current.ay);
                boolean bOnGoal = isOnGoal(blue_goal_x, blue_goal_y, current.bx, current.by);

                for(int[] dta : DT) {
                    int nax = current.ax;
                    int nay = current.ay;
                    if(!aOnGoal) {
                        nax = current.ax + dta[X];
                        nay = current.ay + dta[Y];
                        if(isOffgrid(n, m, nax, nay)) continue;
                        if(!isOnPlate(maze, nax, nay)) continue;
                        if((current.aVisited & (1 << (nay * m + nax))) != 0) continue;
                    }

                    for(int[] dtb : DT) {
                        int nbx = current.bx;
                        int nby = current.by;
                        if(!bOnGoal) {
                            nbx = current.bx + dtb[X];
                            nby = current.by + dtb[Y];
                            if(isOffgrid(n, m, nbx, nby)) continue;
                            if(!isOnPlate(maze, nbx, nby)) continue;
                            if((current.bVisited & (1 << (nby * m + nbx))) != 0) continue;
                        }
                        if(isOverlapping(nax, nay, nbx, nby)) continue;
                        if(isOverlapping(nax, nay, current.bx, current.by) &&
                           isOverlapping(current.ax, current.ay, nbx, nby)) continue;

                        // 이제 방문 가능
                        int nextAVisited = current.aVisited | (1 << (nay * m + nax));
                        int nextBVisited = current.bVisited | (1 << (nby * m + nbx));
                        q.offer(new JointState(nax, nay, nbx, nby, nextAVisited, nextBVisited));

                        if(bOnGoal) break;
                    }
                    if(aOnGoal) break;
                }
            }
            ++steps;
        }
        if(!everReachedGoal)
            return 0;
        int answer = steps;
        return answer;
    }
    
    boolean isOffgrid(int n, int m, int x, int y) {
        return x < 0 || y < 0 || y >= n || x >= m;
    }
    
    boolean isOnPlate(int[][] maze, int x, int y) {
        return maze[y][x] != WALL;
    }
    
    boolean isOverlapping(int ax, int ay, int bx, int by) {
        return ax == bx && ay == by;
    }
    
    boolean isOnGoal(int gx, int gy, int cx, int cy) {
        return isOverlapping(gx, gy, cx, cy);
    }
    
    void debugPrint(int[][] maze, int n, int m, JointState current, JointState goal) {
        int cax = current.ax; int cay = current.ay;
        int cbx = current.bx; int cby = current.by;
        int gax = goal.ax; int gay = goal.ay;
        int gbx = goal.bx; int gby = goal.by;
        StringBuilder sb = new StringBuilder();
        for(int y=0; y<n; ++y) {
            if(y != 0) sb.append('\n');
            for(int x=0; x<m; ++x) {
                if(x != 0) sb.append(' ');
                if (y==cay && x==cax)
                    sb.append('r');
                else if (y==cby && x==cbx)
                    sb.append('b');
                else if (y==gay && x==gax)
                    sb.append('R');
                else if (y==gby && x==gbx)
                    sb.append('B');
                else if(maze[y][x] == WALL)
                    sb.append('W');
                else sb.append('.');
            }
        }
        System.out.println(sb.toString());
    }
    
    private class JointState {
        int ax, ay;
        int bx, by;
        int aVisited;
        int bVisited;

        JointState(int ax, int ay, int bx, int by, int aVisited, int bVisited) {
            this.ax = ax; this.ay = ay;
            this.bx = bx; this.by = by;
            this.aVisited = aVisited;
            this.bVisited = bVisited;
        }
    }
}