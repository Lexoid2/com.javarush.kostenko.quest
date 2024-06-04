<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Code and Portals – Adventure Question</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
    </head>

    <body>
        <div class="container mt-5">
            <jsp:useBean id="question" scope="request" class="com.javarush.kostenko.quest.model.Question" />
            <jsp:useBean id="name" scope="request" class="java.lang.String" />
            <h1 class="display-4 text-center fw-bold">${question.step}</h1>

            <p class="mt-4 fs-4">${question.question}</p>

            <div class="text-center mt-4">
                <img src="${pageContext.request.contextPath}/resources/images/question_${question.id}.jpg"
                     class="img-fluid" alt="Question Image">
            </div>

            <form action="${pageContext.request.contextPath}/start-adventure" method="POST" class="mt-3">
                <input type="hidden" name="stepId" value="${question.id}">

                <c:forEach var="entry" items="${question.options.entrySet()}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="option"
                               id="${entry.key}" value="${entry.key}" required>
                        <label class="form-check-label" for="${entry.key}">
                                ${entry.key}. ${entry.value.text}
                        </label>
                    </div>
                </c:forEach>

                <input type="hidden" name="name" value="${name}">
                <button type="submit" class="btn btn-primary">Next</button>
            </form>

            <div class="mt-5">
                <p class="h6 text-start">Games Played: ${sessionScope.gameCount}</p>
            </div>
        </div>
    </body>
</html>
