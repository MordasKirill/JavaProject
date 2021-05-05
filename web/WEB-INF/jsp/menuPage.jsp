<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
        <a href="Controller?command=gotobasketpage" class="green">Basket</a>
    </div>
</header>
<sql:query dataSource = "${snapshot}" var = "result">
    SELECT * from menu where category='appetizer';
</sql:query>
<div class="container">
    <div class="header-appetizers">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Appetizers!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-soups">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Soups!</h2>
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
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-main-dishes">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Meat dishes!</h2>
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
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-poultry">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Poultry!</h2>
    </div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='poultry';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-fish-and-sea-food">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Sea food!</h2>
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
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
    <div class="header-deserts">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Deserts!</h2>
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
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
<div class="header-soft-drinks">
    <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Soft drinks!</h2>
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
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
<div class="header-hot-drinks">
    <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Hot drinks!</h2>
</div>
</div>
<section>
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='hot-drinks';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list" >
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}" class="button">Add to cart</a>
            </div>
        </div>
    </c:forEach>
</section>
<div class="container">
<div class="header-alcohol">
    <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Alcohol!</h2>
</div>
</div>
<section>
    <form action="Controller" method="post">
    <sql:query dataSource = "${snapshot}" var = "result">
        SELECT * from menu where category='alcohol';
    </sql:query>
    <c:forEach var = "row" items = "${result.rows}" varStatus="Counter">
        <div class="product-item">
            <div class="product-list" data-id="${row.id}">
                <h3 class="test"><c:out value = "${row.name}"/> per bottle</h3>
                <input type="hidden" name="test" value="${row.name}"/>
                <span class="price" >Price: $<c:out value = "${row.price}"/> </span>
                <input type="hidden" name="price" value="price">
                <span class="time">Filing time: <c:out value = "${row.time}"/>min</span>
                <input type="hidden" name="command" value="addtocart" />
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.time}"
                   class="button" >Add to cart</a>
            </div>
        </div>
    </c:forEach>
    </form>
</section>
<%--<script>--%>
<%--    $(document).on('click', 'a[class^="button"]', function(e) {--%>
<%--    e.preventDefault();--%>
<%--    var productName =--%>
<%--        $(this)--%>
<%--        .parent()--%>
<%--        .data('id')--%>
<%--        let itemsArray = [];--%>
<%--        localStorage.setItem('id', JSON.stringify(itemsArray))--%>
<%--        const data = JSON.parse(localStorage.getItem('id'))--%>

<%--        itemsArray.push(productName);--%>
<%--        localStorage.setItem('id', JSON.stringify(itemsArray));--%>

<%--        alert('You added item to cart ' + productName);--%>
<%--    });--%>
<%--</script>--%>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>Your online restaurant...</p>
    </div>
</footer>
</body>
</html>
