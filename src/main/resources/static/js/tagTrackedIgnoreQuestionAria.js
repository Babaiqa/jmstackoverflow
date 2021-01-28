
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

sendRequestTagTrackedtagQuestionAria('GET', requestURLTagTracked)
    .then(response=> {
        document.getElementById('listTagTracked1').innerHTML=""
        response.forEach(elem => {
            document.getElementById('listTagTracked1').innerHTML +=
                "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                "            <br> "
        })
    })
    .catch(err => console.log(err))

function sendRequestTagIgnoreQuestionAria(method, url, body = null) {
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

sendRequestTagIgnoreQuestionAria('GET', requestURLTagIgnore)
    .then(response=> {
        document.getElementById('listTagIgnore1').innerHTML=""
        response.forEach(elem => {
            document.getElementById('listTagIgnore1').innerHTML +=
                "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                "            <br> "
        })
    })
    .catch(err => console.log(err))

const button3 = document.querySelector('#buttonTagTracked1')
button3.onclick = handleButtonClickTrackedQuestionAria;

const button4 = document.querySelector('#buttonTagIgnore1')
button4.onclick = handleButtonClickIgnoreQuestionAria;

function handleButtonClickTrackedQuestionAria() {
    const textInputTracked1 = document.querySelector('#add-tag-tracked1');
    const tagNameTracked1 = textInputTracked1.value;
    if(tagNameTracked1 == ""){
        alert("Введите название метки, пожалуйста");
    }else{
        const ul =  document.querySelector('#listTagTracked1');
        const li = document.createElement("li");
        li.innerHTML = tagNameTracked1;
        ul.appendChild(li);
        alert("Добавлена отслеживаемая метка " + tagNameTracked1);
    }
}

function handleButtonClickIgnoreQuestionAria() {
    const textInputIgnore1 = document.querySelector('#add-tag-ignore1');
    const tagNameIgnore1 = textInputIgnore1.value;
    if(tagNameIgnore1 == ""){
        alert("Введите название метки, пожалуйста");
    }else{
        const il  =  document.querySelector('#listTagIgnore1');
        const li = document.createElement("li");
        li.innerHTML = tagNameIgnore1;
        il.appendChild(li);
        alert("Добавлена отслеживаемая метка " + tagNameIgnore1);
    }
}