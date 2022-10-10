<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enigma</title>
    <link rel="stylesheet" href="/css/answerStyle.css">
</head>
<body>
    <div class="container">
        <form method = "post" name = "dataForm">
          <div class="row">
              <div class="header">
                  <h2>Сообщение для вас:</h2>
                  <img class="iconLetter" src="/iconLetter.png" alt="" width="20" height="20">
              </div>
              <hr>
            <div class="decryptedText">
              <textarea disabled id="decryptedText" name="decryptedText" placeholder="" style="height:200px; resize: none;"></textarea>
            </div>
          </div>
            <button class = "responseBtn">Ответить другим сообщением</button>
        </form>
    </div>
</body>
</html>
