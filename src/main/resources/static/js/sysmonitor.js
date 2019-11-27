function loginout() {
    $.zui.store.clear(); // 清空所有本地存储的条目
    location.href = "/api/logout";
}
function reloadMainRight(url) {
    $("#context").empty();
    $("#context").load(url);
}

window.onload=function(){
//要初始化的东西
    $("#context").empty();
    $("#context").load('/syspages/syscontext.html');
}