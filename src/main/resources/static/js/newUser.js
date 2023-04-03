$(document).ready(function () {
    $('#submit').click(function () {
        if ($('#check').is(":checked") && $('#role').length === 0) {
            alert('After registration you will be sent a confirmation email');
        }
    })
});