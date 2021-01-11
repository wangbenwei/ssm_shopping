package com.NetEase.service.impl;

import com.NetEase.mapper.UserMapper;
import com.NetEase.pojo.User;
import com.NetEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User get(String username, String password) {
        return userMapper.get(username, password);
    }
}
