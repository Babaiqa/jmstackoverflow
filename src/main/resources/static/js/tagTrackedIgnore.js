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


//---------------Tracked Tag listners for Main tab ------------------
const inputTracked1 = document.getElementById('add-tag-tracked1')
inputTracked1.onclick = function () {
    document.getElementById('listOfSuitableTagsForTracking1').setAttribute("style", "display: block")
}
inputTracked1.oninput = function () {
    sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputTracked1.value)
        .then(response=> {
            console.log(response)
            console.log(response.items)
            document.getElementById('listOfSuitableTagsForTracking1').innerHTML=""
            document.getElementById('listOfSuitableTagsForTracking1').addEventListener("click",function (){
                inputTracked1.value = event.target.id
            })
            response.items.forEach(elem => {
                document.getElementById('listOfSuitableTagsForTracking1').innerHTML +=
                    "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
            })
        })
        .catch(err => console.log(err))
}
document.getElementById('buttonTagTracked1').addEventListener('click', function (){
    //api/tag/ignored/add      сделать универсальный запрос
    sendRequestSearchTag('POST', "http://localhost:5557/api/tag/tracked/add?name=" + inputTracked1.value)
        .then( data => populateTrackedTags1())
})

//---------------Ignore Tag listners for Main tab ------------------
const inputIgnore1 = document.getElementById('add-tag-ignore1')
inputIgnore1.onclick = function () {
    document.getElementById('listOfSuitableTagsForIgnoring1').setAttribute("style", "display: block")
}
inputIgnore1.oninput = function () {
    sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputIgnore1.value)
        .then(response=> {
            console.log(response)
            console.log(response.items)
            document.getElementById('listOfSuitableTagsForIgnoring1').innerHTML=""
            document.getElementById('listOfSuitableTagsForIgnoring1').addEventListener("click",function (){
                inputIgnore1.value = event.target.id
            })
            response.items.forEach(elem => {
                document.getElementById('listOfSuitableTagsForIgnoring1').innerHTML +=
                    "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
            })
        })
        .catch(err => console.log(err))
}
document.getElementById('buttonTagIgnore1').addEventListener('click', function (){
    //api/tag/ignored/add      сделать универсальный запрос
    sendRequestSearchTag('POST', "http://localhost:5557/api/tag/ignored/add?name=" + inputIgnore1.value)
        .then( data => populateIgnoredTags1())
})

//---------------Tracked Tag listners for Question tab ------------------
const inputTracked2 = document.getElementById('add-tag-tracked2')
inputTracked2.onclick = function () {
    document.getElementById('listOfSuitableTagsForTracking2').setAttribute("style", "display: block")
}
inputTracked2.oninput = function () {
    sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputTracked2.value)
        .then(response=> {
            console.log(response)
            console.log(response.items)
            document.getElementById('listOfSuitableTagsForTracking2').innerHTML=""
            document.getElementById('listOfSuitableTagsForTracking2').addEventListener("click",function (){
                inputTracked2.value = event.target.id
            })
            response.items.forEach(elem => {
                document.getElementById('listOfSuitableTagsForTracking2').innerHTML +=
                    "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
            })
        })
        .catch(err => console.log(err))
}
document.getElementById('buttonTagTracked2').addEventListener('click', function (){
    //api/tag/ignored/add      сделать универсальный запрос
    sendRequestSearchTag('POST', "http://localhost:5557/api/tag/tracked/add?name=" + inputTracked2.value)
        .then( data => populateTrackedTags2())
})


//---------------Ignore Tag listners for Question tab ------------------
const inputIgnore2 = document.getElementById('add-tag-ignore2')
inputIgnore2.onclick = function () {
    document.getElementById('listOfSuitableTagsForIgnoring2').setAttribute("style", "display: block")
}
inputIgnore2.oninput = function () {
    sendRequestSearchTag('GET', requestURLSearchTag + "?&page=1&size=5&name=" + inputIgnore2.value)
        .then(response=> {
            console.log(response)
            console.log(response.items)
            document.getElementById('listOfSuitableTagsForIgnoring2').innerHTML=""
            document.getElementById('listOfSuitableTagsForIgnoring2').addEventListener("click",function (){
                inputIgnore2.value = event.target.id
            })
            response.items.forEach(elem => {
                document.getElementById('listOfSuitableTagsForIgnoring2').innerHTML +=
                    "<option class=\"dropdown-item\" id=\""+elem.name+"\">" + elem.name + "</option>"
            })
        })
        .catch(err => console.log(err))
}
document.getElementById('buttonTagIgnore2').addEventListener('click', function (){
    //api/tag/ignored/add      сделать универсальный запрос
    sendRequestSearchTag('POST', "http://localhost:5557/api/tag/ignored/add?name=" + inputIgnore2.value)
        .then( data => populateIgnoredTags2())
})

populateTrackedTags1()
populateTrackedTags2()
populateIgnoredTags1()
populateIgnoredTags2()


function sendRequestTagTracked(method, url, body = null) {
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

function deleteTrackedTag() {
    event.target.parentNode.remove()
    // sendRequestTagTracked('DELETE', requestURLTagTracked)
}

function populateTrackedTags1() {
    sendRequestTagTracked('GET', requestURLTagTracked)
        .then(response=> {
            document.getElementById('listTagTracked1').innerHTML=""
            response.forEach(elem => {
                document.getElementById('listTagTracked1').innerHTML +=
                    "<div class=\"mb-1\">" +
                        elem.name + " " +
                    "   <span class='close' onclick='deleteTrackedTag()' style='font-size: 100%; margin-left: 5px;'>X</span>\n" +
                    "</div>\n" +
                    "<br> "
            })
        })
        .catch(err => console.log(err))
}

function populateTrackedTags2() {
    sendRequestTagTracked('GET', requestURLTagTracked)
        .then(response=> {
            document.getElementById('listTagTracked2').innerHTML=""
            response.forEach(elem => {
                document.getElementById('listTagTracked2').innerHTML +=
                    "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                    "            <br> "
            })
        })
        .catch(err => console.log(err))
}

function populateIgnoredTags1() {
    sendRequestTagTracked('GET', requestURLTagIgnore)
        .then(response=> {
            document.getElementById('listTagIgnore1').innerHTML=""
            response.forEach(elem => {
                document.getElementById('listTagIgnore1').innerHTML +=
                    "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                    "            <br> "
            })
        })
        .catch(err => console.log(err))
}

function populateIgnoredTags2 () {
    sendRequestTagTracked('GET', requestURLTagIgnore)
        .then(response=> {
            document.getElementById('listTagIgnore2').innerHTML=""
            response.forEach(elem => {
                document.getElementById('listTagIgnore2').innerHTML +=
                    "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                    "            <br> "
            })
        })
        .catch(err => console.log(err))
}



function sendRequestTagIgnore(method, url, body = null) {
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





