const url = 'http://localhost:8080/api/chat';
let stompClient;
let chatId;


function init() {
    bindEvents();
}

function bindEvents() {
    $('#inputMessage').on('keyup', addMessageEnter.bind(this));
}


function connectToChat() {

    console.log("connecting to chat...")
    let socket = new SockJS("/message");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);

    });
}

function subscribeToChat(id) {
    chatId = id;
    stompClient.subscribe("/chat/" + id + "/messages/", function (response) {
        let data = JSON.parse(response.body);
        render(data.message);
    });
}

function sendMsg(userSender, message, chatId) {
    stompClient.send("/app/message/" + chatId, {}, JSON.stringify({
        userSender: userSender,
        message: message,
        chat: chatId
    }));
}

function sendMessage(chatId) {
    let message = $('#inputMessage').val();
    let currentUserId = getCurrentUserId();

    console.log("Внутри sendMessage():");
    console.log("id = " + chatId);
    console.log(message);
    console.log("User ID = " + currentUserId);

    sendMsg(currentUserId, message, chatId);
    scrollToBottom();
    if (message !== '') {
        $('#messageList').append(`
            <li class="clearfix">
                <div class="message other-message float-right">
                    ${message}
                </div>
                <div class="message-data align-right">
                    <span class="message-data-time">${getCurrentTime()}</span> &nbsp; &nbsp;
                </div>
            </li>
        `);
        scrollToBottom();
        $('#inputMessage').val('');
    }
}




function render(message) {
    scrollToBottom();
    // responses
    setTimeout(function () {
        $('#messageList').append(`
            <li>
                <div class="message my-message">
                    ${message}
                </div>
                <div class="message-data">
                    <span class="message-data-time">${getCurrentTime()}</span>
                </div>
            </li>
        `);
        scrollToBottom();
    }.bind(this), 1500);
}


function scrollToBottom() {
    $('#chatBox').scrollTop($('#chatBox')[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        sendMessage(chatId);
    }
}

init();

