<%@ page contentType="text/html;charset=utf-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enigma</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <form method = "post" name = "dataForm">
            <div class="row">
                <h2>Тайное сообщение</h2>
                <hr>
                <div class="link">
                    <input id="shareLink" class="share" readonly>
                    <div class="image">
                        <img class="copy-buf" src="css/copyBtn.png" alt="copy" width="20" height="20">
                    </div>
                    <div class="image">
                        <img src="css/shareBtn.png" alt="share" width="20" height="20">
                    </div>
                </div>
                <div class="col-75">
                    <textarea id="subject" name="subject" placeholder="Напишите текст..." style="height:200px"></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <p><input id="usePassCheck" type="checkbox" name="usePass" value="" > Использовать пароль</p>
                </div>
                <div class="col-75">
                    <p><input id="userPass" type="password" name="userPass" value="" disabled></p>
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <p><input id="useTimerCheck" type="checkbox" name="useTime" value="yes" > Использовать таймер жизни</p>
                </div>
                <div class="col-75">
                    <p><select id="time-select" name="timeToBurn" disabled>
                        <option value="1H"> 1 час</option>
                        <option value="12H"> 12 часов</option>
                        <option value="1D"> 1 день</option>
                        <option value="3D"> 3 дня</option>
                        <option value="1W"> 1 неделя</option>
                    </select></p>
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <p><input type="checkbox" name="showAndDelete" > Показать секрет и сжечь</p>
                </div>
                <div class="col-75">
                    <p><select id="numberOfRoad-select" name="numberOfReads">
                        <option value="1"> 1 раз</option>
                        <option value="2"> 2 раза</option>
                        <option value="3"> 3 раза</option>
                        <option value="4"> 4 раза</option>
                        <option value="5"> 5 раз</option>
                        <option value="6"> 6 раз</option>
                        <option value="7"> 7 раз</option>
                        <option value="8"> 8 раз</option>
                        <option value="9"> 9 раз</option>
                        <option value="10"> 10 раз</option>
                    </select></p>
                </div>
            </div>
            <div class="row">
                <div class="btnEncrypt">Зашифровать</div>
            </div>
        </form>
    </div>
    <script type="text/javascript" src="/dist/main.js"></script>
</body>
</html>
