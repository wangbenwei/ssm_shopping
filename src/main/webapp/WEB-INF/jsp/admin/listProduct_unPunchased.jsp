<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>

<title>shopping</title>

<div class="g-doc">

    <div class="g-doc">
        <div class="m-tab m-tab-fw m-tab-simple f-cb">
            <div class="tab">
                <ul>
                    <li class=""><a href="admin_product_list">所有内容</a></li>
                    <c:if test="${!empty user}">
                        <c:if test="${user.category eq 'buyer'}">
                            <li class="z-sel"><a href="unPunchased_list">未购买的内容</a></li>
                        </c:if>
                    </c:if>
                </ul>
            </div>
        </div>

        <div class="n-plist">
            <ul class="f-cb" id="plist">

                <c:forEach items="${ps}" var="p">
                    <li id="p-1">
                        <a href="detail_product?pid=${p.id}" class="link">
                            <div class="img"><img
                                    src="${p.picture}"
                                    alt="${p.name}"></div>
                            <h3>${p.name}</h3>
                            <div class="price"><span class="v-unit">¥</span><span class="v-value">${p.price}</span>
                            </div>
                            <c:if test="${!empty user}">
                                <c:if test="${!empty p.orders}">
                                    <c:if test="${user.category eq 'buyer'}">
                                        <span class="had"><b>已购买</b></span>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </a>
                    </li>
                </c:forEach>

            </ul>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>