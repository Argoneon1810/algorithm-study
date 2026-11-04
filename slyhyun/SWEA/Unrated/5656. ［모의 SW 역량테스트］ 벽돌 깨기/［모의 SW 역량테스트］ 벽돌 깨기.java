import java.io.*;
import java.util.*;

public class Solution {
    static int N, W, H;
    static int min;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    static class Point {
        int r, c, size;
        
        Point(int r, int c, int size) {
            this.r = r;
            this.c = c;
            this.size = size;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            int[][] map = new int[H][W];
            int total = 0;

            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    
                    if (map[i][j] == 0) continue;
                    
                    total++;
                }
            }

            min = total;
            dfs(0, total, map);
            
            sb.append("#").append(tc).append(" ").append(min).append("\n");
        }
        
        System.out.print(sb);
    }

    static void dfs(int cnt, int rem, int[][] map) {
        if (rem == 0) {
            min = 0;
            return;
        }

        if (cnt == N) {
            min = Math.min(min, rem);
            return;
        }

        int[][] next = new int[H][W];

        for (int c = 0; c < W; c++) {
            int r = 0;
            
            while (r < H && map[r][c] == 0) r++;

            if (r == H) continue;

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    next[i][j] = map[i][j];
                }
            }

            int broken = bfs(r, c, next);

            dfs(cnt + 1, rem - broken, next);
            
            if (min == 0) return;
        }
    }

    static int bfs(int startR, int startC, int[][] cur) {
        Queue<Point> q = new ArrayDeque<>();
        int cnt = 0;

        q.add(new Point(startR, startC, cur[startR][startC]));
        cur[startR][startC] = 0;
        cnt++;

        while (!q.isEmpty()) {
            Point p = q.poll();

            for (int d = 0; d < 4; d++) {
                int nr = p.r;
                int nc = p.c;

                for (int i = 1; i < p.size; i++) {
                    nr += dr[d];
                    nc += dc[d];

                    if (nr < 0 || nr >= H || nc < 0 || nc >= W) break;
                    if (cur[nr][nc] == 0) continue;

                    if (cur[nr][nc] > 1) {
                        q.add(new Point(nr, nc, cur[nr][nc]));
                    }
                    cur[nr][nc] = 0;
                    cnt++;
                }
            }
        }

        for (int c = 0; c < W; c++) {
            int idx = H - 1;
            
            for (int r = H - 1; r >= 0; r--) {
                if (cur[r][c] == 0) continue;
                
                if (idx != r) {
                    cur[idx][c] = cur[r][c];
                    cur[r][c] = 0;
                }
                
                idx--;
            }
        }

        return cnt;
    }
}
