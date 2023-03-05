package com.manage.trainsys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.manage.trainsys.entity.Digraph;
import com.manage.trainsys.mapper.GraphMapper;
import com.manage.trainsys.model.DigraphCreate;
import com.manage.trainsys.model.GraphCreate;
import com.manage.trainsys.service.GraphService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GraphServiceImpl implements GraphService {
    @Resource
    GraphMapper graphMapper;
    // 获取邻接矩阵
    @Override
    public JSONObject getAdjacencyMatrixAndGetShortestRoute(String start,String end) {
        int count=graphMapper.getMatrixSize();
        Integer[][] matrix=new Integer[count][count];
        List<Integer> matrixColumn;
        for (int i=0;i<count;i++){
            String clm="_"+i;
            matrixColumn=graphMapper.readMatrix(clm);
            for (int j=0;j<count;j++){
                matrix[j][i]=matrixColumn.get(j);
            }
        }
        int[][] matrixInt=new int[count][count];
        for (int i=0;i<count;i++){
            for (int j=0;j<count;j++){
                int k=matrix[i][j];
                matrixInt[i][j]=k;
                System.out.print(matrixInt[i][j]+"  ");
            }
            System.out.println();
        }
        String[] path = new String[count];
        String[] position=graphMapper.selectAllStationName();
        int originIndex=0;
        String destinationIndex="-1";
        int destinationIndexInt=-1;
        String msg="";
        for (int i=0;i<count;i++){
            if (position[i].equals(start)){
                originIndex=i;
            }
            if (position[i].equals(end)){
                destinationIndex=Integer.toString(i);
                destinationIndexInt=i;
            }
        }
        long[] shortestRoute = GraphCreate.dijkstra(matrixInt,count,originIndex,path);
        for (int i = 0; i < count; i++)
            System.out.print(shortestRoute[i]+" ");
        System.out.println();
        String temp = null;
        for (int i = 0; i < count; i++){
            // System.out.println(path[i]);
            int k=path[i].lastIndexOf(" ");
            if (path[i].substring(k + 1).equals(destinationIndex)) {
                System.out.println("最后一个数字是:" + path[i].substring(k + 1));
                temp=path[i];
            }
        }
        System.out.println(temp);
        String positionName="";
        JSONObject json=new JSONObject();
        if(temp!=null) {
            for (int i = 0; i < temp.length(); i = i + 2) {  // 取出路径中的每个点
                System.out.println(temp.substring(i, i + 1));
                int positionNum = Integer.parseInt(temp.substring(i, i + 1));
                positionName = positionName.concat(position[positionNum].concat("->"));
            }
            positionName = positionName.substring(0, positionName.lastIndexOf("-"));
            System.out.println(positionName);
            msg="success";
            json.put("shortestRoute",positionName);
            json.put("routeLong",shortestRoute[destinationIndexInt]);
            json.put("state",msg);
        }else {
            msg="error";
            json.put("shortestRoute","");
            json.put("routeLong",0);
            json.put("state",msg);
            System.out.println("两个站点之间没有可以到达的路径");
        }
        System.out.println(json.toString());
        return json;
    }

    @Override
    public void expandAdjacencyMatrix() {
        int count=graphMapper.getMatrixSize();
        String clm="_"+count;
        graphMapper.addColumn(clm);
        graphMapper.insertLine();
        graphMapper.addMatrixIndexNum(count);
    }

    @Override
    public void updateCell(String clm, String weight, String row_name) {
        int weightN=Integer.parseInt(weight);
        if (Objects.equals(clm, "") || Objects.equals(row_name, "")) throw new NullPointerException("站点有空");
        List<Digraph> list=graphMapper.findAllVertex();
        int clm_num=-1;
        int row_num=-1;
        for (int i=0;i<list.size();i++){
            if (Objects.equals(list.get(i).getStation_name(), clm)){
                clm_num=i;
            }
            if (Objects.equals(list.get(i).getStation_name(), row_name)){
                row_num=i;
            }
        }
        String clm_name="_"+clm_num;
        graphMapper.alterCell(clm_name, weightN, row_num);
    }

    // 构建邻接表
    @Override
    public DigraphCreate buildGraph() {
        // 初始化数组
        List<Digraph> digs=graphMapper.findAllVertex();
        int num=digs.size();
        DigraphCreate digCre=new DigraphCreate(num);
        String[] pos_name=new String[num];
        String[] pos_arrive=new String[num];
        for (Digraph i:digs){
           pos_name[i.getId()]=i.getStation_name();
           pos_arrive[i.getId()]=i.getArrive_pos();
        }
        for(int i=0;i<digs.size();i++){
            pos_arrive[i]+=".";
        }
        // 创建详细
        List<String> list=new ArrayList<>();
        for (int z=0;z<digs.size();z++) {
            StringBuilder word = new StringBuilder();
            for (char c : pos_arrive[z].toCharArray()) {
                if (Character.isLetter(c)) {//为字符
                    word.append(c);
                } else if (word.length() > 0) {
                    String finalword = word.toString();
                    list.add(finalword);
                    word = new StringBuilder();
                }
            }
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < pos_name.length; j++) {
                    if (pos_name[j].equals(list.get(i))) {
                        digCre.addEdge(z, j);
                    }
                }
            }
            //System.out.println(digCre.adj(z));
            list.clear();
        }
        return digCre;
    }

    @Override
    public List<JSONObject> createGraphNeedEdge(Deque<Integer>[] adj) {
        int num=adj.length;
        List<JSONObject> list=new ArrayList<>();
        for (int i=0;i<num;i++){
            System.out.println(adj[i]);
            for (int j : adj[i]){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("source",i);
                jsonObject.put("target",j);
                list.add(jsonObject);
            }
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<JSONObject> createGraphNeedNode(Deque<Integer>[] adj) {
        int num=adj.length;
        List<Digraph> digs=graphMapper.findAllVertex();
        List<JSONObject> list=new ArrayList<>();
        for (Digraph i:digs){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",i.getStation_name());
            list.add(jsonObject);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<Digraph> selectDigraph() {
        return graphMapper.findAllVertex();
    }

    @Override
    public int addOnePosition(String station_name, String arrive_pos) {
        if (Objects.equals(station_name, "") || Objects.equals(arrive_pos, "")) throw new RuntimeException("输入不能为空");
        int id=graphMapper.getDigraphSize();
        return graphMapper.insertDigraph(id, station_name, arrive_pos);
    }

    @Override
    public void saveGraph(int a, int b, int c, int d, int e, int f) {
        graphMapper.saveGraph(a, b, c, d, e, f);
    }
}
