<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminSearch.jsp" %>

<title>王本巍大作业</title>

<%--<div class="noResult">--%>
<%--    <p class="tip">--%>
<%--        您要的“${msg}”还没上架--%>
<%--    </p>--%>
<%--</div>--%>

<div class="noResult">
    <p class="tip">
        <c:if test="${!empty user}">
            <c:if test="${user.category eq 'seller'}">
                <font size=15>“${msg}”还没上架</font>
                <br><br><br><br>
                <a href="notListedPublish?msg=${msg}" style="color:red;font-size: 50px">立即上架</a>
            </c:if>
            <c:if test="${user.category eq 'buyer'}">
                <font size=15>您要的“${msg}”还没上架</font>
            </c:if>
        </c:if>

        <c:if test="${empty user}">
            <font size=15>您要的“${msg}”还没上架</font>
        </c:if>
    </p>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>