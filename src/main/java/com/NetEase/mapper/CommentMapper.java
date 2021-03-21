package com.NetEase.mapper;

import com.NetEase.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    void add(Comment c);

    List<Comment> get(int pid);
}
