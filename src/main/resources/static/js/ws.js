// let stompClient;
// $(document).ready(connect());
//
// function connect() {
//     const socket = new SockJS('/chat-messaging');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function(frame) {
//         console.log("connected: " + frame);
//         stompClient.subscribe('/chat/messages', function(response) {
//             const data = JSON.parse(response.body);
//             draw("left", data.message);
//         });
//     });
// }
//
// function draw(side, text) {
//     console.log("drawing...");
//     let $message;
//     $message = $($('.message_template').clone().html());
//     $message.addClass(side).find('.text').html(text);
//     $('.chat-box').append($message);
//     $('.chat-box').append(getCurrentTime());
//     return setTimeout(function () {
//         return $message.addClass('appeared');
//     }, 0);
//
// }
// function disconnect(){
//     stompClient.disconnect();
// }
// function sendMessage(){
//     stompClient.send("/app/message", {}, JSON.stringify({'message': $("#message-to-send").val()}));
// }
//
// function getCurrentTime() {
//     return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
// }