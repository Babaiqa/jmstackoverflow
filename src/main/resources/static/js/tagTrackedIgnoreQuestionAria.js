
function sendRequestTagTrackedtagQuestionAria(method, url, body = null) {
    return fetch(url, {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")

        })
    }).then(response => {
        if (response.ok) {
            return response.json()
        }
        return response.json().then(error => {
            const exc = new Error('Ошибка')
            exc.data = error
            throw exc
        })
    })
}

// sendRequestTagTrackedtagQuestionAria('GET', requestURLTagTracked)
//     .then(response=> {
//         document.getElementById('listTagTracked1').innerHTML=""
//         response.forEach(elem => {
//             document.getElementById('listTagTracked1').innerHTML +=
//                 "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
//                 "            <br> "
//         })
//     })
//     .catch(err => console.log(err))
//
// function sendRequestTagIgnoreQuestionAria(method, url, body = null) {
//     return fetch(url, {
//         method: 'GET',
//         headers: new Headers({
//             'Content-Type': 'application/json',
//             'Authorization': $.cookie("token")
//         })
//     }).then(response => {
//         if (response.ok) {
//             return response.json()
//         }
//         return response.json().then(error => {
//             const exc = new Error('Ошибка')
//             exc.data = error
//             throw exc
//         })
//     })
// }
//
// sendRequestTagIgnoreQuestionAria('GET', requestURLTagIgnore)
//     .then(response=> {
//         document.getElementById('listTagIgnore1').innerHTML=""
//         response.forEach(elem => {
//             document.getElementById('listTagIgnore1').innerHTML +=
//                 "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
//                 "            <br> "
//         })
//     })
//     .catch(err => console.log(err))
//
