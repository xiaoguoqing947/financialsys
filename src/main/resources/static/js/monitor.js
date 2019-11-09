function loginout() {
    $.zui.store.clear(); // 清空所有本地存储的条目
    location.href = "/logout";
}

$(document).ready(function () {
    var token = $.zui.store.get("token");
    $.ajax({
        url: "/monitor",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("token", token);
        },
        headers: {
            Accept: "application/json; charset=utf-8",
            "token": token
        },
        dataType: "JSON",
        type: "POST",
        success: function (result) {
            alert(result);
        },
        error: function () {
            new $.zui.Messager('系统繁忙', {
                type: 'warning', // 定义颜色主题
                placement: 'center' // 定义显示位置
            }).show();
        }
    });
});
