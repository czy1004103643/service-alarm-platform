$(function () {
    initGroupPage()
})


function initGroupPage() {
    get("/group/getGroupList", function (result) {
        var groupList = result.data
        console.log(groupList)
        buildPage(groupList)
        // var html = ''
        // for (var index = 0; index < groupList.length; index++) {
        //     var group = groupList[index]
        //     html += '<li class="list-group-item">' +
        //         '<a href="/service?groupId=' + group.groupId + '" target="_blank">' + group.groupName + '</a>' +
        //         '</li>'
        // }
        // $("#group-list").html(html)
    }, function (e) {
        console.log(e)
    })
}

function buildPage(groupList) {
    var html = ''
    if (groupList != null) {
        for (var index = 0; index < groupList.length; index++) {
            var imageIndex = index % 10
            var group = groupList[index]
            var time = formatTime(group.updateTime)
            html += '<div class="col-lg-3 col-sm-6 margin-bottom-2">' +
                '<div class="card">' +
                '<div class="view overlay">' +
                '<img class="card-img-top" src="/static/lib/img/' + imageIndex + '.jpg" alt="">' +
                '<a href="#!">' +
                '<div class="mask rgba-white-slight"></div>' +
                '</a>' +
                '</div>' +
                '<div class="card-body">' +
                '<h4 class="card-title">' + group.groupName + '</h4>' +
                '<p class="card-text">' + group.description + '<br/>' + time + '</p>' +
                '<span class="flex-between">' +
                '<span>' +
                '<i class="fas fa-edit text-default cursor-pointer group-edit" data-id="' + group.groupId + '" data-toggle="modal" data-target="#modalContactForm"></i>' +
                '<i class="fas fa-trash-alt text-orange cursor-pointer group-delete" data-id="' + group.groupId + '" data-toggle="modal" data-target="#modalConfirmDelete"></i>' +
                '</span>' +
                '<a href="/service?groupId=' + group.groupId + '" class="btn btn-sm btn-mdb-color">Go >></a>' +
                '</span>' +
                '</div>' +
                '</div>' +
                '</div>'
        }
        $("#group-list").html(html)
    }
}

$("body").delegate(".group-edit", "click", function () {
    var groupId = $(this).attr("data-id")
    get("/group/getGroupById?groupId=" + groupId, function (result) {
        var code = result.code
        if (code == 0) {
            var group = result.data
            $(".input-title").addClass("active")
            $("#group-id").val(group.groupId)
            $("#group-name").val(group.groupName)
            $("#description").val(group.description)
        }
    }, function (e) {
        console.log(e)
    })
})

$("#save-group").on("click", function () {
    var groupId = $("#group-id").val()
    var groupName = $("#group-name").val()
    var description = $("#description").val()

    if (isEmpty(groupName)) {
        layer.msg('group name is empty!')
        return
    }
    var serviceModel = {
        "groupId": groupId,
        "groupName": groupName,
        "description": description
    }
    post("/group/saveGroup", serviceModel, function (result) {
        if (result.code == 0) {
            $("#close-edit").click()
            message(1000, function () {
                layer.msg('save success')
            })
        } else {
            layer.msg('save error', function(){})
        }

    }, function (e) {
        console.log(e)
    })
})

$("body").delegate(".group-delete", "click", function () {
    var groupId = $(this).attr("data-id")
    $("#delete-yes").attr("data-id", groupId)
})

$("#delete-yes").on("click", function () {
    var groupId = $(this).attr("data-id")
    var dataJson = { "groupId": groupId }
    del("/group/deleteGroupById", dataJson, function (result) {
        if (result.code == 0) {
            $("#close").click()
            message(1000, function () {
                layer.msg("delete success")
            })
        } else {
            layer.msg("delete error")
        }

    }, function (e) {
        console.log(e)
    })
})
