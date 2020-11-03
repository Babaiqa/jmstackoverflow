class TagService {

    getTagDtoPaginationByPopular(page, size) {
        let query = '/api/tag/popular?page=' + page + '&size=' + size;
        return this.getResponse(query);

    }

    getTagDtoPaginationOrderByAlphabet(page, size) {
        let query = '/api/tag/alphabet/order?page=' + page + '&size=' + size;
        return this.getResponse(query);
    }

    getTagListDtoByPopularPagination(page, size) {
        let query = '/api/tag/order/popular?page=' + page + '&size=' + size;
        return this.getResponse(query);
    }

    getTagRecentDtoPagination(page, size) {
        let query = '/api/tag/recent?page=' + page + '&size=' + size;
        return this.getResponse(query);
    }

    getTagName(name, page, size) {
        let query = '/api/tag/name?name=' + name + '&page=' + page + '&size=' + size;
        return this.getResponse(query);

    }

    getResponse(query) {
        let result = new Array();
        fetch(query)
            .then(response => {
                if (response.ok) {
                    return response.json()
                } else {
                    let error = new Error();
                    error.response = response.text().then(error => console.log(error));
                    throw error;
                }
            })
            .then(entity => result.push.apply(result, entity.items))
            .catch(error => error.response);
        return result;
    }
}