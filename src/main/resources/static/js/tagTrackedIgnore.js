let requestURLTagTracked= 'http://localhost:5557/api/tag/tracked';
let requestURLTagIgnore= 'http://localhost:5557/api/tag/ignored';
let tokenCookie = document.cookie.replace("token=", "")

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

sendRequestTagTracked('GET', requestURLTagTracked)
    .then(response=> {
        document.getElementById('listTagTracked').innerHTML=""
        response.forEach(elem => {
            document.getElementById('listTagTracked').innerHTML +=
                "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                "            <br> "
        })

        // <a href="/questions/tagged/python" class="post-tag"  rel="tag">python</a>
        // <span class="item-multiplier">
        //     <span class="item-multiplier-x">×</span>
        //     &nbsp;
        //     <span class="item-multiplier-count">148</span>
        // </span>
        // <br>

    })
    .catch(err => console.log(err))


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

sendRequestTagIgnore('GET', requestURLTagIgnore)
    .then(response=> {
        document.getElementById('listTagIgnore').innerHTML=""
        response.forEach(elem => {
            document.getElementById('listTagIgnore').innerHTML +=
                "            <div href=\"#\" class=\"mb-1\">" + elem.name + "</div>\n" +
                "            <br> "
        })
   })
    .catch(err => console.log(err))


const button1 = document.querySelector('#buttonTagTracked')
button1.onclick = handleButtonClickTracked;

const button2 = document.querySelector('#buttonTagIgnore')
button2.onclick = handleButtonClickIgnore;

function handleButtonClickTracked() {
    const textInputTracked = document.querySelector('#add-tag-tracked');
    const tagNameTracked = textInputTracked.value;
    if(tagNameTracked == ""){
        alert("Введите название метки, пожалуйста");
    }else{
        const ul =  document.querySelector('#listTagTracked');
        const li = document.createElement("li");
        li.innerHTML = tagNameTracked;
        ul.appendChild(li);
        alert("Добавлена отслеживаемая метка " + tagNameTracked);
    }
}

function handleButtonClickIgnore() {
    const textInputIgnore = document.querySelector('#add-tag-ignore');
    const tagNameIgnore = textInputIgnore.value;
    if(tagNameIgnore == ""){
        alert("Введите название метки, пожалуйста");
    }else{
        const il  =  document.querySelector('#listTagIgnore');
        const li = document.createElement("li");
        li.innerHTML = tagNameIgnore;
        il.appendChild(li);
        alert("Добавлена отслеживаемая метка " + tagNameIgnore);
    }
}
