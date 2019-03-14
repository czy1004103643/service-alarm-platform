$(function () {
    var serviceId = getUrlParam("serviceId")
    initUrlPage(serviceId)
    get("/service/getServiceById?serviceId=" + serviceId, function (result) {
        var service = result.data
        $("#service-name").text(service.serviceName)
    }, function (e) {
        console.log(e)
    })
})

$("#add-new-url").on("click", function () {
    $(this).attr("data-id", "")
    $("#modalContactForm input").val("")
    $("#modalContactForm textarea").val("")
    $("#url-save").attr("save-type", "")
})

$("#url-save").on("click", function () {
    var urlJson = getInputValues()
    layer.load(2)
    var saveType = $(this).attr("save-type")
    if (saveType == 'copy') {
        post("/url/copyUrl", urlJson, function (result) {
            if (result.code == 0) {
                window.location.reload()
            } else {
                layer.msg("save url error")
            }
            setTimeout(function () {
                layer.closeAll('loading');
            }, 10)
        }, function (e) {
            console.log(e)
        })
    } else {
        post("/url/saveUrl", urlJson, function (result) {
            if (result.code == 0) {
                window.location.reload()
            } else {
                layer.msg("save url error")
            }
            setTimeout(function () {
                layer.closeAll('loading');
            }, 10)
        }, function (e) {
            console.log(e)
        })
    }

})
$("body").delegate(".url-copy", "click", function () {
    $('#modalContactForm').addClass('show')
    $("add-new-url").attr("aria-expanded", true)
    var urlId = $(this).attr("data-id")
    $("#url-save").attr("data-id", urlId)
    get("/url/getUrlById?urlId=" + urlId, function (result) {
        var url = result.data
        setData(url)
        $("#url-save").attr("save-type", "copy")
    }, function (e) {
        console.log(e)
    })
})

$("body").delegate(".url-edit", "click", function () {
    $("#url-save").attr("save-type", "")
    $('#modalContactForm').addClass('show')
    $("add-new-url").attr("aria-expanded", true)
    var urlId = $(this).attr("data-id")
    $("#url-save").attr("data-id", urlId)
    get("/url/getUrlById?urlId=" + urlId, function (result) {
        var url = result.data
        setData(url)
    }, function (e) {
        console.log(e)
    })
})

function setData(url) {
    $("#url-content").val(url.urlContent)
    $("#description").val(url.description)
    var requestType = url.requestType
    if (requestType == "GET") {
        var paramCoantent = url.paramContent
        if (!isEmpty(paramCoantent)) {
            var paramJson = JSON.parse(paramCoantent)
            buildParamList(paramJson)
        }

    } else if (requestType == "POST") {
        $("#url-post").click()
        $("#body-content").val(url.bodyContent)
    }
}

$("body").delegate(".url-delete", "click", function () {
    var urlId = $(this).attr("data-id")
    $("#delete-yes").attr("data-id", urlId)

})

$("#delete-yes").on("click", function () {
    var urlId = $(this).attr("data-id")
    var dataJson = { "urlId": urlId }
    del("/url/deleteUrlById", dataJson, function (result) {
        if (result.code == 0) {
            $("#close").click()
            message(1000, function () {
                layer.msg("Delete Success")
            })
        } else {
            layer.msg("delete error")
        }

    }, function (e) {
        console.log(e)
    })
})

function buildParamList(paramJson) {
    $("#param-list").html('')
    for (var key in paramJson) {
        var keyValHtml = '<div class="row margin-top-10">' +
            '<div class="col-2">' +
            '<input type="text" class="form-control" placeholder="Param Key" value="' + key + '">' +
            '</div>' +
            '<div class="col-2">' +
            '<input type="text" class="form-control" placeholder="Param Value" value="' + paramJson[key] + '">' +
            '</div>' +
            '</div>'
        $("#param-list").append(keyValHtml)
    }

}

function initUrlPage(serviceId) {
    get("/url/getUrlList?serviceId=" + serviceId, function (result) {
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
            var updateTime = url.updateTime
            var time = formatTime(updateTime)
            html += '<tr>' +
                '<td><a class="link-color" target="_blank" href="/rule?urlId=' + urlId + '">' + url.urlContent + '</a></td>' +
                '<td>' + url.requestType + '</td>' +
                '<td>' + url.paramContent + '</td>' +
                '<td>' + url.bodyContent + '</td>' +
                '<td>' + url.description + '</td>' +
                '<td>' + time + '</td>' +
                '<td>' +
                '<i class="fas fa-edit text-default icon-margin url-edit" data-id="' + urlId + '"></i>' +
                '<i class="fas fa-trash-alt text-orange icon-margin url-delete" data-toggle="modal" data-target="#modalConfirmDelete" data-id="' + urlId + '"></i>' +
                '<i class="far fa-copy text-info icon-margin url-copy" data-id="' + urlId + '"></i>' +
                '</td>' +
                '</tr>'
        }

    }
    $("#url-table tbody").html(html)
}

$("#url-test").on("click", function () {
    var urlJson = getInputValues()
    if (!isEmpty(urlJson)) {
        post("/url/checkUrl", urlJson, function (result) {
            layer.load(2)
            var data = result.data
            if (data.key) {
                $("#url-save").show()
                var response = data.value
                if (!isEmpty(response)) {
                    $("#response-data").JSONView(response)
                } else {
                    layer.msg("response is null, please check url and params")
                }

            } else {
                layer.msg("request error, please check url and params")
            }
            setTimeout(function () {
                layer.closeAll('loading');
            }, 10)
        }, function (e) {
            console.log(e)
        })

    }
})

function getInputValues() {
    var serviceId = getUrlParam("serviceId")
    var urlId = $("#url-save").attr("data-id")
    var contentObj = $("#url-content")
    var urlContent = contentObj.val()
    var requestType = contentObj.attr("request-type")
    var bodyContent = $("#body-content").val()
    var description = $("#description").val()
    var paramList = $("#param-list input")
    var paramJSON = new Object()

    if (isEmpty(urlContent)) {
        layer.msg("url is empty!")
        return
    }

    for (var index = 0; index < paramList.length; index += 2) {
        var key = paramList[index].value
        var val = paramList[index + 1].value
        if ((!isEmpty(key) && isEmpty(val)) || (isEmpty(key) && !isEmpty(val))) {
            layer.msg("param value is empty!")
            return
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
        layer.msg("request type not found")
        return
    }
    if (isEmpty(description)) {
        layer.msg("description is empty")
        return
    }
    var paramContent = JSON.stringify(paramJSON)

    var urlJson = {
        "serviceId": serviceId,
        "urlId": urlId,
        "urlContent": urlContent,
        "requestType": requestType,
        "bodyContent": bodyContent,
        "paramContent": paramContent,
        "description": description
    }
    return urlJson
}

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