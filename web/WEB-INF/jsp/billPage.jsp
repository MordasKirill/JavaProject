<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="net.epam.study.resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.bill.thankyou1" var="thankyou1"/>
<fmt:message bundle="${loc}" key="local.bill.thankyou2" var="thankyou2"/>
<fmt:message bundle="${loc}" key="local.menu.back" var="main"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>
<fmt:message bundle="${loc}" key="local.title.bill" var="bill"/>
<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>${bill}</title>
    <link rel="stylesheet" href="css/styleBillPage.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <form action="Controller" method="post">
            <div class="field">
                <p>${thankyou1}!</p>
                <p>${thankyou2}!</p>
            </div>
            <div class="field">
                <input type="hidden" name="command" value="gotomainpage" />
                <input type="submit" class="green" value="${main}!">
            </div>
        </form>
    </div>
</header>
</body>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>${footer}</p>
    </div>
</footer>
</html>