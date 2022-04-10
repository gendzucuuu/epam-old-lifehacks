<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/30/2019
  Time: 12:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include  file="../jspf/import.jspf"%>
    <title><fmt:message key="lifehacksadminpage.title"/></title>
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
            <th scope="col"><fmt:message key="lifehacksadminpage.lifehackid"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.userid"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.category"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.name"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.description"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.image"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.status"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.action"/></th>
            <th scope="col"><fmt:message key="lifehacksadminpage.edit"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="lifeHack" items="${lifeHacks}">
            <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                <tr>
                    <td>${lifeHack.lifeHackId}</td>
                    <td>${lifeHack.userId}</td>
                    <td>${lifeHack.lifeHackCategory}</td>
                    <td>${lifeHack.name}</td>
                    <td>${lifeHack.description}</td>
                    <td><img style="height: 300px; width: 300px; object-fit: contain" src="data:image/jpeg;base64,${lifeHack.pictureEnc}" /></td>
                    <td>
                        <c:if test="${lifeHack.status eq '1'}">
                            <fmt:message key="lifehacksadminpage.confirmed"/>
                        </c:if>

                        <c:if test="${lifeHack.status eq '0'}">
                            <fmt:message key="lifehacksadminpage.offered"/>
                        </c:if>

                        <c:if test="${lifeHack.status eq '2'}">
                            <fmt:message key="lifehacksadminpage.rejected"/>
                        </c:if>
                    </td>

                    <td>
                        <c:if test="${lifeHack.status eq '1'}">
                            <form action="${pageContext.request.contextPath}/main" method="post" id="reject">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="lifehacksadminpage.reject"/></button>
                            <input type="hidden" name="command" value="reject_life_hack">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                                <input type="hidden" name="to" value="admin">
                            </form>
                        </c:if>

                        <c:if test="${lifeHack.status eq '0'}">
                            <div dis>
                            <form action="${pageContext.request.contextPath}/main" method="post" id="confirm">
                                <button type="submit"  class="btn btn-primary"><fmt:message key="lifehacksadminpage.submit"/></button>
                                <input type="hidden" name="command" value="confirm_life_hack">
                                <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                                <input type="hidden" name="to" value="admin">
                            </form>

                            <form action="${pageContext.request.contextPath}/main" method="post" id="reject">
                                <button type="submit"  class="btn btn-primary"><fmt:message key="lifehacksadminpage.reject"/></button>
                                <input type="hidden" name="command" value="reject_life_hack">
                                <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                                <input type="hidden" name="to" value="admin">
                            </form>
                            </div>
                        </c:if>

                        <c:if test="${lifeHack.status eq '2'}">
                            <form action="${pageContext.request.contextPath}/main" method="post" id="confirm">
                                <button type="submit"  class="btn btn-primary"><fmt:message key="lifehacksadminpage.submit"/></button>
                                <input type="hidden" name="command" value="confirm_life_hack">
                                <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                                <input type="hidden" name="to" value="admin">
                            </form>
                        </c:if>
                    </td>

                    <td>
                        <form action="${pageContext.request.contextPath}/main" method="post" id="edit-life-hack">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="lifehacksadminpage.edit"/></button>
                            <input type="hidden" name="command" value="go_to_life_hack_edit_form">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
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
