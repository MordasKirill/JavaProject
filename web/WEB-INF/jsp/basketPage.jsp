<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Basket</title>
    <link rel="stylesheet" href="basketPage.css">
</head>
<body>
<div class="row">
    <div class="col-75">
        <div class="container">
            <div class="col-25">
                <div class="container">
                    <h4>Basket
                        <span class="price" style="color:black">
                            <b><c:out value="${size}"/></b>
                            </span>
                    </h4>
                    <c:choose>
                    <c:when test="${not empty order}">
                    <c:forEach items="${order}" var="item">
                    <p><span><c:out value="${item}" /></span></p>
                    </c:forEach>
                    </c:when>
                    <c:otherwise>
                    <h2>Basket is empty!</h2>
                    </c:otherwise>
                    </c:choose>
                    <hr>
                    <p>Total <span class="price" style="color:black"><b>$<c:out value="${total}"/></b></span></p>
                </div>
            </div>
            <form action="Controller" method="post">
                <div class="row">
                    <div class="col-50" style="text-align: center">
                        <h3>Ordering data</h3>
                        <label for="fname">Full name</label>
                        <span style="color:red; font-size: 13px" >${errMsgFullName}</span>
                        <input type="text" style="border-radius:7px; width: 160px;" id="fname" name="fullName" placeholder="Mordas Kirill Richardovich" required>
                        <label for="email"> Email</label>
                        <span style="color:red; font-size: 13px" >${errMsgEmail}</span>
                        <input type="text" style="border-radius:7px; width: 160px;" id="email" name="email" placeholder="kir.mo@gmail.ru" required>
                        <label for="adr">Address</label>
                        <input type="text" style="border-radius:7px; width: 160px;" id="adr" name="address" placeholder="Hemiga street, 54-7" required>
                        <label for="city">City (only Minsk)</label>
                        <span style="color:red; font-size: 13px" >${errMsgCity}</span>
                        <input type="text" style="border-radius:7px; width: 160px;" id="city" name="city" placeholder="Minsk" required>
                        <div class="row">
                            <div class="col-50">
                                <label for="ph">Phone number +375 format</label>
                                <span style="color:red; font-size: 13px" >${errMsgPhone}</span>
                                <input type="text" style="border-radius:7px; width: 160px;" id="ph" name="phone" placeholder="+375293158990" required>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="command" value="saveneworder">
                <input type="submit" value="Checkout" class="btn">
            </form>
        </div>
    </div>
</div>
</body>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>Your online restaurant...</p>
    </div>
</footer>
</html>