<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>

<title>shopping</title>

<div class="g-doc">
    <div class="n-show f-cb" id="showContent">
        <div class="img"><img src="${p.picture}" alt=""></div>
        <div class="cnt">
            <h2>${p.name}</h2>
            <p class="summary">${p.subTitle}</p>
            <div class="price">
                <span class="v-unit">¥</span><span class="v-value">${p.price}</span>
            </div>
            <!-- 这里要区分买家和卖家，这里还没有做-->
            <div class="num">购买数量：<span id="plusNum" class="lessNum"><a>-</a></span><span class="totalNum"
                                                                                          id="allNum">0</span><span
                    id="addNum" class="moreNum"><a>+</a></span></div>
            <div class="oprt f-cb">
                <!-- 卖家部分-->
                <c:if test="${user.category eq 'seller'}">
                    <a href="propertyEditor?pid=${p.id}" class="u-btn u-btn-primary">编 辑</a>
                    <a href="delete?pid=${p.id}" onclick="return del()" class="u-btn u-btn-primary">删 除</a>
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
        <h2>详细信息</h2>
    </div>
    <div class="n-detail">
        ${p.detail}
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>