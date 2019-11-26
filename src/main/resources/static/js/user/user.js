$(document).ready(function () {
    var currentPage = 1;
    var pageSize = 10;
    var token = $.zui.store.get("token");//Token值
    var queryListFun = function () {
        var queryListUrl = "/api/user/queryListUrl";
        var userCode;
        var username;
        var param = '';
        if ($("#queryUserCode").val() != '') {
            param += "&userCode=" + $("#queryUserCode").val();
            userCode = $("#queryUserCode").val();
        }
        if ($("#queryUserName").val() != '') {
            param += "&username=" + $("#queryUserName").val();
            username = $("#queryUserName").val();
        }
        param += "&pageSize=" + pageSize;
        param += "&currentPage=" + (currentPage - 1);
        var userForm = {
            "userCode": userCode,
            "username": username,
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
            data: JSON.stringify(userForm),//转化为json字符串
            success: function (result) {
                if (result.result == 'success') {
                    for(var i = 0; i < result.data.length; i++){
                        if (result.data[i].power == '0') {
                            result.data[i].power = '普通用户';
                        } else {
                            result.data[i].power = '管理员';
                        }
                        if (result.data[i].sex == '0') {
                            result.data[i].sex = '男';
                        } else if(result.data[i].sex == '1'){
                            result.data[i].sex = '女';
                        }else{
                            result.data[i].sex = '#';
                        }
                    }
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'username', label: '用户名', width: 150},
                                {name: 'power', label: '权限', width: 100},
                                {name: 'name', label: '姓名', width: 152},
                                {name: 'sex', label: '性别', width: 100},
                                {name: 'pic', label: '头像', width: 139},
                                {name: 'contactNumber', label: '联系电话', width: 139},
                                {name: 'email', label: '邮箱', width: 180},
                                {name: 'registData', label: '注册时间', width: -1},
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
                        pageSizeOptions: [5,10, 20, 30, 50, 100],
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
        var url = '/api/user/delete';
        var param = 'username='+selectedItems[0].username;//请求到列表页面
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
            var url = '/api/user/detail';
            var param = 'username='+selectedItems[0].username;//请求到列表页面
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
                        if (result.user.power == '0') {
                            result.user.power = '普通用户';
                        } else {
                            result.user.power = '管理员';
                        }
                        if (result.user.sex == '0') {
                            result.user.sex = '男';
                        } else if(result.user.sex == '1'){
                            result.user.sex = '女';
                        }else{
                            result.user.sex = '#';
                        }
                        var html='<img src="'+result.user.pic+'" height="60" width="60"  />'
                        $("#detailHeadPic").html(html);
                        $("#detailUName").html(result.user.username);
                        $("#detailName").html(result.user.name);
                        $("#detailRTime").html(result.user.registData);
                        $("#detailEmail").html(result.user.email);
                        $("#detailPNumber").html(result.user.contactNumber);
                        $("#detailBorn").html(result.user.born);
                        $("#detailSex").html(result.user.sex);
                        $("#detailPower").html(result.user.power);
                        $("#detailAddress").html(result.user.address);

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