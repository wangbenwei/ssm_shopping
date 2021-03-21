package com.NetEase.service;

import com.NetEase.pojo.Comment;

import java.util.List;

public interface CommentService {
    void add(Comment c);

    List<Comment> get(int pid);
}
