$(function () {

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

$("#url-get").on("click", function() {
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
    
})

$("#url-post").on("click", function() {
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
})