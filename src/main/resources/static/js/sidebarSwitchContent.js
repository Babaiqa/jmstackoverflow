$( document ).ready(function() {

    new PaginationTag(1, 12, 'popular').writeTags()
    new PaginationTag(1, 10, 'popular').writeTopTenTags()
    new PaginationUser(1,20,'week').writeUsers()
    new PaginationQuestion(1,10,'normal').setQuestions()
    new PaginationQuestionForMainPage(1,10, 'new').setQuestions()
    new PaginationQuestionWithoutAnswer(1,10).writeQuestionWithoutAnswer()
    if(/^\/question\//.test(window.location.pathname)){
        openContent("questionLink", "question")
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
        }
    }


    function openContent(evt, contentName){
        console.log("TEST  "+location.pathname.replace(/\D/g, ''))
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