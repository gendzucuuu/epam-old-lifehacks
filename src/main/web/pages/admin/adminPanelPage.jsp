<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/29/2019
  Time: 9:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../jspf/import.jspf"%>
    <title><fmt:message key="adminpanelpage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>
<div>
    <p><fmt:message key="adminpanelpage.countusers"/> ${countOfUsers}</p>
    <p><fmt:message key="adminpanelpage.countactusers"/> ${countOfActiveUsers}</p>
    <p><fmt:message key="adminpanelpage.countblockusers"/> ${countOfBlockedUsers}</p>
    <p><fmt:message key="adminpanelpage.countlifehacks"/> ${countOfLifeHacks}</p>
    <p><fmt:message key="adminpanelpage.countofflifehacks"/> ${countOfOfferedLifeHacks}</p>
    <p><fmt:message key="adminpanelpage.countsublifehacks"/> ${countOfSubmittedLifeHacks}</p>
    <p><fmt:message key="adminpanelpage.countrejlifehacks"/> ${countOfRejectedLifeHacks}</p>
    <form action="${pageContext.request.contextPath}/main" method="post" id="manage-users">
        <button type="submit" class="btn btn-primary"><fmt:message key="adminpanelpage.manageusers"/></button>
        <input type="hidden" name="command" value="manage_users">
    </form>

    <form action="${pageContext.request.contextPath}/main" method="post" id="manage-life-hacks">
        <button type="submit" class="btn btn-primary"><fmt:message key="adminpanelpage.managelifehacks"/></button>
        <input type="hidden" name="command" value="manage_life_hacks">
    </form>

    <form action="${pageContext.request.contextPath}/main" method="post" id="users-offers">
        <button type="submit" class="btn btn-primary"><fmt:message key="adminpanelpage.offers"/></button>
        <input type="hidden" name="command" value="users_offers">
    </form>
</div>
</body>
</html>
