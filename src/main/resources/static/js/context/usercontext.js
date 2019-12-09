function incomeAddInit() {
    var token = $.zui.store.get("token");//Token值
    var url = "/api/income/initAdd";
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'JSON',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("token", token);
        },
        headers: {
            Accept: "application/json; charset=utf-8",
            "token": token
        },
        success: function (result) {
            if (result.dataIncT.length != 0) {
                $("#incomeTypeId").empty();
                $.each(result.dataIncT, function (i, item) {
                    $("#incomeTypeId").append("<option value='" + item.incomeTypeId + "'>" + item.incomeType + "</option>");
                });
            }
            if (result.dataDic.length != 0) {
                $("#payMethod").empty();
                $.each(result.dataDic, function (i, item) {
                    $("#payMethod").append("<option value='" + item.keyValue + "'>" + item.keyName + "</option>");
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("jqXHR.responseText=" + jqXHR.responseText);
            alert("jqXHR.statusText=" + jqXHR.statusText);
            alert("jqXHR.status=" + jqXHR.status)
        }
    });
}
function expendAddInit() {
    var url = "/api/expend/initAdd";
    var token = $.zui.store.get("token");//Token值
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'JSON',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("token", token);
        },
        headers: {
            Accept: "application/json; charset=utf-8",
            "token": token
        },
        success: function (result) {
            if (result.dataExpT.length != 0) {
                $("#expendTypeId").empty();
                $.each(result.dataExpT, function (i, item) {
                    $("#expendTypeId").append("<option value='" + item.expendTypeId + "'>" + item.enpendType + "</option>");
                });
            }
            if (result.dataDic.length != 0) {
                $("#payExpMethod").empty();
                $.each(result.dataDic, function (i, item) {
                    $("#payExpMethod").append("<option value='" + item.keyValue + "'>" + item.keyName + "</option>");
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("jqXHR.responseText=" + jqXHR.responseText);
            alert("jqXHR.statusText=" + jqXHR.statusText);
            alert("jqXHR.status=" + jqXHR.status)
        }
    });
}
$(function(){
    let timerInterval
    Swal.fire({
        title: '今天你记账了吗!',
        html:
            '即将在 <strong></strong> 秒内自动关闭.<br/><br/>' +
            '<button id="increase" class="btn btn-warning">' +
            '请再给我5秒钟!' +
            '</button><br/><br/>' +
            '<button id="expend" class="btn btn-danger" data-toggle="modal" data-target="#addExpendModal">' +
            '支出记账！' +
            '</button><br/><br/>' +
            '<button id="income" class="btn btn-success" data-toggle="modal" data-target="#addIncomeModal">' +
            '收入记账 !' +
            '</button><br/>',
        timer: 10000,
        onBeforeOpen: () => {
            const content = Swal.getContent()
            const $ = content.querySelector.bind(content)

            const expend = $('#expend')
            const income = $('#income')
            const increase = $('#increase')

            Swal.showLoading();

            expend.addEventListener('click', () => {
                expendAddInit();
                Swal.close();
            })

            income.addEventListener('click', () => {
                incomeAddInit();
                Swal.close();
            })

            increase.addEventListener('click', () => {
                Swal.increaseTimer(5000)
            })

            timerInterval = setInterval(() => {
                Swal.getContent().querySelector('strong')
                    .textContent = (Swal.getTimerLeft() / 1000)
                    .toFixed(0)
            }, 100)
        },
        onClose: () => {
            clearInterval(timerInterval)
        }
    })
});
$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    $.ajax({
        url: '/api/Statistics/userContext',
        type: 'post',
        dataType: 'JSON',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("token", token);
        },
        headers: {
            Accept: "application/json; charset=utf-8",
            "token": token
        },
        success: function (result) {
            console.log(result.pieChart);
            var pieChartCanvas = $('#pieChart').get(0).getContext('2d')
            var pieData = {
                labels: result.pieChart.labels,
                datasets: [
                    {
                        data: result.pieChart.chartData,
                        backgroundColor : ['#f56954', '#00a65a', '#f39c12', '#00c0ef', '#3c8dbc', '#F3F114','#EF000D','#F35EB2','#090608'],
                    }
                ]
            }
            var pieOptions     = {
                legend: {
                    display: false
                }
            }
            var pieChart = new Chart(pieChartCanvas, {
                type: 'doughnut',
                data: pieData,
                options: pieOptions
            })
            $('#maxPrice').html('<i class="fas fa-arrow-up text-sm"></i>'+result.pieChart.maxPrice+'.00$');
            $('#minPrice').html('<i class="fas fa-arrow-down text-sm"></i>'+result.pieChart.minPrice+'.00$');
            /*pieChartData  end*/
            console.log(result.dataTable);
            var html='<table class="table table-condensed" style="text-align: center">\n' +
                '                        <thead>\n' +
                '                        <tr>\n' +
                '                            <th style="width: 50px">序号</th>\n' +
                '                            <th>预算说明</th>\n' +
                '                            <th style="width: 200px">进度</th>\n' +
                '                            <th style="width: 100px">所占%比</th>\n' +
                '                        </tr>\n' +
                '                        </thead>\n' +
                '                        <tbody>\n' +
                '                        <tr style="height: 50px;">\n' +
                '                            <td>1.</td>\n' +
                '                            <td>月已达到预定最大支出金额</td>\n' +
                '                            <td>\n' +
                '                                <div class="progress progress-xs  progress-striped active">';
            html +='   <div class="progress-bar progress-bar-danger" style="width: '+(result.dataTable.expMaxPriceRate*100).toFixed(0)+'%"></div>\n' +
                '                                </div>\n' +
                '                            </td>\n' +
                '                            <td><span class="badge bg-danger">'+(result.dataTable.expMaxPriceRate*100).toFixed(0)+'%</span></td>\n' +
                '                        </tr>';
            html +='<tr style="height: 50px">\n' +
                '                            <td>2.</td>\n' +
                '                            <td>月已达到预定合理支出金额</td>\n' +
                '                            <td>\n' +
                '                                <div class="progress progress-xs progress-striped active">\n' +
                '                                    <div class="progress-bar bg-warning" style="width: '+(result.dataTable.expSuitPriceRate*100).toFixed(0)+'%"></div>\n' +
                '                                </div>\n' +
                '                            </td>\n' +
                '                            <td><span class="badge bg-warning">'+(result.dataTable.expSuitPriceRate*100).toFixed(0)+'%</span></td>\n' +
                '                        </tr>';
            html +='<tr style="height: 59px">\n' +
                '                            <td>3.</td>\n' +
                '                            <td>月已达到预定娱乐支出金额</td>\n' +
                '                            <td>\n' +
                '                                <div class="progress progress-xs progress-striped active">\n' +
                '                                    <div class="progress-bar bg-primary" style="width: '+(result.dataTable.expJoyPriceRate*100).toFixed(0)+'%"></div>\n' +
                '                                </div>\n' +
                '                            </td>\n' +
                '                            <td><span class="badge bg-primary">'+(result.dataTable.expJoyPriceRate*100).toFixed(0)+'%</span></td>\n' +
                '                        </tr>';
            html +='<tr style="height: 58px">\n' +
                '                            <td>4.</td>\n' +
                '                            <td>月已达到预定购物支出金额</td>\n' +
                '                            <td>\n' +
                '                                <div class="progress progress-xs progress-striped active">\n' +
                '                                    <div class="progress-bar bg-primary" style="width: '+(result.dataTable.expShopPriceRate*100).toFixed(0)+'%"></div>\n' +
                '                                </div>\n' +
                '                            </td>\n' +
                '                            <td><span class="badge bg-primary">'+(result.dataTable.expShopPriceRate*100).toFixed(0)+'%</span></td>\n' +
                '                        </tr>';
            html +='<tr style="height: 50px">\n' +
                '                            <td>5.</td>\n' +
                '                            <td>月总收入剩余</td>\n' +
                '                            <td>\n' +
                '                                <div class="progress progress-xs progress-striped active">\n' +
                '                                    <div class="progress-bar bg-success" style="width: '+(result.dataTable.incTotalPriceRate*100).toFixed(0)+'%"></div>\n' +
                '                                </div>\n' +
                '                            </td>\n' +
                '                            <td><span class="badge bg-success">'+(result.dataTable.incTotalPriceRate*100).toFixed(0)+'%</span></td>\n' +
                '                        </tr>\n' +
                '                        </tbody>\n' +
                '                    </table>';
            $('#expTable').html(html);
            /*LineChart Start*/
            var salesChartCanvas = $('#salesChart').get(0).getContext('2d')
            var salesChartData = {
                labels  : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                datasets: [
                    {
                        label               :'支出',
                        backgroundColor     : 'rgba(0,111,188,0.9)',
                        borderColor         : 'rgba(0,111,188,0.9)',
                        pointRadius          : false,
                        pointColor          : '#0092bc',
                        pointStrokeColor    : 'rgba(0,111,188,0.9)',
                        pointHighlightFill  : '#fff',
                        pointHighlightStroke: 'rgba(0,111,188,0.9)',
                        data                : result.expLineChart.chartData
                    },
                    {
                        label               : ['收入'],
                        backgroundColor     : 'rgb(0,222,66)',
                        borderColor         : 'rgb(0,222,66)',
                        pointRadius         : false,
                        pointColor          : 'rgb(0,222,66)',
                        pointStrokeColor    : 'rgb(0,222,66)',
                        pointHighlightFill  : '#fff',
                        pointHighlightStroke: 'rgb(0,222,66)',
                        data                : result.incLineChart.chartData
                    },
                ]
            }
            var salesChartOptions = {
                maintainAspectRatio : false,
                responsive : true,
                legend: {
                    display: false
                },
                scales: {
                    xAxes: [{
                        gridLines : {
                            display : false,
                        }
                    }],
                    yAxes: [{
                        gridLines : {
                            display : true,
                        },
                        ticks: { //刻度
                            fontColor: "#000",
                        }
                    }]
                }
            }
            var salesChart = new Chart(salesChartCanvas, {
                    type: 'line',
                    data: salesChartData,
                    options: salesChartOptions
                }
            )
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("jqXHR.responseText=" + jqXHR.responseText);
            alert("jqXHR.statusText=" + jqXHR.statusText);
            alert("jqXHR.status=" + jqXHR.status)
        }
    });
    /*收入添加*/
    $("#addSaveBtn").click(function () {
        var validator = $("#addForm").validate({
            rules: {
                incomeTypeId: {
                    "required": true,
                },
                incomePrice: {
                    "required": true
                },
                incomeSource: {
                    "required": true
                },
                payAccount: {
                    "required": true
                },
                incomeDesc: {
                    "required": true
                },
                payMethod: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/income/add",
                    type: 'post',
                    dataType: 'JSON',
                    beforeSend: function (xhr) {//设置请求头信息
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {
                        Accept: "application/json; charset=utf-8",
                        "token": token
                    },
                    data: $(form).serialize(),
                    success: function (data) {
                        if (data) {
                            new $.zui.Messager('添加成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            $('#incomePrice').val('');
                            $('#incomeSource').val('');
                            $('#payAccount').val('');
                            $('#incomeDesc').val('');
                            $('#addIncomeModal').modal('hide', 'fit');
                            validator.resetForm();
                        } else {
                            new $.zui.Messager("添加失败", {
                                type: 'warning',
                                placement: 'center'
                            }).show();
                        }
                    },
                    error: function (e) {
                        new $.zui.Messager('系统繁忙,请稍候再试!', {
                            type: 'warning',
                            placement: 'center'
                        }).show();
                    }
                });
            }
        });
    });
    //支出添加保存
    $("#addExpSaveBtn").click(function () {
        var validator = $("#addExpendForm").validate({
            rules: {
                expendPrice: {
                    "required": true
                },
                expendUse: {
                    "required": true
                },
                payAccount: {
                    "required": true
                },
                expendDesc: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/expend/add",
                    type: 'post',
                    dataType: 'JSON',
                    beforeSend: function (xhr) {//设置请求头信息
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {
                        Accept: "application/json; charset=utf-8",
                        "token": token
                    },
                    data: $(form).serialize(),
                    success: function (data) {
                        if (data) {
                            new $.zui.Messager('添加成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            $('#incomePrice').val('');
                            $('#incomeSource').val('');
                            $('#payAccount').val('');
                            $('#incomeDesc').val('');
                            $('#addExpendModal').modal('hide', 'fit');
                            validator.resetForm();
                        } else {
                            new $.zui.Messager("添加失败", {
                                type: 'warning',
                                placement: 'center'
                            }).show();
                        }
                    },
                    error: function (e) {
                        new $.zui.Messager('系统繁忙,请稍候再试!', {
                            type: 'warning',
                            placement: 'center'
                        }).show();
                    }
                });
            }
        });
    });
})


