package com.NetEase.service;

import com.NetEase.pojo.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> list();

    void update(Orders o);

    void add(Orders o);
}
