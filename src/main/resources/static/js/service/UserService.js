class UserService {
    getUserById(id) {
        let query = '/api/user/' + id;
        return this.getResponse(query);
    }

    createUser(userRegistrationDto) {
        fetch('/api/user/registration', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userRegistrationDto)
        }).then(function(response) {
            if (!response.ok) {
                let error = new Error();
                error.response = response.text();
                throw error;
            }
            return response.json();
        }).catch(error => error.response.then(message => console.log(message)));
    }

    getResponse(query) {
        let result = new Array();
        fetch(query)
            .then(response =>  {
                if (response.ok) {
                    return response.json()
                } else {
                    let error = new Error();
                    error.response = response.text();
                    throw error;
                }
            })
            .then(entity => result.push(entity))
            .catch( error => error.response.then(message => console.log(message)));
        return result;
    }
}