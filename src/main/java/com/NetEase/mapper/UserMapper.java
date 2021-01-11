package com.NetEase.mapper;

import com.NetEase.pojo.User;

public interface UserMapper {
    User get(String username, String password);
}
