$('#sidebar button').on('click', function (e) {
    e.preventDefault()
    $('.active').prop({

        'class': 'btn btn-sm',
    })

    $(this).prop({
        'class': 'btn btn-sm active',
    })
})
