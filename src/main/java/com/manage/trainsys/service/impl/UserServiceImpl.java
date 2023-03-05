package com.manage.trainsys.service.impl;

import com.manage.trainsys.entity.User;
import com.manage.trainsys.mapper.UserMapper;
import com.manage.trainsys.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String account, String password) {
        User user = userMapper.findByUserId(account);
        if (ObjectUtils.isEmpty(user)) throw new RuntimeException("账号错误");
        if (!user.getPassword().equals(password)) throw new RuntimeException("密码错误");
        return user;
    }
}
