package com.NetEase.pojo;

import java.util.Date;

public class Orders {
    private Integer id;
    private Integer pid;
    private Integer number;
    private Product product;
    private Date createDate;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", pid=" + pid +
                ", number=" + number +
                ", product=" + product +
                ", createDate=" + createDate +
                '}';
    }
}
