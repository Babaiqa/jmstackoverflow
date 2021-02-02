let requestURLTagTracked= 'http://localhost:5557/api/tag/tracked';
let requestURLTagIgnore= 'http://localhost:5557/api/tag/ignored';
let requestURLSearchTag= 'http://localhost:5557/api/tag/name';

function sendRequestSearchTag(requestMethod, url, body = null) {
    return fetch(url, {
        method: requestMethod,
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

// ----------------Universal methods------------------
function getCoords(elem) {
    let box = elem.getBoundingClientRect();

    return {
        bottom: box.bottom + pageYOffset,
        left: box.left + pageXOffset
    };
}

function addListenersForTagBarElems(pageName, tagType) {
    let place

    if (tagType === 'tracked'){
        place = 920
    } else {
        place = 720
    }

    const input = document.getElementById('add-'+tagType+'-tag-'+pageName)

    input.addEventListener("click",function(){
        const searchList = document.getElementById('search-list-' + tagType + '-tag-' + pageName)
        searchList.setAttribute("style", "display: block; background: white; position: relative; width: 170px; max-height: 200px; top: 1px; left: 90px; box-shadow: -1px 2px 7px rgba(0,0,0,0.1);")
        let coordsSearchList = getCoords(searchList)
        let coordsInput = getCoords(input)
        searchList.style.top = (coordsInput.bottom - coordsSearchList.bottom)+"px"
        searchList.style.left = (coordsInput.left - coordsSearchList.left+100)+"px"
        console.log(coordsInput)
        console.log(searchList.style.top)
        console.log(coordsSearchList)
    })

    input.oninput = function () {
        sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + input.value)
            .then(response=> {
                console.log(response.items)
                document.getElementById('search-list-' + tagType + '-tag-' + pageName).innerHTML=""
                document.getElementById('search-list-' + tagType + '-tag-' + pageName).addEventListener("click",function (){
                    input.value = event.target.id
                })
                response.items.forEach(elem => {
                    document.getElementById('search-list-' + tagType + '-tag-' + pageName).innerHTML +=
                        "<option class=\"dropdown-item\" id=\"" + elem.name + "\">" + elem.name + "</option>"
                })
            })
            .catch(err => console.log(err))
    }
    document.getElementById('button-add-'+tagType+'-tag-'+pageName).addEventListener('click', function (){
        //api/tag/ignored/add      сделать универсальный запрос
        sendRequestSearchTag('POST', "http://localhost:5557/api/tag/"+tagType+"/add?name=" + input.value)
            .then( data => {
                populateTags(pageName, tagType)
                input.value=""
                document.getElementById('search-list-'+tagType+'-tag-'+pageName).setAttribute("style", "display: none;")
                document.getElementById('search-list-'+tagType+'-tag-'+pageName).innerHTML = ""
            })
    })

}

function deleteTag(tagType) {
    event.target.parentNode.remove()
    sendRequestSearchTag('DELETE', 'http://localhost:5557/api/tag/'+tagType+'/delete?tagId=' + event.target.dataset.id)
        .catch(err => console.log(err))
}

function populateTags(pageName, tagType) {
    sendRequestSearchTag('GET', 'http://localhost:5557/api/tag/'+tagType)
        .then(response=> {
            document.getElementById('list-'+tagType+'-tag-'+pageName).innerHTML=""
            response.forEach(elem => {
                document.getElementById('list-'+tagType+'-tag-'+pageName).innerHTML +=
                    "<div class=\"mb-1\">" +
                    elem.name + " " +
                    "   <span class='close' data-id=\"" + elem.id + "\" onclick='deleteTag(\""+tagType+"\")' style='font-size: 100%; margin-left: 5px;'>X</span>\n" +
                    "</div>"
            })
        })
        .catch(err => console.log(err))
}

addListenersForTagBarElems('main','tracked')
populateTags('main','tracked')
addListenersForTagBarElems('main','ignored')
populateTags('main','ignored')
addListenersForTagBarElems('question-area','tracked')
populateTags('question-area','tracked')
addListenersForTagBarElems('question-area','ignored')
populateTags('question-area','ignored')
//
//
// //---------------Tracked Tag listners for Main tab ------------------
// const inputTracked1 = document.getElementById('add-tag-tracked1')
// inputTracked1.onclick = function () {
//     document.getElementById('listOfSuitableTagsForTracking1').setAttribute("style", "display: block; background: white; position: relative; width: 170px; max-height: 200px; bottom: 950px; left: 90px; box-shadow: -1px 2px 7px rgba(0,0,0,0.1);")
// }
// inputTracked1.oninput = function () {
//     sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputTracked1.value)
//         .then(response=> {
//             console.log(response)
//             console.log(response.items)
//             document.getElementById('listOfSuitableTagsForTracking1').innerHTML=""
//             document.getElementById('listOfSuitableTagsForTracking1').addEventListener("click",function (){
//                 inputTracked1.value = event.target.id
//             })
//             response.items.forEach(elem => {
//                 document.getElementById('listOfSuitableTagsForTracking1').innerHTML +=
//                     "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
//             })
//         })
//         .catch(err => console.log(err))
// }
// document.getElementById('buttonTagTracked1').addEventListener('click', function (){
//     //api/tag/ignored/add      сделать универсальный запрос
//     sendRequestSearchTag('POST', "http://localhost:5557/api/tag/tracked/add?name=" + inputTracked1.value)
//         .then( data => {
//             populateTrackedTags1()
//             inputTracked1.value=""
//             document.getElementById('listOfSuitableTagsForTracking1').setAttribute("style", "display: none; background: white; position: relative; width: 170px; max-height: 200px; bottom: 950px; left: 90px; box-shadow: -1px 2px 7px rgba(0,0,0,0.1);")
//             document.getElementById('listOfSuitableTagsForTracking1').innerHTML = ""
//         })
// })
//
// //---------------Ignore Tag listners for Main tab ------------------
// const inputIgnore1 = document.getElementById('add-tag-ignore1')
// inputIgnore1.onclick = function () {
//     document.getElementById('listOfSuitableTagsForIgnoring1').setAttribute("style", "display: block; background: white; position: relative; width: 170px; max-height: 200px; bottom: 750px; left: 90px; box-shadow: -1px 2px 7px rgba(0,0,0,0.1);")
// }
// inputIgnore1.oninput = function () {
//     sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputIgnore1.value)
//         .then(response=> {
//             console.log(response)
//             console.log(response.items)
//             document.getElementById('listOfSuitableTagsForIgnoring1').innerHTML=""
//             document.getElementById('listOfSuitableTagsForIgnoring1').addEventListener("click",function (){
//                 inputIgnore1.value = event.target.id
//             })
//             response.items.forEach(elem => {
//                 document.getElementById('listOfSuitableTagsForIgnoring1').innerHTML +=
//                     "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
//             })
//         })
//         .catch(err => console.log(err))
// }
// document.getElementById('buttonTagIgnore1').addEventListener('click', function (){
//     //api/tag/ignored/add      сделать универсальный запрос
//     sendRequestSearchTag('POST', "http://localhost:5557/api/tag/ignored/add?name=" + inputIgnore1.value)
//         .then( data => {
//             populateIgnoredTags1()
//             inputTracked1.value=""
//             document.getElementById('listOfSuitableTagsForIgnoring1').setAttribute("style", "display: none;")
//             document.getElementById('listOfSuitableTagsForIgnoring1').innerHTML = ""
//         })
// })
//
// //---------------Tracked Tag listners for Question tab ------------------
// const inputTracked2 = document.getElementById('add-tag-tracked2')
// inputTracked2.onclick = function () {
//     document.getElementById('listOfSuitableTagsForTracking2').setAttribute("style", "display: block")
// }
// inputTracked2.oninput = function () {
//     sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputTracked2.value)
//         .then(response=> {
//             console.log(response)
//             console.log(response.items)
//             document.getElementById('listOfSuitableTagsForTracking2').innerHTML=""
//             document.getElementById('listOfSuitableTagsForTracking2').addEventListener("click",function (){
//                 inputTracked2.value = event.target.id
//             })
//             response.items.forEach(elem => {
//                 document.getElementById('listOfSuitableTagsForTracking2').innerHTML +=
//                     "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
//             })
//         })
//         .catch(err => console.log(err))
// }
// document.getElementById('buttonTagTracked2').addEventListener('click', function (){
//     //api/tag/ignored/add      сделать универсальный запрос
//     sendRequestSearchTag('POST', "http://localhost:5557/api/tag/tracked/add?name=" + inputTracked2.value)
//         .then( data => populateTrackedTags2())
// })
//
//
// //---------------Ignore Tag listners for Question tab ------------------
// const inputIgnore2 = document.getElementById('add-tag-ignore2')
// inputIgnore2.onclick = function () {
//     document.getElementById('listOfSuitableTagsForIgnoring2').setAttribute("style", "display: block")
// }
// inputIgnore2.oninput = function () {
//     sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputIgnore2.value)
//         .then(response=> {
//             console.log(response)
//             console.log(response.items)
//             document.getElementById('listOfSuitableTagsForIgnoring2').innerHTML=""
//             document.getElementById('listOfSuitableTagsForIgnoring2').addEventListener("click",function (){
//                 inputIgnore2.value = event.target.id
//             })
//             response.items.forEach(elem => {
//                 document.getElementById('listOfSuitableTagsForIgnoring2').innerHTML +=
//                     "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
//             })
//         })
//         .catch(err => console.log(err))
// }
// document.getElementById('buttonTagIgnore2').addEventListener('click', function (){
//     //api/tag/ignored/add      сделать универсальный запрос
//     sendRequestSearchTag('POST', "http://localhost:5557/api/tag/ignored/add?name=" + inputIgnore2.value)
//         .then( data => populateIgnoredTags2())
// })
//
// populateTrackedTags1()
// populateTrackedTags2()
// populateIgnoredTags1()
// populateIgnoredTags2()
//
//
// function sendRequestTagTracked(requestMethod, url, body = null) {
//     return fetch(url, {
//         method: requestMethod,
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
// function deleteTrackedTag() {
//     event.target.parentNode.remove()
//     sendRequestTagTracked('DELETE', 'http://localhost:5557/api/tag/tracked/delete?tagId=' + event.target.dataset.id)
// }
//
// function deleteIgnoredTag() {
//     event.target.parentNode.remove()
//     sendRequestTagTracked('DELETE', 'http://localhost:5557/api/tag/ignored/delete?tagId=' + event.target.dataset.id)
// }
//
// function populateTrackedTags1() {
//     sendRequestTagTracked('GET', requestURLTagTracked)
//         .then(response=> {
//             document.getElementById('list-tracked-tag-main').innerHTML=""
//             response.forEach(elem => {
//                 document.getElementById('list-tracked-tag-main').innerHTML +=
//                     "<div class=\"mb-1\">" +
//                         elem.name + " " +
//                     "   <span class='close' data-id=\"" + elem.id + "\" onclick='deleteTrackedTag()' style='font-size: 100%; margin-left: 5px;'>X</span>\n" +
//                     "</div>"
//             })
//         })
//         .catch(err => console.log(err))
// }
//
// function populateTrackedTags2() {
//     sendRequestTagTracked('GET', requestURLTagTracked)
//         .then(response=> {
//             document.getElementById('listTagTracked2').innerHTML=""
//             response.forEach(elem => {
//                 document.getElementById('listTagTracked2').innerHTML +=
//                     "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
//                     "            <br> "
//             })
//         })
//         .catch(err => console.log(err))
// }
//
// function populateIgnoredTags1() {
//     sendRequestTagTracked('GET', requestURLTagIgnore)
//         .then(response=> {
//             document.getElementById('listTagIgnore1').innerHTML=""
//             response.forEach(elem => {
//                 document.getElementById('listTagIgnore1').innerHTML +=
//                     "<div class=\"mb-1\">" +
//                     elem.name + " " +
//                     "   <span class='close' data-id=\"" + elem.id + "\" onclick='deleteIgnoredTag()' style='font-size: 100%; margin-left: 5px;'>X</span>\n" +
//                     "</div>"
//             })
//         })
//         .catch(err => console.log(err))
// }
//
// function populateIgnoredTags2 () {
//     sendRequestTagTracked('GET', requestURLTagIgnore)
//         .then(response=> {
//             document.getElementById('listTagIgnore2').innerHTML=""
//             response.forEach(elem => {
//                 document.getElementById('listTagIgnore2').innerHTML +=
//                     "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
//                     "            <br> "
//             })
//         })
//         .catch(err => console.log(err))
// }

