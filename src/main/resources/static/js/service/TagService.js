class TagService {

    getTagDtoPaginationByPopular(page, size) {
        let query = '/api/tag/popular?page=' + page + '&size=' + size;
        return this.getResponse(query);

    }

    getTagDtoPaginationOrderByAlphabet(page, size) {
        let query = '/api/tag/alphabet/order?page=' + page + '&size=' + size;
        return this. getResponse(query);
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
                if (response.status !== 200) {
                    let error = new Error('Не корректный запрос! Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100!');
                    error.response = response;
                    throw error;
                } else {
                    return response.json()
                }
            })
            .then(entity => result.push.apply(result, entity.items))
            .catch((e) => {
                console.log('Error: ' + e.message);
                console.log(e.response);
            });
        return result;
    }
}