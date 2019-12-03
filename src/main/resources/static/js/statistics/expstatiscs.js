// 仅选择日期
$("#mouth").datetimepicker(
    {
        language: "zh-CN",
        weekStart: 1,
        autoclose: 1,
        startView: 3,
        minView: 3,
        forceParse: 0,
        format: "yyyy年mm月"
    });
$("#year").datetimepicker(
    {
        language: "zh-CN",
        weekStart: 1,
        autoclose: 1,
        startView: 4,
        minView: 4,
        forceParse: 0,
        format: "yyyy年"
    });
$("#selectTimeMethod").on("change", function () {
    pID = $("option:selected", this).val();//需求主键
    if (pID == "mouth") {
        $("#year").val('');
        $("#year").attr("placeholder", "不可选择");
        $("#mouth").attr("placeholder", "请在这里选择日期");
        $("#mouth").attr("disabled", false);
        $("#year").attr("disabled", true);
    } else if (pID == "year") {
        $("#mouth").val('');
        $("#mouth").attr("disabled", true);
        $("#mouth").attr("placeholder", "不可选择");
        $("#year").attr("placeholder", "请在这里选择日期");
        $("#year").attr("disabled", false);
    }
});
$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var queryLineChart = function () {
        $.ajax({
            url: '/api/Statistics/expend',
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("token", token);
            },
            headers: {
                Accept: "application/json; charset=utf-8",
                "token": token
            },
            data: {
                "mouth": $('#mouth').val(),
                "year": $('#year').val()
            },//转化为json字符串
            success: function (result) {
                console.log(result.lineChart)
                var chart = Highcharts.chart('container', {
                    credits: {
                        enabled: false
                    },
                    exporting: false,
                    title: {
                        text: '个人用户支出统计图'
                    },
                    subtitle: {
                        text: '肖国清'
                    },
                    yAxis: {
                        title: {
                            text: '支出金额'
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle'
                    },
                    xAxis: {
                        categories: result.lineChart.xcategories,
                        showEmpty: false
                    },
                    tooltip: {
                        valueDecimals: 2,
                        valuePrefix: '$'
                    },
                    series: [{
                        name:  result.lineChart.seriesName,
                        data: result.lineChart.chartData
                    }],
                    responsive: {
                        rules: [{
                            condition: {
                                maxWidth: 500
                            },
                            chartOptions: {
                                legend: {
                                    layout: 'horizontal',
                                    align: 'center',
                                    verticalAlign: 'bottom'
                                }
                            }
                        }]
                    }
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        });
    };
    queryLineChart();
    $("#searchBtn").click(function () {
        queryLineChart();
    });
});

