$(function() {

});

// user login
$("#login").on("click", function() {
    var userName = $("#userName").val();
    var password = $("#password").val();
    var user = {};
    user.userName = userName;
    user.password = password;
    $.ajax({
        url: "/user/userLogin",
        type: "POST",
        contentType: "application/json",
        dataType: "JSON",
        data: JSON.stringify(user),
        success: function(result) {
            if (result.code == 0) {
                window.location.href = "/index";
            } else {
                console.log(result)
            }
        },
        error: function(e) {
            console.log(e)
        }
    })
})