$(function () {
    var groupId = getUrlParam("groupId")
    initServiceList(groupId)
    get("/group/getGroupById?groupId=" + groupId, function (result) {
        var group = result.data
        $("#group-name").val(group.groupName)
    }, function () {

    })

    get("/service/getWechatAppList", function (result) {
        var appNameList = result.data
        var html = ''
        if (appNameList != null && appNameList.length > 0) {
            for (var index = 0; index < appNameList.length; index++) {
                var appName = appNameList[index]
                html += '<div class="custom-control custom-checkbox">' +
                    '<input type="checkbox" class="custom-control-input" data-id="' + appName + '" id="' + appName + '" value="' + appName + '">' +
                    '<label class="custom-control-label cursor-pointer" for="' + appName + '">' + appName + '</label>' +
                    '</div>'
            }
            $("#wechat-app-list").html(html)
        }
    }, function (e) {

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
    $(".input-title").addClass("active")
    get("/service/getServiceById?serviceId=" + serviceId, function (result) {
        console.log(result)
        var code = result.code
        if (code == 0) {
            var service = result.data
            $("#service-id").val(service.serviceId)
            $("#service-name").val(service.serviceName)
            $("#description").val(service.description)
            var alarmWay = service.alarmWay
            $("body .check-box-container input[type=checkbox]").removeAttr("checked")
            if (!isEmpty(alarmWay)) {
                var alarmWayList = alarmWay.split("|")
                for (var index = 0; index < alarmWayList.length; index++) {
                    var wayName = alarmWayList[index].toLowerCase()
                    if (isEmpty(wayName)) {
                        continue
                    }
                    $("#" + wayName).attr("checked", "checked")
                    if (wayName == 'wechat') {
                        var wechatAppName = service.wechatAppName
                        $("body .wechat-app-container input[type=checkbox]").removeAttr("checked")
                        if (!isEmpty(wechatAppName)) {
                            var wechatAppNameList = wechatAppName.split("|")
                            for (var i = 0; i < wechatAppNameList.length; i++) {
                                var app = wechatAppNameList[i]
                                if (isEmpty(app)) {
                                    continue
                                }
                                $("#" + app).attr("checked", "checked")
                            }
                            $(".wechat-app-container").show()
                        }
                    }
                }
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
    var wechatAppName = ''
    $(".check-box-container").find('input:checkbox').each(function () { //遍历所有复选框
        if ($(this).prop('checked') == true) {
            var oneWay = $(this).val().toUpperCase()
            alarmWay += oneWay
            alarmWay += '|'
            if (oneWay == 'WECHAT') {
                $(".wechat-app-container").find('input:checkbox').each(function () { //遍历所有复选框
                    if ($(this).prop('checked') == true) {
                        wechatAppName += $(this).val()
                        wechatAppName += '|'
                    }
                })
            }
        }
    })

    if (isEmpty(serviceName)) {
        layer.msg('service name is empty!')
        return
    }
    var serviceModel = {
        "serviceId": serviceId,
        "groupId": grouId,
        "serviceName": serviceName,
        "alarmWay": alarmWay,
        "wechatAppName": wechatAppName,
        "description": description
    }
    post("/service/saveServiceModel", serviceModel, function (result) {
        if (result.code == 0) {
            $("#close-edit").click()
            message(1000, function () {
                layer.msg('Save Success')
            })
        } else {
            layer.msg('save error', function(){})
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
    var dataJson = { "serviceId": serviceId }
    del("/service/deleteServiceById", dataJson, function (result) {
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
            '<i class="fas fa-edit text-default icon-margin service-edit" data-toggle="modal" data-target="#modalContactForm" data-id="' + serviceId + '"></i>' +
            '<i class="fas fa-trash-alt text-orange icon-margin service-delete" data-toggle="modal" data-target="#modalConfirmDelete" data-id="' + serviceId + '"></i>' +
            '</td>' +
            '</tr>'
    }
    $("#service-table tbody").html(html)
}

$("input[type=checkbox]").on("click", function () {
    if ($(this).attr('checked') == 'checked') {
        $(this).removeAttr('checked')
        var id = $(this).attr("id")
        if (id == 'wechat') {
            $(".wechat-app-container").hide()
        }
    } else {
        $(this).attr('checked', 'checked')
        var id = $(this).attr("id")
        if (id == 'wechat') {
            $(".wechat-app-container").show()
        }
    }
})

$("body").delegate(".wechat-app-container input[type=checkbox]", "click", function () {
    if ($(this).attr('checked') == 'checked') {
        $(this).removeAttr('checked')
    } else {
        $(this).attr('checked', 'checked')
    }
})