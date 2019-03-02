// ajax contentType: "application/x-www-form-urlencoded;charset=UTF-8"

function isEmpty(value) {
    return value == null || value == "" || value == 'undefined'
}

function post(url, data, successCallback, failCallback) {

    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(data),
        timeout: 3600000,
        dataType: 'json',
        contentType: 'application/json',
        success: function (result) {
            successCallback(result);
        },
        error: function (result) {
            failCallback(result);
        }
    })
}

function get(url,successCallback, failCallback) {
    $.ajax({
        url: url,
        type: "GET",
        timeout: 3600000,
        success: function (result) {
            successCallback(result);
        },
        error: function (result) {
            failCallback(result);
        }
    })
}