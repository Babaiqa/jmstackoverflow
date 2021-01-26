const formRegistration = document.getElementById('registration-page')
const registrationInfo = document.getElementById('registration-error')
let emailRegistration = document.getElementById('email')
let fullnameRegistration = document.getElementById('fullname')
let passwordRegistration = document.getElementById('password')

formRegistration.addEventListener('submit', (event) => {

    event.preventDefault()
    const data = {
        fullName: fullnameRegistration.value.toString(),
        email: emailRegistration.value.toString(),
        password: passwordRegistration.value.toString()
    }

    fetch('http://localhost:5557/api/auth/reg/registration', {
        method: 'post',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        }
    }).then(promise => {
            if (promise.status >= 200 && promise.status < 300) {
                emailRegistration.value = ''
                fullnameRegistration.value = ''
                passwordRegistration.value = ''
                registrationInfo.innerHTML = ''
                registrationInfo.innerHTML = '<div class="alert alert-success " role="alert">' +
                    'Регистрация прошла успешно! Cсылка для подтверждения регистрации отправлена на ваш email</div>'
                return promise.json()

            } else {
                emailRegistration.value = ''
                fullnameRegistration.value = ''
                passwordRegistration.value = ''
                registrationInfo.innerHTML = ''
                registrationInfo.innerHTML = '<div class="alert alert-danger" role="alert">' +
                    'Пользователь с таким email уже существует</div>'
            }
        }
    )})





