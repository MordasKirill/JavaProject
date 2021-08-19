<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.account.orders" var="orders_amount"/>
<fmt:message bundle="${loc}" key="local.account.placeholder.pw1" var="pw1"/>
<fmt:message bundle="${loc}" key="local.account.placeholder.pw2" var="pw2"/>
<fmt:message bundle="${loc}" key="local.title.account" var="logo"/>
<fmt:message bundle="${loc}" key="local.account.changepw" var="change"/>
<fmt:message bundle="${loc}" key="local.menu.back" var="back"/>
<fmt:message bundle="${loc}" key="local.admin.accepted" var="accepted"/>
<fmt:message bundle="${loc}" key="local.admin.address" var="address"/>
<fmt:message bundle="${loc}" key="local.admin.back" var="back"/>
<fmt:message bundle="${loc}" key="local.admin.details" var="details"/>
<fmt:message bundle="${loc}" key="local.admin.done" var="done"/>
<fmt:message bundle="${loc}" key="local.admin.rejected" var="rejected"/>
<fmt:message bundle="${loc}" key="local.admin.uponreceipt" var="uponreceipt"/>
<fmt:message bundle="${loc}" key="local.admin.next" var="next"/>
<fmt:message bundle="${loc}" key="local.admin.phone" var="phone"/>
<fmt:message bundle="${loc}" key="local.admin.processing" var="processing"/>
<fmt:message bundle="${loc}" key="local.admin.payment" var="payment"/>
<fmt:message bundle="${loc}" key="local.account.passworderr" var="passerr"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!DOCTYPE html PUBLIC>
<html>
<script src="https://kit.fontawesome.com/f37755bf06.js" crossorigin="anonymous"></script>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>${logo}</title>
    <link rel="stylesheet" href="css/styleAccountPage.css">
</head>
<body>
<header class="site-header">
    <div class="card">
        <img src="resources/avatar.png" alt="User" style="width:100%">
        <h1>${login}</h1>
        <c:if test="${successDelete != null}">
            <fmt:message bundle="${loc}" key="${successDelete}" var="err"/>
            <c:set var = "success" value = "${err}"/>
            <h3 style="color: green"><c:out value = "${fn:toUpperCase(success)}"/></h3>
        </c:if>
        <c:set var = "role" value = "${role}"/>
        <h3 style="color: #4CAF50"><c:out value = "${fn:toUpperCase(role)}"/></h3>
        <b>${orders_amount} ${ordersAmount}</b>
        <form action="Controller" method="post">
            <input name="password" style="border-radius:7px; width: 200px; height: 40px; border: white; outline: none;" type="password" id="password1" placeholder="${pw1}" required/>
            <input name="password" style="border-radius:7px; width: 200px; height: 40px; border: white; outline: none;" type="password" id="password2" placeholder="${pw2}" required/>
            <buttons >
                <div>
                    <input type="hidden" name="command" value="savenewpassword" />
                    <input type="submit" class="black" value="${change}">
                </div>
            </buttons>
        </form>
        <a href="Controller?command=gotomainpage" class="btn">${back}</a>
    </div>
<section>
    <c:forEach var = "row" items = "${userOrders}">
        <div class="product-item">
            <div class="product-list" data-id="${row.id}">
                <c:set var = "status" value = "${row.status}"/>
                <c:if test="${status == 'accepted'}">
                    <c:set var = "statusAccepted" value = "${accepted}"/>
                    <h3 style="color: green"><c:out value = "${fn:toUpperCase(statusAccepted)}"/></h3>
                </c:if>
                <c:if test="${status == 'rejected'}">
                    <c:set var = "statusRejected" value = "${rejected}"/>
                    <h3 style="color: red"><c:out value = "${fn:toUpperCase(statusRejected)}"/></h3>
                </c:if>
                <c:if test="${status == 'processing'}">
                    <c:set var = "statusProcessing" value = "${processing}"/>
                    <h3 style="color: black"><c:out value = "${fn:toUpperCase(statusProcessing)}"/></h3>
                </c:if>
                <c:set var = "paymentStatus" value = "${row.paymentStatus}"/>
                <c:if test="${paymentStatus == 'done'}">
                    <c:set var = "done" value = "${done}"/>
                    <h3 style="color: green"><c:out value = "${payment}: ${fn:toUpperCase(done)}"/></h3>
                </c:if>
                <c:if test="${paymentStatus == 'uponReceipt'}">
                    <c:set var = "uponReceipt" value = "${uponreceipt}"/>
                    <h3 style="color: green"><c:out value = "${payment}: ${fn:toUpperCase(uponReceipt)}"/></h3>
                </c:if>
                <c:if test="${paymentStatus == 'rejected'}">
                    <c:set var = "rejected" value = "${rejected}"/>
                    <h3 style="color: red"><c:out value = "${payment}: ${fn:toUpperCase(rejected)}"/></h3>
                </c:if>
                <c:if test="${paymentStatus == 'processing'}">
                    <c:set var = "processing" value = "${processing}"/>
                    <h3 style="color: black"><c:out value = "${payment}: ${fn:toUpperCase(paymentStatus)}"/></h3>
                </c:if>
                <span class="price"><c:out value = "${row.fullName}"/></span>
                <span class="price">${address}: <c:out value = "${row.address}"/></span>
                <span class="time">Email: <c:out value = "${row.email}"/></span>
                <span class="time">${phone}: <c:out value = "${row.phone}"/></span>
                <span class="time">${details}: <c:out value = "${row.details}"/></span>
            </div>
        </div>
    </c:forEach>
    </section>
    <div class="container">
        <c:if test="${resultOrdersBack}">
            <a href="Controller?command=account&back_orders=1" class="green" style="width: 200px;">${back}</a>
        </c:if>
        <c:if test="${resultOrdersNext}">
            <a href="Controller?command=account&load_orders=1" class="green" style="width: 200px;">${next}</a>
        </c:if>
        </div>
    </header>
</body>

<script type="text/javascript">
    window.onload = function () {
        document.getElementById("password1").onchange = validatePassword;
        document.getElementById("password2").onchange = validatePassword;
    }
    function validatePassword(){
        var pass2=document.getElementById("password2").value;
        var pass1=document.getElementById("password1").value;
        if(pass1!=pass2)
            document.getElementById("password2").setCustomValidity("${passerr}");
        else
            document.getElementById("password2").setCustomValidity('');
    }
</script>
<script>
    let cords = ['scrollX', 'scrollY'];
    window.addEventListener('unload', e =>
        cords.forEach(cord => localStorage[cord] = window[cord]));
    window.scroll(...cords.map(cord => localStorage[cord]));
</script>
</html>