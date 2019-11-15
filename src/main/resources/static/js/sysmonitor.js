function loginout() {
    $.zui.store.clear(); // 清空所有本地存储的条目
    location.href = "/api/logout";
}
function reloadMainRight() {
    alert('ok');
}