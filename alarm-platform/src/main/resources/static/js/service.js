$(function () {
    initServiceList()
})

$("body").delegate(".service-edit", "click", function () {
    var serviceId = $(this).attr("data-id")
    console.log(serviceId)
    get("/service/getServiceById?serviceId=" + serviceId, function (result) {
        console.log(result)
        var code = result.code
        if (code == 0) {
            var data = result.data
            $("#service-info-body label").addClass("active")
            $("#service-id").val(data.serviceId)
            $("#service-group").val(data.serviceGroup)
            $("#service-name").val(data.serviceName)
            $("#description").val(data.description)
        }

    }, function (e) {
        console.log(e)
    })
})

$("#save-service").on("click", function () {
    var serviceId = $("#service-id").val()
    var serviceGroup = $("#service-group").val()
    var serviceName = $("#service-name").val()
    var description = $("#description").val()
    if (isEmpty(serviceGroup)) {
        alert("group is empty!")
        return
    }
    if (isEmpty(serviceName)) {
        alert("service name is empty!")
        return
    }
    var serviceModel = {
        "serviceId": serviceId,
        "serviceGroup": serviceGroup,
        "serviceName": serviceName,
        "description": description
    }
    console.log(serviceModel);
    post("/service/saveServiceModel", serviceModel, function (result) {
        console.log(result)
    }, function (e) {
        console.log(e)
    })
})

$("body").delegate(".service-delete", "click", function () {
    var serviceId = $(this).attr("data-id")
    $("#delete-yes").attr("data-id", serviceId)

})

$("#delete-yes").on("click", function () {
    var serviceId = $(this).attr("data-id")
    post("/service/deleteServiceById", serviceId, function (result) {
        console.log(result)
    }, function (e) {
        console.log(e)
    })
})

function initServiceList() {
    get("/service/getServiceModelList", function (result) {
        var code = result.code;
        if (code == 0) {
            var serviceList = result.data
            buildServiceTable(serviceList)
        }
    }, function (e) {
        console.log(e)
    })
}


function buildServiceTable(serviceList) {
    var html = ''

    for (var index = 0; index < serviceList.length; index++) {
        var serviceModel = serviceList[index];
        var serviceId = serviceModel.serviceId
        var updateTime = serviceModel.updateTime
        var time = formatTime(updateTime)
        html += '<tr>' +
            '<td>' + serviceModel.serviceGroup + '</td>' +
            '<td>' + serviceModel.serviceName + '</td>' +
            '<td>' +
            '<i class="fab fa-weixin text-success"></i>' +
            '</td>' +
            '<td>' + serviceModel.description + '</td>' +
            '<td>' + time + '</td>' +
            '<td>' +
            '<i class="fas fa-edit text-default service-edit" data-toggle="modal" data-target="#modalContactForm" data-id="' + serviceId + '"></i>' +
            '<i class="fas fa-trash-alt text-orange service-delete" data-toggle="modal" data-target="#modalConfirmDelete" data-id="' + serviceId + '"></i>' +
            '</td>' +
            '</tr>'
    }
    $("#service-table tbody").html(html)
}
123
