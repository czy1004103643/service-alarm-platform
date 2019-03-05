$(function () {
    var serviceId = getUrlParam("serviceId")
    initUrlPage(serviceId)
})

$("#add-new-url").on("click", function() {
    $(this).attr("data-id", "")
    // TODO: 置空
})

$("#url-save").on("click", function() {
    var urlJson = getInputValues()
    post("/url/saveUrl", urlJson, function(result) {
        console.log(result)
    }, function(e) {
        console.log(e)
    })
})

$("body").delegate(".url-edit", "click", function () {
    var urlId = $(this).attr("data-id")
    $("#url-save").attr("data-id", urlId)
   console.log(urlId)

})

$("body").delegate(".url-delete", "click", function () {
    var urlId = $(this).attr("data-id")
    $("#delete-yes").attr("data-id", urlId)

})

function initUrlPage(serviceId) {
    console.log(serviceId)
    get("/url/getUrlList?serviceId=" + serviceId, function (result) {
        console.log(result)
        if (result.code == 0) {
            var urlList = result.data
            buildUrlTable(urlList)
        }
    }, function (e) {
        console.log(e)
    })
}

function buildUrlTable(urlList) {
    var html = ''
    if (urlList != null && urlList.length > 0) {
        var size = urlList.length
        for (var index = 0; index < size; index++) {
            var url = urlList[index]
            var urlId = url.urlId
            html += '<tr>' +
                '<td><a target="_blank" href="/rule?urlId=' + urlId + '">' + url.urlContent + '</a></td>' +
                '<td>' + url.requestType + '</td>' +
                '<td>' + url.paramContent + '</td>' +
                '<td>' + url.bodyContent + '</td>' +
                '<td>' + url.description + '</td>' +
                '<td>' + formatTime(url.paramContent) + '</td>' +
                '<td>' +
                '<i class="fas fa-edit text-default url-edit" data-toggle="modal" data-target="#modalContactForm" data-id="' + urlId + '"></i>' +
                '<i class="fas fa-trash-alt text-orange url-delete"  data-id="' + urlId + '"></i>' +
                '</td>' +
                '</tr>'
        }

    }
    $("#url-table tbody").html(html)
}

$("#url-test").on("click", function () {
    var urlJson = getInputValues()
    if(!isEmpty(urlJson)) {
        $("#url-save").show()
    } else {
        alert("略略略")
    }
})

function getInputValues() {
    var serviceId = getUrlParam("serviceId")
    var urlId = $("#url-save").attr("data-id")
    var contentObj = $("#url-content")
    var urlContent = contentObj.val()
    var requestType = contentObj.attr("request-type")
    var bodyContent = $("#body-content").val()
    var paramList = $("#param-list input")
    var paramJSON = new Object()

    if (isEmpty(urlContent)) {
        alert("url is empty!")
        return
    }

    for (var index = 0; index < paramList.length; index += 2) {
        var key = paramList[index].value
        var val = paramList[index + 1].value
        if ((!isEmpty(key) && isEmpty(val)) || (isEmpty(key) && !isEmpty(val))) {
            alert("param value is empty!")
            return;
        } else if (isEmpty(key) && isEmpty(val)) {
            continue
        }
        paramJSON[key] = val
    }

    if (requestType == 'GET') {
        bodyContent = ''
    } else if (requestType == 'POST') {
        paramMap = new Map()
    } else {
        alert("request type not found")
        return
    }
    var paramContent = JSON.stringify(paramJSON)

    console.log(paramContent)
    var urlJson = {
        "serviceId": serviceId,
        "urlId": urlId,
        "urlContent": urlContent,
        "requestType": requestType,
        "bodyContent": bodyContent,
        "paramContent": paramContent
    }
    console.log(urlJson)
    return urlJson
}
// $("#add-new-url").on("tigger", function () {

// })

$("#add-param").on("click", function () {

    var keyValHtml = '<div class="row margin-top-10">' +
        '<div class="col-2">' +
        '<input type="text" class="form-control" placeholder="Param Key">' +
        '</div>' +
        '<div class="col-2">' +
        '<input type="text" class="form-control" placeholder="Param Value">' +
        '</div>' +
        '</div>'
    $("#param-list").append(keyValHtml)

})



$("#url-get").on("click", function () {
    $("#url-btn").text("GET")
    var paramTab = $("#param-tab")
    var paramContent = $("#param")
    paramTab.addClass("active")
    paramTab.addClass("show")
    paramTab.show()
    paramContent.addClass("active")
    paramContent.addClass("show")
    paramContent.show()
    var bodyTab = $("#body-tab")
    var bodyContent = $("#body")
    bodyTab.removeClass("active")
    bodyTab.removeClass("show")
    bodyTab.hide()
    bodyContent.removeClass("active")
    bodyContent.removeClass("show")
    bodyContent.hide()
    $("#url-content").attr("request-type", "GET")
})

$("#url-post").on("click", function () {
    $("#url-btn").text("POST")

    var bodyTab = $("#body-tab")
    var bodyContent = $("#body")
    bodyTab.addClass("active")
    bodyTab.addClass("show")
    bodyTab.show()
    bodyContent.addClass("active")
    bodyContent.addClass("show")
    bodyContent.show()
    var paramTab = $("#param-tab")
    var paramContent = $("#param")
    paramTab.removeClass("active")
    paramTab.removeClass("show")
    paramTab.hide()
    paramContent.removeClass("active")
    paramContent.removeClass("show")
    paramContent.hide()
    $("#url-content").attr("request-type", "POST")

})