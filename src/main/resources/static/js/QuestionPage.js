class QuestionPage {

    constructor(questionId) {
        this.questionId = questionId;
        this.questionService = new QuestionService();
        this.answerService = new AnswerService();
    }

    populateQuestionPage() {
        this.questionService.getQuestionById(this.questionId)
            .then(response => {
                const date = new Date(response.persistDateTime)
                const stringDate = ('0' + date.getDate()).slice(-2) + "."
                    + ('0' + (date.getMonth() + 1)).slice(-2) + "."
                    + date.getFullYear()
                const dateLastUpdate = new Date(response.persistDateTime)
                const stringDateLastUpdate = ('0' + dateLastUpdate.getDate()).slice(-2) + "."
                    + ('0' + (dateLastUpdate.getMonth() + 1)).slice(-2) + "."
                    + dateLastUpdate.getFullYear()

                document.getElementById('question-title').innerHTML = "<p>" + response.title + "</p>"

                $('#question-underheader').children().remove()
                $('#question-underheader').append(
                    "            <div class=\"col\">\n" +
                    "                <p>\n" +
                    "                    <span style=\"color: #6a737c\">Вопрос задан:</span> " + stringDate + "&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                    "                    <span style=\"color: #6a737c\">Последняя активность:</span> " + stringDateLastUpdate + "&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                    "                    <span style=\"color: #6a737c\">Просмотрен:</span> " + response.viewCount + " раза\n" +
                    "                </p>\n" +
                    "            </div>\n"
                )

                $('#question-area').children().remove()
                $('#question-area').append(
                    "                    <div class=\"row\">\n" +
                    "                        <div vote-area-question class=\"col-1\">\n" +
                    "                            <svg width=\"36\" height=\"36\" >\n" +
                    "                                <path d=\"M2 26h32L18 10 2 26z\"></path>\n" +
                    "                            </svg>\n" +
                    "                            <div style=\"font-size: 200%\"> &nbsp;" + response.countValuable + "</div>\n" +
                    "                            <svg  width=\"36\" height=\"36\" >\n" +
                    "                                <path d=\"M2 10h32L18 26 2 10z\"></path>\n" +
                    "                            </svg>\n" +
                    "                        </div>\n" +
                    "                        <div question-and-comments-area class=\"col-11\">\n" +
                    "                            <div question-area class=\"col\">\n" +
                    "                                <div id=\"question-describtion\" describlion class=\"s-prose js-post-body\" itemprop=\"text\">\n" +
                    "<p>" + response.description + "</p>" +
                    "                                </div>\n" +
                    "                                <div id='question-tags' class=\"tags\">\n" +

                    "                                </div>\n" +
                    "                                <div underquestion class=\"mb0 \">\n" +
                    "                                    <div class=\"row justify-content-between px-3\">\n" +
                    "                                        <div>\n" +
                    "                                            <a href=\"#\">Поделиться</a>\n" +
                    "                                            <a href=\"#\">Править</a>\n" +
                    "                                            <a href=\"#\">Отслеживать</a>\n" +
                    "                                        </div>\n" +
                    "                                        <div>\n" +
                    "                                            <div class=\"card\" style=\"width: 250px\">\n" +
                    "                                                <div class=\"card-body p-2\" style=\"background: #e0ebf3\">\n" +
                    "                                                    <div>\n" +
                    "                                                        <span>Задан " + stringDate + "</span>\n" +
                    "                                                    </div>\n" +
                    "                                                    <div class=\"row\">\n" +
                    "                                                        <div class=\"col-3\">\n" +
                    "                                                            <img width=\"48\" height=\"48\" src=\"" + response.authorImage + "\" alt=\"...\">\n" +
                    "                                                        </div>\n" +
                    "                                                        <div class=\"col-8\">\n" +
                    "                                                            <div>" + response.authorName + "</div>\n" +
                    "                                                            <div><b>17</b>&nbsp;&nbsp;&nbsp;12</div>\n" +
                    "                                                        </div>\n" +
                    "                                                    </div>\n" +
                    "                                                </div>\n" +
                    "                                            </div>\n" +
                    "                                        </div>\n" +
                    "\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                            <div comments class=\"post-layout--right\">\n" +
                    "                                <div id=\"comments-1237608\" class=\"comments js-comments-container bt bc-black-075 mt12  dno\" data-post-id=\"1237608\" data-min-length=\"15\">\n" +
                    "                                    <ul class=\"comments-list js-comments-list\" data-remaining-comments-count=\"0\" data-canpost=\"false\" data-cansee=\"true\" data-comments-unavailable=\"false\" data-addlink-disabled=\"true\">\n" +
                    "                                        <hr/>\n" +
                    "                                        <li>\n" +
                    "                                            <span>Что такое var_with_long_name ?</span>\n" +
                    "                                            –\n" +
                    "                                            <a href=\"#\">Alpensin</a>\n" +
                    "                                            <span>1 час назад</span>\n" +
                    "                                        </li>\n" +
                    "                                        <hr/>\n" +
                    "                                        <li>\n" +
                    "                                            <span>Возможное имя переменной.</span>\n" +
                    "                                            –\n" +
                    "                                            <a href=\"#\">Алексей</a>\n" +
                    "                                            <span>44 минуты назад</span>\n" +
                    "                                        </li>\n" +
                    "                                        <hr/>\n" +
                    "                                    </ul>\n" +
                    "                                </div>\n" +
                    "\n" +
                    "                                <div id=\"comments-link-1237608\" data-rep=\"50\" data-reg=\"true\">\n" +
                    "                                    <a href=\"#\">добавить комментарий</a>\n" +
                    "                                    <span >&nbsp;|&nbsp;</span>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>"
                )
                response.listTagDto.forEach(tag => {
                    $('#question-tags').append(
                        "                                    <div class=\"mb-1\">\n" +
                        tag.name +
                        "                                    </div>")
                })
            })
        this.answerService.getAnswerListByQuestionId(this.questionId)
            .then(response => {

                $('#answer-area').children().remove()
                $('#answer-area').append(
                    "                    <div info class=\"row justify-content-between m-2 py-5\">\n" +
                    "                        <div style=\"font-size: 150%\">\n" +
                    "                            " + response.length + " Ответ\n" +
                    "                        </div>\n" +
                    "                        <div>\n" +
                    "                            <div class=\"btn-group\">\n" +
                    "                                <button type=\"button\" class=\"btn btn-outline-secondary\">Текущие</button>\n" +
                    "                                <button type=\"button\" class=\"btn btn-outline-secondary\">По дате побликации</button>\n" +
                    "                                <button type=\"button\" class=\"btn btn-outline-secondary active\">Голоса</button>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>"
                )


                response.forEach(elem => {
                    const date = new Date(elem.persistDate)
                    const stringDate = ('0' + date.getDate()).slice(-2) + "."
                        + ('0' + (date.getMonth() + 1)).slice(-2) + "."
                        + date.getFullYear()

                    $('#answer-area').append(
                        "<div answer1 class=\"row\">\n" +
                        "    <div vote-area-answer class=\"col-1\">\n" +
                        '<a  onclick="new AnswerService().getUpVoteAnswer(' + this.questionId + ',\'' + elem.id + '\')">' +
                        "        <svg width=\"36\" height=\"36\" >\n" +
                        "              <path d=\"M2 26h32L18 10 2 26z\"></path>\n" +
                        "        </svg>\n" +
                        '</a>' +
                        "             <div style=\"font-size: 200%\"> &nbsp;" + elem.countValuable + "</div>\n" +
                        '<a  onclick="new AnswerService().getDownVoteAnswer(' + this.questionId + ',\'' + elem.id + '\')"> ' +
                        "                 <svg  width=\"36\" height=\"36\" >\n" +
                        "                     <path d=\"M2 10h32L18 26 2 10z\"></path>\n" +
                        "                 </svg>\n" +
                        '</a>     ' + (elem.isHelpful == true ? '<svg width="36" height="36">\n' +
                        '                                               <path d="M6 14l8 8L30 6v8L14 30l-8-8v-8z"></path>\n' +
                        '                                        </svg>' : '') +
                        "             </div>\n" +
                        "                        <div answer-and-comments-area class=\"col-11\">\n" +
                        "                            <div>" +
                        "                                  <p>" + elem.body + "</p>" +
                        "                            </div>\n" +
                        "                            <div usderanswer class=\"mb0 \">\n" +
                        "                                <div class=\"row justify-content-between px-3\">\n" +
                        "                                    <div>\n" +
                        "                                        <a href=\"#\">Поделиться</a>\n" +
                        "                                        <a href=\"#\">Править</a>\n" +
                        "                                        <a href=\"#\">Отслеживать</a>\n" +
                        "                                    </div>\n" +
                        "                                    <div>\n" +
                        "                                        <div style=\"width: 250px\">\n" +
                        "                                            <div>\n" +
                        "                                                <div>\n" +
                        "                                                    <span>Ответ дан: " + stringDate + "</span>\n" +
                        "                                                </div>\n" +
                        "                                                <div class=\"row\">\n" +
                        "                                                    <div class=\"col-3\">\n" +
                        "                                                        <img width=\"48\" height=\"48\" src=\"" + elem.image + "\" alt=\"...\">\n" +
                        "                                                    </div>\n" +
                        "                                                    <div class=\"col\">\n" +
                        "                                                        <div>" + elem.nickName + "</div>\n" +
                        "                                                        <div><b>25</b>&nbsp;&nbsp;&nbsp;1</div>\n" +
                        "                                                    </div>\n" +
                        "                                                </div>\n" +
                        "                                            </div>\n" +
                        "                                        </div>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                            <div comments class=\"post-layout--right\">\n" +
                        "                                <div>\n" +
                        "                                    <ul>\n" +
                        "                                        <hr/>\n" +
                        "                                        <li>\n" +
                        "                                            <span>Что такое var_with_long_name ?</span>\n" +
                        "                                            –\n" +
                        "                                            <a href=\"#\">Alpensin</a>\n" +
                        "                                            <span>1 час назад</span>\n" +
                        "                                        </li>\n" +
                        "                                        <hr/>\n" +
                        "                                        <li>\n" +
                        "                                            <span>Возможное имя переменной.</span>\n" +
                        "                                            –\n" +
                        "                                            <a href=\"#\">Алексей</a>\n" +
                        "                                            <span>44 минуты назад</span>\n" +
                        "                                        </li>\n" +
                        "                                        <hr/>\n" +
                        "                                    </ul>\n" +
                        "                                </div>\n" +
                        "                                <div id=\"comments-link-1237602\" data-rep=\"50\" data-reg=\"true\">\n" +
                        "                                    <a href=\"#\" >добавить комментарий</a>\n" +
                        "                                    <span>&nbsp;|&nbsp;</span>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                    <hr/>")
                })
            })
    }

}
