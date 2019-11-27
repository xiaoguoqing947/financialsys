$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var queryListFun = function () {
        var queryListUrl = "/api/incometype/queryListUrl";
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
                                {name: 'incomeTypeId', label: '收入类型ID', width: -1},
                                {name: 'incomeType', label: '收入类型名称', width: -1},
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
        var validIncTyprNameUrl = "/api/incometype/validIncTypeName";
        var validator = $("#addForm").validate({
            rules: {
                incomeType: {
                    "required": true,
                    "remote": {
                        url: validIncTyprNameUrl,
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
                            'incomeType': function () {
                                return $("#incomeType").val();
                            }
                        }
                    }
                }
            },
            messages: {
                incomeType: {
                    "remote": "收入类别已经存在,请重新输入"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/incometype/add",
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
            var url = '/api/incometype/initUpdate';
            var param = 'incomeTypeId=' + selectedItems[0].incomeTypeId;//请求到列表页面
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
                        $("#updateIncTypeId").val(data.incomeType.incomeTypeId);
                        $("#updateIncType").val(data.incomeType.incomeType);
                        $("#oldIncType").val(data.incomeType.incomeType);
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
        var validIncTypeNameUrl = '/api/incometype/validUpdateName';
        var validator=$("#updateForm").validate({
            rules: {
                updateIncType: {
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
                            'updateIncType': function () {
                                return $("#updateIncType").val();
                            },
                            'oldIncType': function () {
                                return $("#oldIncType").val();
                            }
                        }
                    }
                },
            },
            messages: {
                updateIncType: {
                    "remote": "收入类别已经存在,请重新输入"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/incometype/update",
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
        var url = '/api/incometype/delete';
        var param = 'incomeTypeId='+selectedItems[0].incomeTypeId;//请求到列表页面
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