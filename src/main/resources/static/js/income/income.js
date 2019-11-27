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
                        html += '<option value="' + result.data[i].incomeTypeId + '">' + result.data[i].incomeType + '</option>';
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
    };
    initIncomeType();
    var queryListFun = function () {
        var queryListUrl = "/api/income/queryListUrl";
        var minPrice;
        var maxPrice;
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
    //添加保存
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
                            $('#addModal').modal('hide', 'fit');
                            queryListFun();
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
    //初始化修改
    $("#updateBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要修改的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要修改的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $("#updateForm")[0].reset();
            var url = '/api/income/initUpdate';
            var param = 'incomeId=' + selectedItems[0].income_id;//请求到列表页面
            console.log('param=' + param);
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'JSON',
                beforeSend: function (xhr) {//设置请求头信息
                    xhr.setRequestHeader("token", token);
                },
                headers: {'token': token},
                data: param,
                success: function (data) {
                    if (data.result == 'success') {
                        $('#updateModal').modal('show', 'fit');
                        $("#udIncPrice").val(data.income.incomePrice);
                        $("#udIncSource").val(data.income.incomeSource);
                        $("#udPayAccount").val(data.income.payAccount);
                        $("#udIncDesc").val(data.income.incomeDesc);
                        $("#udIncId").val(data.income.incomeId);
                        if (data.dataIncT.length != 0) {
                            $("#udIncTypeId").empty();
                            $.each(data.dataIncT, function (i, item) {
                                if (data.income.incomeTypeId == item.incomeTypeId) {
                                    $("#udIncTypeId").append("<option value='" + item.incomeTypeId + "'selected>" + item.incomeType + "</option>");
                                } else {
                                    $("#udIncTypeId").append("<option value='" + item.incomeTypeId + "'>" + item.incomeType + "</option>");
                                }
                            });
                        }
                        if (data.dataDic.length != 0) {
                            $("#udPayMethod").empty();
                            $.each(data.dataDic, function (i, item) {
                                if (data.income.payMethod == item.keyValue) {
                                    $("#udPayMethod").append("<option value='" + item.keyValue + "'selected>" + item.keyName + "</option>");
                                } else {
                                    $("#udPayMethod").append("<option value='" + item.keyValue + "'>" + item.keyName + "</option>");
                                }
                            });
                        }
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
    //修改保存
    $("#updateSaveBtn").click(function () {
        var updateValite = $("#updateForm").validate({
            rules: {
                udIncPrice: {
                    "required": true,
                },
                udIncSource: {
                    "required": true
                },
                udPayAccount: {
                    "required": true
                },
                udIncDesc: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/income/update",
                    type: 'post',
                    dataType: 'JSON',
                    beforeSend: function (xhr) {//设置请求头信息
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {'token': token},
                    data: $(form).serialize(),
                    success: function (data) {
                        console.log('data=' + data);
                        if (data.success == '1') {
                            new $.zui.Messager('修改成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            $('#updateModal').modal('hide', 'fit');
                            queryListFun();
                            updateValite.resetForm();
                        } else {
                            new $.zui.Messager(data.msg, {
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
    /*删除按钮*/
    $("#deleteBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要删除的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要删除的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $('#deleteModal').modal('show', 'fit');
        }
    });
    //删除菜单
    $("#deleteMenuBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        var url = '/api/income/delete';
        var param = 'incomeId=' + selectedItems[0].income_id;//请求到列表页面
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {//设置请求头信息
                xhr.setRequestHeader("token", token);
            },
            headers: {'token': token},
            data: param,
            success: function (data) {
                $('#deleteModal').modal('hide', 'fit');
                if (data.success == '1') {
                    new $.zui.Messager('删除成功!', {
                        type: 'success',
                        placement: 'center'
                    }).show();
                    queryListFun();
                } else {
                    new $.zui.Messager("删除失败", {
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
    });
    //初始化详情
    $("#detailBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要查看的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要查看的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $("#detailForm")[0].reset();
            var url = '/api/income/detail';
            var param = 'incomeId=' + selectedItems[0].income_id;//请求到列表页面
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'JSON',
                beforeSend: function (xhr) {//设置请求头信息
                    xhr.setRequestHeader("token", token);
                },
                headers: {'token': token},
                data: param,
                success: function (result) {
                    if (result.success == '1') {
                        $('#detailModal').modal('show', 'fit');
                        $("#dtIncPrice").html(result.income.incomePrice+'.00');
                        $("#dtIncSource").html(result.income.incomeSource);
                        $("#dtPayAccount").html(result.income.payAccount);
                        $("#dtIncDesc").html(result.income.incomeDesc);
                        if (result.dataIncT.length != 0) {
                            $.each(result.dataIncT, function (i, item) {
                                if (result.income.incomeTypeId == item.incomeTypeId) {
                                    $("#dtIncTypeId").html(item.incomeType);
                                }
                            });
                        }
                        if (result.dataDic.length != 0) {
                            $.each(result.dataDic, function (i, item) {
                                if (result.income.payMethod == item.keyValue) {
                                    $("#dtPayMethod").html(item.keyName);
                                }
                            });
                        }
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