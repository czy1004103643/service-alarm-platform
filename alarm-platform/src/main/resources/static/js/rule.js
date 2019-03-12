$(function () {
    //$("#response-data").JSONView(jsonStr)
    var urlId = getUrlParam("urlId")
    initRulePage(urlId)
})

function initRulePage(urlId) {
    get("/rule/getRuleList?urlId=" + urlId, function (result) {

        if (result.code == 0) {
            buildRuleTable(result.data)
        }
    }, function (e) {
        console.log(e)
    })
    get("/url/getUrlById?urlId=" + urlId, function (result) {
        $("#current-url").text(result.data.urlContent)
    }, function (e) {
        console.log(e)
    })
}


function buildRuleTable(ruleList) {
    if (ruleList != null && ruleList.length > 0) {
        var html = ''
        var size = ruleList.length
        for (var index = 0; index < size; index++) {
            var rule = ruleList[index]
            var ruleId = rule.ruleId
            var updateTime = rule.updateTime
            var time = formatTime(updateTime)
            html += '<tr>' +
                '<td>' + rule.ruleAlias + '</td>' +
                '<td>' + rule.formula + '</td>' +
                '<td>' + rule.description + '</td>' +
                '<td>' + time + '</td>' +
                '<td>' +
                '<i class="fas fa-edit text-default rule-edit" data-toggle="collapse" data-target="#modalContactForm" data-id="' + ruleId + '"></i>' +
                '<i class="fas fa-trash-alt text-orange rule-delete" data-toggle="modal" data-target="#modalConfirmDelete" data-id="' + ruleId + '"></i>' +
                '</td>' +
                '</tr>'
        }
        $("#rule-table tbody").html(html)
    }
}


$("#request-url").on("click", function () {
    var urlId = getUrlParam("urlId")
    get("/rule/requestUrl?urlId=" + urlId, function (result) {
        var data = result.data
        if (data.key) {
            $("#response-data").JSONView(data.value);
        } else {
            layer.msg("request error, please check url and params")
        }
    }, function (e) {
        console.log(e)
    })
})

$("body").delegate(".rule-edit", "click", function () {
    var ruleId = $(this).attr("data-id")
    $("#rule-save").attr("data-id", ruleId)
    get("/rule/getRuleById?ruleId=" + ruleId, function (result) {
        var rule = result.data
        $("#modalContactForm label").addClass("active")
        $("#rule-alias").val(rule.ruleAlias)
        $("#formula").val(rule.formula)
        $("#description").val(rule.description)
    }, function (e) {

    })
})

$("body").delegate(".rule-delete", "click", function () {
    var ruleId = $(this).attr("data-id")
    $("#delete-yes").attr("data-id", ruleId)

})

$("#delete-yes").on("click", function () {
    var ruleId = $(this).attr("data-id")
    var dataJson = { "ruleId": ruleId }
    del("/rule/deleteRuleById", dataJson, function (result) {
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

$("#add-new-rule").on("click", function () {
    $("#modalContactForm label").removeClass("active")
    $("#modalContactForm input").val("")
    $("#response-data").text("")
})

$("#rule-save").on("click", function () {
    var urlId = getUrlParam("urlId")
    var ruleId = $(this).attr("data-id")
    var ruleAlias = $("#rule-alias").val()
    var formula = $("#formula").val()
    var description = $("#description").val()

    if (isEmpty(ruleAlias)) {
        layer.msg("rule alias is empty!")
        return
    }

    if (isEmpty(formula)) {
        layer.msg("formula is empty!")
        return
    }

    var ruleJson = {
        "urlId": urlId,
        "ruleId": ruleId,
        "ruleAlias": ruleAlias,
        "formula": formula,
        "description": description
    }
    console.log(ruleJson)
    post("/rule/checkRule", ruleJson, function (result) {
        if (result.code == 0) {
            post("/rule/saveRule", ruleJson, function (result2) {
                if (result2.code == 0) {
                    window.location.reload()
                } else {
                    layer.msg("save rule error")
                }
            }, function (e) {
                console.log(e)
            })
        } else {
            layer.msg("formula verification failed, please check")
        }
    }, function (e) {

    })

})

$("#formula-tip").on("click", function() {
    $("#formula-tip-content").show()
})

var keyStr = ''
var keyType = ''
var keyValue
$("body").delegate(".prop", "click", function () {

    var currentDom = $(this)
    var keyType = currentDom.next().attr("class")
    if (isEmpty(keyType)) {
        layer.msg("bad choose!")
        return
    }
    keyValue = currentDom.next().text()
    var currentKey = currentDom.text().replace(new RegExp('"', 'g'), '')
    keyStr = currentKey
    getHierarchy(currentDom)
    keyStr = "$." + keyStr
    var lastIndex = keyStr.lastIndexOf("\.")
    var tempPrefix = keyStr.substring(0, lastIndex)
    var tempLastField = keyStr.substring(lastIndex + 1, keyStr.length)
    keyStr = tempPrefix + '[?(@.' + tempLastField + ' > ' + keyValue + ')]'
    $("#formula").val(keyStr)
    $("#formula").next().addClass("active")
})

function getHierarchy(currentDom) {
    var parentDom = currentDom.parent()
    var classes = parentDom.attr("class")
    if (!isEmpty(classes)) {
        if (classes == "jsonview") {
            return
        } else {
            if (classes.indexOf("collapsible") > 0) {
                parentDom = parentDom.prev()
                classes = parentDom.attr("class")
                if (classes == "prop") {
                    var currentKey = parentDom.text().replace(new RegExp('"', 'g'), '')
                    keyStr = currentKey + "." + keyStr
                }
            }
        }
    }
    getHierarchy(parentDom)
}



const jsonStr = '{"_shards":{"total":80,"failed":0,"successful":80,"skipped":0},"hits":{"hits":[],"total":14516204,"max_score":0},"took":424,"timed_out":false,"aggregations":{"result":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"doc_count":725556,"key":"2019022000"},{"doc_count":776835,"key":"2019022001"},{"doc_count":793158,"key":"2019022002"}]}}}';