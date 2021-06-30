<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="resources.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.admin.logo" var="logo"/>
<fmt:message bundle="${loc}" key="local.main.logout" var="logout"/>
<fmt:message bundle="${loc}" key="local.admin.accept" var="accept"/>
<fmt:message bundle="${loc}" key="local.admin.accepted" var="accepted"/>
<fmt:message bundle="${loc}" key="local.admin.addnew" var="addnew"/>
<fmt:message bundle="${loc}" key="local.admin.address" var="address"/>
<fmt:message bundle="${loc}" key="local.admin.admin" var="admin"/>
<fmt:message bundle="${loc}" key="local.admin.user" var="user"/>
<fmt:message bundle="${loc}" key="local.admin.owner" var="owner"/>
<fmt:message bundle="${loc}" key="local.admin.back" var="back"/>
<fmt:message bundle="${loc}" key="local.admin.create" var="create"/>
<fmt:message bundle="${loc}" key="local.admin.delete" var="delete"/>
<fmt:message bundle="${loc}" key="local.admin.details" var="details"/>
<fmt:message bundle="${loc}" key="local.admin.deleteitem" var="deleteitem"/>
<fmt:message bundle="${loc}" key="local.admin.done" var="done"/>
<fmt:message bundle="${loc}" key="local.admin.hello" var="hello"/>
<fmt:message bundle="${loc}" key="local.admin.rejected" var="rejected"/>
<fmt:message bundle="${loc}" key="local.admin.uponreceipt" var="uponreceipt"/>
<fmt:message bundle="${loc}" key="local.admin.toadmin" var="toadmin"/>
<fmt:message bundle="${loc}" key="local.admin.touser" var="touser"/>
<fmt:message bundle="${loc}" key="local.admin.next" var="next"/>
<fmt:message bundle="${loc}" key="local.admin.phone" var="phone"/>
<fmt:message bundle="${loc}" key="local.admin.reject" var="reject"/>
<fmt:message bundle="${loc}" key="local.admin.processing" var="processing"/>
<fmt:message bundle="${loc}" key="local.admin.payment" var="payment"/>
<fmt:message bundle="${loc}" key="local.admin.nameadd" var="nameadd"/>
<fmt:message bundle="${loc}" key="local.admin.priceadd" var="priceadd"/>
<fmt:message bundle="${loc}" key="local.admin.waittimeadd" var="waittimeadd"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd" var="categoryadd"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.alcohol" var="alcohol"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.appetizer" var="appetizer"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.deserts" var="deserts"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.fish-and-sea-food" var="seafood"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.hot-drinks" var="hotDrinks"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.meat" var="meat"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.poultry" var="poultry"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.soft-drinks" var="softDrinks"/>
<fmt:message bundle="${loc}" key="local.admin.categoryadd.soups" var="soups"/>
<fmt:message bundle="${loc}" key="local.admin.placeholder.name" var="placeholderName"/>
<fmt:message bundle="${loc}" key="local.admin.placeholder.price" var="placeholderPrice"/>
<fmt:message bundle="${loc}" key="local.admin.placeholder.waittime" var="placeholderWaittime"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.login.locbutton.name.ru" var="ru_button"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!DOCTYPE html PUBLIC>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>${logo}</title>
    <link rel="stylesheet" href="css/styleAdmin.css">
</head>
<body>
<header>

</header>

<div class="container">
    <div class="header-admin">
        <h2 class="centered" style="-webkit-text-stroke: 1px #b1b3a0;">${hello}</h2>
    </div>
    <a href="Controller?command=gotoadminpage&locale=en" class="green" style="width: 100px">${en_button}</a>
    <a href="Controller?command=gotoadminpage&locale=ru" class="green" style="width: 100px">${ru_button}</a>
    <a href="Controller?command=logout" class="green" style="width: 200px; ">${logout}</a>
