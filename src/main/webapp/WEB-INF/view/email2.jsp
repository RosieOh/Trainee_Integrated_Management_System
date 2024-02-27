<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path1" value="${pageContext.servletContext.contextPath }" />

<!DOCTYPE html>
<html lang="en">

<body>
<div style="margin: 3rem;padding-left: 10rem">
    <h1 style="font-size: 3rem; font-weight: bold;">Word Master</h1>
    <br>
    <div style='border:none; text-align: left;'>
        <h2>문의하신 내용</h2>
        <h2>문의하신 내용</h2>
        <h3><%= message.getMainTitle() %></h3>
        <h3><%= message.getMainContent() %></h3>
        <br>
        <br>
        <h2>관리자 답변</h2>
        <h3><%= message.getSubtitle() %></h3>
        <h3><%= message.getMessage() %></h3>
    </div>
    <br>
    <br>
    <br>
    <div>
        <h2> 추가 문의가 필요하실 경우 </h2>
    </div>
</div>
</body>
</html>