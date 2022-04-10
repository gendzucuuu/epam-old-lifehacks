<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@include  file="jspf/import.jspf"%>
    <title><fmt:message key="startpage.title"/></title>
    <c:set var="pagePass" value="pages/start.jsp" scope="request"/>
    

</head>
<body>

<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <%@ include file="jspf/header.jspf"%>
    <form name=search action = "${pageContext.request.contextPath}/main" method="get" class="form-inline my-2 my-lg-0">
        <%--<label>--%>
            <%--<select class="form-control mr-sm-2">--%>
                <%--<option>By name</option>--%>
                <%--<option>By category</option>--%>
            <%--</select>--%>
        <%--</label>--%>
            <button class="btn btn-primary" name="command" value="change_locale" type="submit"><fmt:message key="startpage.changelocale"/></button>
        <input class="form-control mr-sm-2" type="text" width="250" name="searchString" placeholder=<fmt:message key="startpage.searchplace"/> aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" name="command" value="search_by_name" type="submit"><fmt:message key="startpage.searchbtn"/></button>
    </form>
    </nav>
</div>
<div>
    <c:if test="${lifeHacks == null || fn:length(lifeHacks) == 0}">
        <fmt:message key="startpage.nulllifehacks"/>
    </c:if>
    <c:if test="${lifeHacks != null || fn:length(lifeHacks) != 0}">
        <c:forEach var="lifeHack" items="${lifeHacks}">
            <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form">
                <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <strong>${lifeHack.name}<br></strong>
                    <img style="width: 100%" src="data:image/jpeg;base64,${lifeHack.pictureEnc}" /><br>
                    <p class="card-text"><fmt:message key="startpage.date"/> ${lifeHack.dateOfPosting}<br></p>

                    <button type="submit"  class="btn btn-primary"><fmt:message key="startpage.gotolifehack"/></button>
                    <input type="hidden" name="command" value="go_to_life_hack_page">
                    <input type="hidden" name="lifeHackId" value="${lifeHack.lifeHackId}">
                </div>
            </div>

            </form>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
