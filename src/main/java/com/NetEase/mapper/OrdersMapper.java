package com.NetEase.mapper;

import com.NetEase.pojo.Orders;

import java.util.List;

public interface OrdersMapper {
    List<Orders> list();

    void update(Orders o);

    void add(Orders o);

    Orders get(int pid);
}
