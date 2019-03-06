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
        contentType: 'application/json; charset=utf-8',
        success: function (result) {
            successCallback(result);
        },
        error: function (result) {
            failCallback(result);
        }
    })
}

function get(url, successCallback, failCallback) {
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

function del(url, data, successCallback, failCallback) {
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        timeout: 3600000,
        success: function (result) {
            successCallback(result);
        },
        error: function (result) {
            failCallback(result);
        }
    })
}

function formatTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear()
    M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
    D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
    h = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
    m = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
    s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
    return Y + '-' + M + '-' + D + ' ' + h + ':' + m + ':' + s;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function message(delayTime, callback) {
    callback()
    setTimeout(function () {
        window.location.reload()
    }, delayTime);
}