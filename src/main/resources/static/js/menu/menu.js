$(document).ready(function () {
    var currentPage = 1;
    var pageSize = 10;
    var token = $.zui.store.get("token");//Token值
    var queryListFun = function () {
        var queryListUrl = "/api/menu/queryListUrl";
        var menuCode;
        var menuNameLike;
        var param = '';
        if ($("#queryMenuCode").val() != '') {
            param += "&menuCode=" + $("#queryMenuCode").val();
            menuCode = $("#queryMenuCode").val();
        }
        if ($("#queryMenuNameLike").val() != '') {
            param += "&menuNameLike=" + $("#queryMenuNameLike").val();
            menuNameLike = $("#queryMenuNameLike").val();
        }
        param += "&pageSize=" + pageSize;
        param += "&currentPage=" + (currentPage - 1);
        var menuForm = {
            "menuCode": menuCode,
            "menuNameLike": menuNameLike,
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
            data: JSON.stringify(menuForm),//转化为json字符串
            success: function (result) {
                if (result.result == 'success') {
                    for (var i = 0; i < result.data.length; i++) {
                        if (result.data[i].parentId == '0') {
                            result.data[i].parentId = '是';
                            result.data[i].priority = '';
                        } else {
                            result.data[i].parentId = '';
                            for (var j = 0; j < result.menus.length; j++) {
                                if (result.data[i].priority == result.menus[j].id) {
                                    result.data[i].priority = result.menus[j].name;
                                }
                            }
                        }
                    }
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'id', label: '菜单编码', width: 150},
                                {name: 'name', label: '菜单名称', width: 152},
                                {name: 'parentId', label: '是否为上级菜单', width: 152},
                                {name: 'priority', label: '上一级菜单名称', width: 152},
                                {name: 'url', label: '菜单路径', width: 139},
                                {name: 'createTime', label: '创建时间', width: -1},
                                {name: 'updateTime', label: '更新时间', width: -1}
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
        var url = "/api/menu/menuList";
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
                if (result.data.length != 0) {
                    $("#priority").empty();
                    $.each(result.data, function (i, item) {
                        if (item.parentId == 0) {
                            $("#priority").append("<option value='" + item.id + "'>" + item.name + "</option>");
                        }
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
        var validMenuNameUrl = "/api/menu/validMenuName";
        $("#addForm").validate({
            rules: {
                name: {
                    "required": true,
                    "remote": {
                        url: validMenuNameUrl,
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
                            'name': function () {
                                return $("#name").val();
                            }
                        }
                    }
                },
                url: {
                    "required": true
                }
            },
            messages: {
                menuName: {
                    "remote": "菜单名称已经存在,请重新输入"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/menu/add",
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
                            $('#addModal').modal('hide', 'fit');
                            queryListFun();
                            $("#addForm").resetForm();
                            $("#addForm").validate().resetForm();
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
            var url = '/api/menu/initUpdate';
            var param = 'menuId=' + selectedItems[0].id;//请求到列表页面
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
                        $("#updateUrl").val(data.menu.url);
                        $("#updateName").val(data.menu.name);
                        $("#updateMenuId").val(data.menu.id);
                        $("#updateMenuName").val(data.menu.name);
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
        var validMenuNameUrl = '/api/menu/validUpdateName';
        $("#updateForm").validate({
            rules: {
                updateName: {
                    "required": true,
                    "remote": {
                        url: validMenuNameUrl,
                        type: 'get',
                        beforeSend: function (xhr) {//设置请求头信息
                            xhr.setRequestHeader("token", token);
                        },
                        headers: {'token': token},
                        dataType: 'json',
                        data: {
                            'updateName': function () {
                                return $("#updateName").val();
                            },
                            'updateMenuName': function () {
                                return $("#updateMenuName").val();
                            }
                        }
                    }
                },
                updateUrl: {
                    "required": true
                }
            },
            messages: {
                menuName: {
                    "remote": "菜单名称已经存在"
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/menu/update",
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
                            $("#updateForm").resetForm();
                            $("#updateForm").validate().resetForm();
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
        var url = '/api/menu/delete';
        var param = 'menuId='+selectedItems[0].id;//请求到列表页面
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
    //初始化详情
    $("#detailBtn").click(function(){
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要查看的记录', {
                type: 'warning',
                placement:'center'
            }).show();
        }
        else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要查看的记录', {
                type: 'warning',
                placement:'center'
            }).show();
        }
        else {
            $("#detailForm")[0].reset();
            var url = '/api/menu/detail';
            var param = 'menuId='+selectedItems[0].id;//请求到列表页面
            $.ajax({
                url:url,
                type:'post',
                dataType:'JSON',
                beforeSend:function(xhr){//设置请求头信息
                    xhr.setRequestHeader("token",token);
                },
                headers:{'token':token},
                data:param,
                success:function(result){
                    if (result.success=='1'){
                        $('#detailModal').modal('show', 'fit');
                        $("#detailName").html(result.menu.name);
                        $("#detailCTime").html(result.menu.createTime);
                        $("#detailUTime").html(result.menu.updateTime);
                        if(result.menu.parentId == '0'){
                            result.menu.parentId='是';
                        }else{
                            result.menu.parentId='否';
                        }
                        $("#detailParentId").html(result.menu.parentId);
                        $("#detailUrl").html(result.menu.url);
                    }
                },
                error:function(e){
                    new $.zui.Messager('系统繁忙,请稍候再试!', {
                        type: 'warning',
                        placement:'center'
                    }).show();
                }
            });
        }
    });
});

function disable() {
    document.getElementById("priority").disabled = true
}

function enable() {
    document.getElementById("priority").disabled = false
}