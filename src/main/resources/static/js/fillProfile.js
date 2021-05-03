fillProfile()
fillAnswers()
fillQuestions()
fillTags()
fillBookmarks()

$(document).ready(function () {


});

function fillProfile() {

    fetch('/api/auth/principal', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => (response.json()))
        .then(data => {

            $('.avatar').append(
                "<img src=\"" + data['avatarUrl'] + "\" alt=\"\" width=\"164\" height=\"164\" class=\"avatar-user\">"
            )
            $('#name').append(
                data['fullName']
            )
            $('#city').append(
                "<div class=\"grid--cell fl1\">" + "Город: " + data['city'] + "</div>"
            )
            fetch('/api/user/' + data['id'], {
                method: 'GET',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            })
                .then(response => response.json())
                .then(data1 => {
                    $('.reputation-number').append(
                        data1['reputation']
                    )
                })

            fetch('http://localhost:5557/api/user/reputation/history/' + data['id'] + '?page=1&size=1', {
                method: 'GET',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            })
                .then(response => response.json())
                .then(function (data2) {
                    for (var i=0; i < 3; i++) {


                        $('#countRep' + (i + 1)).append(
                            "+" + data2.items[i].count + " <br/> репутации"
                        )
                        $('#reasonRep' + (i + 1)).append(
                            data2.items[i].type
                        )

                    }
                })
        })


}

function fillAnswers() {
    fetch('/api/user/currentUser/answers?page=1&size=3', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => response.json())
        .then(function (dat) {
            $('#countAnswers').append(
                dat.totalResultCount
            )
            for (let i = 0; i < 3; i++){



                $('#voices' + (i + 1)).append(
                    dat.items[i].countValuable + "<br/>" + " голосов"
                )
                $('#answer' + (i + 1)).append(
                    dat.items[i].body
                )

            }
        })
}

function fillQuestions() {
    fetch('/api/user/currentUser/questions?page=1&size=3', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => response.json())
        .then(function (dat) {
            $('#countQuestions').append(
                dat.totalResultCount
            )
            for (let i = 0; i < 3; i++){


                $('#voicesQ' + (i + 1)).append(
                    dat.items[i].countValuable + "<br/>" + " голосов"
                )
                $('#question' + (i + 1)).append(
                    dat.items[i].title
                )

            }

        })
}

function fillTags() {
    fetch('/api/tag/tracked', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => response.json())
        .then(function (data) {
            for (var i=0; i < data.length; i++) {
                $('#tags').append(
                    "<button type=\"button\" class=\"btn btn-sm\">" + data[i].name + "</button>"
                )
            }
        })
}

function fillBookmarks() {
    fetch('/api/user/bookmarks', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => response.json())
        .then(function (data) {
            $('#bmVotes1').append(
                data.questionVotes + " <br/> голосов"
            )
            $('#bmQuestion1').append(
                data.questionTitle
            )
        })
}


