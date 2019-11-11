$(document).ready(function () {
    /*
    服务器重启后session会清空，而存在zui中的token不会清空*/
    $.zui.store.clear();
    var token = $.zui.store.get('token');
    var power = $.zui.store.get('power');
    if (typeof (token) != 'undefined' && token != '' && typeof (power) != 'undefined' && power != '') {
        if (power == 0) {
            window.location.href = "/api/admin";
        } else if (power == 1) {
            location.href = "/api/sysadmin";
        }
    }
    $("#login-admin").bind("click",
        function () {
            var username = $("#username").val();
            var passBefore = $("#passBefore").val();
            if (username == '') {
                $('#username').tooltip("show", '请输入用户名');
            } else if (passBefore == '') {
                $('#passBefore').tooltip("show", '请输入密码');
            } else {
                var b = document.forms[0];
                b = document.loginForm.passBefore.value,
                    document.loginForm.password.value = md5(b);
                var password = $('#password').val();
                $.ajax(
                    {
                        url: "/login.action",
                        type: 'post',
                        dataType: 'JSON',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            password: password,
                            username: username
                        }),
                        success: function (data) {
                            if (data.status == 'success') {
                                $.zui.store.set('token', data.token);
                                $.zui.store.set('power', data.power);
                                /*    window.location.href='/demo/testPathVariable/1/2';//测试使用*/
                                if (data.power == 0) {
                                    location.href = "/api/admin";
                                } else if (data.power == 1) {
                                    location.href = "/api/sysadmin";
                                }
                            } else {
                                alert('账号或密码错误，请重新输入');
                                window.location.href = '/login?result=fail';
                            }
                        }
                    }
                )
            }
        });
    $("#regist-admin").bind("click", function () {
        window.location.href = "/register";
    });
    $("#registForm").validate({
        // debug: true,//表单调试时使用
        submitHandler: function (form) {
            var password = md5($('#password').val());
            var username = $('#username').val();
            var email = $('#email').val();
            $.ajax({
                url: "/register.action",
                type: 'post',
                dataType: 'JSON',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    password: password,
                    username: username,
                    email: email,
                    registDate: new Date()
                }),
                success: function (data) {
                    console.log(data);
                    if (data.status == 'success') {
                        alert("恭喜您，注册成功");
                        window.location.href = '/login';
                    } else if (data.status == 'fail') {
                        alert("注册失败");
                        window.location.href = '/register';
                    }
                }
            });
        },
        rules: {
            username: {
                required: true,
                remote: {
                    url: "/validateUname.action",
                    type: 'post',
                    dataType: "json",
                    data: {
                        username: function () {
                            return $("#username").val();
                        }
                    }
                }
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 6,
            },
            confirm_password: {
                required: true,
                equalTo: "#password",
            }
        },
        messages: {
            username: {
                required: "<span style='color: red'>*</span>",
                remote: "<span style='color: red'>*</span>用户名已经存在<span style='color: red'>*</span>"
            },
            email: {
                required: "<span style='color: red'>*</span>",
                email: "E-Mail格式不正确"
            },
            password: {
                required: "<span style='color: red'>*</span>密码不为空<span style='color: red'>*</span>",
                minlength: "密码长度不能小于 6 个字母",
                // regex:"密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在6-16之间"
            },
            confirm_password: {
                required: "<span style='color: red'>*</span>密码不为空<span style='color: red'>*</span>",
                minlength: "密码长度不能小于 6 个字母",
                // regex:"密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在6-16之间",
                equalTo: "<span style='color: red'>*</span>两次密码输入不一致<span style='color: red'>*</span>"
            }
        }
    });
});