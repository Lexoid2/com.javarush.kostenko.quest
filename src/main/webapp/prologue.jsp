<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Code and Portals — Prologue</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
    </head>

    <body>
        <div class="container mt-5">
            <h1 class="display-4 text-center fw-bold">Prologue</h1>

            <p class="mt-4 fs-4">You are a new programmer starting your journey at a big tech company.
                One day, while setting up your desk, you discover a mysterious glowing light.
                Curiosity gets the best of you, and you touch the light.
                To your amazement, it turns out to be a portal to other worlds.
                Now, you must travel through these worlds, solve problems, and find your way back home.
                Each choice you make will shape your adventure. Get ready for an unforgettable journey!</p>

            <p class="mt-5 fs-5">Please enter your name to start the adventure:</p>

            <form action="/start-adventure" method="POST" class="mt-3">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           required maxlength="20" pattern=".{1,20}">
                    <div class="form-text">Name should be 1 to 20 characters long.</div>
                </div>

                <button type="submit" class="btn btn-primary">Start Adventure</button>
            </form>
        </div>
    </body>
</html>
