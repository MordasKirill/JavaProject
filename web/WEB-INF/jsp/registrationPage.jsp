<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<fmt:setBundle basename="resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.login.login" var="loginLabel"/>
<fmt:message bundle="${loc}" key="local.login.password" var="passwordLabel"/>
<fmt:message bundle="${loc}" key="local.login.placeholder.login" var="placeholder_login"/>
<fmt:message bundle="${loc}" key="local.login.placeholder.password" var="placeholder_password"/>
<fmt:message bundle="${loc}" key="local.login.register" var="reg"/>
<fmt:message bundle="${loc}" key="local.login.backtologin" var="back"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>
<fmt:message bundle="${loc}" key="local.title.registration" var="registration"/>
<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>${registration}</title>
    <link rel="stylesheet" href="css/styleLoginRegistr.css">
</head>
<body>
<header class="site-header" >
    <a href="Controller?command=registration&locale=en" class="green" style="width: 40px; margin-left: 100px">${en_button}</a>
    <a href="Controller?command=registration&locale=ru" class="green" style="width: 40px; margin-left: 100px">${ru_button}</a>
    <div class="container">
        <div class="time" id="current_date_time_block2" ></div>
        <form action="Controller" method="post" >
            <c:if test="${errMsg != null}">
                <fmt:message bundle="${loc}" key="${errMsg}" var="err"/>
                <span style="color:red; font-size: 15px; background: #fe8687;" >${err}</span>
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
        <p>?? KirMoSoft, 2021</p>
        <p>${footer}</p>
    </div>
</footer>

</body>
<c:if test="${sessionScope.local == 'ru' || sessionScope.local == null}">
    <script type="text/javascript">
        /* ?????????????? ???????????????????? ?????????????? ?????????? */
        /* (???????? ?????????? ???????????? ????????????, ?????????? ???????????? ?????????????????? ????????) */
        function zero_first_format(value)
        {
            if (value < 10)
            {
                value='0'+value;
            }
            return value;
        }

        /* ?????????????? ?????????????????? ?????????????? ???????? ?? ?????????????? */
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

        /* ???????????? ?????????????? ???????????????? ?????????????? ???????? ?? ?????????? */
        /* ?? ?????????????????? ???????????????? ?? ???????? ?? id "current_date_time_block2" */
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
            return  month+"-"+day+"-"+year+" "+hoursAmPm+":"+minutes+":"+seconds+" "+ampm;
        }

        setInterval(function () {
            document.getElementById('current_date_time_block2').innerHTML = date_time_en();
        }, 1000);
    </script>
</c:if>
</html>
