function openNav() {
    document.getElementById("chatSidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("chatSidenav").style.width = "0";
}

function getListSingleChat(page, size) {
    let query = '/api/chat/single?page=' + page + '&size=' + size;
    return fetch(query, {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            let error = new Error();
            error.response = response.text();
            throw error;
        }
    }).catch(error => error.response.then(message => console.log(message)));
}

function getAllMessageSingleChat(id, page, size) {
    let query = '/api/chat/' + id + '/message?page=' + page + '&size=' + size;
    return fetch(query, {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    }).then(response => {
        if (response.ok) {
            return response.json()
        } else {
            let error = new Error();
            error.response = response.text();
            throw error;
        }
    }).catch(error => error.response.then(message => console.log(message)));
}

function showAllMessage(id) {
    subscribeToChat(id);

    getAllMessageSingleChat(id, 1, 10)
        .then(function () {
            $('#chatBox-footer').empty();
            $('#chatBox-footer').append(`
                <form id="form ${id}" class="bg-light">
                    <div class="input-group">
                        <input id="inputMessage" type="text" placeholder="Type a message" aria-describedby="sendBtn" class="form-control rounded-0 border-0 py-4 bg-light"/>
                        <div class="input-group-append">
                            <button id="sendBtn" onclick="sendMessage(${id});return false" type="submit" class="btn btn-link"> <i class="bi bi-chat-dots"></i></button>
                        </div>
                    </div>
                </form>
            `)
        })
    $('.sh').removeClass('active');
    $('#sh' + id).addClass('active');


}


$(document).ready(function () {
    fetch('/api/auth/principal', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => (response.json()))
        .then(user => {
            $('#lovelyMe').attr('val', user.id);
        });

    connectToChat();


    getListSingleChat(1, 10).then(function (json) {

        console.log(json);


        $('#listChats').children().remove();
        var totalResultCount = json.totalResultCount;
        console.log(totalResultCount)
        for (let i = 0; i < totalResultCount; i++) {

            $('#listChats').append(
                '<a href="#" class="list-group-item list-group-item-action list-group-item-light singleChat rounded-0 sh" id="sh' + json.items[i].id + '" onclick="showAllMessage(' + json.items[i].id + ')">'
                + '<div class="media"><img src="https://www.java-mentor.com/images/jm-logo-sq.png" alt="user" width="50"'
                + '      class="rounded-circle">'
                + '     <div class="media-body ml-4">'
                + '         <div class="d-flex align-items-center justify-content-between mb-1">'
                + '             <h6 class="mb-0">' + json.items[i].title + '</h6><small class="small font-weight">Вчера</small>'
                + '         </div>'
                + '         <p class="font-italic text-muted mb-0 text-small">Нужно конечно, поэтому надо написать свой'
                + 'генератор, который не по одному, а по 200к, например, будет за раз брать.</p>'
                + '     </div>'
                + '</div>'
                + '</a>'
            )
        }
        $('#sh' + json.items[0].id).click().addClass('active');
    })

})


