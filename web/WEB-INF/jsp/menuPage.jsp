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
<header>
    <img src="resources/menu.png" style="text-align: center" alt="Snow">
    <div class="menu">
        <a href="" class="green">Basket</a>
    </div>
</header>
<sql:query dataSource = "${snapshot}" var = "result">
    SELECT * from menu where category='appetizer';
</sql:query>
<div class="container">
    <div class="header-appetizers">
        <h2 class="centered">Appetizers!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-soups">
        <h2 class="centered">Soups!</h2>
    </div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='soups';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-main-dishes">
        <h2 class="centered">Meat dishes!</h2>
    </div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='meat-dishes';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-poultry">
        <h2 class="centered">Poultry!</h2>
    </div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='meat-dishes';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-fish-and-sea-food">
        <h2 class="centered">Sea food!</h2>
    </div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='fish-and-sea-food';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-deserts">
        <h2 class="centered">Deserts!</h2>
    </div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='deserts';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
<div class="header-soft-drinks">
    <h2 class="centered">Soft drinks!</h2>
</div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='soft-drinks';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
<div class="header-hot-drinks">
    <h2 class="centered">Hot drinks!</h2>
</div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='hot-drinks';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
<div class="header-alcohol">
    <h2 class="centered">Alcohol!</h2>
</div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='alcohol';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/> per bottle</h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="" class="button">Add to cart</a>
            </div>
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
