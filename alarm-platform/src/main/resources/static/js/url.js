$(function () {
    
    
})

$("#url-test").on("click", function () {
    var contentObj = $("#url-content")
    var urlContent = contentObj.val()
    var requestType = contentObj.attr("request-type")
    var bodyContent = $("#body-content").val()
    var paramList = $("#param-list input")
    var paramMap = new Map()

    if(isEmpty(urlContent)) {
        alert("url is empty!")
        return
    }

    for (var index = 0; index < paramList.length; index += 2) {
        var key = paramList[index].value
        var val = paramList[index + 1].value
        if ((!isEmpty(key) && isEmpty(val)) || (isEmpty(key) && !isEmpty(val)))  {
            alert("param value is empty!")
            return;
        } else if (isEmpty(key) && isEmpty(val)) {
            continue
        }
        paramMap.set(key, val)
    }
    console.log(paramMap)

    if(requestType == 1) {
        bodyContent = ''
    } else if(requestType == 2) {
        paramMap = new Map()
    } else {
        alert("request type not found")
        return
    }
    var urlJson = {
        "urlContent": urlContent,
        "requestType": requestType,
        "bodyContent": bodyContent,
        "paramContent": paramMap
    }
    console.log(urlJson)

})

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
    $("#url-content").attr("request-type", 1)
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
    $("#url-content").attr("request-type", 2)

})