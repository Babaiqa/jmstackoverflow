let buttonAskQuestion = document.getElementById('buttonAskQuestion');
let newQuestionId = 0;
let userId;

$(document).ready(function() {
    getUserId();
});

document.querySelector('.tip-button-1').onclick = function () {
    document.querySelector('.tip-text-1').classList.toggle('d-none');
}

document.querySelector('.tip-button-2').onclick = function () {
    document.querySelector('.tip-text-2').classList.toggle('d-none');
}

document.querySelector('.tip-button-3').onclick = function () {
    document.querySelector('.tip-text-3').classList.toggle('d-none');
}

document.querySelector('.tip-button-4').onclick = function () {
    document.querySelector('.tip-text-4').classList.toggle('d-none');
}

buttonAskQuestion.onclick = function (e) {
    e.preventDefault();

    let description = $('#summernote').summernote('code');

    let tagNames = $('#tags').val().split(' ');
    let tags = [];

    for (let i = 0; i < tagNames.length; i++) {

        let str = '';

        tags.push(
            {
                id: i + 1,
                name: str + tagNames[i]
            })
    }

    let questionCreateDto = {
        title: $('#questionTitle').val(),
        userId: userId,
        description: description,
        tags: tags
    };

    if (!fetch('http://localhost:5557/api/question/add', {
        method: 'POST',
        headers: {
            'content-type': 'application/json;charset=utf-8', 'Authorization': $.cookie("token")
        },
        body: JSON.stringify(questionCreateDto)
    }).then(response => response.json()
    ).then(question => {
        newQuestionId = question.id;
        window.location.href = '/site';
    })) {
        alert('Вопрос не был добавлен');
    }
}

function getUserId() {
    fetch('/api/auth/principal', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => response.json())
        .then(principal => userId = principal['id'])
}

