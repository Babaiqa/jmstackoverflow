$( document ).ready(function() {

    new PaginationTag(1, 12, 'popular').writeTags()
    new PaginationTag(1, 10, 'popular').writeTopTenTags()
    new PaginationUser(1,20,'week').writeUsers()
    new PaginationQuestion(1,10,'normal').setQuestions()
    new PaginationQuestionForMainPage(1,10, 'new').setQuestions()
    new PaginationQuestionWithoutAnswer(1,10).writeQuestionWithoutAnswer()
    new PaginationQuestionWithoutAnswer(1,10).totalResultCountView()

    if(/^\/question\//.test(window.location.pathname)){
        let tabcontent
        tabcontent = document.getElementsByClassName("tabcontent");
        for(let i=0; i<tabcontent.length; i++){
            tabcontent[i].style.display = "none";
        }
        document.getElementById("question").style.display = "block";
        let questionId = window.location.pathname.replace(/\D/g, '')
        new QuestionPage(questionId).populateQuestionPage()
    } else {
        switch (location.pathname) {
            case "/users": openContent("areaUsersLink", "areaUsers")
                break;
            case "/site": openContent("mainPageLink", "mainPage")
                break;
            case "/tagsAria": openContent("areaTagLink", "areaTag")
                break;
            case "/questionAria": openContent("areaQuestionLink", "areaQuestion")
                break;
            case "/unansweredAria": openContent("areaUnansweredLink", "areaUnanswered")
                break;
            case "/search": openContent("searchResult", "searchResult")
                break;
        }
    }

    function openContent(evt, contentName){
        var i, tabcontent, tablinks;

        tabcontent = document.getElementsByClassName("tabcontent");
        for(i=0; i<tabcontent.length; i++){
            tabcontent[i].style.display = "none";
        }

        tablinks = document.getElementsByClassName("tablinks");
        for(i=0; i<tablinks.length; i++){
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        document.getElementById(contentName).style.display = "block";
        document.getElementById(evt).className += " active";
    }
})