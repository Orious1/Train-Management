package com.manage.trainsys.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphCreate {
    public final static int MAX_INT = 1000000;
    private int[][] graph;
    public GraphCreate() {
    }
    public GraphCreate(int n) {
        graph=new int[n][n];
        Random random=new Random();
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                if (i==j) {
                    graph[i][j] = MAX_INT;
                }
                else {
                    graph[i][j] = random.nextInt(20);
                    graph[j][i]=graph[i][j];
                }
            }
        }
    }

    public void graphOut(int copy_graph[][]){
        for (int i=0;i<graph.length;i++){
            for (int j=0;j<graph.length;j++){
                copy_graph[i][j]=graph[i][j];
            }
        }
    }

    /**
     * 迪杰斯卡尔算法 寻找图中的最短路径
     * 这种写法在遇到不可达的点的时候会陷入死循环。
     * @param edges 表示邻接矩阵
     * @param n     表示图中节点个数
     * @param point 表示出发点
     * @param path  表示出发点到每个点的最短路径
     * @return dis 返回出发点到所有点的最短路径的长度
     */
    public static long[] dijkstra(int[][] edges, int n, int point, String[] path) {
        //dis数组存放 point点 到每个点的最短路径长度
        long[] dis = new long[n];
        //用于标记某个点是否已经完成 完成为true 没完成为false
        boolean[] flag = new boolean[n];
        //存放 point点 可以到达的点的集合
        Set<Integer> ablePoint = new HashSet<>();
        int temp = point;
        //存放 point点 到每个点的最短路径
        for (int i = 0; i < n; i++) {
            path[i] = Integer.toString(point);
        }
        int k = 0;
        while (ablePoint.size() != n) {
            ablePoint.add(point);// 把可以抵达的点放入集合
            for (int i = 0; i < edges[point].length; i++) {
                if (edges[point][i] != MAX_INT && edges[point][i] != 0) {
                    if ((dis[point] + edges[point][i] <= dis[i]) || dis[i] == 0) { // 只有当新路径比原来短时才替换 或者 一开始为0的时候赋值
                        dis[i] = dis[point] + edges[point][i]; // 记录最短路径长度
                        path[i] = path[point].concat(" ".concat(Integer.toString(i))); // 记录路径
                    }
                }
            }
            flag[point] = true; //把已经完成的点排除在外
            point = getMinimum(dis, flag, n); //获取最小的路径的点作为下一个基准点
            k = k + 1; // 防止有不可达的点产生死循环
            if (k > n || point == -1)
                break;
        }
        dis[temp] = 0; // 如果图中出现出发点指向出发点的环，强制置0
        return dis;
    }

    private static int getMinimum(long[] arr, boolean[] flag, int n) {
        int minIndex = -1;
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (arr[i] != 0 && arr[i] < min && !flag[i]) {
                min = arr[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
}
