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
})


