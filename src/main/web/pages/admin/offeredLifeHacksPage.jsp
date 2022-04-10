<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/26/2019
  Time: 10:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page import="LifeHackCategory" %>--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--<% pageContext.setAttribute("public", LifeHackCategory.PUBLIC); %>--%>
<html>
<head>
    <%@include  file="../jspf/import.jspf"%>
    <title><fmt:message key="offeredlifehackspage.title"/></title>
</head>
<body>

<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>
<div>
    <c:if test="${offeredLifeHacks == null || fn:length(offeredLifeHacks) == 0}">
        <fmt:message key="offeredlifehackspage.no"/>
    </c:if>
    <c:if test="${offeredLifeHacks != null || fn:length(offeredLifeHacks) != 0}">
        <c:forEach var="lifeHack" items="${offeredLifeHacks}">
            <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                    ${lifeHack.lifeHackId}<br>
                    ${lifeHack.userId}<br>
                    ${lifeHack.lifeHackCategory}<br>
                    <%--<c:when test="${lifeHack.category == public}">PUBLIC</c:when>--%>
                    ${lifeHack.name}<br>
                    ${lifeHack.description}<br>
                        <img style="width: 100%" src="data:image/jpeg;base64,${lifeHack.pictureEnc}" /><br>
                    <%--${lifeHack.dateOfPosting}<br>--%>
                        <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                            <button type="submit" name="submit" class="btn btn-primary"><fmt:message key="offeredlifehackspage.submit"/></button>
                            <input type="hidden" name="command" value="confirm_life_hack">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                            <input type="hidden" name="to" value="offered">
                        </form>
                        <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                            <button type="submit" name="reject" class="btn btn-primary"><fmt:message key="offeredlifehackspage.reject"/></button>
                            <input type="hidden" name="command" value="reject_life_hack">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                            <input type="hidden" name="to" value="offered">
                        </form>


            </form>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
