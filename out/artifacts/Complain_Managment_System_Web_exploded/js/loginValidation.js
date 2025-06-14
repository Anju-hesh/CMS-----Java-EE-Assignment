$(document).ready(function(){
    $("#loginForm").on("submit", function(e){

        $("#usernameError").text('');
        $("#passwordError").text('');
        $("#serverError").text('');
        $("#username").removeClass("invalid");
        $("#password").removeClass("invalid");

        let isValid = true;

        let username = $("#username").val();
        if (!/^[A-Za-z0-9_]{3,20}$/.test(username)) {
            $("#usernameError").text('Invalid User Name');
            $("#username").addClass("invalid");
            isValid = false;
        }

        let password = $("#password").val();
        if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d ]{6,}$/.test(password)) {
            $("#passwordError").html('Invalid Password');
            $("#password").addClass("invalid");
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault();
        }
    });
});
