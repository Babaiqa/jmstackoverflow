// $(document).on('click', '#addComment', function () {
//     var form = $("#commentForm");
//     form.css("display", "block");
//     $("#addComment").replaceWith(form);
// });
// $(document).on('click', '#submitComment', function () {
//
//     let comment = document.getElementById("commentTextArea").value;
//     console.log("Вот эта херь:" + comment);
//     console.log(document.);
//     alert(comment);
//
//     fetch('/api/question/' + this.questionId + '/comment',
//         {
//             method: 'POST',
//             headers: new Headers({
//                 'Content-Type': 'application/json',
//                 'Authorization': $.cookie("token")
//             }),
//             body: JSON.stringify(comment)
//         }).then(data => new QuestionPage().getCommentsById(questionId))
//         .catch(error => console.log(error.message));
// });