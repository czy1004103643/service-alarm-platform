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
                '<span class="flex-end">' +
                '<a href="/service?groupId=' + group.groupId + '" class="btn btn-sm btn-mdb-color">Go >></a>' +
                '</span>' +
                '</div>' +
                '</div>' +
                '</div>'
        }
        $("#group-list").html(html)
    }
}
