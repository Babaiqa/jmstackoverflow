

function openNav() {
    document.getElementById("chatSidenav").style.width = "250px"; // показать nav
}

function closeNav() {
    document.getElementById("chatSidenav").style.width = "0"; // скрыть nav
}

function getListSingleChat(page, size) {                                // Get всех chats
    let query = '/api/chat/single?page=' + page + '&size=' + size;
    return fetch(query, {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    }).then(response =>  {
        if (response.ok) {
            return response.json()
        } else {
            let error = new Error();
            error.response = response.text();
            throw error;
        }
    }).catch( error => error.response.then(message => console.log(message)));
}

function getAllMessageSingleChat(id, page, size) {                              // get списка сообщений пользователя
    let query = '/api/chat/'+ id +'/message?page=' + page + '&size=' + size;
    return fetch(query, {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    }).then(response =>  {
        if (response.ok) {
            return response.json()
        } else {
            let error = new Error();
            error.response = response.text();
            throw error;
        }
    }).catch( error => error.response.then(message => console.log(message)));
}

function showAllMessage(id){                                                            // показываем все сообщения
    getAllMessageSingleChat(id, 1, 10).then(function (response){
        var principal = response.meta[0];
        var nowDate = new Date();
        $('#chatBox').children().remove();

        for(let i =0; i < response.items.length; i++){
            let date = new Date(response.items[i].lastRedactionDate);
            if (nowDate.getFullYear() == date.getFullYear() && nowDate.getMonth() == date.getMonth() && nowDate.getDate() == date.getDate()){
                date = ('0' + date.getHours()).slice(-2) + ':'  + ('0' + date.getMinutes()).slice(-2);
            } else {
                date = ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ' ' + ('0' + date.getDate()).slice(-2) + '.' + ('0' + (date.getMonth()+1)).slice(-2) + '.' + date.getFullYear();
            }
            if(principal == response.items[i].userSenderId){
                $('#chatBox').append(
                    '<div class="media w-50 ml-auto mb-3">'
                    +'    <div class="media-body">'
                    +'        <div class="bg-primary rounded py-2 px-3 mb-2">'
                    +'            <p class="text-small mb-0 text-white">' + response.items[i].message + '</p>'
                    +'        </div>'
                    +'        <p class="small text-muted">' + date + '</p>'
                    +'    </div>'
                    +'</div>'
                )
            } else {
                $('#chatBox').append(
                    '<div class="media w-50 mb-3"><img'
                    + '  src="' + response.items[i].imageLink + '" alt="user"'
                    + '  width="50" class="rounded-circle">'
                    + '      <div class="media-body ml-3">'
                    + '          <div class="bg-light rounded py-2 px-3 mb-2">'
                    + '              <p class="text-small mb-0 text-muted">' + response.items[i].message + '</p>'
                    + '          </div>'
                    + '          <p class="small text-muted">' + date + '</p>'
                    + '      </div>'
                    + '</div>'
                )
            }
        }
    })
}

function cutMessage(message){                       // если сообщение в chat box  больше определенного кол ва символов
    if(message.length > 20 ) {                      // обрезаем
        return message.substring(0,20) + '...';
    } else {
        return message;
    }
}

$(document).ready(function () {
    showListChat(1,10);
})

function showListChat(page, size){                                          // показываем все чаты пользователя
    getListSingleChat(page, size).then(function (json) {
        $('#listChats').children().remove();
        console.log(json)
        if(json.totalResultCount != 0) {
            let nowDate = new Date();
            for (let i = 0; i < json.items.length; i++) {               // parse date
                let date = new Date(json.items[i].lastRedactionDate);
                if (nowDate.getFullYear() == date.getFullYear() && nowDate.getMonth() == date.getMonth() && nowDate.getDate() == date.getDate()){
                    date = ('0' + date.getHours()).slice(-2) + ':'  + ('0' + date.getMinutes()).slice(-2);
                } else if(nowDate.getFullYear() == date.getFullYear() && nowDate.getMonth() == date.getMonth() && (Math.abs(nowDate.getDate() - date.getDate()) == 1)) {
                    date = "Вчера";
                } else {
                    date = ('0' + date.getDate()).slice(-2) + '.' + ('0' + (date.getMonth()+1)).slice(-2) + '.' + date.getFullYear();
                }
                $('#listChats').append(
                    '<a href="#" class="list-group-item list-group-item-action list-group-item-light singleChat rounded-0 sh" id="sh' + json.items[i].id + '" onclick="showAllMessage(' + json.items[i].id + ')">'
                    + '<div class="media"><img src="' + json.items[i].imageLink + '" alt="' + json.items[i].nickname + '" width="50"'
                    + '      class="rounded-circle">'
                    + '     <div class="media-body ml-4">'
                    + '         <div class="d-flex align-items-center justify-content-between mb-1">'
                    + '             <h6 class="mb-0">' + json.items[i].nickname + '</h6><small class="small font-weight">' + date + '</small>'
                    + '         </div>'
                    + '         <p class="font-italic text-muted mb-0 text-small">' + cutMessage(json.items[i].message) + '</p>'
                    + '     </div>'
                    + '</div>'
                    + '</a>'
                )
            }
        } else{
            $('#listChats').append(
                '<div class="mb-3">'
                +'    <h5 class= "text-center">У вас нет чатов</h5>'
                +'</div>'
            )
            return;
        }
        $('#sh' + json.items[0].id).click();
        if(json.totalPageCount != 1) {                                             // если страницы больше 1
            $('#questionsPagesNavigation').children().remove();                 // пагинация
            var currentPageNumber = json.currentPageNumber;
            var nextPage = json.currentPageNumber + 1;
            var secondNextPage = json.currentPageNumber + 2;
            var totalPageCount = json.totalPageCount;
            var previousPage = json.currentPageNumber - 1;
            var startPageCount = 1;
            if (currentPageNumber != 1) {
                $('#questionsPagesNavigation').append(
                    '<li class=page-item><a class=page-link href=# onclick=showListChat(' + previousPage + ',' + size + ')>Назад</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + startPageCount + ',' + size + ')>' + startPageCount + '</a></li>'
                );
            }

            if (currentPageNumber == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    '<li class=page-item active><a class=page-link href=# >' + currentPageNumber + '</a></li>'
                );
            }

            if (nextPage == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    '<li class=page-item active><a class=page-link href=# >' + currentPageNumber + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + nextPage + ',' + size + ')>' + nextPage + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + nextPage + ',' + size + ')>Далее</a></li>'
                );
            }

            if (secondNextPage == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    '<li class=page-item active><a class=page-link href=# >' + currentPageNumber + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + nextPage + ',' + size + ')>' + nextPage + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + secondNextPage + ',' + size + ')>' + secondNextPage + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + nextPage + ',' + size + ')>Далее</a></li>'
                );
            }

            if (secondNextPage < totalPageCount) {
                $('#questionsPagesNavigation').append(
                    '<li class=page-item active><a class=page-link href=# >' + currentPageNumber + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + nextPage + ',' + size + ')>' + nextPage + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + secondNextPage + ',' + size + ')>' + secondNextPage + '</a></li>'
                    + '<li class=page-item><span class=mr-2 ml-2>' + "..." + '</span></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + totalPageCount + ',' + size + ')>' + totalPageCount + '</a></li>'
                    + '<li class=page-item><a class=page-link href=# onclick=showListChat(' + nextPage + ',' + size + ')>Далее</a></li>'
                );
            }
        }
    });
}
