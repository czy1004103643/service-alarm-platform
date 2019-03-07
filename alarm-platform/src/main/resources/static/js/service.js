$(function () {
    var groupId = getUrlParam("groupId")
    initServiceList(groupId)
    get("/group/getGroupById?groupId=" + groupId, function (result) {
        var group = result.data
        $("#group-name").val(group.groupName)
    }, function () {

    })
})

$("#new-service").on("click", function () {
    $("#group-name").next().addClass("active")
    $("#service-name").val("")
    $("#description").val("")
    $("#service-id").val("")
})

$("body").delegate(".service-edit", "click", function () {
    var serviceId = $(this).attr("data-id")
    get("/service/getServiceById?serviceId=" + serviceId, function (result) {
        console.log(result)
        var code = result.code
        if (code == 0) {
            var service = result.data
            $(".input-title").addClass("active")
            $("#service-id").val(service.serviceId)
            $("#service-name").val(service.serviceName)
            $("#description").val(service.description)
            var alarmWay = service.alarmWay
            if (!isEmpty(alarmWay)) {
                var alarmWayList = alarmWay.split("|")
                for (var index = 0; index < alarmWayList.length; index++) {
                    var wayName = alarmWayList[index].toLowerCase()
                    $("#" + wayName).attr("checked", "checked")
                }
            } else {
                $(".check-box-container input[type=checkbox]").removeAttr("checked")
            }

        }

    }, function (e) {
        console.log(e)
    })
})

$("#save-service").on("click", function () {
    var serviceId = $("#service-id").val()
    var grouId = getUrlParam("groupId")
    var serviceName = $("#service-name").val()
    var description = $("#description").val()
    var alarmWay = ''
    $(".check-box-container").find('input:checkbox').each(function () { //遍历所有复选框
        if ($(this).prop('checked') == true) {
            alarmWay += $(this).val().toUpperCase()
            alarmWay += '|'
        }
    })

    console.log(alarmWay)

    if (isEmpty(serviceName)) {
        alert("service name is empty!")
        return
    }
    var serviceModel = {
        "serviceId": serviceId,
        "groupId": grouId,
        "serviceName": serviceName,
        "alarmWay": alarmWay,
        "description": description
    }
    post("/service/saveServiceModel", serviceModel, function (result) {
        if (result.code == 0) {
            $("#close-edit").click()
            message(2000, function () {
                alert("Save Success")
            })
        } else {
            alert("save error")
        }

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
    console.log(serviceId)
    var dataJson = { "serviceId": serviceId }
    del("/service/deleteServiceById", dataJson, function (result) {
        if (result.code == 0) {
            $("#close").click()
            message(2000, function () {
                alert("Delete Success")
            })
        } else {
            alert("delete error")
        }

    }, function (e) {
        console.log(e)
    })
})

function initServiceList(groupId) {
    get("/service/getServiceModelList?groupId=" + groupId, function (result) {
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
    console.log(serviceList)
    var html = ''
    var wechatHtml = '<i class="fab fa-weixin text-success margin-r-1"></i>'
    var rocketchatHtml = '<i class="fab fa-rocketchat text-danger margin-r-1"></i>'
    var size = serviceList.length
    for (var index = 0; index < size; index++) {
        var serviceModel = serviceList[index]
        var serviceId = serviceModel.serviceId
        var updateTime = serviceModel.updateTime
        var time = formatTime(updateTime)
        var alarmWay = serviceModel.alarmWay
        var alarm = ''
        if (!isEmpty(alarmWay)) {
            var alarmWayList = alarmWay.toLowerCase().split("|")
            for (var i = 0; i < alarmWayList.length; i++) {
                var wayName = alarmWayList[i]
                if (wayName == 'wechat') {
                    alarm += wechatHtml
                }

                if (wayName == 'rocketchat') {
                    alarm += rocketchatHtml
                }
            }
        }
        html += '<tr>' +
            '<td><a class="link-color" target="_blank" href="/url?serviceId=' + serviceId + '">' + serviceModel.serviceName + '</a></td>' +
            '<td>' + alarm + '</td>' +
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
