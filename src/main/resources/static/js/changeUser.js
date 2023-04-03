$(document).ready(function () {
    $('#email').change(function () {
        if ($('#name').val() !== 'admin') {
            alert('You will be sent a confirmation email');
        }
    });
});