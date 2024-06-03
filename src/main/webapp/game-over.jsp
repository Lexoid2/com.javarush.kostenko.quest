<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Code and Portals - Game Over</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
    </head>

    <body>
        <div class="container mt-5">
            <h1 class="display-4 text-center fw-bold">Game Over</h1>

            <jsp:useBean id="outcome" scope="request" type="java.lang.String" />
            <p class="mt-4 fs-4">${outcome}</p>

            <form action="${pageContext.request.contextPath}/start-adventure" method="POST" class="mt-3">
                <input type="hidden" name="stepId" value="1">
                <button type="submit" class="btn btn-primary">Restart</button>
            </form>
        </div>
    </body>
</html>
