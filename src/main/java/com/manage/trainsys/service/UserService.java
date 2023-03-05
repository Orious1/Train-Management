package com.manage.trainsys.service;


import com.manage.trainsys.entity.User;

public interface UserService {

    User login(String account,String password);
}
