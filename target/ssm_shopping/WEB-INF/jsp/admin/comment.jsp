<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp" %>


<title>王本巍大作业</title>

<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2>提交评价</h2>
    </div>
    <div class="n-public">
        <form class="m-form m-form-ht" id="form" method="post" action="submitComment?pid=${p.id}"
              autocomplete="off">
            <div class="fmitem">
                <label class="fmlab">商品名称：</label>
                <div class="fmipt">
                    <input class="u-ipt ipt" name="name" autofocus value="${p.name}" disabled="disabled"/>
                </div>
            </div>
            <div class="fmitem">
                <label class="fmlab">评价内容：</label>
                <div class="fmipt">
                    <textarea class="u-ipt" name="content" rows="10" placeholder="2-1000个字符"></textarea>
                </div>
            </div>
            <div class="fmitem fmitem-nolab fmitem-btn">
                <div class="fmipt">
                    <button type="submit" class="u-btn u-btn-primary u-btn-lg">发 布</button>
                </div>
            </div>
        </form>
    </div>
</div>


<%@include file="../include/admin/adminFooter.jsp" %>