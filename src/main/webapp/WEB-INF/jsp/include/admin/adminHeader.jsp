<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!-- 引入标签库，包括c,fmt,fn-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<html>

<head>
    <meta charset="utf-8"/>
    <link href="css/style.css" rel="stylesheet">
    <script>
        function del() {
            var fdel = window.confirm("是否确定删除？ ");
            if (fdel) {
                return true;
            }
            else {
                return false;
            }
        }
    </script>
</head>

<body>

<div class="n-support">请使用Chrome、Safari等webkit内核的浏览器！</div>
<div class="n-head">
    <div class="g-doc f-cb">

        <c:if test="${!empty user}">
            <c:if test="${user.category eq 'seller'}">
                <a href="loginPage">卖家您好，${user.username}！</a>
            </c:if>
            <c:if test="${user.category eq 'buyer'}">
                <a href="loginPage">买家您好，${user.username}！</a>
            </c:if>
            <a href="logout">[退出]</a>
        </c:if>

        <c:if test="${empty user}">
            <a href="loginPage">[登录]</a>
        </c:if>

        <ul class="nav">
            <c:if test="${!empty user}">
                <c:if test="${user.category eq 'seller'}">
                    <a href="publish">发布</a>
                </c:if>
            </c:if>
        </ul>

        <ul class="nav">
            <c:if test="${!empty user}">
                <c:if test="${user.category eq 'buyer'}">
                    <a href="shoppingCart">购物车</a>
                </c:if>
            </c:if>
        </ul>

        <ul class="nav">
            <c:if test="${!empty user}">
                <c:if test="${user.category eq 'buyer'}">
                    <a href="accountPage">账目</a>
                </c:if>
            </c:if>
        </ul>

        <ul class="nav">
            <li><a href="admin_product_list">首页</a></li>
        </ul>


    </div>
</div>