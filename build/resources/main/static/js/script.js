function loadListImage(productId) {
    $.ajax({
        url:"/ProductImage/ListImage/" + productId,
        data : {
            productId : productId
        },
        type:"POST",
        dataType:"json",
        contentType: "application/json",
        success : function (data) {
            if (data.success === true){
                $("#block-image").empty();
                var list = data.data;
                listImage = data.data;
                var length = list.length;
                for (var i = 0 ; i < length ; i++){
                    var div = $("<div></div>");
                    div.addClass("col-sm-3 mx-15px edit-image");
                    div.attr("id",list[i].id);

                    var image=$("<img>")
                    image.attr("src",list[i].link);
                    image.attr("width","100%");
                    image.attr("id",list[i].id);
                    image.attr("onclick","myFunction(this)");
                    image.attr("class","image-product-2");
                    image.addClass("mx-auto");
                    image.attr("height","100px");
                    image.attr("width","auto");

                    // div.append(span);
                    div.append(image);
                    $("#block-image").append(div);
                }
            }
            else {
                alert(data.message);
            }

        }.bind(this),
        error: function (e) {
            console.log(e);

        }
    });
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    }

    var delete_cookie = function(name) {
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    };

    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    }

    function checkCookie() {
        var user = getCookie("username");
        if (user != "") {
            alert("Welcome again " + user);
        } else {
            user = prompt("Please enter your name:", "");
            if (user != "" && user != null) {
                setCookie("username", user, 365);
            }
        }
    }

    function custom_alert( message, title ) {
        if ( !title )
            title = 'Alert';

        if ( !message )
            message = 'No Message to Display.';

        $('<div></div>').html( message ).dialog({
            title: title,
            resizable: false,
            modal: true,
            buttons: {
                'Ok': function()  {
                    $( this ).dialog( 'close' );
                }
            }
        });
    }

    function myFuncDel(obj) {
        console.log("del: ",obj);
    }
}
$('.changeRole').on('change',function () {
    var id = $(this).data('id');
    var role = parseInt($(this).val());
    var data = {};
    data.id = $(this).data('id');
    data.roleId = $(this).val();
    console.log("data: ", data);
    $.ajax({
        url: "/api/user/changeRole",
        data: JSON.stringify(data),
        type: "POST",
        crossDomain: true,
        contentType: "application/json",
        dataType: 'json',
        success: function (data) {
            if (data.success === true) {
                swal({
                    title: 'Success',
                    text: data.message,
                    type: 'success',
                    timer: 1500
                });
            }
            else {
                swal({
                    title: 'Fail!',
                    text: data.message,
                    type: 'error',
                    timer: 1500
                }).then(function () {
                    window.location.reload();
                });
            }
        }.bind(this),
        error: function (e) {
            console.log(e);
        }
    });
});

$('.changeStatus').on('change',function () {
    var id = $(this).data('id');
    var status = parseInt($(this).val());
    var data = {};
    data.id=$(this).data('id');
    data.status = $(this).val();
    console.log("data : ",data);
    $.ajax({
        url:"/order/changeStatus",
        data: JSON.stringify(data),
        type: "POST",
        crossDomain: true,
        contentType: "application/json",
        dataType: 'json',
        success: function (data) {
            if (data.success === true){
                swal({
                    title: 'Success',
                    text : data.message,
                    type : 'success',
                    timer: 1500
                });
            }
            else {
                swal({
                    title : 'Error',
                    text : data.message,
                    type: 'error',
                    timer: 1500

                }).then(function () {
                    window.location.reload();

                });
            }

        }.bind(this),
        error: function (e) {
            console.log(e);

        }
    });

});

$('#selectDeliveryStatus').on('change',function () {
    $('#myFormOrderList').trigger('submit');

})
$('.delUser').on('click', function(){
    var id=$(this).data('id');
    var data = {};
    data.id = id;
    console.log("data: ", data);
    swal({
        title: 'Xóa tài khoản?',
        text: "Bạn sẽ không có khả năng khôi phục!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Xóa',
        cancelButtonText:'Hủy'
    }).then(function(result)  {
        if (result.value) {
            NProgress.start();
            var linkGet = "/api/user/delete/"+id;
            axios.get(linkGet).then(function(res){
                NProgress.done();
                if(res.data.success) {
                    swal(
                        {
                            title:'Success',
                            text:res.data.message,
                            type:'success',
                            showCancelButton: false,
                            timer:1500
                        }
                    ).then(function() {
                        location.reload();
                    });
                } else {
                    swal(
                        'Fail',
                        res.data.message,
                        'error'
                    );
                }
            }, function(err){
                NProgress.done();
                swal(
                    'Thất bại',
                    'Xảy ra lỗi',
                    'error'
                );
            });
        }
    });

});