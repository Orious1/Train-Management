package com.manage.trainsys.mapper;

import com.manage.trainsys.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("select * from user where account=#{account}")
    User findByUserId(@Param("account")String account);
}
