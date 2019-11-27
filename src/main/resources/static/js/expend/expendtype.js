$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var queryListFun = function () {
        var queryListUrl = "/api/expendtype/queryListUrl";
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
            success: function (result) {
                if (result.status == 'success') {
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'expendTypeId', label: '支出类型ID', width: -1},
                                {name: 'enpendType', label: '支出类型名称', width: -1},
                            ],
                            cache: false,
                            array: result.data
                        }
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
//添加保存
    $("#addSaveBtn").click(function () {
        var validExpTyprNameUrl = "/api/expendtype/validExpTypeName";
        var validator = $("#addForm").validate({
            rules: {
                enpendType: {
                    "required": true,
                    "remote": {
                        url: validExpTyprNameUrl,
                        type: 'get',
                        beforeSend: function (xhr) {//设置请求头信息
                            xhr.setRequestHeader("token", token);
                        },
                        headers: {
                            Accept: "application/json; charset=utf-8",
                            "token": token
                        },
                        dataType: 'json',
                        data: {
                            'enpendType': function () {
                                return $("#enpendType").val();
                            }
                        }
                    }
                }
            },
            messages: {
                enpendType: {
                    "remote": "支出类别已经存在,请重新输入"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/expendtype/add",
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
                            $('#incomeType').val('');
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
            var url = '/api/expendtype/initUpdate';
            var param = 'expendTypeId=' + selectedItems[0].expendTypeId;//请求到列表页面
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
                        $("#updateExpTypeId").val(data.expendType.expendTypeId);
                        $("#updateExpType").val(data.expendType.enpendType);
                        $("#oldExpType").val(data.expendType.enpendType);
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
        var validIncTypeNameUrl = '/api/expendtype/validUpdateName';
        var validator=$("#updateForm").validate({
            rules: {
                updateExpType: {
                    "required": true,
                    "remote": {
                        url: validIncTypeNameUrl,
                        type: 'get',
                        beforeSend: function (xhr) {//设置请求头信息
                            xhr.setRequestHeader("token", token);
                        },
                        headers: {'token': token},
                        dataType: 'json',
                        data: {
                            'updateExpType': function () {
                                return $("#updateExpType").val();
                            },
                            'oldExpType': function () {
                                return $("#oldExpType").val();
                            }
                        }
                    }
                },
            },
            messages: {
                updateExpType: {
                    "remote": "支出类别已经存在,请重新输入"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/expendtype/update",
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
                            validator.resetForm();
                        } else {
                            new $.zui.Messager('修改失败', {
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
    $("#deleteBtn").click(function(){
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要删除的记录', {
                type: 'warning',
                placement:'center'
            }).show();
        }
        else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要删除的记录', {
                type: 'warning',
                placement:'center'
            }).show();
        }
        else {
            $('#deleteModal').modal('show', 'fit');
        }
    });
    //删除菜单
    $("#deleteMenuBtn").click(function(){
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        var url = '/api/expendtype/delete';
        var param = 'expendTypeId='+selectedItems[0].expendTypeId;//请求到列表页面
        $.ajax({
            url:url,
            type:'post',
            dataType:'JSON',
            beforeSend:function(xhr){//设置请求头信息
                xhr.setRequestHeader("token",token);
            },
            headers:{'token':token},
            data:param,
            success:function(data){
                $('#deleteModal').modal('hide', 'fit');
                if (data.success=='1'){
                    new $.zui.Messager('删除成功!', {
                        type: 'success',
                        placement:'center'
                    }).show();
                    queryListFun();
                }
                else{
                    new $.zui.Messager("删除失败", {
                        type: 'warning',
                        placement:'center'
                    }).show();
                }
            },
            error:function(e){
                new $.zui.Messager('系统繁忙,请稍候再试!', {
                    type: 'warning',
                    placement:'center'
                }).show();
            }
        });
    });
});