</div>
<c:if test="${role == 'owner'}">
    <section>
        <c:forEach var = "row" items = "${users}">
            <div class="product-item">
                <div class="product-list" data-id="${row.id}">
                    <c:set var = "roleParsed" value = "${row.role}"/>
                    <c:if test="${roleParsed == 'owner'}">
                        <c:set var = "local" value = "${owner}"/>
                        <h3 style="color: red"><c:out value = "${fn:toUpperCase(owner)}"/></h3>
                    </c:if>
                    <c:if test="${row.role == 'admin'}">
                        <c:set var = "local" value = "${admin}"/>
                        <h3 style="color: blue"><c:out value = "${fn:toUpperCase(admin)}"/></h3>
                    </c:if>
                    <c:if test="${row.role == 'user'}">
                        <c:set var = "local" value = "${user}"/>
                        <h3><c:out value = "${fn:toUpperCase(user)}"/></h3>
                    </c:if>
                    <span class="price">Login: <c:out value = "${row.login}"/></span>
                    <div class="form-buttons">
                        <c:if test="${row.role == 'admin'}">
                            <a href="Controller?command=userrole&id=${row.id}&role=user" class="green">${touser}</a>
                        </c:if>
                        <c:if test="${row.role == 'user'}">
                            <a href="Controller?command=userrole&id=${row.id}&role=admin" class="green">${toadmin}</a>
                        </c:if>
                        <c:if test="${row.role == 'user' || row.role == 'admin'}">
                            <a href="Controller?command=dbinfodelete&idUser=${row.id}" class="green" style="color: darkred">${delete}</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </section>
    <div class="container">
        <c:if test="${resultUsersBack}">
            <a href="Controller?command=gotoadminpage&back_users=1" class="green" style="width: 200px;">${back}</a>
        </c:if>
        <c:if test="${resultUsersNext}">
            <a href="Controller?command=gotoadminpage&load_users=1" class="green" style="width: 200px;">${next}</a>
        </c:if>
    </div>
