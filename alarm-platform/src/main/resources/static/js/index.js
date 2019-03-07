$(function () {
    initGroupPage()
})

function initGroupPage() {
    get("/group/getGroupList", function (result) {
        var groupList = result.data
        console.log(groupList)
        var html = ''
        for (var index = 0; index < groupList.length; index++) {
            var group = groupList[index]
            html += '<li class="list-group-item">' +
                '<a href="/service?groupId=' + group.groupId + '" target="_blank">' + group.groupName + '</a>' +
                '</li>'
        }
        $("#group-list").html(html)
    }, function (e) {
        console.log(e)
    })
}
