class AnswerService {

    getAnswerListByQuestionId(questionId) {
        let query = '/api/question/' + questionId + '/answer';
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response => {
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch(error => console.log(error.message));
    }


    getUpVoteAnswer(questionId, answerId, index) {
        fetch('/api/question/' + questionId + '/answer/' + answerId + '/upVote',
            {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).then(data => {
            let count = 0;
            let isHelpful = false;
            this.getAnswerListByQuestionId(questionId).then(response => {
                response.forEach(elem => {
                    if (elem.id === answerId) {
                        count = elem.countValuable;
                        isHelpful = elem.isHelpful;
                    }
                })
            }).then(function () {
                switch (count) {
                    case null:
                        count=0;
                        break;
                }
                document.querySelectorAll('div.countAnswer')[index].innerHTML = '&nbsp;' + count;

                let html = '<path d="M6 14l8 8L30 6v8L14 30l-8-8v-8z"></path>\n';
                if (isHelpful === true) {
                    document.querySelectorAll('svg.isHelpful')[index].innerHTML = html;
                }
            })
        }).catch(error => console.log(error.message));
    }


    getDownVoteAnswer(questionId, answerId, index) {
        fetch('/api/question/' + questionId + '/answer/' + answerId + '/downVote',
            {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).then(data => {
            let count = 0;
            this.getAnswerListByQuestionId(questionId).then(response => {
                response.forEach(elem => {
                    if (elem.id === answerId) {
                        count = elem.countValuable;
                    }
                })
            }).then(function () {
                switch (count) {
                    case null:
                        count=0;
                        break;
                }
                document.querySelectorAll('div.countAnswer')[index].innerHTML = '&nbsp;'+count

            })
        }).catch(error => console.log(error.message));
    }

}