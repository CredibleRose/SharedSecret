const CryptoJS = require('crypto-js');

if (location.pathname.slice(1)) {;
    const url = location.href.replace("answer/", "api/secret/?uuid=");
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if ((data.showAndBurn === true && data.numberOfReads > 1)) {
                fetch("/secondAcceptPage.jsp")
                    .then((response) => {
                        return response.text();
                    })
                    .then((html) => {
                        document.write(html);
                    })
                    .then(() => {
                        document.querySelector('.acceptYes_btn').onclick = acceptForShow;
                        document.querySelector('.acceptNo_btn').onclick = createNewMessage;
                    });
            }
            else if (data.showAndBurn === true && data.numberOfReads === 1) {
                fetch("/acceptPage.jsp")
                    .then((response) => {
                        return response.text();
                    })
                    .then((html) => {
                        document.write(html);
                    })
                    .then(() => {
                        document.querySelector('.acceptYes_btn').onclick = acceptForShow;
                        document.querySelector('.acceptNo_btn').onclick = createNewMessage;
                    });
            }
            else {
                if (data.needPassword === true) {
                    fetch("/passConfirm.jsp")
                        .then((response) => {
                            return response.text();
                        })
                        .then((html) => {
                            document.body.innerHTML = html;
                        })
                        .then(() => {
                            document.querySelector('.passneed_btn').onclick = authPassword;
                        });
                }
                else {
                    showMessage("/answerPage.jsp", data.encryptedSecret);
                }
            }
        })
        .catch(err => checkError());
}

let passArea = document.getElementById('userPass');
let needPass = document.getElementById('usePassCheck');
let needTimer = document.getElementById('useTimerCheck');
let timeSelection = document.getElementById('time-select');
const hashKey = location.hash.slice(1);

function checkMark(checkedObject, switchableObject) {
    checkedObject.addEventListener("change", function(event) {
        if (event.target.checked) {
            switchableObject.disabled = false;
        } else {
            switchableObject.disabled = true;
        }
    }, false);
}

checkMark(needPass, passArea);
checkMark(needTimer, timeSelection);

function generateLink(uuid, key) {
    return location.href + "answer/" + uuid + "#" + key;
}

function copyToClipboard() {
    const text = document.getElementById('shareLink').value;
    navigator.clipboard.writeText(text).then(function() {
        console.log('Async: Copying to clipboard was successful!');
    }, function(err) {
        console.error('Async: Could not copy text: ', err);
    });
}

function processData() {
    let hashPass = '';
    let messageForm = document.forms['dataForm'];
    let text = messageForm.elements['subject'].value;
    let currentDate = (new Date()).valueOf().toString();
    let randomNum = Math.random().toString();
    let genKey = CryptoJS.SHA256(currentDate + randomNum).toString();
    let ciphertext = "";
    if (text) {
        ciphertext = CryptoJS.AES.encrypt(text, genKey).toString();
    }
    let password = messageForm.elements['userPass'].value;
    let needPass = document.getElementById('usePassCheck');
    if (needPass) {
        if (password) {
            hashPass = CryptoJS.SHA256(password).toString();
        }
    }
    let lifeTime = messageForm.elements['timeToBurn'].value;
    if (!needTimer.checked) {
        lifeTime = '';
    }
    needPass = messageForm.elements['usePassCheck'].checked;
    let numOfRead = messageForm.elements['numberOfReads'].value;
    let showAndBurn = messageForm.elements['showAndDelete'].checked;
    let data = JSON.stringify({
        encryptedSecret : ciphertext,
        password : hashPass,
        needPassword: needPass,
        timeToBurn : lifeTime,
        numberOfReads : numOfRead,
        showAndBurn : showAndBurn
    })

    let request = new XMLHttpRequest();
    request.open('POST', '/api/secret', true)
    request.setRequestHeader(
        'Content-Type',
        'application/json'
    )
    request.addEventListener('load', function () {
        let receivedData = JSON.parse(request.response);
        let linkToSecret = generateLink(receivedData, genKey);
        document.getElementById('shareLink').value = linkToSecret;
    })
    request.send(data);
    request.onload = function() {
        if (request.status === 400) {
            alert(`Error: ${request.responseText}`);
        }
    }
    console.log(data);
}

document.querySelector('.btnEncrypt').onclick = processData;
document.querySelector('.copy-buf').onclick = copyToClipboard;

function createNewMessage() {
    location.href = window.location.protocol + "//" + window.location.host;
}

function showMessage(view, encryptedSecret) {
    fetch(view)
        .then((response) => {
            return response.text();
        })
        .then((html) => {
            document.body.innerHTML = html;
        })
        .then(() => {
            const bytes = CryptoJS.AES.decrypt(encryptedSecret, hashKey);
            const originalText = bytes.toString(CryptoJS.enc.Utf8);
            const input = document.getElementById('decryptedText');
            input.value = originalText;
            document.querySelector('.responseBtn').addEventListener("click", () => {
                window.open(window.location.protocol + "//" + window.location.host);
            });
        });
}

function checkError() {
    fetch("/SecretNotFound.jsp")
        .then((response) => {
            return response.text();
        })
        .then((html) => {
            document.body.innerHTML = html;
        })
        .then(() => {
            document.querySelector('.createYes_btn').onclick = createNewMessage;
        });
}

function acceptForShow(){
    const url = location.href.replace("answer/", "api/secret/?uuid=");
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.needPassword) {
                fetch("/passConfirm.jsp")
                    .then((response) => {
                        return response.text();
                    })
                    .then((html) => {
                        document.body.innerHTML = html;
                    })
                    .then(() => {
                        document.querySelector('.passneed_btn').onclick = authPassword;
                    });
            }
            else {
                showMessage("/answerPage.jsp", data.encryptedSecret);
            }
        });
}

function authPassword() {
    const url = location.href.replace("answer/", "api/secret/auth/?uuid=");
    const password = document.getElementById('password-input').value;
    fetch(url, {
        method: 'post',
        headers: new Headers({
            'Authorization': CryptoJS.SHA256(password).toString(),
            'Content-Type': 'application/x-www-form-urlencoded'
        }),
        body: ''
    }).then(response => response.json()).then(data => {
        if (data.encryptedSecret === "") {
            alert("Wrong password! Try again!");
        }
        else {
            showMessage("/answerPage.jsp", data.encryptedSecret);
        }
    });
}
