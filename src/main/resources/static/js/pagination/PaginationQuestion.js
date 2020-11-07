class PaginationQuestion {

    constructor(page, size) {
        this.page = page;
        this.size = size;
        this.questionService = new QuestionService();
    }

    findPagination() {
        this.questionService.findPagination(this.page, this.size);
    }

    findPaginationPopular() {
        this.questionService.findPaginationPopular(this.page, this.size);
    }
}