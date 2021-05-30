
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="net.epam.study.resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.ru" var="ru_button"/>
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
<fmt:message bundle="${loc}" key="local.menu.price" var="localPrice"/>
<fmt:message bundle="${loc}" key="local.menu.time" var="localTime"/>
<fmt:message bundle="${loc}" key="local.menu.time.min" var="localTimeMin"/>
<fmt:message bundle="${loc}" key="local.menu.addtocart" var="localAddToCart"/>
<fmt:message bundle="${loc}" key="local.title.menuPage" var="menuPage"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>${menuPage}</title>
    <link rel="stylesheet" href="css/styleMenu.css">
</head>
<body>
<header>
    <img src="resources/menu.png" style="text-align: center" alt="Snow">

    <a href="Controller?command=gotomainpage" class="green" style="margin-top: -100px; margin-left: 400px">${back}</a>
    <a href="Controller?command=logout" class="green" style="margin-left: 400px">${logout}</a>
    <a href="Controller?command=gotomenupage&locale=en" class="green" style="margin-top: -100px; margin-left: 600px">${en_button}</a>
    <a href="Controller?command=gotomenupage&locale=ru" class="green" style=" margin-left: 600px">${ru_button}</a>
    <div class="menu">
        <a href="Controller?command=gotobasketpage" class="green">${cart} <c:out value="${size}"/> ${item}</a>
    </div>
</header>
<div class="container">
    <div class="header-appetizers">
        <input type="hidden" name="locale" value="${sessionScope.local}">
        <a href="Controller?command=gotomenupage&category=appetizer&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${appetizer}!</a>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
            <c:if test="${category=='appetizer'}">
                <c:if test="${row.category=='appetizer'}">
        <div class="product-item">
            <div class="product-list" >
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=appetizer" class="button">${localAddToCart}</a>
            </div>
        </div>
                </c:if>
            </c:if>
    </c:forEach>
</section>
<div class="container">
    <div class="header-soups">
        <input type="hidden" name="locale" value="${sessionScope.local}">
        <a href="Controller?command=gotomenupage&category=soups&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${soups}!</a>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='soups'}">
            <c:if test="${row.category=='soups'}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=soups" class="button">${localAddToCart}</a>
            </div>
        </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
    <div class="header-main-dishes">
        <input type="hidden" name="locale" value="${sessionScope.local}">
        <a href="Controller?command=gotomenupage&category=meat&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${meat_dishes}!</a>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='meat'}">
            <c:if test="${row.category=='meat'}">
        <div class="product-item">
            <div class="product-list">
                <h3><c:out value = "${row.name}"/></h3>
                <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=meat" class="button">${localAddToCart}</a>
            </div>
        </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
    <div class="header-poultry">
        <input type="hidden" name="locale" value="${sessionScope.local}">
        <a href="Controller?command=gotomenupage&category=poultry&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${poultry}!</a>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='poultry'}">
            <c:if test="${row.category=='poultry'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                        <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=poultry" class="button">${localAddToCart}</a>
                    </div>
                </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
    <div class="header-fish-and-sea-food">
        <input type="hidden" name="locale" value="${sessionScope.local}">
        <a href="Controller?command=gotomenupage&category=fish-and-sea-food&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${sea_food}!</a>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='fish-and-sea-food'}">
            <c:if test="${row.category=='fish-and-sea-food'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                        <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=fish-and-sea-food" class="button">${localAddToCart}</a>
                    </div>
                </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
    <div class="header-deserts">
        <input type="hidden" name="locale" value="${sessionScope.local}">
        <a href="Controller?command=gotomenupage&category=deserts&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${deserts}!</a>
    </div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='deserts'}">
            <c:if test="${row.category=='deserts'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                        <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=deserts" class="button">${localAddToCart}</a>
                    </div>
                </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
<div class="header-soft-drinks">
    <input type="hidden" name="locale" value="${sessionScope.local}">
    <a href="Controller?command=gotomenupage&category=soft-drinks&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${soft_drinks}!</a>
</div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='soft-drinks'}">
            <c:if test="${row.category=='soft-drinks'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                        <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=soft-drinks" class="button">${localAddToCart}</a>
                    </div>
                </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
<div class="header-hot-drinks">
    <input type="hidden" name="locale" value="${sessionScope.local}">
    <a href="Controller?command=gotomenupage&category=hot-drinks&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${hot_drinks}!</a>
</div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='hot-drinks'}">
            <c:if test="${row.category=='hot-drinks'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                        <span class="time">${localTime}: <c:out value = "${row.filingTime}"/>${localTimeMin}</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time= ${row.filingTime}&category=hot-drinks" class="button">${localAddToCart}</a>
                    </div>
                </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<div class="container">
<div class="header-alcohol">
    <input type="hidden" name="locale" value="${sessionScope.local}">
    <a href="Controller?command=gotomenupage&category=alcohol&locale=${sessionScope.local}" class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${alcohol}!</a>
</div>
</div>
<section>
    <c:forEach var = "row" items = "${menuItems}">
        <c:if test="${category=='alcohol'}">
            <c:if test="${row.category=='alcohol'}">
                <div class="product-item">
                    <div class="product-list">
                        <h3><c:out value = "${row.name}"/></h3>
                        <span class="price">${localPrice}: $<c:out value = "${row.price}"/></span>
                        <span class="time">${localTime}: <c:out value = "${row.filingTime}"/> ${localTimeMin}</span>
                        <a href="Controller?command=addtocart&name=${row.name}&price=${row.price}&time=${row.filingTime}&category=alcohol" class="button">${localAddToCart}</a>
                    </div>
                </div>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<script>
    let cords = ['scrollX', 'scrollY'];
    window.addEventListener('unload', e =>
    cords.forEach(cord => localStorage[cord] = window[cord]));
    window.scroll(...cords.map(cord => localStorage[cord]));
</script>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>${footer}</p>
    </div>
</footer>
</body>
</html>