</c:if>
<section>
    <c:forEach var = "row" items = "${orders}">
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
                <div class="form-buttons">
                    <c:if test="${row.status == 'processing'}">
                        <a href="Controller?command=orderstatus&id=${row.id}&status=accepted&email=${row.email}" class="green">${accept}</a>
                        <a href="Controller?command=orderstatus&id=${row.id}&status=rejected&email=${row.email}" class="green">${reject}</a>
                    </c:if>
                    <c:if test="${row.status == 'rejected'}">
                        <a href="Controller?command=orderstatus&id=${row.id}&status=accepted&email=${row.email}" class="green">${accept}</a>
                    </c:if>
                    <c:if test="${row.status == 'accepted'}">
                        <a href="Controller?command=orderstatus&id=${row.id}&status=rejected&email=${row.email}" class="green">${reject}</a>
                    </c:if>
                    <c:if test="${role == 'owner'}">
                        <a href="Controller?command=dbinfodelete&idOrder=${row.id}" class="green" style="color: darkred">${delete}</a>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</section>
    <div class="container">

        <c:if test="${resultOrdersBack}">
            <a href="Controller?command=gotoadminpage&back_orders=1" class="green" style="width: 200px;">${back}</a>
        </c:if>
        <c:if test="${resultOrdersNext}">
            <a href="Controller?command=gotoadminpage&load_orders=1" class="green" style="width: 200px;">${next}</a>
        </c:if>
    </div>
    <form action="Controller" method="post" >
        <div class="container-form">
            <h1>${addnew}</h1>
            <c:if test="${success != null}">
                <fmt:message bundle="${loc}" key="${success}" var="err"/>
                <c:set var = "success" value = "${err}"/>
                <h3 style="color: green"><c:out value = "${fn:toUpperCase(success)}"/></h3>
            </c:if>
            <c:if test="${errMsgItemExist != null}">
                <fmt:message bundle="${loc}" key="${errMsgItemExist}" var="err"/>
                <c:set var = "itemExist" value = "${err}"/>
                <h3 style="color: red"><c:out value = "${fn:toUpperCase(itemExist)}"/></h3>
            </c:if>
            <div class="field">
                <label>${nameadd}:</label>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" pattern="\D [^0-9]" placeholder="${placeholderName}" name="itemName" value="${login}" maxlength="70" inputmode="text" required>
            </div>
            <div class="field">
                <label>${priceadd}:</label>
                <c:if test="${errMsgPrice != null}">
                    <fmt:message bundle="${loc}" key="${errMsgPrice}" var="err"/>
                    <span style="color:red; font-size: 15px" >${err}</span>
                </c:if>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="number" step="0.01" min="0.1" placeholder="${placeholderPrice}" name="itemPrice" value="${login}" maxlength="60" inputmode="decimal" required/>
            </div>
            <div class="field">
                <label>${waittimeadd}:</label>
                <c:if test="${errMsgWaitTime != null}">
                    <fmt:message bundle="${loc}" key="${errMsgWaitTime}" var="err"/>
                    <span style="color:red; font-size: 15px" >${err}</span>
                </c:if>
                <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="number" min="1" max="999"  placeholder="${placeholderWaittime}" name="itemWaitTime" value="${login}" maxlength="60" inputmode="numeric" required/>
            </div>
            <div class="field">
                <label>${categoryadd}:</label>
                <p><select name="category">
                    <option selected value="appetizer">${appetizer}</option>
                    <option value="soups">${soups}</option>
                    <option value="meat">${meat}</option>
                    <option value="poultry">${poultry}</option>
                    <option value="fish-and-sea-food">${seafood}</option>
                    <option value="deserts">${deserts}</option>
                    <option value="soft-drinks">${softDrinks}</option>
                    <option value="hot-drinks">${hotDrinks}</option>
                    <option value="alcohol">${alcohol}</option>
                </select></p>
            </div>
            <input type="hidden" name="command" value="savemenuitem" />
            <input type="submit" class="green" style="text-align: center" value="${create}">
        </div>
    </form>
<form action="Controller" method="post" >
    <div class="container-form">
        <h1>${deleteitem}</h1>
        <c:if test="${successDelete != null}">
            <fmt:message bundle="${loc}" key="${successDelete}" var="err"/>
            <c:set var = "successDelete" value = "${err}"/>
            <h3 style="color: green"><c:out value = "${fn:toUpperCase(successDelete)}"/></h3>
        </c:if>
        <c:if test="${notFound != null}">
            <fmt:message bundle="${loc}" key="${notFound}" var="err"/>
            <c:set var = "notFound" value = "${err}"/>
            <h3 style="color: red"><c:out value = "${fn:toUpperCase(notFound)}"/></h3>
        </c:if>
        <div class="field">
            <label>${nameadd}:</label>
            <input style="border-radius:7px; width: 160px; height: 25px; border: darkgreen" type="text" placeholder="${placeholderName}" name="itemNameDelete" value="${login}" maxlength="70" inputmode="text" pattern="\D [^0-9]" required/>
        </div>
        <div class="field">
            <label>${categoryadd}:</label>
            <p><select name="categoryDelete">
                <option selected value="appetizer">${appetizer}</option>
                <option value="soups">${soups}</option>
                <option value="meat">${meat}</option>
                <option value="poultry">${poultry}</option>
                <option value="fish-and-sea-food">${seafood}</option>
                <option value="deserts">${deserts}</option>
                <option value="soft-drinks">${softDrinks}</option>
                <option value="hot-drinks">${hotDrinks}</option>
                <option value="alcohol">${alcohol}</option>
            </select></p>
        </div>
        <input type="hidden" name="command" value="deletemenuitem" />
        <input type="submit" class="green" style="text-align: center; color: darkred" value="${delete}">
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