<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>

<title>王本巍大作业</title>

<div class="g-doc">
    <div class="n-result">
        <h3>评价成功！</h3>
        <p><a href="detail_product?pid=${pid}">[查看内容]</a><a href="admin_product_list">[返回首页]</a></p>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>