<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>

<title>王本巍大作业</title>


<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2>已购买的内容</h2>
    </div>
    <table class="m-table m-table-row n-table g-b3">
        <colgroup>
            <col class="img"/>
            <col/>
            <col class="time"/>
            <col/>
            <col class="num"/>
            <col/>
            <col class="price"/>
            <col/>
        </colgroup>
        <thead>
        <tr>
            <th align="right">内容图片</th>
            <th align="right">内容名称</th>
            <th align="right">购买时间</th>
            <th align="right">购买数量</th>
            <th align="right">购买单价</th>
            <th align="right">购买总价</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${o}" var="o">
            <tr>
                <td><a href="detail_product?pid=${o.pid}"><img src="${o.product.picture}" alt=""></a></td>
                <td><h4><a href="detail_product?pid=${o.pid}">${o.product.name}</a></h4></td>
                <td><span class="v-time">${o.createDate}</span></td>
                <td><span class="v-num">${o.number}</span></td>
                <td><span class="v-unit">¥</span><span class="value">${o.product.price}</span></td>
                <td><span class="v-unit">¥</span><span class="value">${o.product.price*o.number}</span></td>
            </tr>
        </c:forEach>
        </tbody>

        <tfoot>
        <tr>
            <td colspan="5"><!-- 这里的数字5代表这一行是把5列合并成一列（只对这一行起作用）-->
                <div class="total">总计：</div>
            </td>
            <td><span class="v-unit">¥</span><span class="value">${price}</span></td>
        </tr>
        </tfoot>
    </table>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>