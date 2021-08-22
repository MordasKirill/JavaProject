
<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 24.04.2021
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "customTag" uri = "/WEB-INF/tld/tagLib.tld"%>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="resources.local" var="loc" />
<fmt:message bundle="${loc}" key="local.login.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.login.login" var="loginLabel"/>
<fmt:message bundle="${loc}" key="local.login.password" var="passwordLabel"/>
<fmt:message bundle="${loc}" key="local.login.placeholder.login" var="placeholder_login"/>
<fmt:message bundle="${loc}" key="local.login.placeholder.password" var="placeholder_password"/>
<fmt:message bundle="${loc}" key="local.login.signin" var="signin"/>
<fmt:message bundle="${loc}" key="local.login.signup" var="signup"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>
<fmt:message bundle="${loc}" key="local.title.loginPage" var="loginPage"/>


<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>${loginPage}</title>
    <link rel="stylesheet" href="css/styleLoginRegistr.css" />
</head>
<body>
<header class="site-header">
    <a href="Controller?command=gotologinpage&locale=en" class="green" style="width: 40px; margin-left: 100px">${en_button}</a>
    <a href="Controller?command=gotologinpage&locale=ru" class="green" style="width: 40px; margin-left: 100px">${ru_button}</a>
    <div class="container">
        <div class="time" id="current_date_time_block2" ></div>
        <form action="Controller" method="post">
            <c:if test="${errMsg != null}">
                <fmt:message bundle="${loc}" key="${errMsg}" var="err"/>
                <span style="color:darkred; font-size: 15px; background: #fe8687;" >${err}</span>
            </c:if>
        <div class="field">
            <label>${loginLabel}:</label>
            <input style="outline: none; border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="${placeholder_login}" name="login" value="${login}" maxlength="60" required/>
        </div>
        <div class="field">
            <label>${passwordLabel}:</label>
            <input style="outline: none; border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="password" placeholder="${placeholder_password}" name="password" maxlength="100" required/>
        </div>
        <buttons >
            <div class="form-buttons">
                <input type="hidden" name="command" value="checkloginandpassword" />
                <input type="hidden" name="locale" value="${sessionScope.local}">
                <input type="submit" class="green" value="${signin}">
            <a href="Controller?command=registration&locale=${sessionScope.local}" class="green">${signup}</a>
            </div>
        </buttons>
    </form>
    </div>
</header>
<footer class="site-footer">
    <div class="container">
        <customTag:MessageTag message="© KirMoSoft, 2021"/>
        <p>${footer}</p>
    </div>
</footer>
</body>

<c:if test="${sessionScope.local == 'ru' || sessionScope.local == null}">
    <script type="text/javascript">
    /* функция добавления ведущих нулей */
    /* (если число меньше десяти, перед числом добавляем ноль) */
    function zero_first_format(value)
    {
        if (value < 10)
        {
            value='0'+value;
        }
        return value;
    }

    /* функция получения текущей даты и времени */
    function date_time()
    {
        var current_datetime = new Date();
        var day = zero_first_format(current_datetime.getDate());
        var month = zero_first_format(current_datetime.getMonth()+1);
        var year = current_datetime.getFullYear();
        var hours = zero_first_format(current_datetime.getHours());
        var minutes = zero_first_format(current_datetime.getMinutes());
        var seconds = zero_first_format(current_datetime.getSeconds());

        return day+"."+month+"."+year+" "+hours+":"+minutes+":"+seconds;
    }

    /* каждую секунду получаем текущую дату и время */
    /* и вставляем значение в блок с id "current_date_time_block2" */
    setInterval(function () {
        document.getElementById('current_date_time_block2').innerHTML = date_time();
    }, 1000);
    </script>
</c:if>
<c:if test="${sessionScope.local == 'en'}">
    <script type="text/javascript">
        function zero_first_format(value)
        {
            if (value < 10)
            {
                value='0'+value;
            }
            return value;
        }

    function date_time_en()
    {
        var current_datetime = new Date();
        var day = zero_first_format(current_datetime.getDate());
        var month = zero_first_format(current_datetime.getMonth()+1);
        var year = current_datetime.getFullYear();
        var hours = current_datetime.getHours();
        var minutes = zero_first_format(current_datetime.getMinutes());
        var seconds = zero_first_format(current_datetime.getSeconds());
        var ampm = (hours >= 12) ? "PM" : "AM";
        var hoursAmPm = (hours>12) ? (hours-12) : hours;
        if (hours===0){
            hoursAmPm = 12;
        }
        return month+"-"+day+"-"+year+" "+hoursAmPm+":"+minutes+":"+seconds+" "+ampm;
    }

    setInterval(function () {
        document.getElementById('current_date_time_block2').innerHTML = date_time_en();
    }, 1000);
    </script>
</c:if>
</html>
