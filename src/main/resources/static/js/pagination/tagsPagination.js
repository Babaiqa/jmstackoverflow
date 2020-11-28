class PaginationTag {

    constructor(page, size, sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;

        this.tagService = new TagService();

        if(this.sort == 'name'){
            this.tags = this.tagService.getTagDtoPaginationOrderByAlphabet(this.page, this.size);
        }
        else if(this.sort == 'new'){
            this.tags = this.tagService.getTagRecentDtoPagination(this.page, this.size);
        }
        else {
            this.tags = this.tagService.getTagListDtoByPopularPagination(this.page, this.size);
        }
    }

    writeTags() {

        $('#tagsTable').children().remove()

        this.tags.then(function (response) {
            for (var i = 0; i < response.items.length; i++) {
                $('#tagsTable').append(
                    " <div class=\"child\">"
                    + "<p class=\"media\">"
                    + "<a href=\"#\" class=\"mb-1\">" + response.items[i].name + "</a>"
                    + "<div><small>" + response.items[i].description + "</small></div>"
                    + "<p>"
                    + "<div class=\"child2\"><small class=\"text-muted\">" + response.items[i].countQuestion + " вопросов" + "</small></div>"
                    + "<div class=\"child2\">"
                    + "<small><a href=\"#\" class=\"text-muted\">" + response.items[i].countQuestionToDay + " задано сегодня, " + "</a></small>"
                    + "<small><a href=\"#\" class=\"text-muted\">" + response.items[i].countQuestionToWeek + " за неделю" + "</a></small>"
                    + "</div> </p> </div>");
            }
        })
        this.tagsPageNavigation()
    }

    tagsPageNavigation() {
        var size = this.size;
        var sort = this.sort;

        this.tags.then(function (response) {
            var currentPageNumber = response.currentPageNumber;
            var nextPage = response.currentPageNumber + 1;
            var secondNextPage = response.currentPageNumber + 2;
            var totalPageCount = response.totalPageCount;
            var previousPage = response.currentPageNumber - 1;


            $('#tagsPagesNavigation').children().remove();
            if (currentPageNumber != 1) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + previousPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()' >Назад</a></li>"
                );
            }

            if (currentPageNumber == totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                );
            }

            if (nextPage == totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage == totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + secondNextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + "Далее" + "</a></li>"
                );
            }

            if (secondNextPage < totalPageCount) {
                $('#tagsPagesNavigation').append(
                    "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + currentPageNumber + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + nextPage + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + secondNextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + secondNextPage + "</a></li>"
                    + "<li class=\"page-item\"><span class='mr-2 ml-2'>" + "..." + "</span></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + totalPageCount + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + totalPageCount + "</a></li>"
                    + "<li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick='new PaginationTag(" + nextPage + "," + size + "," + "\"" + sort + "\"" + ").writeTags()'>" + "Далее" + "</a></li>"
                );
            }
        })
    }
}
