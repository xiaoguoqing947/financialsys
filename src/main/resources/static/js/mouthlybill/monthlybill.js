$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var initPage = function () {
        $.ajax({
            url: '/api/Statistics/monthlybill',
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
                console.log(result.dataTable.joyExpPrice+'---'+result.dataTable.shopExpPrice+'---'+result.dataTable.totalExpPrice)
                $('#shopprice').text(result.dataTable.shopExpPrice+'元');
                $('#joyprice').text(result.dataTable.joyExpPrice+'元');
                var otherprice=result.dataTable.totalExpPrice-result.dataTable.shopExpPrice-result.dataTable.joyExpPrice;
                $('#otherprice').text(otherprice+'元');
                $('#totalprice').text(result.dataTable.totalExpPrice+'元');
                var date = new Date();
                $('#time').text((date .getMonth()+1)+'/'+date .getFullYear());
                $('#month').text((date .getMonth()+1));

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        })
    }
    initPage();
});