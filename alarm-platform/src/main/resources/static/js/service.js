$(function() {
    
})

$("#save-service").on("click", function() {
    var group = $("#group").val()
    var serviceName = $("#service-name").val()
    var description = $("#description").val()
    if(isEmpty(group)) {
        alert("group is empty!")
        return
    }
    if(isEmpty(serviceName)) {
        alert("service name is empty!")
        return
    }
    var serviceModel = {
        "group": group,
        "serviceName": serviceName,
        "description": description
    }
    console.log(serviceModel);
})