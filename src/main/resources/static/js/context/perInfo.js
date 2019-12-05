$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var initCurrentUser = function () {
        $.ajax({
            url:'/api/monitor/queryCurrentUser',
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
                if(result.status == 'success'){
                    if (result.currentUser.sex == '0') {
                        result.currentUser.sex = '男';
                    } else if(result.currentUser.sex == '1'){
                        result.currentUser.sex = '女';
                    }else{
                        result.currentUser.sex = '#';
                    }
                    $('#headPic').attr("src", result.currentUser.pic);
                    $('#username').text(result.currentUser.username);
                    $('#born').text(result.currentUser.born);
                    $('#address').text(result.currentUser.address);
                    $('#sex').text(result.currentUser.sex);
                    $('#name').text(result.currentUser.name);

                    $('#mIncTotalPrice').val(result.userBudget.mIncTotalPrice);
                    $('#mExpMaxPrice').val(result.userBudget.mExpMaxPrice);
                    $('#mExpSuitPrice').val(result.userBudget.mExpSuitPrice);
                    $('#mExpJoyPrice').val(result.userBudget.mExpJoyPrice);
                    $('#mExpShopPrice').val(result.userBudget.mExpShopPrice);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        })
    };
    initCurrentUser();
    //修改保存
    $("#updateSavaBtn").click(function () {
        var updateValite = $("#updateForm").validate({
            rules: {
                mIncTotalPrice: {
                    "required": true,
                },
                mExpMaxPrice: {
                    "required": true
                },
                mExpSuitPrice: {
                    "required": true
                },
                mExpJoyPrice: {
                    "required": true
                },
                mExpShopPrice: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/userbudget/update",
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
});
