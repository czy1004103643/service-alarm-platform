$(function () {
    initServiceList()
})

function initServiceList() {
    get("/service/getServiceModelList", function (result) {
        console.log(result)
        var code = result.code;
        if (code == 0) {
            var serviceList = result.data
            buildServiceTable(serviceList)
        }
    }, function (result) {
        console.log(result)
    })
}

function buildServiceTable(serviceList) {
    var html;
    for (var service in serviceList) {
        console.log(service)
        var serviceId = service.serviceId
        html += '<tr>' +
            '<td>' + service.serviceGroup + '</td>' +
            '<td>' + service.serviceName + '</td>' +
            '<td>' +
            '<i class="fab fa-weixin text-success"></i>' +
            '</td>' +
            '<td>' + service.description + '</td>' +
            '<td>' + service.updateTime + '</td>' +
            '<td>' +
            '<i class="fas fa-edit text-default service-edit" data-toggle="modal" data-target="#modalContactForm" data-id="' + serviceId + '"></i>' +
            '<i class="fas fa-trash-alt text-orange service-delete" data-toggle="modal" data-target="#modalConfirmDelete" data-id="' + serviceId + '"></i>' +
            '</td>' +
            '</tr>'
    }
    $("#service-table tbody").html(html)
}

$("#save-service").on("click", function () {
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
        "serviceGroup": serviceGroup,
        "serviceName": serviceName,
        "description": description
    }
    console.log(serviceModel);
    post("/service/addServiceModel", serviceModel, function (result) {
        console.log(result)
    }, function (result) {
        console.log(result)
    });

})