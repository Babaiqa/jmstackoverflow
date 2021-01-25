class PaginationQuestion {

    constructor(page, size, type) {
        this.page = page;
        this.size = size;
        this.type = type;

        this.questionService = new QuestionService();

        if (this.type == 'normal') {
            this.questions = this.questionService.findPagination(this.page, this.size);
        } else if (this.type == 'popular') {
            this.questions = this.questionService.findPaginationPopular(this.page, this.size);
        } else if (this.type === 'withoutAnswers') {
            this.questions = this.questionService.getQuestionsWithoutAnswers(this.page, this.size)
        } else if (this.type === 'new') {
            this.questions = this.questionService.findPaginationNew(this.page, this.size);
        } else {
            this.questions = this.questionService.findPagination(this.page, this.size);
        }
    }

    setQuestions() {
        $('#questionsTable').children().remove()
        $('#questionsPagesNavigation').children().remove();



        this.questions.then(function (response) {
            document.getElementById("questionsQuantity").innerText = response.totalResultCount + " вопросов"

            for (var i = 0; i < response.items.length; i++) {

                let shuffledNames = response.items[i].listTagDto.map(i => i.name).sort(() => Math.random() - 0.5);
                let text  = shuffledNames.map(i => `<a href="#" class="tag"> ${i} </a>`).join('');

                $('#questionsTable').append(
                    "<div class=\"d-flex w-100 justify-content-between\">\n" +
                    "<h5 class=\"mb-1\">" + response.items[i].title + "</h5>\n" +
                    "<small class=\"text-muted\">" + response.items[i].lastUpdateDateTime + "</small>\n" +
                    "</div>\n" +
                    "<p class=\"mb-1\">" + response.items[i].description + "<</p>\n" +
                    "<div class=\"nav-col btn-group  btn-block mr-0   \">\n" +
                    text +
                    "</div>\n" +
                    "<small class=\"text-muted\">" + response.items[i].countValuable + " голосов</small>\n" +
                    "<small class=\"text-muted\">" + response.items[i].countAnswer + " ответов</small>\n" +
                    "<small class=\"text-muted\">" + response.items[i].viewCount + " просмотров</small>\n" +
                    "<p/> <small class=\"text-muted\">задан " + response.items[i].persistDateTime + "</small>\n " +
                    "<p/> <small class=\"text-muted\">" + response.items[i].authorName + "</small>\n " +
                    "<img src=" + response.items[i].authorImage + " class=\"mr-3\" alt=\"...\">"
                )
            }
        })
        this.questionsPagesNavigation()
    }

    questionsPagesNavigation() {
        var size = this.size;
        var type = this.type;

        this.questions.then(function (response) {
            var currentPageNumber = response.currentPageNumber;
            var nextPage = response.currentPageNumber + 1;
            var secondNextPage = response.currentPageNumber + 2;
            var totalPageCount = response.totalPageCount;
            var previousPage = response.currentPageNumber - 1;


            if (currentPageNumber != 1) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + previousPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()' >Назад</a></li>"
                );
            }

            if (currentPageNumber == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                );
            }

            if (nextPage == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage == totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + secondNextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage < totalPageCount) {
                $('#questionsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + secondNextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + totalPageCount + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + totalPageCount + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationQuestion(" + nextPage + "," + size + "," + "\"" + type + "\"" + ").setQuestions()'>" + "Далее" + "</a></li>"
                );
            }
        })
    }
}