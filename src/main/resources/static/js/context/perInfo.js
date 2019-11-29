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
});
