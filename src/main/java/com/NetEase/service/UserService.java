package com.NetEase.service;

import com.NetEase.pojo.User;

public interface UserService {
    User get(String username, String password);
}
