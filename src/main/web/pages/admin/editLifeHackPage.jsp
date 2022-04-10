<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/30/2019
  Time: 1:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../jspf/import.jspf"%>
    <title><fmt:message key="editlifehackpage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>
<div class="container">
    <h1 align="center"><fmt:message key="editlifehackpage.title"/></h1>
    <form action="${pageContext.request.contextPath}/main" method="post" id="edit-form" enctype="multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.category"/></label>
            <label>
                <select id="category" value="${lifeHack.lifeHackCategory}" name="category"  class="form-control mr-sm-2">
                    <option name = "SAVVY">SAVVY</option>
                    <option name = "MNEMONICS">MNEMONICS</option>
                    <option name = "MEMORY_CARDS">MEMORY CARDS</option>
                    <option name = "TIME_MANAGEMENT">TIME MANAGEMENT</option>
                    <option name = "SOCIAL_ENGINEERING">SOCIAL ENGINEERING</option>
                    <option name = "EASY_TIPS">EASY TIPS</option>
                </select>
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.name"/></label>
            <span id="error-name-of-item-span"></span>
            <input type="text" value="${lifeHack.name}" id="name-of-item" class="form-control" name="name" placeholder="Name">

        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.description"/></label>
            <input type="text" value="${lifeHack.description}" class="form-control" id="description" name="description" >
        </div>
        <div>
            <label class="col-sm-2 col-form-label"><fmt:message key="editlifehackpage.oldpic"/></label>
            <img style="width: 100%; object-fit: contain" src="data:image/jpeg;base64,${lifeHack.pictureEnc}" name="oldPicture"/>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="editlifehackpage.newpic"/></label>
            <input type="file" id="picture" accept="image/x-png, image/jpeg"
                   name="picture">
        </div>
        <div>
            <c:if test="${editLifeHackError == 'true'}">
                <b style="color: red">${editLifeHackErrorMessage}</b>
            </c:if>
        </div>

        <button type="submit" class="btn btn-primary"><fmt:message key="editlifehackpage.edit"/></button>
        <input type="hidden" name="command" value="edit_life_hack">
        <input type="hidden" name="page" value="${pagePass}">
    </form>
</div>
</body>
</html>
