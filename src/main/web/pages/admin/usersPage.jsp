<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/29/2019
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include  file="../jspf/import.jspf"%>
    <title><fmt:message key="userspage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>
<div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="userspage.userid"/></th>
            <th scope="col"><fmt:message key="userspage.status"/></th>
            <th scope="col"><fmt:message key="userspage.username"/></th>
            <th scope="col"><fmt:message key="userspage.firstname"/></th>
            <th scope="col"><fmt:message key="userspage.secondname"/></th>
            <th scope="col"><fmt:message key="userspage.email"/></th>
            <th scope="col"><fmt:message key="userspage.blockunlock"/></th>
            <th scope="col"><fmt:message key="userspage.makeadmin"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                <tr>
                <td>${user.userId}</td>
                <td>
                    <c:if test="${!user.blocked}">
                        <fmt:message key="userspage.blocked"/>
                    </c:if>
                    <c:if test="${user.blocked}">
                        <fmt:message key="userspage.notblocked"/>
                    </c:if>
                </td>
                <td>${user.username}</td>
                <td>${user.firstName}</td>
                <td>${user.secondName}</td>
                <td>${user.email}</td>
                <td>
                    <c:if test="${!user.blocked}">
                        <form action="${pageContext.request.contextPath}/main" method="post" id="block">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="userspage.block"/></button>
                            <input type="hidden" name="command" value="block_user">
                            <input type="hidden" name="userId" value="${user.userId}">
                        </form>
                    </c:if>

                    <c:if test="${user.blocked}">
                        <form action="${pageContext.request.contextPath}/main" method="post" id="unlock">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="userspage.unlock"/></button>
                            <input type="hidden" name="command" value="unlock_user">
                            <input type="hidden" name="userId" value="${user.userId}">
                        </form>
                    </c:if>
                </td>

                <td>
                    <form action="${pageContext.request.contextPath}/main" method="post" id="make-admin">
                        <button type="submit"  class="btn btn-primary"><fmt:message key="startpage.title"/></button>
                        <input type="hidden" name="command" value="make_admin">
                        <input type="hidden" name="userId" value="${user.userId}">
                    </form>
                </td>
                </tr>
            </form>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
