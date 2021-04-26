function addCommentFunc(){
    var form = $("form[name='commentForm']");
    form.css("display", "block");
    $("#addComment").replaceWith(form)
});