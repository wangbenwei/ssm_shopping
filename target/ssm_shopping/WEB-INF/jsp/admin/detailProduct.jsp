<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminSearch.jsp" %>

<title>王本巍大作业</title>

<div class="g-doc">
    <div class="n-show f-cb" id="showContent">
        <div class="img"><img src="${p.picture}" alt=""></div>
        <div class="cnt">
            <h2 style="font-size: 32px">${p.name}</h2>
            <p class="summary">${p.subTitle}</p>
            <div class="price">
                <span class="v-unit">¥</span><span class="v-value">${p.price}</span>
            </div>
            <!-- 这里要区分买家和卖家-->
            <c:if test="${!empty user}">
                <!-- 买家-->
                <c:if test="${user.category eq 'buyer'}">
                    <div class="num">购买数量：<span id="plusNum" class="lessNum"><a>-</a></span><span class="totalNum"
                                                                                                  id="allNum">0</span><span
                            id="addNum" class="moreNum"><a>+</a></span>
                    </div>
                </c:if>

                <!-- 卖家-->
                <c:if test="${user.category eq 'seller'}">
                    <div class="num">
                        <c:if test="${!empty p.orders}">售出数量：${p.orders.number}</c:if>
                        <c:if test="${empty p.orders}">未售出</c:if>
                    </div>
                </c:if>
            </c:if>


            <div class="oprt f-cb">
                <!-- 卖家部分-->
                <c:if test="${user.category eq 'seller'}">
                    <a href="propertyEditor?pid=${p.id}" class="u-btn u-btn-primary">编 辑</a>
                    <!--
                    <a href="delete?pid=${p.id}" onclick="return del()" class="u-btn u-btn-primary">删 除</a>
                    -->
                </c:if>

                <!-- 买家部分-->
                <c:if test="${user.category eq 'buyer'}">
                    <button class="u-btn u-btn-primary" id="add" data-id="${p.id}" data-title="${p.name}"
                            data-price="${p.price}">
                        加入购物车
                    </button>
                </c:if>
            </div>
        </div>
    </div>
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2 style="font-size: 26px">详细信息</h2>
    </div>
    <div class="n-detail">
        ${p.detail}
    </div>
    <br><br>
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2 style="font-size: 26px">商品评价</h2>
    </div>
    <c:forEach items="${p.comments}" var="pComment">
        <div class="n-detail">
                ${pComment.content}
        </div>
        <br>
    </c:forEach>

</div>

<%@include file="../include/admin/adminFooter.jsp" %>