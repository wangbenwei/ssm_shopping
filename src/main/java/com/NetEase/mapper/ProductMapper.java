package com.NetEase.mapper;

import com.NetEase.pojo.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> list();

    Product get(int id);

    void add(Product product);

    void update(Product product);

    void delete(int pid);

    Product search(String productName);
}
