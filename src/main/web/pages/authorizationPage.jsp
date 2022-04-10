<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="jspf/import.jspf"%>
    <title><fmt:message key="authpage.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <%@ include file="jspf/header.jspf"%>
    </nav>
</div>

<div class="container">
    <h1 align="center"><fmt:message key="authpage.title"/></h1>
    <div>
        <form id="autorization-form" action="${pageContext.request.contextPath}/main" method="post">
            <div class="form-group">
                <label for="username"><fmt:message key="registrationpage.username"/></label>
                <span style="display: grid" id="error-username-span"></span>
                <input type="text" class="form-control" id="username" name="username"
                       placeholder="<fmt:message key="registrationpage.usernameplace"/>">
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="registrationpage.pass1"/></label>
                <span style="display: grid" id="error-password-span"></span>
                <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="registrationpage.pass1place"/>">
            </div>
            <div>
                <c:if test="${signInError == 'true'}">
                <b style="color: red">Authorization failed. Please check your data.</b>

                </c:if>
            </div>

            <button type="submit"  class="btn btn-primary"><fmt:message key="registrationpage.submit"/></button>
            <input type="hidden" name="command" value="authorization">
            <input type="hidden" name="page" value="${pagePass}">


        </form>

    </div>
</div>
</body>
</html>
