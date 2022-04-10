<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/27/2019
  Time: 2:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/flatly/bootstrap.min.css">
    <%@ include file="../jspf/import.jspf"%>
    <title><fmt:message key="createlifehackpage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>
<div>
    <div class="container">
        <h1 align="center"><fmt:message key="createlifehackpage.title"/></h1>
        <form action="${pageContext.request.contextPath}/main" method="post" id="offer-form" enctype="multipart/form-data">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.category"/></label>
                <label>
                    <select id="category" name="category"  class="form-control mr-sm-2">
                        <option name = "SAVVY">SAVVY</option>
                        <option name = "MNEMONICS">MNEMONICS</option>
                        <option name = "MEMORY_CARDS">MEMORY CARDS</option>
                        <option name = "TIME_MANAGEMENT">TIME MANAGEMENT</option>
                        <option name = "SOCIAL_ENGINEERING">SOCIAL ENGINEERING</option>
                        <option name = "EASY_TIPS">EASY TIPS</option>
                    </select>
                </label>
                <%--<input type="text" class="form-control" id="category" name="category" placeholder="Category">--%>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.name"/></label>
                <span id="error-name-of-item-span"></span>
                <input type="text" id="name-of-item" class="form-control" name="name" placeholder="Name">

            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.description"/></label>
                <textarea class="form-control" id="description" name="description" placeholder="Description"></textarea>
            </div>
            <div >
                <label class="col-sm-2 col-form-label"><fmt:message key="offerlifehackpage.picture"/></label>
                <input type="file" id="picture" accept="image/x-png, image/jpeg"
                       name="picture">
            </div>
            <div>
                <c:if test="${addLifeHackError == 'true'}">
                    <b style="color: red">${addLifeHackErrorMessage}</b>
                </c:if>

            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="offerlifehackpage.submit"/></button>
            <input type="hidden" name="command" value="create_life_hack">
            <input type="hidden" name="page" value="${pagePass}">
        </form>
    </div>
</div>

</body>
</html>
