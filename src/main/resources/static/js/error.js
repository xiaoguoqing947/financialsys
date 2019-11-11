$(document).ready(function () {
    $("#submit").bind("click",function () {
       var search=$("#search").val();
       if(search == ''){
           $('#search').tooltip("show", '请输请求路径');
       }else{
           location.href=localSystemUrl+search;
       }
    });
});