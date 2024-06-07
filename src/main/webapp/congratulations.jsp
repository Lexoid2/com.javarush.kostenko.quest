<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Code and Portals – Congratulations</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
    </head>

    <body>
        <div class="container mt-5">
            <jsp:useBean id="name" scope="request" class="java.lang.String" />
            <h1 class="display-4 text-center fw-bold">Congratulations, ${name}!</h1>

            <p class="mt-4 fs-4">You have successfully completed the adventure
                and returned to the office. Great job!</p>

            <form action="${pageContext.request.contextPath}/start-adventure" method="POST" class="mt-3">
                <input type="hidden" name="stepId" value="1">
                <input type="hidden" name="name" value="${name}">
                <button type="submit" class="btn btn-primary">Restart</button>
                <c:set var="gameCount" scope="session" value="${sessionScope.gameCount + 1}" />
            </form>
        </div>
    </body>
</html>
