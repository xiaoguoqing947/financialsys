$(function () {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
    });
    $('.icon-trash').bind('click', function () {
        swalWithBootstrapButtons.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            reverseButtons: true
        }).then((result) => {
            if (result.value) {
                swalWithBootstrapButtons.fire(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                )
            } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
            ) {
                swalWithBootstrapButtons.fire(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                )
            }
        })
    });
    $('.fa-edit').bind('click', function () {
        Swal.fire({
            title: '请记录下你的所想所做',
            input: 'text',
            inputAttributes: {
                autocapitalize: 'off'
            },
            showCancelButton: true,
            confirmButtonText: 'OK',
            showLoaderOnConfirm: true,
            preConfirm: (login) => {
                return fetch(`//api.github.com/users/${login}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(response.statusText)
                        }
                        return response.json()
                    })
                    .catch(error => {
                        Swal.showValidationMessage(
                            `Request failed: ${error}`
                        )
                    })
            },
            allowOutsideClick: () => !Swal.isLoading()
        }).then((result) => {
            if (result.value) {
                Swal.fire({
                    title: `${result.value.login}'s avatar`,
                    imageUrl: result.value.avatar_url
                })
            }
        })
    })
});
$("#inputBorn").datetimepicker(
    {
        language: "zh-CN",
        weekStart: 1,
        autoclose: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: "yyyy年mm月ss日"
    });
$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var initCurrentUser = function () {
        $.ajax({
            url: '/api/monitor/queryCurrentUser',
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
                    if (result.currentUser.sex == '0') {
                        result.currentUser.sex = '男';
                    } else if (result.currentUser.sex == '1') {
                        result.currentUser.sex = '女';
                    } else {
                        result.currentUser.sex = '#';
                    }
                    $('#headPic').attr("src", result.currentUser.pic);
                    $('#username').text(result.currentUser.username);
                    $('#born').text(result.currentUser.born);
                    $('#address').text(result.currentUser.address);
                    $('#sex').text(result.currentUser.sex);
                    $('#name').text(result.currentUser.name);

                    $('#inputPicHead').attr("src", result.currentUser.pic);
                    $('#inputName').val(result.currentUser.name);
                    $('#inputBorn').val(result.currentUser.born);
                    $('#inputAddress').val(result.currentUser.address);
                    $('#inputSex').val(result.currentUser.sex);
                    $('#inputNumber').val(result.currentUser.contactNumber);

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
    $("#savePersonBtn").click(function () {
        var updateValite = $("#updatePerSonForm").validate({
            rules: {
                file: {
                    "required": true
                },
                inputName: {
                    "required": true
                },
                inputSex: {
                    "required": true
                },
                inputNumber: {
                    "required": true
                },
                inputAddress: {
                    "required": true
                },
                inputBorn: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                var formdata = new FormData(form);
                $.ajax({
                    url: "/api/file/upload/uploadimg",
                    type: 'post',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    dataType: 'JSON',
                    beforeSend: function (xhr) {//设置请求头信息
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {'token': token},
                    data: formdata,
                    success: function (data) {
                        console.log('data=' + data);
                        if (data.code == 200) {
                            new $.zui.Messager('修改成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            updateValite.resetForm();
                            window.location.href = "/api/admin";
                        } else if (data.code == 500) {
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
