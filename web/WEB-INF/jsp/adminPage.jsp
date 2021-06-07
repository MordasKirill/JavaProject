<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!DOCTYPE html PUBLIC>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Admin</title>
    <link rel="stylesheet" href="css/styleAdminPage.css">
</head>
<body>
<header>

</header>

<div class="container">
    <div class="header-admin">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Hello, admin!</h2>
    </div>
    <a href="Controller?command=logout" class="green" style="width: 200px; ">Logout</a>
</div>
<section>
    <c:forEach var = "row" items = "${orders}">
        <div class="product-item">
            <div class="product-list" data-id="${row.id}">
                <c:set var = "status" value = "${row.status}"/>
                <h3><c:out value = "${fn:toUpperCase(status)}"/></h3>
                <span class="price"><c:out value = "${row.fullName}"/></span>
                <span class="price">Address: <c:out value = "${row.address}"/></span>
                <span class="time">Email: <c:out value = "${row.email}"/></span>
                <span class="time">Phone: <c:out value = "${row.phone}"/></span>
                <span class="time">Details: <c:out value = "${row.details}"/></span>
                <div class="form-buttons">
                    <c:if test="${row.status == 'processing'}">
                        <a href="Controller?command=orderstatus&id=${row.id}&status=accepted" class="green">Accept</a>
                        <a href="Controller?command=orderstatus&id=${row.id}&status=rejected" class="green">Reject</a>
                    </c:if>
                    <c:if test="${row.status == 'rejected'}">
                        <a href="Controller?command=orderstatus&id=${row.id}&status=accepted" class="green">Accept</a>
                    </c:if>
                    <c:if test="${row.status == 'accepted'}">
                        <a href="Controller?command=orderstatus&id=${row.id}&status=rejected" class="green">Reject</a>
                    </c:if>
                    <a href="Controller?command=orderdelete&id=${row.id}" class="green" style="color: darkred">Delete</a>
                </div>
            </div>
        </div>
    </c:forEach>
</section>
    <div class="container">
        <c:if test="${result}">
            <a href="Controller?command=gotoadminpage&load=1" class="green" style="width: 200px;">Load more</a>
        </c:if>
    </div>
</body>
<script>
    let cords = ['scrollX', 'scrollY'];
    window.addEventListener('unload', e =>
        cords.forEach(cord => localStorage[cord] = window[cord]));
    window.scroll(...cords.map(cord => localStorage[cord]));
</script>
</html>