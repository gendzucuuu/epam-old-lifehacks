<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/27/2019
  Time: 10:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="jspf/import.jspf"%>
    <title><fmt:message key="lifehackpage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <%@ include file="jspf/header.jspf"%>
    </nav>
</div>
<div class="container" style="display: flex; justify-content: center">
    <form action="${pageContext.request.contextPath}/main" method="post" id="life-hack-form">
        <div class="card" style="width: 18rem;">
            <div class="card-body">
                <h5 class="card-title">
                    <strong>
                        <fmt:message key="lifehackpage.name"/> ${lifeHack.name}<br>
                        <fmt:message key="lifehackpage.username"/> ${lifeHack.username}<br>
                        <fmt:message key="lifehackpage.category"/> ${lifeHack.lifeHackCategory}<br>
                        <fmt:message key="lifehackpage.description"/><br>
                        ${lifeHack.description}<br>
                        <img style="width: 100%" src="data:image/jpeg;base64,${lifeHack.pictureEnc}" /><br>
                        <p class="card-text"><fmt:message key="lifehackpage.date"/><br>
                            ${lifeHack.dateOfPosting}<br></p>
                    </strong>
                </h5>

            </div>
            <div class="card-footer">
                <c:if test="${role == 'GUEST'}">
                        <b><fmt:message key="lifehackpage.notauth"/></b>
                </c:if>

                <c:if test="${role == 'ADMIN' || role == 'USER'}">
                    <c:if test="${isAdded == 'notAdded'}">
                        <form action="${pageContext.request.contextPath}/main" method="post" id="add-to-favorite">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="lifehackpage.addtofav"/></button>
                            <input type="hidden" name="command" value="add_to_favorite">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                        </form>
                    </c:if>
                    <c:if test="${isAdded == 'added'}">
                        <form action="${pageContext.request.contextPath}/main" method="post" id="delete-from-favorite">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="lifehackpage.delete"/></button>
                            <input type="hidden" name="command" value="delete_from_favorite">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                        </form>
                    </c:if>
                    <div>
                        <c:if test="${addCommentError == 'true'}">
                            <b style="color: red">${addCommentErrorMessage}</b>
                        </c:if>

                    </div>
                    <form action="${pageContext.request.contextPath}/main" method="post" id="add-comment">
                        <b><fmt:message key="lifehackpage.inputcomment"/></b>
                        <textarea name="comment" class="form-control"></textarea>
                        <button type="submit" class="btn btn-primary"><fmt:message key="lifehackpage.addcomment"/></button>
                        <input type="hidden" name="command" value="add_comment">
                        <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                    </form>
                    <c:if test="${commentsStatus == 'notShowed'}">
                        <form action="${pageContext.request.contextPath}/main" method="post" id="show-comments">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="lifehackpage.showcomments"/></button>
                            <input type="hidden" name="command" value="show_comments">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                        </form>

                    </c:if>
                    <c:if test="${commentsStatus == 'showed'}">
                        <form action="${pageContext.request.contextPath}/main" method="post" id="hide-comments">
                            <button type="submit"  class="btn btn-primary"><fmt:message key="lifehackpage.hidecomments"/></button>
                            <input type="hidden" name="command" value="hide_comments">
                            <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                        </form>
                        <c:if test="${comments == null || fn:length(comments) == 0}">
                            <fmt:message key="lifehackpage.nocomments"/>
                        </c:if>
                        <c:if test="${comments != null || fn:length(comments) != 0}">
                            <c:forEach var="comment" items="${comments}">
                                <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                                    <fmt:message key="lifehackpage.comuser"/>: ${comment.username}<br>
                                    <fmt:message key="lifehackpage.comrole"/>: ${comment.userRole}<br>
                                        ${comment.description}<br>
                                    <fmt:message key="lifehackpage.comdate"/>: ${comment.dateOfComment}<br>
                                    <c:if test="${role == 'ADMIN' || comment.username == user.username}">
                                        <button type="submit"  class="btn btn-primary"><fmt:message key="lifehackpage.deletecomment"/></button>
                                        <input type="hidden" name="command" value="delete_comment">
                                        <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                                        <input type="hidden" name="commentId" value="${comment.commentId}">
                                    </c:if>

                                </form>
                            </c:forEach>
                        </c:if>
                    </c:if>

                </c:if>
            </div>
        </div>


    </form>
</div>
</body>
</html>
