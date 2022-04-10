<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../jspf/import.jspf"%>
    <title><fmt:message key="profile.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <%@ include file="../jspf/header.jspf"%>
    </nav>
</div>

<div class="container" style="display: flex; justify-content: center">
    <div class="card" style="width: 30rem;">
        <h1 align="center"><fmt:message key="profile.title"/></h1>
        <div class="card-body">
            <h5 class="card-title">
                <form action="${pageContext.request.contextPath}/main" method="post" class="col-md-8">
                    <fmt:message key="profile.role"/> ${role}<br>
                    <fmt:message key="profile.username"/> ${user.username}<br>
                    <fmt:message key="profile.firstname"/> ${user.firstName}<br>
                    <fmt:message key="profile.secondname"/> ${user.secondName}<br>
                    <fmt:message key="profile.email"/> ${user.email}<br>
                </form>

                <form action="${pageContext.request.contextPath}/main" method="post" id="go-to-favorite">
                    <button type="submit"  class="btn btn-primary"><fmt:message key="profile.favlifehacks"/></button>
                    <input type="hidden" name="command" value="find_all_favorite_life_hacks">
                </form>
            </h5>
        </div>
        </div>
    </div>


</div>
</body>
</html>
