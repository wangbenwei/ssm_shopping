package com.NetEase.service.impl;

import com.NetEase.mapper.OrdersMapper;
import com.NetEase.pojo.Orders;
import com.NetEase.pojo.Product;
import com.NetEase.service.OrdersService;
import com.NetEase.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    ProductService productService;

    @Override
    public List<Orders> list() {
        List<Orders> ordersList = ordersMapper.list();
        setProduct(ordersList);
        return ordersList;
    }

    @Override
    public void update(Orders o) {
        ordersMapper.update(o);
    }

    @Override
    public void add(Orders o) {
        ordersMapper.add(o);
    }

    @Override
    public Orders get(int pid) {
        return ordersMapper.get(pid);
    }

    public void setProduct(List<Orders> ordersList) {
        for (Orders ol : ordersList) {
            setProduct(ol);
        }
    }

    public void setProduct(Orders orders) {
        Product p = productService.get(orders.getPid());
        orders.setProduct(p);
    }
}
