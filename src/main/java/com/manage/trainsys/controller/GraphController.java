package com.manage.trainsys.controller;

import com.alibaba.fastjson.JSONObject;
import com.manage.trainsys.entity.Digraph;
import com.manage.trainsys.model.DigraphCreate;
import com.manage.trainsys.service.GraphService;
import com.manage.trainsys.model.GraphCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Deque;
import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphController {
    private GraphService graphService;
    @Autowired

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @RequestMapping("/save")
    public String saveGraph(){
        GraphCreate g=new GraphCreate(6);
        int[][] graph=new int[6][6];
        g.graphOut(graph);
        for (int i=0;i<6;i++)
             graphService.saveGraph(graph[i][0],graph[i][1],graph[i][2],graph[i][3],graph[i][4],graph[i][5]);
        return "success";
    }

    // 创建站点有向图的邻接表 提供站点的图的边
    @RequestMapping("/create/edge")
    public List<JSONObject> createGraphEdge(){
        DigraphCreate dig=graphService.buildGraph();
        return graphService.createGraphNeedEdge(dig.adjAll());
    }

    // 创建站点有向图的邻接表 提供站点的图的点
    @RequestMapping("/create/node")
    public List<JSONObject> createGraphNode(){
        DigraphCreate dig=graphService.buildGraph();
        return graphService.createGraphNeedNode(dig.adjAll());
    }

    // 读取邻接矩阵 后面会改为提供最短路径
    @RequestMapping("/read")
    public JSONObject read(String origin, String destination){
        return graphService.getAdjacencyMatrixAndGetShortestRoute(origin,destination);
    }

    // 修改邻接矩阵的值
    @RequestMapping("/alter")
    public String alterCell(String clm_name, String weight, String row_name){
        try{
            graphService.updateCell(clm_name, weight, row_name);
            return "success";
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
            return "number_error";
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return "empty_error";
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return "error";
        }
    }

    // 扩大邻接矩阵 用于插入一个站点
    @RequestMapping("/expend")
    public String expendMatrix(){
        graphService.expandAdjacencyMatrix();
        return "success";
    }

    // 取出digraph中所有记录
    @RequestMapping("/selectAll")
    public List<Digraph> selectDigraph(){
        return graphService.selectDigraph();
    }

    // 在digraph中添加一条记录
    @RequestMapping("/add/position")
    public String addDigraph(String station_name,String arrive_pos){
        try {
            graphService.addOnePosition(station_name, arrive_pos);
            return "success";
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return "error";
        }
    }
}
