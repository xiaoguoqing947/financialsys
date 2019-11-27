function loginout() {
    $.zui.store.clear(); // 清空所有本地存储的条目
    location.href = "/api/logout";
}
window.onload=function(){
//要初始化的东西
    $("#context").empty();
    $("#context").load('/pages/usercontext.html');
}
$(document).ready(function () {
    var token = $.zui.store.get("token");
    $.ajax({
        url: "/api/monitor",
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
            if (result.status == 'success') {
                var html = '';
                for (var i = 0; i < result.menuList.length; i++) {
                    if (result.menuList[i].parentId == 0) {
                        html+='<li class="nav-item has-treeview">\n' +
                            '            <a href="#" class="nav-link active">\n' +
                            '              <i class="nav-icon fas '+result.menuList[i].icon+'"></i>\n' +
                            '              <p>';
                        html+='<p>'+result.menuList[i].name+'</p>';
                        html+='<i class="right fas fa-angle-left"></i>\n' +
                            '              </p>\n' +
                            '            </a>';
                        for (var j = 0; j < result.menuList.length; j++) {
                            if(result.menuList[j].priority == result.menuList[i].id){
                                html+='<ul class="nav nav-treeview">\n' +
                                    '              <li class="nav-item" a href="javascript:void(0)" onclick="reloadMainRight('+result.menuList[j].url+')">\n' +
                                    '                <a href="#" class="nav-link active">\n' +
                                    '                  <i class="far fa-circle nav-icon"></i>';
                                html+='<p>'+result.menuList[j].name+'</p>';
                                html+='</a>\n' +
                                    '              </li>\n' +
                                    '            </ul>';
                            }
                        }
                        html+='</li>';
                    }
                }
                document.getElementById("menuId").innerHTML = html;
            } else {
                new $.zui.Messager('系统繁忙', {
                    type: 'warning', // 定义颜色主题
                    placement: 'center' // 定义显示位置
                }).show();
            }

        },
        error: function () {
            new $.zui.Messager('系统繁忙', {
                type: 'warning', // 定义颜色主题
                placement: 'center' // 定义显示位置
            }).show();
        }
    });
});

function reloadMainRight(url) {
    $("#context").empty();
    $("#context").load(url);
}

