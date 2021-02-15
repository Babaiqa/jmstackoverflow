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
        }).catch(error => console.log(error.message))
    }


    getUpVoteByAnswer(questionId) {
        return fetch("/api/question/" + questionId + "/answer/" + 1 + "/upVote",
            {
                method: 'GET',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch(error => console.log(error.message));
    }


    getDownVoteByAnswer(questionId, answerId) {
        return fetch("/api/question/" + questionId + "/answer/" + answerId + "/downVote",
            {
                method: 'GET',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch(error => console.log(error.message));
    }
}