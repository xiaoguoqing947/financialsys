$(function () {
    var token = $.zui.store.get("token");//Token值
    function getTimeAgo(period) {
        var d = new Date();
        var yearLevelValue = d.getFullYear();
        var monthLevelValue = change(d.getMonth() + 1);
        var dayLevelValue = change(d.getDate());
        var hourLevelValue = change(d.getHours());
        var minuteLevelValue = change(d.getMinutes());
        var secondLevelValue = change(d.getSeconds());

        period = new Date(period);
        var year = period.getFullYear();
        var month = change(period.getMonth() + 1);
        var day = change(period.getDate());
        var hour = change(period.getHours());
        var minute = change(period.getMinutes());
        var second = change(period.getSeconds());

        function change(t) {
            if (t < 10) {
                return "0" + t;
            } else {
                return t;
            }
        }

        /*    console.log(yearLevelValue+'-'+monthLevelValue+'-'+dayLevelValue+'' +
                ' '+hourLevelValue+':'+minuteLevelValue+':'+secondLevelValue)
            console.log(year+'-'+month+'-'+day+'' +
                ' '+hour+':'+minute+':'+second)*/

        year = yearLevelValue - year;
        month = monthLevelValue - month;
        day = dayLevelValue - day;
        hour = hourLevelValue - hour;
        minute = minuteLevelValue - minute;
        second = secondLevelValue - second;

        if (year > 0) {
            year = (year == 1) ? year + ' year' : year + ' years';
            return '<small class="badge badge-danger"><i class="far fa-clock"></i>' + year + '</small>';
        } else if (month > 0) {
            month = (month == 1) ? month + ' month' : month + ' months';
            return '<small class="badge badge-warning"><i class="far fa-clock"></i>' + month + '</small>';
        } else if (day > 0) {
            day = (day == 1) ? day + ' day' : day + ' days';
            return '<small class="badge badge-info"><i class="far fa-clock"></i>' + day + '</small>';
        } else if (hour > 0) {
            hour = (hour == 1) ? hour + ' hour' : hour + ' hours';
            return '<small class="badge badge-success"><i class="far fa-clock"></i>' + hour + '</small>';

        } else if (minute > 0) {
            minute = (minute == 1) ? minute + ' minute' : minute + ' minutes';
            return '<small class="badge badge-secondary"><i class="far fa-clock"></i>' + minute + '</small>';
        } else if (second > 0) {
            second = second + ' s';
            return '<small class="badge badge-danger"><i class="far fa-clock"></i>' + second + '</small>';
        } else if (second == 0) {
            return '<small class="badge badge-danger"><i class="far fa-clock"></i>1 s</small>';
        }
    }

    var initItemPage = function () {
        $.ajax({
            url: '/api/bookmark/queryList',
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
                console.log(result.bMarkList);
                var html = '';
                for (var i = 0; i < result.bMarkList.length; i++) {
                    var timeHtml = getTimeAgo((result.bMarkList[i].oprTime));
                    html += '<li>\n' +
                        '                                                <!-- checkbox -->\n' +
                        '                                                <div  class="icheck-primary d-inline ml-2">\n' +
                        '                                                    <input type="checkbox" value="' + result.bMarkList[i].id + '" name="todo' + result.bMarkList[i].id + '" id="todo' + result.bMarkList[i].id + '">\n' +
                        '                                                    <label for="todo' + result.bMarkList[i].id + '"></label>\n' +
                        '                                                </div>\n' +
                        '                                                <span class="text">' + result.bMarkList[i].title + '</span>\n' +
                        '                                                <!-- Emphasis label -->\n';
                    html += timeHtml +
                        '                                                <!-- General tools such as edit or delete-->\n' +
                        '                                                <div class="tools">\n' +
                        '                                                    <i class="fas fa-edit"  id="' + result.bMarkList[i].id + '"></i>\n' +
                        '                                                    <i class="fas icon-trash" id="' + result.bMarkList[i].id + '"></i>\n' +
                        '                                                </div>\n' +
                        '                                            </li>';
                }
                $('#listBookMarks').html(html);
                const swalWithBootstrapButtons = Swal.mixin({
                    customClass: {
                        confirmButton: 'btn btn-success',
                        cancelButton: 'btn btn-danger'
                    },
                    buttonsStyling: false
                });
                /*删除按钮*/
                $('.icon-trash').bind("click", function () {
                    var id = $(this).prop("id");
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You won't be able to revert this!",
                        type: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, delete it!',
                        cancelButtonText: 'No, cancel!',
                        reverseButtons: true
                    }).then((result) => {
                        console.log(result)
                        if (result.value) {
                            $.ajax({
                                url: "/api/bookmark/delete",
                                type: 'POST',
                                beforeSend: function (xhr) {
                                    xhr.setRequestHeader("token", token);
                                },
                                headers: {
                                    Accept: "application/json; charset=utf-8",
                                    "token": token
                                },
                                data: {
                                    "id": id
                                },
                                success: function (result) {
                                    if(result.success == 1){
                                        swalWithBootstrapButtons.fire(
                                            'Deleted!',
                                            '删除成功！',
                                            'success'
                                        );
                                        initItemPage();
                                    }else {
                                        swalWithBootstrapButtons.fire(
                                            'Deleted!',
                                            'Your file  deleted fail.',
                                            'error'
                                        );
                                    }
                                }
                            });
                        } else if (
                            result.dismiss === Swal.DismissReason.cancel
                        ) {
                            swalWithBootstrapButtons.fire(
                                'Cancelled',
                                '您已取消了删除操作!',
                                'error'
                            )
                        }
                    })
                });
                /*修改按钮*/
                $('.fa-edit').bind('click', function () {
                    var id = $(this).prop("id");
                    Swal.fire({
                        title: '请输入您想要重新记录的内容',
                        input: 'text',
                        inputAttributes: {
                            autocapitalize: 'off'
                        },
                        showCancelButton: true,
                        confirmButtonText: 'OK',
                    }).then((result) => {
                        console.log(result);
                        if (result.value != '') {
                            $.ajax({
                                url: "/api/bookmark/update",
                                type: 'POST',
                                beforeSend: function (xhr) {
                                    xhr.setRequestHeader("token", token);
                                },
                                headers: {
                                    Accept: "application/json; charset=utf-8",
                                    "token": token
                                },
                                data: {
                                    "title": result.value,
                                    "id":id
                                },
                                success: function (result) {
                                    if (result) {
                                        console.log('标签修改成功！');
                                        initItemPage();
                                    } else {
                                        Swal.fire(
                                            'Warning!',
                                            '添加失败',
                                            'error'
                                        )
                                    }
                                }

                            })
                        } else {
                            Swal.fire(
                                'Warning!',
                                '输入内容不能为空！，请重新选择',
                                'warning'
                            )
                        }
                    })
                })
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        });
    };
    initItemPage();
    $('#addList').bind('click', function () {
        Swal.fire({
            title: '请记录下你的所想所做',
            input: 'text',
            inputAttributes: {
                autocapitalize: 'off'
            },
            showCancelButton: true,
            confirmButtonText: 'OK',
        }).then((result) => {
            console.log(result);
            if (result.value != '') {
                $.ajax({
                    url: "/api/bookmark/add",
                    type: 'POST',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {
                        Accept: "application/json; charset=utf-8",
                        "token": token
                    },
                    data: {
                        "title": result.value
                    },
                    success: function (result) {
                        if (result) {
                            console.log('标签写入成功！');
                            initItemPage();
                        } else {
                            Swal.fire(
                                'Warning!',
                                '添加失败',
                                'error'
                            )
                        }
                    }

                })
            } else {
                Swal.fire(
                    'Warning!',
                    '输入内容不能为空！，请重新选择',
                    'warning'
                )
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
