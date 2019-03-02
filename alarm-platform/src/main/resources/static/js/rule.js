$(function () {
    // $.ajax({
    //     url: "../json/response.json",//json文件位置
    //     type: "GET",//请求方式为get
    //     dataType: "json", //返回数据格式为json
    //     success: function (data) {//请求成功完成后要执行的方法 
           
    //     }
    // })
    $("#response-data").JSONView(jsonStr);
})

$("#rule-confirm").on("click", function () {
    var ruleAlias = $("#rule-alias").val()
    var formula = $("#formula").val()
    var description = $("#description").val()
    var ruleJson = {
        "ruleAlias": ruleAlias,
        "formula": formula,
        "description": description
    }
    console.log(ruleJson)
})

function getKeyPath(formula) {

}

function checkKeyPath(keyPath, response) {

}


const jsonStr = '{"_shards":{"total":80,"failed":0,"successful":80,"skipped":0},"hits":{"hits":[],"total":14516204,"max_score":0},"took":424,"timed_out":false,"aggregations":{"result":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"doc_count":725556,"key":"2019022000"},{"doc_count":776835,"key":"2019022001"},{"doc_count":793158,"key":"2019022002"}]}}}';