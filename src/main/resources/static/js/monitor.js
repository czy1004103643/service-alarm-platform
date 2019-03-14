$(function () {
    var serviceId = getUrlParam("serviceId")
    post("/monitor/getMonitorDataList?serviceId=" + serviceId, function (result) {
        console.log(result)
    }, function (e) {
        console.log(e)
    })
})