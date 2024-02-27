<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <title>applyList</title>

</head>
<body>
    <h2>Apply List</h2>

    <c:forEach items="${applyDTOList }" var="apply" varStatus="status">
        <tr>
            <td class="has-text-centered">${status.count }</td>
            <td class="has-text-centered">${apply.name }</td>
            <td class="has-text-centered">${apply.age }</td>
            <td class="has-text-centered">${apply.tel }</td>
            <td class="has-text-centered">${apply.email }</td>
            <td class="has-text-centered">${apply.comment }</td>
            <td class="has-text-centered">${apply.regDate }</td>
            <td class="has-text-centered">${apply.category }</td>
        </tr>
    </c:forEach>
    <c:if test="${empty applyDTOList}" >
        <h1> 목록이 없음 </h1>
    </c:if>

</body>
</html>
