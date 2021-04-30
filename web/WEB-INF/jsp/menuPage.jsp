<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
                   url = "jdbc:mysql://localhost:3306/test"
                   user = "root"  password = "3158095KIRILLMordas"/>


<!DOCTYPE html PUBLIC>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Menu</title>
    <link rel="stylesheet" href="styleMenuPage.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <h1>Food Bar Online</h1>
        <p>Delicious food!</p>
    </div>
</header>
<sql:query dataSource = "${snapshot}" var = "result">
    SELECT * from menu where category='appetizer';
</sql:query>
<section class="appetizers">
    <h2>Appetizers!</h2>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="card">
            <h3><c:out value = "${row.name}"/></h3>
            <p class="price">Price: $<c:out value = "${row.price}"/></p>
            <p>Filing time: <c:out value = "${row.time}"/>min</p>
            <button>Add to cart.</button>
        </div>
    </c:forEach>
</section>
<section class="soups">
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='soups';
    </sql:query>
    <h2>Soups!</h2>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="card">
            <h3><c:out value = "${row.name}"/></h3>
            <p class="price">Price: $<c:out value = "${row.price}"/></p>
            <p>Filing time: <c:out value = "${row.time}"/>min</p>
            <button>Add to cart.</button>
        </div>
    </c:forEach>
</section>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>Your online restaurant...</p>
    </div>
</footer>
</body>
</html>
