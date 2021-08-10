
let userId = document.getElementById("id")

document.getElementById("sendMessageFromUserPage")
    .addEventListener("submit", sendMessageFromUser);

async function sendMessageFromUser(event) {
    event.preventDefault();

    let data = {
        chatType: "SINGLE",
        title: "Single chat title",
        userSender: userId,
        userRecipient: "http://localhost:5557/user/profile" + document.getElementById("id${user.id}").value,
        message: document.getElementById("message").value
    }

    let response = await fetch('http://localhost:5557/api/chat/single/add', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    if (response.ok) {
        $("#sendMessageFromUserPage .close").click();
        $('.modal-backdrop').remove();
    } if (response.status >= 500) {
        alert("Внутренняя ошибка сервера. Попробуйте отправить сообщение позже.");
    }
}
