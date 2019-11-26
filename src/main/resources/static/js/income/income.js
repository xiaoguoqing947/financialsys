$(document).ready(function () {
    var currentPage = 1;
    var pageSize = 10;
    var token = $.zui.store.get("token");//Token值
    var initIncomeType = function () {
        $.ajax({
            url: '/api/incometype/queryListUrl',
            contentType: "application/json;charset=UTF-8",
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
                if (result.status == 'success') {
                    var html = '';
                    html += '<option value="" ></option>';
                    for (var i = 0; i < result.data.length; i++) {
                        html += '<option value="'+result.data[i].incomeTypeId+'">'+result.data[i].incomeType+'</option>';
                    }
                    $('#queryIncomeType').html(html);
                } else {
                    new $.zui.Messager("请重新登录", {
                        type: 'warning', // 定义颜色主题
                        placement: 'center' // 定义显示位置
                    }).show();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        });
    }
    initIncomeType();
    var queryListFun = function () {
        var queryListUrl = "/api/income/queryListUrl";
        var minPrice ;
        var maxPrice ;
        var incomeType;
        if ($("#queryMinPrice").val() != '') {
            minPrice = $("#queryMinPrice").val();
        }
        if ($("#queryMaxPrice").val() != '') {
            maxPrice = $("#queryMaxPrice").val();
        }
        if (minPrice > maxPrice) {
            new $.zui.Messager("请调整输入金额范围", {
                type: 'warning', // 定义颜色主题
                placement: 'center' // 定义显示位置
            }).show();
            return;
        }
        if ($("#queryIncomeType").val() != '') {
            incomeType = $("#queryIncomeType").val();
        }
        var incomeForm = {
            "minPrice": minPrice,
            "maxPrice": maxPrice,
            "incomeType": incomeType,
            "pageSize": pageSize,
            "currentPage": currentPage - 1
        };
        $.ajax({
            url: queryListUrl,
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("token", token);
            },
            headers: {
                Accept: "application/json; charset=utf-8",
                "token": token
            },
            data: JSON.stringify(incomeForm),//转化为json字符串
            success: function (result) {
                if (result.result == 'success') {
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'income_source', label: '收入来源', width: 152},
                                {name: 'key_name', label: '接收方式', width: 152},
                                {name: 'income_price', label: '收入金额', width: 152},
                                {name: 'income_type', label: '收入类型', width: 139},
                                {name: 'income_desc', label: '收入描述', width: -1},
                                {name: 'income_date', label: '收入时间', width: -1}
                            ],
                            cache: false,
                            array: result.data
                        }
                    });
                    // 手动进行初始化
                    $('#listPager').pager({
                        page: currentPage,
                        recPerPage: pageSize,
                        elements: ['prev_icon', 'pages', 'next_icon', 'total_text', 'size_menu'],
                        pageSizeOptions: [10, 20, 30, 50, 100],
                        recTotal: result.pager.recTotal
                    });
                } else {
                    new $.zui.Messager("请重新登录", {
                        type: 'warning', // 定义颜色主题
                        placement: 'center' // 定义显示位置
                    }).show();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        });
    };
    queryListFun();
    $("#searchBtn").click(function () {
        queryListFun();
    });
    //监听分页，修改当前页，条数
    $('#listPager').on('onPageChange', function (e, state, oldState) {
        currentPage = state.page;
        pageSize = state.recPerPage;
        queryListFun();
    });
    //初始化添加
    $("#addBtn").click(function () {
        //获取菜单列表
        var url = "/api/income/initAdd";
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
    });
});