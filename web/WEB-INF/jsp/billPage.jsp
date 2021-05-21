<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>Bill</title>
    <link rel="stylesheet" href="css/styleBillPage.css">
</head>
<body>
<header class="site-header">
    <div class="container">
        <form action="Controller" method="post">
            <div class="field">
                <p>Thank you for ordering!</p>
                <p>We will be glad to see you again!</p>
            </div>
            <div class="field">
                <input type="hidden" name="command" value="gotomainpage" />
                <input type="submit" class="green" value="Go to main page!">
            </div>
        </form>
    </div>
</header>
</body>
<footer class="site-footer">
    <div class="container">
        <p>© KirMoSoft, 2021</p>
        <p>Your online restaurant...</p>
    </div>
</footer>
</html>