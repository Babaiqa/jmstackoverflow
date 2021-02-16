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


    getUpVoteAnswer(questionId, answerId) {
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
                    if (elem.id == answerId) {
                        console.log(elem);
                        count = elem.countValuable;
                        isHelpful = elem.isHelpful;
                    }
                })
            }).then(response => {
                document.getElementById('countValuable').innerHTML ="&nbsp;" + count;
                if (isHelpful == true) {
                    let html = '<path d="M6 14l8 8L30 6v8L14 30l-8-8v-8z"></path>\n';
                    document.getElementById('isHelpful').innerHTML = html;
                }
            })
        }).catch(error => console.log(error.message));
    }


    getDownVoteAnswer(questionId, answerId) {
        fetch('/api/question/' + questionId + '/answer/' + answerId + '/downVote',
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
                    if (elem.id == answerId) {
                        console.log(elem);
                        count = elem.countValuable;
                        isHelpful = elem.isHelpful;
                    }
                })
            }).then(response => {
                document.getElementById('countValuable').innerHTML ="&nbsp;" + count;
                if (isHelpful == false) {
                    let html = '';
                    document.getElementById('isHelpful').innerHTML = html;
                }
            })
        }).catch(error => console.log(error.message));
    }

}