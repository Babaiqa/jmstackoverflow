class PaginationQuestionForMainPage {

    constructor(page, size, type) {
        this.page = page;
        this.size = size;
        this.type = type;

        this.questionService = new QuestionService();

        if (this.type == 'new') {
            this.questions = this.questionService.findPaginationNew(this.page, this.size);
        } else if (this.type == 'popular') {
            this.questions = this.questionService.findPaginationPopular(this.page, this.size);
        } else if (this.type == 'popularWeek') {
            this.questions = this.questionService.findPaginationPopular(this.page, this.size, "week");
        } else if (this.type == 'popularMonth') {
            this.questions = this.questionService.findPaginationPopular(this.page, this.size, "month");
        } else if (this.type == 'withTags') {
            this.questions = this.questionService.getQuestionsWithGivenTags(this.page, this.size);
        } else {
            this.questions = this.questionService.findPagination(this.page, this.size);
        }

        // this.questions = this.questionService.findPaginationNew(this.page, this.size);

    }

    setQuestions() {
        $('#questionsAll').children().remove()

        this.questions.then(function (response) {
            for (var i = 0; i < response.items.length; i++) {
                $('#questionsAll').append(
                    "        <a href=\"#\" class=\"list-group-item list-group-item-action h-100\">\n" +
                    "            <div class=\"row align-items-center h-100\">\n" +
                    "                <div class=\"col-sm-2 mx-auto\">\n" +
                    "                    <div class=\"row\">\n" +
                    "                        <div class=\"col-sm-4\">\n" +
                    "                            <div style=\"text-align: center;\">\n" +
                    "                                <small style=\"display: inline-block;\">" + response.items[i].countValuable +"<br /> голосов</small>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"col-sm-4\">\n" +
                    "                            <div style=\"text-align: center;\">\n" +
                    "                                <small style=\"display: inline-block;\">" + response.items[i].countAnswer +"<br /> ответов</small>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"col-sm-4\">\n" +
                    "                            <div style=\"text-align: center;\">\n" +
                    "                                <small style=\"display: inline-block;\">" + response.items[i].viewCount +"<br /> показов</small>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <div class=\"col-sm-10\">\n" +
                    "                    <div class=\"d-flex w-100 justify-content-between\">\n" +
                    "                        <h5 href=\"#\" class=\"mb-1\">" + response.items[i].title + "</h5>\n" +
                    "                        <small> задан "+ response.items[i].persistDateTime + "</small>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"nav-col btn-group  btn-block mr-0   \">\n" +
                    "                        <button type=\"button\" class=\"btn  btn-sm   active \">" + response.items[i].listTagDto[0].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn btn-sm\">" + response.items[i].listTagDto[1].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn btn-sm\">" + response.items[i].listTagDto[2].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn  btn-sm \">" + response.items[i].listTagDto[3].name + "</button>\n" +
                    "                        <button type=\"button\" class=\"btn  btn-sm overflow-hidden\">" + response.items[i].listTagDto[4].name + "</button>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </a>"
                )
            }
        })
    }
}

// $('#questionsTable').append(
//     "<div class=\"d-flex w-100 justify-content-between\">\n" +
//     "<h5 class=\"mb-1\">" + response.items[i].title + "</h5>\n" +
//     "<small class=\"text-muted\">" + response.items[i].lastUpdateDateTime + "</small>\n" +
//     "</div>\n" +
//     "<p class=\"mb-1\">" + response.items[i].description + "<</p>\n" +
//     "<div class=\"nav-col btn-group  btn-block mr-0   \">\n" +
//     "<button type=\"button\" class=\"btn  btn-sm   active \">" + response.items[i].listTagDto[0].name + "</button>\n" +
//     "<button type=\"button\" class=\"btn  btn-sm   active \">" + response.items[i].listTagDto[1].name + "</button>\n" +
//     "<button type=\"button\" class=\"btn  btn-sm   active \">" + response.items[i].listTagDto[2].name + "</button>\n" +
//     "<button type=\"button\" class=\"btn  btn-sm   active \">" + response.items[i].listTagDto[3].name + "</button>\n" +
//     "<button type=\"button\" class=\"btn  btn-sm overflow-hidden\">" + response.items[i].listTagDto[4].name + "</button>\n" +
//     "</div>\n" +
//     "<small class=\"text-muted\">" + response.items[i].countValuable + " голосов</small>\n" +
//     "<small class=\"text-muted\">" + response.items[i].countAnswer + " ответов</small>\n" +
//     "<small class=\"text-muted\">" + response.items[i].viewCount + " просмотров</small>\n" +
//     "<p/> <small class=\"text-muted\">задан " + response.items[i].persistDateTime + "</small>\n " +
//     "<p/> <small class=\"text-muted\">" + response.items[i].authorName + "</small>\n " +
//     "<img src=" + response.items[i].authorImage + " class=\"mr-3\" alt=\"...\">"
// )