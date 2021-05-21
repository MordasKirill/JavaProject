<%--
  Created by IntelliJ IDEA.
  User: Kirill
  Date: 24.04.2021
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Restaurant Online</title>
    <link rel="stylesheet" href="css/styleMainPage.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <h1>Food Bar Online</h1>
        <p>Delicious food!</p>
        <p1>Hello, <%= request.getAttribute("login")%> !
            <a href="Controller?command=logout" class="green" style="margin-left: -440px">Logout</a>
        </p1>
    </div>
</header>
<section class="features">
    <h2>What can we offer you?</h2>
    <a href="Controller?command=gotomenupage" class="gradient-button">View menu!</a>
</section>
<section class="advantages">
    <h2>What make's us uniq?</h2>
    <ul class="advantages-list">
        <li>Only natural product are used!</li>
        <li>Minimum wait time!</li>
        <li>Food will be hot, straight from chef!</li>
        <li>Free delivery!</li>
    </ul>
</section>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>Your online restaurant...</p>
    </div>
</footer>
</body>
</html>
