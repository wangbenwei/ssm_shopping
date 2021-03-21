package com.NetEase.service.impl;

import com.NetEase.mapper.CommentMapper;
import com.NetEase.pojo.Comment;
import com.NetEase.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public void add(Comment c) {
        commentMapper.add(c);
    }

    @Override
    public List<Comment> get(int pid) {
        return commentMapper.get(pid);
    }
}
