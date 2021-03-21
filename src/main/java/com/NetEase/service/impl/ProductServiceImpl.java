package com.NetEase.service.impl;

import com.NetEase.mapper.ProductMapper;
import com.NetEase.pojo.Product;
import com.NetEase.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    public List<Product> list() {
        return productMapper.list();
    }

    public Product get(int id) {
        return productMapper.get(id);
    }


    public void add(Product product) {
        productMapper.add(product);
    }


    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public void delete(int pid) {
        productMapper.delete(pid);
    }

    public Product search(String productName) {
        return productMapper.search(productName);
    }

    ;
}
