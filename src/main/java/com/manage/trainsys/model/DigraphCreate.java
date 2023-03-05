package com.manage.trainsys.model;

import java.util.Deque;
import java.util.LinkedList;

public class DigraphCreate {
    //顶点数目
    private final int V;
    //边的数目
    private int Edge;
    //邻接表
    private Deque<Integer>[] adj;

    public DigraphCreate(int V){
        //初始化顶点数量
        this.V = V;
        //初始化边的数量
        this.Edge = 0;
        //初始化邻接表
        this.adj = new Deque[V];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LinkedList<>();
        }
    }
    //获取顶点数目
    public int getVertexNumber(){
        return V;
    }
    //获取边的数目
    public int getEdgeNumber(){
        return Edge;
    }
    //向有向图中添加一条边 v->w
    public void addEdge(int v, Integer w) {
        //只需要让顶点w出现在顶点v的邻接表中，因为边是有方向的，最终，顶点v的邻接表中存储的相邻顶点的含义是： v->其他顶点
        adj[v].offer(w);
        Edge++;
    }
    //获取由v指出的边所连接的所有顶点
    public Deque<Integer> adj(int v){
        return adj[v];
    }

    public Deque<Integer>[] adjAll(){
        return adj;
    }
}
