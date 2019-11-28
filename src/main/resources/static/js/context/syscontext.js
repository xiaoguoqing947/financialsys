$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
   var initSysContext = function () {
       $.ajax({
           url:'/api/sysmonitor/queryUserList',
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
                    for(var i = 0; i < result.userList.length; i++){
                        if (result.userList[i].power == '0') {
                            result.userList[i].power = '普通用户';
                        } else {
                            result.userList[i].power = '管理员';
                        }
                        if (result.userList[i].sex == '0') {
                            result.userList[i].sex = '男';
                        } else if(result.userList[i].sex == '1'){
                            result.userList[i].sex = '女';
                        }else{
                            result.userList[i].sex = '#';
                        }
                    }
                    var html='';
                    $.each(result.userList, function (i, item) {
                        html+='<div class="col-12 col-sm-6 col-md-4 d-flex align-items-stretch">\n' +
                            '                    <div class="card bg-light">\n' +
                            '                        <div class="card-header text-muted border-bottom-0">'+item.power;
                        html+='</div>\n' +
                            '                        <div class="card-body pt-0">\n' +
                            '                            <div class="row">\n' +
                            '                                <div class="col-7">\n' +
                            '                                    <h2 class="lead"><b>'+item.name+'</b></h2>\n' +
                            '                                    <p class="text-muted text-sm"><b>About: </b> 性别：'+item.sex+' / 邮箱：'+item.email+' / 出生日期：'+item.born+'</p>';
                        html+='<ul class="ml-4 mb-0 fa-ul text-muted">\n' +
                            '                                        <li class="small"><span class="fa-li"><i\n' +
                            '                                                class="fas fa-lg fa-building"></i></span> Address:'+ item.address +'</li>';
                        html+='<li class="small"><span class="fa-li"><i class="fas fa-lg fa-phone"></i></span>\n' +item.contactNumber+
                            '                                        </li> </ul>\n' +
                            '                                </div>';
                        html+='<div class="col-5 text-center">\n' +
                            '                                    <img src="'+item.pic+'" alt="" class="img-circle img-fluid">\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </div>';
                        html+='<div class="card-footer">\n' +
                            '                            <div class="text-right">\n' +
                            '                                <a href="javascript:void(0)" onclick="detailMes(\''+item.username+'\')" class="btn btn-sm btn-primary">\n' +
                            '                                    <i class="fas fa-user"></i> 详细信息\n' +
                            '                                </a>\n' +
                            '                            </div>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </div>';
                    });
                    $('#userContext').html(html);
                }
           },
           error: function (jqXHR, textStatus, errorThrown) {
               alert("jqXHR.responseText=" + jqXHR.responseText);
               alert("jqXHR.statusText=" + jqXHR.statusText);
               alert("jqXHR.status=" + jqXHR.status)
           }
       })
   };
    initSysContext();
});

//初始化详情
function detailMes(id) {
    var token = $.zui.store.get("token");//Token值
    var url = '/api/user/detail';
    console.log(id)
    var param = 'username='+id;//请求到列表页面
    $.ajax({
        url:url,
        type:'post',
        dataType:'JSON',
        beforeSend:function(xhr){//设置请求头信息
            xhr.setRequestHeader("token",token);
        },
        headers:{'token':token},
        data:param,
        success:function(result){
            if (result.success=='1'){
                $('#detailModal').modal('show', 'fit');
                if (result.user.power == '0') {
                    result.user.power = '普通用户';
                } else {
                    result.user.power = '管理员';
                }
                if (result.user.sex == '0') {
                    result.user.sex = '男';
                } else if(result.user.sex == '1'){
                    result.user.sex = '女';
                }else{
                    result.user.sex = '#';
                }
                var html='<img src="'+result.user.pic+'" height="60" width="60"  />'
                $("#detailHeadPic").html(html);
                $("#detailUName").html(result.user.username);
                $("#detailName").html(result.user.name);
                $("#detailRTime").html(result.user.registData);
                $("#detailEmail").html(result.user.email);
                $("#detailPNumber").html(result.user.contactNumber);
                $("#detailBorn").html(result.user.born);
                $("#detailSex").html(result.user.sex);
                $("#detailPower").html(result.user.power);
                $("#detailAddress").html(result.user.address);

            }
        },
        error:function(e){
            new $.zui.Messager('系统繁忙,请稍候再试!', {
                type: 'warning',
                placement:'center'
            }).show();
        }
    });
}