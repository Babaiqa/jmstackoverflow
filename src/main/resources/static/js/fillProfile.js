

$(document).ready(function () {
    fillProfile();
    
});

function fillProfile() {

    fetch('/api/auth/principal', {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    })
        .then(response => (response.json()))
        .then(data => {

            $('.avatar').append(
                "<img src=\"" + data['avatarUrl'] + "\" alt=\"\" width=\"164\" height=\"164\" class=\"avatar-user\">"
            )
            $('#name').append(
                data['fullName']
            )
            $('#city').append(
                "<div class=\"grid--cell fl1\">" + "Город: " + data['city'] + "</div>"
            )
            fetch('/api/user/' + data['id'], {
                method: 'GET',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Authorization': $.cookie("token")
                })
            })
                .then(response => response.json())
                .then(data1 => {
                    $('.reputation-number').append(
                        data1['reputation']
                    )
                })
        })

}





