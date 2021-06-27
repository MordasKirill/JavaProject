<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 24.04.2021
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.main.logo" var="logo"/>
<fmt:message bundle="${loc}" key="local.main.regards" var="regards"/>
<fmt:message bundle="${loc}" key="local.main.logout" var="logout"/>
<fmt:message bundle="${loc}" key="local.main.uniq" var="uniq"/>
<fmt:message bundle="${loc}" key="local.main.offer" var="offer"/>
<fmt:message bundle="${loc}" key="local.main.uniq.list.1" var="first"/>
<fmt:message bundle="${loc}" key="local.main.uniq.list.2" var="second"/>
<fmt:message bundle="${loc}" key="local.main.uniq.list.3" var="third"/>
<fmt:message bundle="${loc}" key="local.main.uniq.list.4" var="fourth"/>
<fmt:message bundle="${loc}" key="local.main.menu" var="button"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>
<fmt:message bundle="${loc}" key="local.title.mainPage" var="mainPage"/>
<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>${mainPage}</title>
    <link rel="stylesheet" href="css/styleMainPage.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <h1>Food Bar Online</h1>
        <p>${logo}!</p>
        <p1>${regards}, <%= session.getAttribute("login")%> !
            <a href="Controller?command=logout" class="green" style="margin-left: -440px">${logout}</a>
        </p1>
    </div>
</header>
<section class="features">
    <h2>${offer}?</h2>
    <a href="Controller?command=gotomenupage" class="gradient-button">${button}!</a>
</section>
<section class="advantages">
    <h2>${uniq}?</h2>
    <ul class="advantages-list">
        <li>${first}!</li>
        <li>${second}!</li>
        <li>${third}!</li>
        <li>${fourth}!</li>
    </ul>
</section>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>${footer}</p>
    </div>
</footer>
</body>
</html>
