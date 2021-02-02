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
        searchList.style.top = (coordsInput.bottom - coordsSearchList.bottom+2)+"px"
        searchList.style.left = (coordsInput.left - coordsSearchList.left+91)+"px"

        //Закрытие выпадающего окна с тегами, при клике на область за пределами окна
        jQuery(function($){
            $(document).mouseup(function (e){ // событие клика по веб-документу
                const div = $("#search-list-" + tagType + "-tag-" + pageName); // тут указываем ID элемента
                const addButton = $("#button-add-" + tagType + "-tag-" + pageName); // тут указываем ID элемента
                if (!div.is(e.target) // если клик был не по нашему блоку
                    && div.has(e.target).length === 0
                    && (!addButton.is(e.target))) { // и не по его дочерним элементам
                    div.hide(); // скрываем его
                    document.getElementById("search-list-" + tagType + "-tag-" + pageName).innerHTML=""
                    input.value=""
                }
            });
        });
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

