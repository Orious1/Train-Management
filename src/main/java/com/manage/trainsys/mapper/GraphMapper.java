package com.manage.trainsys.mapper;

import com.manage.trainsys.entity.Digraph;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GraphMapper {
    @Insert("INSERT INTO graph VALUES(#{a},#{b},#{c},#{d},#{e},#{f})")
    void saveGraph(@Param("a") int a, @Param("b") int b , @Param("c") int c ,@Param("d") int d ,@Param("e") int e,@Param("f") int f);

    @Select("SELECT * FROM digraph")
    List<Digraph> findAllVertex();

    //获取digraph表的行数
    @Select("SELECT COUNT(*) FROM digraph")
    int getDigraphSize();

    @Insert("INSERT INTO digraph VALUES(#{id},#{station_name},#{arrive_pos})")
    int insertDigraph(@Param("id") int id,@Param("station_name") String station_name,@Param("arrive_pos") String arrive_pos);

    // 读取某一列
    @Select("SELECT ${clm} FROM graph")
    List<Integer> readMatrix(@Param("clm")String clm);

    // 获取矩阵行数
    @Select("SELECT COUNT(*) FROM graph")
    int getMatrixSize();

    //添加一列
    @Update("ALTER TABLE graph ADD ${clm} int DEFAULT 1000000")
    void addColumn(@Param("clm")String clm);

    //添加一行记录
    @Insert("INSERT INTO graph VALUES()")
    void insertLine();

    //添加索引
    @Update("UPDATE graph SET row_non=#{num} WHERE row_non IS NULL")
    void addMatrixIndexNum(@Param("num")int num);

    // 修改矩阵单元格
    @Update("UPDATE graph SET ${clm}=#{weight} WHERE row_non=#{row_num}")
    void alterCell(@Param("clm") String clm,@Param("weight") int weight,@Param("row_num") int row_num);

    @Select("SELECT station_name FROM  digraph")
    String[] selectAllStationName();
}
