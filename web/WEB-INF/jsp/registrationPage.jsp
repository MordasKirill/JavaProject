<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 24.04.2021
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Registration</title>
    <link rel="stylesheet" href="styleLoginRegistr.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <div class="time" id="current_date_time_block2" ></div>
        <form action="Controller" method="post" >
            <span style="color:red; font-size: 13px" >${errMsg}</span>
            <div class="field">
                <label>Login:</label>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="Enter login" name="login" required/>
            </div>
            <div class="field">
                <label>Password:</label>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="password" placeholder="Enter password" name="password" required/>
            </div>
            <buttons >
                <div class="form-buttons">
                    <input type="hidden" name="command" value="savenewuser" />
                    <input type="submit" class="green" value="Register!">
                    <a href="Controller?command=gotologinpage" class="green">Back to login</a>
                </div>
            </buttons>
        </form>
    </div>
</header>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>Your online restaurant...</p>
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
