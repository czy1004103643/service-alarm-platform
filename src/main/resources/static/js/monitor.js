$(function() {
    var serviceId = getUrlParam("serviceId")
    get("/monitor/getMonitorDataList?serviceId=" + serviceId, function(result) {
        console.log(result)
        if (result.code == 0) {
            var monitorDataList = result.data
            buildUrlTable(monitorDataList)
            $('#monitor-data-table').DataTable()
            $('.dataTables_length').addClass('bs-select')
        }

    }, function(e) {
        console.log(e)
    })

    get("/service/getServiceById?serviceId=" + serviceId, function(result) {
        var service = result.data
        $("#service-name").text(service.serviceName)
    }, function(e) {
        console.log(e)
    })
})


function buildUrlTable(monitorDataList) {
    var html = ''
    if (monitorDataList != null && monitorDataList.length > 0) {
        var size = monitorDataList.length
        for (var index = 0; index < size; index++) {
            var monitorData = monitorDataList[index]
            var updateTime = monitorData.updateTime
            var time = formatTime(updateTime)
            html += '<tr>' +
                '<td></td>' +
                '<td>' + monitorData.urlDescription + '</td>' +
                '<td>' + monitorData.ruleAlias + '</td>' +
                '<td>' + monitorData.formula + '</td>' +
                '<td>' + monitorData.dataContent + '</td>' +
                '<td>' + monitorData.ruleDescription + '</td>' +
                '<td>' + time + '</td>' +
                '</tr>'
        }

    }
    $("#monitor-data-table tbody").html(html)
}