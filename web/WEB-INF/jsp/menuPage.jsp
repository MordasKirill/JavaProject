
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="net.epam.study.resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.main.logout" var="logout"/>
<fmt:message bundle="${loc}" key="local.menu.back" var="back"/>
<fmt:message bundle="${loc}" key="local.menu.cart" var="cart"/>
<fmt:message bundle="${loc}" key="local.menu.cart.item" var="item"/>
<fmt:message bundle="${loc}" key="local.menu.appetizer" var="appetizer"/>
<fmt:message bundle="${loc}" key="local.menu.soups" var="soups"/>
<fmt:message bundle="${loc}" key="local.menu.meat_dishes" var="meat_dishes"/>
<fmt:message bundle="${loc}" key="local.menu.poultry" var="poultry"/>
<fmt:message bundle="${loc}" key="local.menu.sea_food" var="sea_food"/>
<fmt:message bundle="${loc}" key="local.menu.soft_drinks" var="soft_drinks"/>
<fmt:message bundle="${loc}" key="local.menu.hot_drinks" var="hot_drinks"/>
<fmt:message bundle="${loc}" key="local.menu.alcohol" var="alcohol"/>
<fmt:message bundle="${loc}" key="local.menu.deserts" var="deserts"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Menu</title>
    <link rel="stylesheet" href="css/styleMenu.css">
</head>
<body>
<header>
    <img src="resources/menu.png" style="text-align: center" alt="Snow">
    <a href="Controller?command=gotomainpage" class="green" style="margin-top: -100px; margin-left: 400px">${back}</a>
    <a href="Controller?command=logout" class="green" style="margin-left: 400px">${logout}</a>
    <div class="menu">
        <a href="Controller?command=gotobasketpage" class="green">${cart} <c:out value="${size}"/> ${item}</a>
    </div>
</header>
<div class="container">
    <div class="header-appetizers">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${appetizer}!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='appetizer'}">
        <div class="product-item">
            <div class="product-list" >
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
            </div>
        </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
    <div class="header-soups">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${soups}!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='soups'}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
            </div>
        </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
    <div class="header-main-dishes">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${meat_dishes}!</h2>
    </div>
</div>
<section>
        <c:forEach var = "row" items = "${menuItems}">
            <c:choose>
                <c:when test="${row.category=='meat'}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">Price: $<c:out value = "${row.price}"/></span>
                <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
            </div>
        </div>
                </c:when>
            </c:choose>
    </c:forEach>
</section>
<div class="container">
    <div class="header-poultry">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${poultry}!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='poultry'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">Price: $<c:out value = "${row.price}"/></span>
                        <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
    <div class="header-fish-and-sea-food">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${sea_food}!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='fish-and-sea-food'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">Price: $<c:out value = "${row.price}"/></span>
                        <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
    <div class="header-deserts">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${deserts}!</h2>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='deserts'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">Price: $<c:out value = "${row.price}"/></span>
                        <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
<div class="header-soft-drinks">
    <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${soft_drinks}!</h2>
</div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='soft-drinks'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">Price: $<c:out value = "${row.price}"/></span>
                        <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
<div class="header-hot-drinks">
    <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${hot_drinks}!</h2>
</div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='hot-drinks'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">Price: $<c:out value = "${row.price}"/></span>
                        <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<div class="container">
<div class="header-alcohol">
    <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${alcohol}!</h2>
</div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:choose>
            <c:when test="${row.category=='alcohol'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">Price: $<c:out value = "${row.price}"/></span>
                        <span class="time">Filing time: <c:out value = "${row.filingTime}"/>min</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}" class="button">Add to cart</a>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<script>
    let cords = ['scrollX', 'scrollY'];
    window.addEventListener('unload', e =>
    cords.forEach(cord => localStorage[cord] = window[cord]));
    window.scroll(...cords.map(cord => localStorage[cord]));
</script>
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
        <p>${footer}</p>
    </div>
</footer>
</body>
</html>
