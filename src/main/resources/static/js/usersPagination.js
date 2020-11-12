function writeUsers(page, size, timeInterval) {
    var userService = new UserService();
    var users;

    if(timeInterval == 'week') {
        users = userService.getUserDtoPaginationByReputationOverWeek(page, size);
    }
    else if(timeInterval == 'month'){
        users = userService.getUserDtoPaginationByReputationOverMonth(page, size);
    }
    //заглушка
    else {
        users = userService.getUserDtoPaginationByReputationOverMonth(page, size);
    }

    $('#usersTable').children().remove()

    users.then(function(response) {
        for(var i=0; i<response.items.length; i++){
            $('#usersTable').append(
                "<div class=\"col-3 mb-3\">"
                + "<div class=\"media\">"
                + "<img src="+ response.items[i].linkImage +" class=\"mr-3\" alt=\"...\">"
                + "<div class=\"media-body\">"
                + "<a class=\"mt-0\" href=\"#\">" + response.items[i].fullName + "</a>"
                + "<div>" + response.items[i].reputation + "</div>"
                + "</div> </div> </div>");
        }
    })
    usersPagesNavigation(page,size)
}

function usersPagesNavigation(page, size) {
    var userService = new UserService();
    var users = userService.getUserDtoPaginationByReputationOverMonth(page, size);

    users.then(function(response) {
        var currentPageNumber = response.currentPageNumber;
        var nextPage = response.currentPageNumber + 1;
        var secondNextPage =  response.currentPageNumber + 2;
        var totalPageCount = response.totalPageCount;
        var previousPage = response.currentPageNumber - 1;


        $('#usersPageNavigation').children().remove();
        if(currentPageNumber != 1) {
            $('#usersPageNavigation').append(
                "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers(" + previousPage + "," + size + ")' >Назад</a></li>"
            );
        }

        if (currentPageNumber == totalPageCount) {
            $('#usersPageNavigation').append(
                "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
            );
        }

        if (nextPage == totalPageCount) {
            $('#usersPageNavigation').append(
                "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+nextPage+ "," + size + ")'>" + nextPage + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+nextPage+ "," + size + ")'>" + "Далее"+ "</a></li>"
            );
        }

        if (secondNextPage == totalPageCount) {
            $('#usersPageNavigation').append(
                "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+nextPage+ "," + size + ")'>" + nextPage + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+secondNextPage+ "," + size + ")'>" + secondNextPage + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+nextPage+ "," + size + ")'>" + "Далее"+ "</a></li>"
            );
        }

        if (secondNextPage < totalPageCount) {
            $('#usersPageNavigation').append(
                "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+nextPage+ "," + size + ")'>" + nextPage + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+secondNextPage+ "," + size + ")'>" + secondNextPage + "</a></li>"
                + "<li class=\"page-item\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+totalPageCount+ "," + size + ")'>" + totalPageCount + "</a></li>"
                + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='writeUsers("+nextPage+ "," + size + ")'>" + "Далее"+ "</a></li>"
            );
        }
    })
}
