<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 24.04.2021
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="net.epam.study.resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.login.login" var="login"/>
<fmt:message bundle="${loc}" key="local.login.password" var="password"/>
<fmt:message bundle="${loc}" key="local.login.placeholder.login" var="placeholder_login"/>
<fmt:message bundle="${loc}" key="local.login.placeholder.password" var="placeholder_password"/>
<fmt:message bundle="${loc}" key="local.login.register" var="reg"/>
<fmt:message bundle="${loc}" key="local.login.backtologin" var="back"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>

<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Registration</title>
    <link rel="stylesheet" href="css/styleLoginRegistr.css">
</head>
<body>
<header class="site-header" >
    <form action="Controller" method="post" style="margin-top: -20px">
        <input type="hidden" name="command" value="registration"/>
        <input type="hidden" name="local" value="en"/>
        <input type="submit" name="local" style="width: 55px; height: 30px" class="green" value="${en_button}"> <br />
    </form>
    <form action="Controller" method="post" style="margin-top: -50px">
        <input type="hidden" name="command" value="registration"/>
        <input type="hidden" name="local" value="ru"/>
        <input type="submit" name="local" style="width: 55px; height: 30px" class="green" value="${ru_button} "/> <br />
    </form>
    <div class="container">
        <div class="time" id="current_date_time_block2" ></div>
        <form action="Controller" method="post" >
            <span style="color:red; font-size: 13px" >${errMsg}</span>
            <div class="field">
                <label>${login}:</label>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="${placeholder_login}" name="login" required/>
            </div>
            <div class="field">
                <label>${password}:</label>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="password" placeholder="${placeholder_password}" name="password" required/>
            </div>
            <buttons >
                <div class="form-buttons">
                    <input type="hidden" name="command" value="savenewuser" />
                    <input type="hidden" name="locale" value="${sessionScope.local}">
                    <input type="submit" class="green" value="${reg}">
                    <a href="Controller?command=gotologinpage" class="green">${back}</a>
                </div>
            </buttons>
        </form>
    </div>
</header>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>${footer}</p>
    </div>
</footer>

</body>
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
</html>
