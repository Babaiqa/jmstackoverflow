
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
        }).catch(error => console.log(error.message));
    }

    getUpVoteAnswer(questionId, answerId, count, isHelpful) {
        fetch('/api/question/'+ questionId +'/answer/'+ answerId+'/upVote',
            {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).then(response => {
            document.getElementById('countValuable').innerHTML = "&nbsp;" + (count + 1);
            document.reload();
        }).catch(error => console.log(error.message));

        if(isHelpful == true) {
            let html = '<svg width="36" height="36">\n' +
                '          <path d="M6 14l8 8L30 6v8L14 30l-8-8v-8z"></path>\n' +
                '       </svg>';
            document.getElementById('isHelpful').innerHTML = html;
        }
    }

    getDownVoteAnswer(questionId, answerId, count) {
         fetch('/api/question/'+ questionId +'/answer/'+ answerId+'/downVote',
            {
                method: 'PATCH',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            }).then(response =>{
             document.getElementById('countValuable').innerHTML ="&nbsp;"+ (count - 1);
         }).catch(error => console.log(error.message));
    }


}