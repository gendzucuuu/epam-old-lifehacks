<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/28/2019
  Time: 1:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../jspf/import.jspf"%>
    <title><fmt:message key="favoritelifehackspage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>
<div>
    <c:if test="${favoriteLifeHacks == null || fn:length(favoriteLifeHacks) == 0}">
        <fmt:message key="favoritelifehackspage.no"/>
    </c:if>
    <c:if test="${favoriteLifeHacks != null || fn:length(favoriteLifeHacks) != 0}">
        <c:forEach var="lifeHack" items="${favoriteLifeHacks}">
            <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                    ${lifeHack.name}<br>
                        <img style="height: 300px; width: 300px; object-fit: contain" src="data:image/jpeg;base64,${lifeHack.pictureEnc}" /><br>
                <button type="submit"  class="btn btn-primary"><fmt:message key="favoritelifehackspage.go"/></button>
                <input type="hidden" name="command" value="go_to_life_hack_page">
                <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
            </form>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
