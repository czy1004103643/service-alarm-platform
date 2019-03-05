$(function () {
    $("#response-data").JSONView(jsonStr);
    var urlId = getUrlParam("urlId")
    initRulePage(urlId)
})

function initRulePage(urlId) {
    get("/rule/getRuleList?urlId=" + urlId, function (result) {
        
        if(result.code == 0) {
            buildRuleTable(result.data)
        }
    }, function (e) {
        console.log(e)
    })
}

function buildRuleTable(ruleList) {
    console.log(ruleList)
    if (ruleList != null && ruleList.length > 0) {
        var html = ''
        var size = ruleList.length
        for (var index = 0; index < size; index++) {
            var rule = ruleList[index]
            var ruleId = rule.ruleId
            html += '<tr>' +
                '<td>' + rule.ruleAlias + '</td>' +
                '<td>' + rule.formula + '</td>' +
                '<td>' + rule.description + '</td>' +
                '<td>' + formatTime(rule.updateTime) + '</td>' +
                '<td>' +
                '<i class="fas fa-edit text-default" data-toggle="modal" data-target="#modalContactForm" data-id="' + ruleId + '"></i>' +
                '<i class="fas fa-trash-alt text-orange" data-id="' + ruleId + '"></i>' +
                '</td>' +
                '</tr>'
        }
        $("#rule-table tbody").html(html)
    }
}

$("#rule-save").on("click", function () {
    var urlId = getUrlParam("urlId")
    var ruleId = $(this).attr("data-id")
    var ruleAlias = $("#rule-alias").val()
    var formula = $("#formula").val()
    var description = $("#description").val()

    if (isEmpty(ruleAlias)) {
        alert("rule alias is empty!")
        return
    }

    if (isEmpty(formula)) {
        alert("formula is empty!")
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
    post("/rule/saveRule", ruleJson, function (result) {
        window.location.reload()
        console.log(result)
    }, function (e) {
        console.log(e)
    })
})

function getKeyPath(formula) {

}

function checkKeyPath(keyPath, response) {

}


const jsonStr = '{"_shards":{"total":80,"failed":0,"successful":80,"skipped":0},"hits":{"hits":[],"total":14516204,"max_score":0},"took":424,"timed_out":false,"aggregations":{"result":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"doc_count":725556,"key":"2019022000"},{"doc_count":776835,"key":"2019022001"},{"doc_count":793158,"key":"2019022002"}]}}}';