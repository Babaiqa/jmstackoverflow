class AnswerService {

    getAnswerListByQuestionId(questionId) {
        let query = '/api/question/'+questionId+'/answer';
        return fetch(query, {
            method: 'GET',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': $.cookie("token")
            })
        }).then(response =>{
            if (response.ok) {
                return response.json()
            } else {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
        }).catch(error => console.log(error.message))
    }

    getUpVoteAnswer(questionId, answerId) {
        fetch('/api/question/'+ questionId +'/answer/'+ answerId+'/upVote',
            {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).catch(error => console.log(error.message));
    }

    getDownVoteAnswer(questionId, answerId) {
         fetch('/api/question/'+ questionId +'/answer/'+ answerId+'/downVote',
            {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).catch(error => console.log(error.message));
    }
}