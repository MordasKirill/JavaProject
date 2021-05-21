<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Error</title>
</head>
<body>
    <span style="color:red; font-size: 20px" ><%= session.getAttribute("error")%></span>
</body>
</html>