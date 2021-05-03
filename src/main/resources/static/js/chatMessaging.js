
let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;


let stompClient;
let selectedUser;

function connectToChat() {
    console.log("Connection to chat...");
    let socket = new SockJS('/message');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
       console.log("Connected to:" + frame);
       // stompClient.subscribe("/" + chatId + "/message", function (response) {
       //    let data = JSON.parse(response.body);
       // });                   /* этот закомменченый код перенесен в chatService.js */
    });
}
function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function sendMsg(id){
    let text = $('#inputMessage').val();
    console.log(text);

    stompClient.send("/app/message", {}, JSON.stringify( {
        message: text,
        chatId: id

    }));
    console.log("Message sended:" + text + '\n' + getCurrentTime() + '\n' + "to chat #" + id);

    showSendedMessage(text);
    $('#inputMessage').val('');
}

function showSendedMessage(message) {
    $('#chatBox').append(
        `<div class="media w-50 ml-auto mb-3">
                <div class="media-body">
                    <div class="bg-primary rounded py-2 px-3 mb-2">
                        <p class="text-small mb-0 text-white">${message}</p>
                    </div>
                    <p class="small text-muted">${getCurrentTime()}</p>
                </div>
            </div>`
    )
}


function sendMessage(message) {
    let username = $('#userName').val();
    console.log(username)
    sendMsg(username, message);
    scrollToBottom();
    if (message.trim() !== '') {
        let template = Handlebars.compile($("#message-template").html());
        let context = {
            messageOutput: message,
            time: getCurrentTime(),
            toUserName: selectedUser
        };

        $chatHistoryList.append(template(context));
        scrollToBottom();
        $textarea.val('');
    }
}
function render(message, userName) {
    scrollToBottom();
    // responses
    let templateResponse = Handlebars.compile($("#message-response-template").html());
    let contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName
    };
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

/** ********************************


function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-box');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}




    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1500);
}





function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
    event.preventDefault();
}

init();
*/

// var stompClient = null;
// $(document).ready(connect());
//
// function setConnected(connected) {
//     document.getElementById('connect').disabled = connected;
//     document.getElementById('disconnect').disabled = !connected;
//     document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
//     document.getElementById('response').innerHTML = '';
// }
//
// function connect() {
//     var socket = new SockJS('/message');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function(frame) {
//         setConnected(true);
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/message', function(message){
//             showMessage(JSON.parse(message.body).content);
//         });
//     });
// }
//
// function disconnect() {
//     stompClient.disconnect();
//     setConnected(false);
//     console.log("Disconnected");
// }
//
// function sendMessage() {
//     var message = document.getElementById('inputMessage').value;
//     stompClient.send("/api/chat/" + id + "/message", {}, JSON.stringify(
//     {
//         message: message
//     }));
// }
//
// function showMessage(message) {
//     var response = document.getElementById('response');
//     var p = document.createElement('p');
//     p.style.wordWrap = 'break-word';
//     p.appendChild(document.createTextNode(message));
//     response.appendChild(p);
// }
//
// function getCurrentTime() {
//     return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
// }