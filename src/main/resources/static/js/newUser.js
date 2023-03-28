$(document).ready(function () {
    $('#submit').click(function () {
        if ($("#check").is(":checked")){
            alert('After registration you will be sent a confirmation email');
        }
    })
});
