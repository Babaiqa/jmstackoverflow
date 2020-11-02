class TagService {

    getTagDtoPaginationByPopular(page, size) {
        let result = new Array();
        fetch('http://localhost:5557/api/tag/popular?page=' + page + '&size=' + size)
            .then(response => {
                if (response.status !== 200) {
                    let error = new Error('Не корректный запрос! Проверьте значения page и size!');
                    error.response = response;
                    throw error;
                } else {
                    return response.json()
                }
            })
            .then(tagPopular => {
                if (tagPopular.items.length === 0) {
                    let error = new Error('Не корректный запрос! Page превышает допустимые значения! Страница не найдена!');
                    error.response = tagPopular;
                    throw error;
                } else {
                    result.push.apply(result, tagPopular.items);
                }
            })
            .catch((e) => {
                console.log('Error: ' + e.message);
                console.log(e.response);
            });
        return result;
    }

    getTagDtoPaginationOrderByAlphabet(page, size) {
        let result = new Array();
        fetch('http://localhost:5557/api/tag/alphabet/order?page=' + page + '&size=' + size)
            .then(response => {
                if (response.status !== 200) {
                    let error = new Error('Не корректный запрос! Проверьте значения page и size!');
                    error.response = response;
                    throw error;
                } else {
                    return response.json()
                }
            })
            .then(tagOrderByAlphabet => {
                if (tagOrderByAlphabet.items.length === 0) {
                    let error = new Error('Не корректный запрос! Page превышает допустимые значения! Страница не найдена!');
                    error.response = tagOrderByAlphabet;
                    throw error;
                } else {
                    result.push.apply(result, tagOrderByAlphabet.items);
                }
            })
            .catch((e) => {
                console.log('Error: ' + e.message);
                console.log(e.response);
            });
        return result;
    }

    getTagListDtoByPopularPagination(page, size) {
        let result = new Array();
        fetch('http://localhost:5557/api/tag/order/popular?page=' + page + '&size=' + size)
            .then(response => {
                if (response.status !== 200) {
                    let error = new Error('Не корректный запрос! Проверьте значения page и size!');
                    error.response = response;
                    throw error;
                } else {
                    return response.json()
                }
            })
            .then(tagOrderByPopular => {
                if (tagOrderByPopular.items.length === 0) {
                    let error = new Error('Не корректный запрос! Page превышает допустимые значения! Страница не найдена!');
                    error.response = tagOrderByPopular;
                    throw error;
                } else {
                    result.push.apply(result, tagOrderByPopular.items);
                }
            })
            .catch((e) => {
                console.log('Error: ' + e.message);
                console.log(e.response);
            });
        return result;
    }

    getTagRecentDtoPagination(page, size) {
        let result = new Array();
        fetch('http://localhost:5557/api/tag/recent?page=' + page + '&size=' + size)
            .then(response => {
                if (response.status !== 200) {
                    let error = new Error('Не корректный запрос! Проверьте значения page и size!');
                    error.response = response;
                    throw error;
                } else {
                    return response.json()
                }
            })
            .then(tagRecent => {
                if (tagRecent.items.length === 0) {
                    let error = new Error('Не корректный запрос! Page превышает допустимые значения! Страница не найдена!');
                    error.response = tagRecent;
                    throw error;
                } else {
                    result.push.apply(result, tagRecent.items);
                }
            })
            .catch((e) => {
                console.log('Error: ' + e.message);
                console.log(e.response);
            });
        return result;
    }
}