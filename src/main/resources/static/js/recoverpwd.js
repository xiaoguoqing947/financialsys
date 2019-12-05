$.validator.addMethod(
    //规则的名称
    "validatePwd",
    //校验函数
    //value:输入的内容,element:被校验的元素对象,params:规则对应的参数值
    function(value,element,params){
        var flag = false;
        var oldPwd=md5(value);
        $.ajax({
            "async":false,
            "url":"/api/validPwd",
            "data":{"oldPwd":oldPwd},
            "type":"post",
            "dataType":"json",
            "success":function(data){
                flag = data;
            }
        });
        //返回false代表检验器不通过
        return flag;
    }
,"密码不正确，请重新输入！");
$("#recoverForm").validate({
// debug: true,//表单调试时使用
    submitHandler: function (form) {
        var pwd = md5($('#pwd').val());
        $.ajax({
            "async":false,
            "url":"/api/recoverPwd",
            "type":"post",
            "dataType":"json",
            "data": {"pwd": pwd},
            "success":function(data){
                if (data.status == 'success') {
                    alert('修改成功');
                    // location.href='/api/sysadmin';
                } else if (data.status == 'fail') {
                    alert('修改失败')
                }
            }
        });
    },
    rules: {
        oldPwd: {
            required: true,
            validatePwd: true
        },
        pwd: {
            required: true,
            minlength: 6
        },
        confirmPwd: {
            required: true,
            equalTo: "#pwd",
            minlength: 6
        }
    },
    messages: {
        oldPwd: {
            required: "<span style='color: red'>*</span>密码不为空<span style='color: red'>*</span>",
        },
        pwd: {
            required: "<span style='color: red'>*</span>密码不为空<span style='color: red'>*</span>",
            minlength: "密码长度不能小于 6 个字母",
        },
        confirmPwd: {
            required: "<span style='color: red'>*</span>密码不为空<span style='color: red'>*</span>",
            minlength: "密码长度不能小于 6 个字母",
            equalTo: "<span style='color: red'>*</span>两次密码输入不一致<span style='color: red'>*</span>"
        }
    }
});
