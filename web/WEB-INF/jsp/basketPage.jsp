<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="net.epam.study.resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.basket.logo" var="basket"/>
<fmt:message bundle="${loc}" key="local.basket.total" var="total_sum"/>
<fmt:message bundle="${loc}" key="local.basket.delete" var="delete"/>
<fmt:message bundle="${loc}" key="local.basket.wantsomemore" var="somemore"/>
<fmt:message bundle="${loc}" key="local.basket.data" var="order_data"/>
<fmt:message bundle="${loc}" key="local.basket.fullname" var="fullname"/>
<fmt:message bundle="${loc}" key="local.basket.address" var="address"/>
<fmt:message bundle="${loc}" key="local.basket.city" var="city"/>
<fmt:message bundle="${loc}" key="local.basket.phone" var="phone"/>
<fmt:message bundle="${loc}" key="local.basket.checkout" var="checkout"/>
<fmt:message bundle="${loc}" key="local.basket.placeholder.fullname" var="placeholder_fullname"/>
<fmt:message bundle="${loc}" key="local.basket.placeholder.address" var="placeholder_address"/>
<fmt:message bundle="${loc}" key="local.basket.placeholder.city" var="placeholder_city"/>
<fmt:message bundle="${loc}" key="local.basket.empty" var="emptyCart"/>
<fmt:message bundle="${loc}" key="local.login.footer" var="footer"/>
<fmt:message bundle="${loc}" key="local.title.basket" var="basket"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${basket}</title>
    <link rel="stylesheet" href="css/styleBasketPage.css">
</head>
<body>
<div class="row">
    <div class="col-75">
        <div class="container">
            <div class="col-25">
                <div class="container">
                    <h4>${basket}
                        <span class="price" style="color:black">
                            <b><c:out value="${size}"/></b>
                            </span>
                    </h4>
                    <c:choose>
                        <c:when test="${not empty order}">
                            <c:forEach items="${order}" var="item">
                                <p><span><c:out value="${item}" /></span></p>
                                <a href="Controller?command=itemdelete&item=${item}" class="green">${delete}</a>
                            </c:forEach>
                        </c:when>
                    <c:otherwise>
                        <h2>${emptyCart}!</h2>
                        <c:if test="${error != null}">
                            <fmt:message bundle="${loc}" key="${error}" var="err"/>
                            <span style="color:red; font-size: 13px" >${err}</span>
                        </c:if>
                    </c:otherwise>
                    </c:choose>

                    <hr>
                    <p>${total_sum} <span class="price" style="color:black"><b>$<c:out value="${total}"/></b></span></p>
                    <form action="Controller" method="post">
                        <input type="hidden" name="command" value="gotomenupage" />
                        <input type="submit" class="btn" value="${somemore}">
                    </form>
                </div>
            </div>
            <form action="Controller" method="post">
                <div class="row">
                    <div class="col-50" style="text-align: center">
                        <h3>${order_data}</h3>
                        <label for="fname">${fullname}</label>
                        <c:if test="${errMsgFullName != null}">
                            <fmt:message bundle="${loc}" key="${errMsgFullName}" var="err"/>
                            <span style="color:red; font-size: 13px" >${err}</span>
                        </c:if>
                        <input type="text" style="border-radius:7px; width: 160px;" id="fname" name="fullName" placeholder="${placeholder_fullname}" required>
                        <label for="email"> Email</label>
                        <c:if test="${errMsgEmail != null}">
                            <fmt:message bundle="${loc}" key="${errMsgEmail}" var="err"/>
                            <span style="color:red; font-size: 13px" >${err}</span>
                        </c:if>
                        <input type="text" style="border-radius:7px; width: 160px;" id="email" name="email" placeholder="kir.mo@gmail.ru" required>
                        <label for="adr">${address}</label>
                        <input type="text" style="border-radius:7px; width: 160px;" id="adr" name="address" placeholder="${placeholder_address}" required>
                        <label for="city">${city}</label>
                        <c:if test="${errMsgCity != null}">
                            <fmt:message bundle="${loc}" key="${errMsgCity}" var="err"/>
                            <span style="color:red; font-size: 13px" >${err}</span>
                        </c:if>
                        <input type="text" style="border-radius:7px; width: 160px;" id="city" name="city" placeholder="${placeholder_city}" required>
                        <div class="row">
                            <div class="col-50">
                                <label for="ph">${phone}</label>
                                <c:if test="${errMsgPhone != null}">
                                    <fmt:message bundle="${loc}" key="${errMsgPhone}" var="err"/>
                                    <span style="color:red; font-size: 13px" >${err}</span>
                                </c:if>
                                <input type="text" style="border-radius:7px; width: 160px;" id="ph" name="phone" placeholder="+375291234567" required>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="command" value="saveneworder">
                <input type="submit" value="${checkout}" class="btn">
            </form>
        </div>
    </div>
</div>
</body>
<footer class="site-footer">
    <div class="container">
        <p style="color: chocolate">© KirMoSoft, 2021</p>
        <p style="color: chocolate">${footer}</p>
    </div>
</footer>
</html>