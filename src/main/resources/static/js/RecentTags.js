$(document).ready(function (){
    $('#mainTag').children().remove();
    $.get("/api/tag/order/popular?page=1&size=20",function (response) {
        for (var i = 0; i < response.items.length; i++) {
            $('#mainTag').append(
                '<tr>'
                + '<td>'
                + '<div class="mb-1">'
                + response.items[i].name
                + '</div>'
                + '<span id="spanX">'
                +     '<span  class="item-multiplier-x">x</span>'
                +     '<span  class="item-multiplier-count ">'+ response.items[i].countQuestionToDay + '</span>'
                + '</span>'
                + '</td>'
                +'</tr>'
            );
        }
    })
})

$(document).ready(function (){
    $('#questionTag').children().remove();
    $.get("/api/tag/order/popular?page=1&size=20",function (response) {
        for (var i = 0; i < response.items.length; i++) {
            $('#questionTag').append(
                '<tr>'
                + '<td>'
                + '<div class="mb-1">'
                + response.items[i].name
                + '</div>'
                + '<span id="spanX">'
                +     '<span  class="item-multiplier-x">x</span>'
                +     '<span  class="item-multiplier-count ">'+ response.items[i].countQuestionToDay + '</span>'
                + '</span>'
                + '</td>'
                +'</tr>'
            );
        }
    })
})

