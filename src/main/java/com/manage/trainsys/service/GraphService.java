package com.manage.trainsys.service;

import com.alibaba.fastjson.JSONObject;
import com.manage.trainsys.entity.Digraph;
import com.manage.trainsys.model.DigraphCreate;
import org.apache.ibatis.annotations.Param;

import java.util.Deque;
import java.util.List;


public interface GraphService {

    void saveGraph(int a,int b,int c,int d,int e,int f);

    DigraphCreate buildGraph();

    JSONObject getAdjacencyMatrixAndGetShortestRoute(String start, String end);

    void expandAdjacencyMatrix();

    void updateCell(String clm,String weight,String row_name);

    List<Digraph> selectDigraph();

    int addOnePosition(String station_name,String arrive_pos);

    List<JSONObject> createGraphNeedEdge(Deque<Integer>[] adj);

    List<JSONObject> createGraphNeedNode(Deque<Integer>[] adj);
}
