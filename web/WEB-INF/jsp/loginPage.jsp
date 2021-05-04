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
    <title>Login</title>
    <link rel="stylesheet" href="styleLoginPage.css">
</head>
<body>
<header class="site-header">
    <div class="container">

    <form action="Controller" method="post">
        <span style="color:red; font-size: 13px" >${errMsg}</span>
        <div class="field">
            <label>Login:</label>
            <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="Enter login" name="login" required/>
        </div>
        <div class="field">
            <label>Password:</label>
            <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="password" placeholder="Enter password" name="password" required/>
            <input type="hidden" name="test" value="test"/>
        </div>
        <buttons >
            <div class="form-buttons">
            <input type="hidden" name="command" value="checkloginandpassword" />
            <input type="submit" class="green" value="Sign in"><!--
            --><a href="Controller?command=registration" class="green">Sign up</a>
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
</html>
