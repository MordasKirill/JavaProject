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
    <link rel="stylesheet" href="css/test.css">
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
<c:if test="${role == 'owner'}">
    <section>
        <c:forEach var = "row" items = "${users}">
            <div class="product-item">
                <div class="product-list" data-id="${row.id}">
                    <c:set var = "roleParsed" value = "${row.role}"/>
                    <c:if test="${roleParsed == 'owner'}">
                        <h3 style="color: red"><c:out value = "${fn:toUpperCase(roleParsed)}"/></h3>
                    </c:if>
                    <c:if test="${row.role == 'admin'}">
                        <h3 style="color: blue"><c:out value = "${fn:toUpperCase(roleParsed)}"/></h3>
                    </c:if>
                    <c:if test="${row.role == 'user'}">
                        <h3><c:out value = "${fn:toUpperCase(roleParsed)}"/></h3>
                    </c:if>
                    <span class="price">Login: <c:out value = "${row.login}"/></span>
                    <div class="form-buttons">
                        <c:if test="${row.role == 'admin'}">
                            <a href="Controller?command=userrole&id=${row.id}&role=user" class="green">To User</a>
                        </c:if>
                        <c:if test="${row.role == 'user'}">
                            <a href="Controller?command=userrole&id=${row.id}&role=admin" class="green">To Admin</a>
                        </c:if>
                        <c:if test="${row.role == 'user' || row.role == 'admin'}">
                            <a href="Controller?command=dbinfodelete&idUser=${row.id}" class="green" style="color: darkred">Delete</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </section>
    <div class="container">
        <c:if test="${resultUsersBack}">
            <a href="Controller?command=gotoadminpage&back_users=1" class="green" style="width: 200px;">Back</a>
        </c:if>
        <c:if test="${resultUsersNext}">
            <a href="Controller?command=gotoadminpage&load_users=1" class="green" style="width: 200px;">Next</a>
        </c:if>
    </div>
</c:if>
<section>
    <c:forEach var = "row" items = "${orders}">
        <div class="product-item">
            <div class="product-list" data-id="${row.id}">
                <c:set var = "status" value = "${row.status}"/>
                <c:if test="${status == 'accepted'}">
                    <h3 style="color: green"><c:out value = "${fn:toUpperCase(status)}"/></h3>
                </c:if>
                <c:if test="${status == 'rejected'}">
                    <h3 style="color: red"><c:out value = "${fn:toUpperCase(status)}"/></h3>
                </c:if>
                <c:if test="${status == 'processing'}">
                    <h3 style="color: black"><c:out value = "${fn:toUpperCase(status)}"/></h3>
                </c:if>
                <c:set var = "paymentStatus" value = "${row.paymentStatus}"/>
                <c:if test="${paymentStatus == 'done' || paymentStatus == 'uponReceipt'}">
                    <h3 style="color: green"><c:out value = "Payment: ${fn:toUpperCase(paymentStatus)}"/></h3>
                </c:if>
                <c:if test="${paymentStatus == 'rejected'}">
                    <h3 style="color: red"><c:out value = "Payment: ${fn:toUpperCase(paymentStatus)}"/></h3>
                </c:if>
                <c:if test="${paymentStatus == 'processing'}">
                    <h3 style="color: black"><c:out value = "Payment: ${fn:toUpperCase(paymentStatus)}"/></h3>
                </c:if>
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
                    <c:if test="${role == 'owner'}">
                        <a href="Controller?command=dbinfodelete&idOrder=${row.id}" class="green" style="color: darkred">Delete</a>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</section>
    <div class="container">

        <c:if test="${resultOrdersBack}">
            <a href="Controller?command=gotoadminpage&back_orders=1" class="green" style="width: 200px;">Back</a>
        </c:if>
        <c:if test="${resultOrdersNext}">
            <a href="Controller?command=gotoadminpage&load_orders=1" class="green" style="width: 200px;">Next</a>
        </c:if>
    </div>
    <form action="Controller" method="post" >
        <div class="container-form">
            <h1>Add new menu item</h1>
            <c:if test="${success != null}">
                <c:set var = "success" value = "${success}"/>
                <h3 style="color: green"><c:out value = "${fn:toUpperCase(success)}"/></h3>
            </c:if>
            <div class="field">
                <label>Name:</label>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="Product name" name="itemName" value="${login}" maxlength="70" inputmode="text" required/>
            </div>
            <div class="field">
                <label>Price:</label>
                <c:if test="${errMsgPrice != null}">
                    <span style="color:red; font-size: 15px" >${errMsgPrice}</span>
                </c:if>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="Product price" name="itemPrice" value="${login}" maxlength="60" inputmode="decimal" required/>
            </div>
            <div class="field">
                <label>Wait time:</label>
                <c:if test="${errMsgWaitTime != null}">
                    <span style="color:red; font-size: 15px" >${errMsgWaitTime}</span>
                </c:if>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="Product wait time" name="itemWaitTime" value="${login}" maxlength="60" inputmode="numeric" required/>
            </div>
            <div class="field">
                <label>Category:</label>
                <p><select name="category">
                    <option selected value="appetizer">Appetizer</option>
                    <option value="soups">Soups</option>
                    <option value="meat">Meat</option>
                    <option value="poultry">Poultry</option>
                    <option value="fish-and-sea-food">Fish-and-sea-food</option>
                    <option value="deserts">Deserts</option>
                    <option value="soft-drinks">Soft-drinks</option>
                    <option value="hot-drinks">Hot-drinks</option>
                    <option value="alcohol">Alcohol</option>
                </select></p>
            </div>
            <input type="hidden" name="command" value="savemenuitem" />
            <input type="submit" class="green" style="text-align: center" value="Create">
        </div>
    </form>
<form action="Controller" method="post" >
    <div class="container-form">
        <h1>Delete menu item</h1>
        <c:if test="${successDelete != null}">
            <c:set var = "successDelete" value = "${successDelete}"/>
            <h3 style="color: green"><c:out value = "${fn:toUpperCase(successDelete)}"/></h3>
        </c:if>
        <c:if test="${notFound != null}">
            <c:set var = "notFound" value = "${notFound}"/>
            <h3 style="color: red"><c:out value = "${fn:toUpperCase(notFound)}"/></h3>
        </c:if>
        <div class="field">
            <label>Name:</label>
            <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="Product name" name="itemNameDelete" value="${login}" maxlength="70" inputmode="text" required/>
        </div>
        <div class="field">
            <label>Category:</label>
            <p><select name="categoryDelete">
                <option selected value="appetizer">Appetizer</option>
                <option value="soups">Soups</option>
                <option value="meat">Meat</option>
                <option value="poultry">Poultry</option>
                <option value="fish-and-sea-food">Fish-and-sea-food</option>
                <option value="deserts">Deserts</option>
                <option value="soft-drinks">Soft-drinks</option>
                <option value="hot-drinks">Hot-drinks</option>
                <option value="alcohol">Alcohol</option>
            </select></p>
        </div>
        <input type="hidden" name="command" value="deletemenuitem" />
        <input type="submit" class="green" style="text-align: center; color: darkred" value="Delete">
    </div>
</form>

</body>
<script>
    let cords = ['scrollX', 'scrollY'];
    window.addEventListener('unload', e =>
        cords.forEach(cord => localStorage[cord] = window[cord]));
    window.scroll(...cords.map(cord => localStorage[cord]));
</script>
</html>