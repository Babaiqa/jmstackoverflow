class TagService {

    getTagDtoPaginationByPopular(page, size) {
        let result = new Array();
        fetch('api/tag/popular?page=' + page + '&size=' + size)
            .then(response => response.json())
            .then(tagPopular => result.push.apply(result, tagPopular.items));
        return result;
    }

    getTagDtoPaginationOrderByAlphabet(page, size) {
        let result = new Array();
        fetch('api/tag/alphabet/order?page=' + page + '&size=' + size)
            .then(response => response.json())
            .then(tagOrderByAlphabet => result.push.apply(result, tagOrderByAlphabet.items));
        return result;
    }

    getTagListDtoByPopularPagination(page, size) {
        let result = new Array();
        fetch('api/tag/order/popular?page=' + page + '&size=' + size)
            .then(response => response.json())
            .then(tagOrderByPopular => result.push.apply(result, tagOrderByPopular.items));
        return result;
    }

    getTagRecentDtoPagination(page, size) {
        let result = new Array();
        fetch('api/tag/recent?page=' + page + '&size=' + size)
            .then(response => response.json())
            .then(tagRecent => result.push.apply(result, tagRecent.items));
        return result;
    }

}