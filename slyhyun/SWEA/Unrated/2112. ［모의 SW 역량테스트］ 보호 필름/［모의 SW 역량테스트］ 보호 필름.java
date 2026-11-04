import java.io.*;
import java.util.*;

public class Solution {
	static int D, W, K;
    static int[][] map;
    static int[][] copy;
    static int ans;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[D][W];
            copy = new int[D][W];

            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    int val = Integer.parseInt(st.nextToken());
                    map[i][j] = val;
                    copy[i][j] = val;
                }
            }

            ans = K;

            if (check()) {
                sb.append("#").append(tc).append(" ").append(0).append("\n");
            } else {
                dfs(0, 0);
                sb.append("#").append(tc).append(" ").append(ans).append("\n");
            }
        }
        
        System.out.print(sb);
    }

    static void dfs(int layer, int count) {
        if (count >= ans) return;

        if (layer == D) {
            if (check()) {
                ans = Math.min(ans, count);
            }
            
            return;
        }

        dfs(layer + 1, count);

        for (int i = 0; i < W; i++) copy[layer][i] = 0;
        dfs(layer + 1, count + 1);

        for (int i = 0; i < W; i++) copy[layer][i] = 1;
        dfs(layer + 1, count + 1);

        for (int i = 0; i < W; i++) copy[layer][i] = map[layer][i];
    }

    static boolean check() {
        for (int j = 0; j < W; j++) {
            boolean valid = false;
            int count = 1;
            
            for (int i = 1; i < D; i++) {
                if (copy[i][j] == copy[i - 1][j]) {
                    count++;
                } else {
                    count = 1;
                }
                
                if (count >= K) {
                    valid = true;
                    break;
                }
            }
            
            if (K == 1) valid = true;
            if (!valid) return false;
        }
        
        return true;
    }
}
