package com.app.datastructures.grid;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，
 * 并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出: 1
 * ① 二维数组唯一标识(m,n)：m*collen+n
 * ② 标志位不需要另外记录，设置为'0'
 * @author:wuqi
 * @date:2020/2/26
 * @description:com.app.datastructures.grid
 * @version:1.0
 */
public class BFSForIslands {

    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '0') {
                    continue;
                }
                bfs(i, j, grid);
                count++;
            }
        }
        return count;
    }

    public void bfs(int i, int j, char[][] grid) {
        Queue<Integer> q = new LinkedBlockingQueue<>();
        grid[i][j] = '0';
        q.add(i * grid[0].length + j);
        while (!q.isEmpty()) {
            int num = q.poll();
            int x = num / grid[0].length;
            int y = num % grid[0].length;
            if (x - 1 >= 0 && grid[x - 1][y] == '1') {
                q.add((x - 1) * grid[0].length + y);
                grid[x - 1][y] = '0';
            }
            if (y + 1 < grid[0].length && grid[x][y + 1] == '1') {
                q.add((x) * grid[0].length + y + 1);
                grid[x][y + 1] = '0';
            }
            if (x + 1 < grid.length && grid[x + 1][y] == '1') {
                q.add((x + 1) * grid[0].length + y);
                grid[x + 1][y] = '0';
            }
            if (y - 1 >= 0 && grid[x][y - 1] == '1') {
                q.add((x) * grid[0].length + y - 1);
                grid[x][y - 1] = '0';
            }
        }
    }

    public static void main(String[] args) {
        //char[][] grid = {{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}};
        //System.out.println(new BFSForIslands().numIslands(grid));
        char[][] grid2 = {{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println(new BFSForIslands().numIslands(grid2));
        String s = null;
        s.toString();
    }
}
