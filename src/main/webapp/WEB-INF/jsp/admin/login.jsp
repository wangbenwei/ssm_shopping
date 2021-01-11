<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../include/admin/adminHeader.jsp" %>

<script>
    if ("${msg}" == "账号密码错误") {
        alert("${msg}");
    }
</script>

<title>shopping</title>
<!-- onsubmit是在表单提交时触发的事件, 没有搞懂怎么弄-->

<form class="m-form m-form-ht n-login" id="loginForm" autocomplete="off" action="foreLogin" method="post"
      autocomplete="off">

    <%--<form class="m-form m-form-ht n-login" id="loginForm" autocomplete="off" onsubmit="return false"--%>
    <%--autocomplete="off">--%>

    <div class="fmitem">
        <label class="fmlab">用户名：</label>
        <div class="fmipt">
            <input class="u-ipt" name="userName" autofocus/>
        </div>
    </div>
    <div class="fmitem">
        <label class="fmlab">密码：</label>
        <div class="fmipt">
            <input class="u-ipt" type="password" name="password"/>
        </div>
    </div>
    <div class="fmitem fmitem-nolab fmitem-btn">
        <div class="fmipt">
            <button type="submit" class="u-btn u-btn-primary u-btn-lg u-btn-block">登 录</button>
        </div>
    </div>
</form>


<%@include file="../include/admin/adminFooter.jsp" %>