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
    <title>Admin</title>
    <link rel="stylesheet" href="styleAdminPage.css">
</head>
<body>
<header>

</header>
<sql:query dataSource = "${snapshot}" var = "result">
SELECT * from orders;
</sql:query>
<div class="container">
    <div class="header-admin">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">Hello, admin!</h2>
    </div>
    <a href="Controller?command=logout" class="green" style="width: 200px; ">Logout</a>
</div>
<section>
    <c:forEach var = "row" items = "${result.rows}">
        <div class="product-item">
            <div class="product-list" data-id="${row.id}">
                <h3><c:out value = "${row.fullName}"/></h3>
                <span class="price">Address: <c:out value = "${row.address}"/></span>
                <span class="time">Email: <c:out value = "${row.email}"/></span>
                <span class="time">Phone: <c:out value = "${row.phone}"/></span>
                <span class="time">Details: <c:out value = "${row.details}"/></span>
                <div class="form-buttons">
                    <a href="" class="green">Accept</a>
                    <a href="Controller?command=orderdelete&id=${row.id}" class="green" style="color: darkred">Delete</a>
                </div>
            </div>
        </div>
    </c:forEach>
</section>